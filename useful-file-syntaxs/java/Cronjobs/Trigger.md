# Cronjob Trigger

### cron expression

| Field Name   | Mandatory | Allowed Values   | Allowed Special Characters |
| ------------ | --------- | ---------------- | -------------------------- |
| Seconds      | YES       | 0-59             | , - \* /                   |
| Minutes      | YES       | 0-59             | , - \* /                   |
| Hours        | YES       | 0-23             | , - \* /                   |
| Day of month | YES       | 31-Jan           | , - \* ? / L W             |
| Month        | YES       | 1-12 or JAN-DEC  | , - \* /                   |
| Day of week  | YES       | 1-7 or SUN-SAT   | , - \* ? / L #             |
| Year         | NO        | empty, 1970-2099 | , - \* /                   |

### Impexx

```
INSERT_UPDATE ServicelayerJob;code[unique=true];SpringId;
;sendNewsJob;sendNewsJob

# Define the cron job and the job that it wraps
INSERT_UPDATE CronJob; code[unique=true];job(code);singleExecutable;sessionLanguage(isocode)
;sendNewsCronJob;sendNewsJob;false;de

# Define the trigger that periodically invokes the cron job
INSERT_UPDATE Trigger;cronjob(code)[unique=true];cronExpression
#% afterEach: impex.getLastImportedItem().setActivationTime(new Date());
; sendNewsCronJob; 0/10 * * * * ?
```
