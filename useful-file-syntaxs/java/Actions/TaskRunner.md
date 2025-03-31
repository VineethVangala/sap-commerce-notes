```java
public class MyTaskRunner implements TaskRunner<TaskModel>
{
   public void run(TaskService taskService, TaskModel task) throws RetryLaterException
   {
      // business code goes here
   }

   public void handleError( TaskService taskService, TaskModel task, Throwable error)
   {
      // this is called if a error occurred or a scheduled action could not be executed
      // in time
   }
}
```

```xml
<bean id="MyRunner" class="MyTaskRunner" >
   <property .../>
</bean>
```

### Triggering an event

```java
TaskService taskService = ...

// trigger the event
taskService.triggerEvent( "MyEventArrived" );
```
