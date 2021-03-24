# Simple Quartz Scheduler
A Simple Spring Boot Quartz Library

## Guide
Download realease jar file and add to your project

Create class wanna create cron job that implements ***Job*** in org.quartz.Job and implement method ***execute***
Example:
```java
public class CronJobService implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
       // your code here
    }
}
```

In your service declare ***ScheduleService*** object and ***@Autowired*** your constructor with input param is ***ScheduleService*** object
Example:
```java
public class PlaygroundServices {

    private final ScheduleService scheduleService;

    @Autowired
    public PlaygroundServices(final ScheduleService scheduleService){
        this.scheduleService = scheduleService;
    }

    public void runCronJobHandler(){
        scheduleService.schedule(CronJobService.class, "0 0 12/4 1/1 * ? *");
    }

}

```
First param is class wanna schedule it must implements Job, Second param is cron job expression you can create it by this site: http://www.cronmaker.com

Copy below properties to application.properties
```bash
## Database Properties
spring.datasource.url = jdbc:postgresql://localhost:5432/yourdatabase?useSSL=false
spring.datasource.username = postgres
spring.datasource.password = postgres
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.PostgreSQLDialect
spring.quartz.properties.org.quartz.jobStore.driverDelegateClass=org.quartz.impl.jdbcjobstore.PostgreSQLDelegate

spring.quartz.job-store-type=jdbc
spring.quartz.jdbc.initialize-schema=never
```
I used postgresql if you use the different database you can searching on internet to config that

Finally you have add @ComponentScan above main application
```java
@SpringBootApplication
@ComponentScan({"com.groupid-project","com.easyquartz"})
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}

```

## Note
You have create table quartz before using it
