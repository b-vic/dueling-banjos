# dueling-banjos

Evaluation of **RestTemplate** performance compared to the new **RestClient**, **WebClient sync** (blocking) and **WebClient async** (non-blocking/subscribe).

Test involves Banjo One (above REST clients) calling Banjo Two (normal API).  Banjo Two API sleeps for a random number of milliseconds (0 to 100) to simulate real activity occuring like a database call or accessing an external system.  NOTE: This time is subtracted from the final performance results in the database at the end (NOT the jmeter results however).  This means WebClient Async JMeter time doen't include the sleep time since that happens after the initial request and before the response.

The test plan consists of 200 users (threads) of each type of client running concurrently.  In order to reduce overloading the JVMs, think times were introduced which is more realistic.  Maximum available ports needed to be adjusted to run on Windows (Intel Core Ultra 5 1.3 GHz with 16GB RAM).

See JMeter Test Suite under src/test/resources to try it out!

Final results are saved to H2 database since WebClient Async is a callback not a blocking thread, so its actual time taken is not known by JMeter, only in the code.

## Observations:

- RestClient and WebClient (sync) were operating nearly identically throughout the test - had to check the code wasn't the same by mistake!
- WebClient (async) finished it's 1,000,000 async requests while the others were still at 700,000
- RestTemplate had roughly 50K more requests still to run when RestClient/WebClient(sync) finished

### JMeter Results:

<img width="946" alt="JMeter-results" src="https://github.com/user-attachments/assets/d2380517-b2a3-4b3d-b7fb-8735149e6879" />


### SQL results (include random sleep time subtracted - only known at runtime):

<img alt="H2-results" src="https://github.com/user-attachments/assets/a04554a1-6788-4000-b4ce-ed6272e1c0b1" />
