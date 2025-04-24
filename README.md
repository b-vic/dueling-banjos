# dueling-banjos: Spring REST client comparison

Evaluation of **RestTemplate** performance compared to Spring's new **RestClient**.  Also comparing it to WebFlux **WebClient sync** (blocking) and **WebClient async** (non-blocking/subscribe).

Test involves _Banjo One_ (above 4 REST clients) calling _Banjo Two_ (a simple API).  The Banjo Two API sleeps for a random number of milliseconds (1 to 50) to simulate real activity occurring like a database call or accessing an external system.  

NOTE: This time is subtracted from the final performance results at the end of this report (NOT the JMeter results as the sleep time is not known by JMeter since its internal).  This means WebClient Async JMeter time doesn't include the second API's sleep time since that happens after the initial request and _before_ the final response is sent (subscribe).  For the other 3, the time is inclusive in JMeter.  SQL times exclude any sleep time.

The test plan consists of 200 users (threads) of each of the 4 types of clients running 1,000,000 calls.  In order to reduce overloading the JVMs, think times of 100-110ms between requests were introduced to be more realistic.  Scaling or throttling would be required without this, particularly for the callback scenario as it overloads the JVM threads.

Restart of JVM after each of the million calls with 10 warmup calls (excluded from results) to allow like for like baseline.

Maximum available ports needed to be adjusted to run on Windows 11 with specs: Intel Core Ultra 5 (12 core) 1.3 GHz with 16GB RAM.  Additionally, timed wait on the ports was lowered from 2 minutes to 30 seconds to resolve connection issues.

See JMeter Test Suite under src/test/resources to try it out!

Final results are saved to H2 database since WebClient Async is a callback not a blocking thread, so its actual time taken is not known by JMeter, only in the code at runtime after substracting the random JVM 2 sleep time.

## Observations:

- RestClient and WebClient (sync) were operating nearly identically throughout every version of the test - had to check the test wasn't misconfigured by mistake!
- WebClient (async) has a significantly improved overall throughput but a similar per call time when compared to RestClient / WebClientSync
- RestTemplate is starting to show its age, but still was in keepign with the average results
- Recommendation is to upgrade to RestClient if you are in the Spring 6 version

### SQL results (include random sleep time subtracted - only known at runtime):

| Call Client    | Average | Median | 90th Percentile | 95th Percentile | 99th Percentile | Total Calls | Test Duration |
|----------------|---------|--------|-----------------|-----------------|-----------------|-------------|---------------|
| RestTemplate   | 9.10    | 8.99   | 16.36           | 17.33           | 18.92           | 1,000,000   | 12:40.528464  |
| RestClient     | 1.60    | 1.31   | 2.59            | 3.08            | 3.87            | 1,000,000   | 11:33.803078  |
| WebClientSync  | 1.61    | 1.35   | 2.59            | 3.07            | 3.93            | 1,000,000   | 11:33.711818  |
| WebClientAsync | 1.62    | 1.42   | 2.56            | 3.04            | 3.91            | 1,000,000   | 09:19.613708  |

### JMeter Results:

| Label          | Avg | Med | 90% Line | 95% Line | 99% Line | Min | Max | Error % | Thru-put | Recv KB/s | Sent KB/s |
|----------------|-----|-----|----------|----------|----------|-----|-----|---------|----------|-----------|-----------|
| RestTemplate   | 34  | 35  | 55       | 59       | 64       | 2   | 83  | 0.00%   | 1314.806 | 251.46    | 165.64    |
| RestClient     | 27  | 27  | 47       | 49       | 51       | 1   | 76  | 0.00%   | 1441.242 | 276.26    | 178.75    |
| WebClientSync  | 27  | 27  | 47       | 49       | 51       | 1   | 71  | 0.00%   | 1441.379 | 280.52    | 182.99    |
| WebClientAsync | 0   | 0   | 1        | 1        | 1        | 0   | 23  | 0.00%   | 1787.000 | 325.83    | 228.61    |
| TOTAL          | 22  | 21  | 48       | 51       | 60       | 0   | 83  | 0.00%   | 1308.721 | 248.62    | 165.19    |

SQL query for results:

```
SELECT type, 
    ROUND(avg(response_time),2) as Avg, 
    ROUND(PERCENTILE_CONT(0.50) WITHIN GROUP(ORDER BY response_time),2) as Median,
    ROUND(PERCENTILE_CONT(0.90) WITHIN GROUP(ORDER BY response_time),2) as Perc90th,  
    ROUND(PERCENTILE_CONT(0.95) WITHIN GROUP(ORDER BY response_time),2) as Perc95th, 
    ROUND(PERCENTILE_CONT(0.99) WITHIN GROUP(ORDER BY response_time),2) as Perc99th, count(1) as TotalCalls,
    MAX(CREATION_TIME) - MIN(CREATION_TIME) as TestDuration
FROM perf_result 
GROUP BY type;
```
   
