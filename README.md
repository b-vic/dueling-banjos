# dueling-banjos

Evaluation of RestTemplate performance compared to the new RestClient, WebClient sync (blocking) and WebClient async (non-blocking/subscribe).

Test involves Banjo One calling Banjo Two.  Banjo Two sleeps for a random number of milliseconds (0 to 100) to simulate real activity.  This time is subtracted from the final performance results (NOT the jmeter results however).

See JMeter Test Suite under src/test/resources to try it out!

Test consists of 200 users (threads) of each type of client running concurrently.  In order to reduce overloading the JVMs, think times were introduced which is more realistic.

Final results are saved to H2 db since WebClient Async is a callback not a blocking thread, so its time taken is not known by JMeter.

Observations:

- RestClient and WebClient (sync) were operating nearly identically throughout the test
- WebClient (async) finished 1MM requests while the others were still at 700K
- RestTemplate had roughly 50K more requests to run when RestClient/WebClient (sync) finished

JMeter Results:


SQL results (include random sleep time subtracted - only known at runtime):

