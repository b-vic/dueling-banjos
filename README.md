# dueling-banjos

Evaluation of **RestTemplate** performance compared to Spring's new **RestClient**.  Also comparing it to WebFlux **WebClient sync** (blocking) and **WebClient async** (non-blocking/subscribe).

Test involves Banjo One (above 4 REST clients) calling Banjo Two (just a normal API).  Banjo Two API sleeps for a random number of milliseconds (0 to 100) to simulate real activity occuring like a database call or accessing an external system.  

NOTE: This time is subtracted from the final performance results at the end of this report (NOT the jmeter results however as the sleep time is not known by jmeter).  This means WebClient Async JMeter time doesn't include the second API's sleep time since that happens after the initial request and before the final response (subscribe).  For the other 3, the time is inclusive.

The test plan consists of 200 users (threads) of each of the 4 types of clients running concurrently.  In order to reduce overloading the JVMs, think times of 100ms between requests were introduced to be more realistic.  Scaling or throttling would be required without this, particularly for the callback threading.

Maximum available ports needed to be adjusted to run on Windows 11 with specs: Intel Core Ultra 5 1.3 GHz with 16GB RAM.

See JMeter Test Suite under src/test/resources to try it out!

Final results are saved to H2 database since WebClient Async is a callback not a blocking thread, so its actual time taken is not known by JMeter, only in the code at runtime after substracting the random JVM 2 sleep time.

## Observations:

- RestClient and WebClient (sync) were operating nearly identically throughout the entire test - had to check the test wasn't misconfigured by mistake!
- WebClient (async) finished it's 1,000,000 async requests while the others were still reaching 700,000 so roughly 30% improved throughput, but see final results before celebrating
- RestTemplate had roughly 50K more requests still to run when RestClient/WebClient(sync) had finished

### JMeter Results:

<img width="946" alt="JMeter-results" src="https://github.com/user-attachments/assets/d2380517-b2a3-4b3d-b7fb-8735149e6879" />


### SQL results (include random sleep time subtracted - only known at runtime):

<img alt="H2-results" src="https://github.com/user-attachments/assets/a04554a1-6788-4000-b4ce-ed6272e1c0b1" />
