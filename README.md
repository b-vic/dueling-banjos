# dueling-banjos

## Purpose

Evaluation of **RestTemplate** performance compared to the new **RestClient** as well as **WebClient** (both sync and async).

See JMeter Test Suite under src/test/resources to try it out!  Banjo One calls Banjo Two in an API call and Two sleeps for a random amount of 0 to 10 milliseconds (subtracted from result time).

_Note_: currently WebClient Async cannot run the same 100 concurrent user load as the other three due to insufficient scaling of the VMs - banjo one and two.  Some throttling is needed so banjo one doesn't overload banjo two (or itself).

Results are saved to H2 db since WebClient Async is a callback not a blocking thread:

select avg(response), type from result group by type;

> RESPONSE (ms)  	TYPE
> --------------------------------- 
> 13.50	          RestTemplateBanjo
> 
> 3.863	          RestClientBanjo
> 
> 2.937          	WebClientBanjoSync
> 
> 1.310	          WebClientBanjoAsync
> 
