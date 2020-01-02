# Spring-Cloud-Microservices
Cloud config

- [Router and Filter: Zuul](https://cloud.spring.io/spring-cloud-netflix/multi/multi__router_and_filter_zuul.html#_custom_zuul_filter_examples)
- [Spring Boot and Spring Cloud and OAuth2](https://itnext.io/microservices-with-spring-boot-and-spring-cloud-16d2c056ba12)

```
ribbonTimeout = (ribbon.ConnectTimeout + ribbon.ReadTimeout) * (ribbon.MaxAutoRetries + 1) * (ribbon.MaxAutoRetriesNextServer + 1);
// ...
if(hystrixTimeout < ribbonTimeout) {
        LOGGER.warn("The Hystrix timeout of " + hystrixTimeout + "ms for the command " + commandKey +
            " is set lower than the combination of the Ribbon read and connect timeout, " + ribbonTimeout + "ms.");
    }
```

