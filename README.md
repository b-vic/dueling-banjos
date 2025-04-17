# dueling-banjos

Evaluation of RestTemplate performance compared to the new RestClient as well as WebClient (both sync and async).

See JMeter Test Suite under src/test/resources to try it out!

Note: currently WebClient Async cannot run the same 100 concurrent user load as the other three due to insufficient scaling of the VMs - banjo one and two.  Some throttling is needed to one doesn't overload two.

Results are saved to H2 db since WebClient Async is a callback not a blocking thread:

select avg(response), type from result group by type;

