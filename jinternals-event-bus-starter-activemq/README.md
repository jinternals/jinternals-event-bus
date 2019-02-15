# jinternals-event-bus-starter-activemq


>#### Maven Dependency:
```xml
<dependency>
    <artifactId>jinternals-event-bus-starter-activemq</artifactId>
    <groupId>com.jinternals.event.bus</groupId>
    <version>${latest-version}</version>
</dependency>
```

>#### Application Configuration:
```yaml
event:
  bus:
    activemq:
      listener-package: <listener-package-name>
      event-package: <event-package-name>
      broker:
        url: tcp://localhost:61616
        user-name: <user-name>
        password: <password>      
      producer:
        enabled: false
      consumer:
        destination: Event.Bus.Demo
        concurrency: 1-1
```



## ActiveMQ configuration setup: 

>#### DLQ Configuration: 
```
<policyEntry queue=">">
  <deadLetterStrategy>
    <individualDeadLetterStrategy queuePrefix="DLQ." useQueueForQueueMessages="true"/>
  </deadLetterStrategy>
</policyEntry>
```

>#### Destination Configuration:

```xml
 <destinationInterceptors>
    <virtualDestinationInterceptor>
        <virtualDestinations>
            <compositeQueue name="Event.Bus">
              <forwardTo>
                <queue physicalName="Event.Bus.{name}" />
              </forwardTo>
            </compositeQueue>
        </virtualDestinations>
    </virtualDestinationInterceptor>
</destinationInterceptors>
```