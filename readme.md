# How To test spring boot application

You find find tests for:

- Repositories
- Services
- Controllers

### Sonarqube 

If you want to integrate your project into sonarqube, just follow the instructions below :

- create a sonarqube instance on docker

```
docker run -d --name sonarqube -p 9000:9000 sonarqube:7.5-community
```

- Go to http://localhost:9000 , login with admin/admin and generate a token
- Execute from the root of your project

```
 mvn sonar:sonar \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=your-token-here
```

### Useful links

https://docs.spring.io/spring-boot/docs/1.5.2.RELEASE/reference/html/boot-features-testing.html
https://www.baeldung.com/java-spring-mockito-mock-mockbean
https://www.baeldung.com/spring-boot-testing
