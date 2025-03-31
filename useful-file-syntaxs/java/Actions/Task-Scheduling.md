# Task Scehduling

### Time-based actions

```java
ModelService modeService = ...
TaskService taskService = ...

// create model
TaskModel task = modelService.create(TaskModel.class);

// configure it
task.setRunnerBean("MyRunner"); // the action bean name
task.setExecutionDate( new Date() );  // the execution time - here asap

// schedule
taskService.scheduleTask(task);
```

### Event-based scheduling

```java
// create models
TaskModel task = modelService.create(TaskModel.class);
TaskConditionModel cond = modelService.create(TaskConditionModel.class);

// configure them
task.setRunnerBean("MyRunner");
// define event name
cond.setUniqueID("MyEventArrived");
// add to task
task.setConditions( Collections.singleton( cond ) );

// schedule
taskService.scheduleTask(task);
```
