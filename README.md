# Edge Clients
This is a compilation of various client libraries and such. The goal with many of these libraries is to establish a minimal set of functionality with a minimum of dependencies. Typically, [Spring](http://projects.spring.io/spring-framework/) and [Google Guava](https://code.google.com/p/guava-libraries/), although there are a few others that are BYU-created dependencies.

## Modules

### apikey-client
This is mostly a database-connected api-key client. It can look up just about any api key and use it. Mostly created for testing. There is a CLI. This was built on top of the [auth-client](#auth-client).

### auth-client