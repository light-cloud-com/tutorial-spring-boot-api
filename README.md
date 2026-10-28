# tutorial-spring-boot-api

[![Deploy to Light Cloud](https://light-cloud.com/button.svg)](https://console.light-cloud.com/new?repo=https://github.com/light-cloud-com/tutorial-spring-boot-api)

A small Spring Boot 4 API used in the Light Cloud tutorial
[Spring Boot on Light Cloud: JAR, port and memory settings](https://blog.light-cloud.com/tutorials/deploy-a-spring-boot-app).

```mermaid
flowchart LR
  push[git push] --> build[./gradlew build]
  build --> jar[runnable JAR]
  jar -->|java -jar, SERVER_PORT| app[Spring Boot app]
  app --> health[/actuator/health/]
  app --> tasks[/tasks/]
```

## Run it

```sh
./gradlew bootRun
curl localhost:8080/actuator/health
```
