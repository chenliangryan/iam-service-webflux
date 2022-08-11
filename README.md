# IAM Sample


## Local Development

```bash
yb-ctl destroy && yb-ctl --rf 3 create --tserver_flags="cql_nodelist_refresh_interval_secs=10" --master_flags="tserver_unresponsive_timeout_ms=10000"
ysqlsh -c 'create database iam'
ysqlsh -d iam -f src/main/resources/schema.sql
ysqlsh -d iam -f src/main/resources/data-user_profile.sql
ysqlsh -d iam -f src/main/resources/data-user_credentials.sql
ysqlsh -d iam -f src/main/resources/data-user_svc_account.sql
ysqlsh -d iam -f src/main/resources/data-user_audit.sql

./mvnw clean
./mvnw spring-boot:run

```

Run *On Terminal 2*

```bash
curl "http://localhost:8081/user/profile?userId=fleadley0" --silent| jq

```
*Output:*
```json
{
  "userProfileId": 1,
  "firstName": "Leena",
  "lastName": "Le feaver",
  "gender": "M",
  "birthday": "2013-01-30",
  "email": "llefeaver0@sogou.com",
  "emailVerified": "N",
  "createTime": "2021-08-21T11:56:01.000+00:00",
  "lastAccessTime": "2021-07-18T09:02:38.000+00:00",
  "salutation": "Ms",
  "serviceAccount": [
    {
      "accountId": 1,
      "userId": "fleadley0",
      "creationTime": "2021-12-03T23:22:22.000+00:00",
      "lastAccessTime": "2022-04-18T10:01:59.000+00:00",
      "userVerified": "Y",
      "svcName": "svc1",
      "svcDescription": "Service 1"
    },
    {
      "accountId": 2,
      "userId": "fleadley0",
      "creationTime": "2021-12-03T23:22:22.000+00:00",
      "lastAccessTime": "2022-04-18T10:02:59.000+00:00",
      "userVerified": "Y",
      "svcName": "svc2",
      "svcDescription": "Service 2"
    }
  ],
  "userActivities": [
    {
      "action": "login",
      "description": "user logged in from web",
      "transactionTime": "2022-07-22T01:21:22.000+00:00",
      "device": "tablet",
      "clientIp": "36.96.14.13",
      "location": "LOC 9 - Kefar Shemaryahu"
    },
    {
      "action": "login",
      "description": "user logged in from web",
      "transactionTime": "2022-07-22T00:21:22.000+00:00",
      "device": "tablet",
      "clientIp": "36.96.14.13",
      "location": "LOC 8 - Kefar Shemaryahu"
    },
    {
      "action": "login",
      "description": "user logged in from web",
      "transactionTime": "2022-07-22T00:21:22.000+00:00",
      "device": "tablet",
      "clientIp": "36.96.14.13",
      "location": "LOC 8 - Kefar Shemaryahu"
    },
    {
      "action": "login",
      "description": "user logged in from web",
      "transactionTime": "2022-07-21T23:21:22.000+00:00",
      "device": "tablet",
      "clientIp": "36.96.14.13",
      "location": "LOC 7 - Kefar Shemaryahu"
    },
    {
      "action": "login",
      "description": "user logged in from web",
      "transactionTime": "2022-07-21T23:21:22.000+00:00",
      "device": "tablet",
      "clientIp": "36.96.14.13",
      "location": "LOC 7 - Kefar Shemaryahu"
    },
    {
      "action": "login",
      "description": "user logged in from web",
      "transactionTime": "2022-07-21T22:21:22.000+00:00",
      "device": "tablet",
      "clientIp": "36.96.14.13",
      "location": "LOC 6 - Kefar Shemaryahu"
    },
    {
      "action": "login",
      "description": "user logged in from web",
      "transactionTime": "2022-07-21T22:21:22.000+00:00",
      "device": "tablet",
      "clientIp": "36.96.14.13",
      "location": "LOC 6 - Kefar Shemaryahu"
    },
    {
      "action": "login",
      "description": "user logged in from web",
      "transactionTime": "2022-07-21T21:21:22.000+00:00",
      "device": "tablet",
      "clientIp": "36.96.14.13",
      "location": "LOC 5 - Kefar Shemaryahu"
    },
    {
      "action": "login",
      "description": "user logged in from web",
      "transactionTime": "2022-07-21T21:21:22.000+00:00",
      "device": "tablet",
      "clientIp": "36.96.14.13",
      "location": "LOC 5 - Kefar Shemaryahu"
    },
    {
      "action": "login",
      "description": "user logged in from web",
      "transactionTime": "2022-07-21T20:21:22.000+00:00",
      "device": "tablet",
      "clientIp": "36.96.14.13",
      "location": "LOC 4 - Kefar Shemaryahu"
    }
  ]
}
```

```bash
curl "http://localhost:8081/user/auth\?userId\=fleadley0\&passwordHash\=tVOKv51X" --slient | jq

```
*Output:*
```json
{
  "userProfileId": 1,
  "firstName": "Leena",
  "lastName": "Le feaver",
  "gender": "M",
  "birthday": "2013-01-30",
  "email": "llefeaver0@sogou.com",
  "emailVerified": "N",
  "createTime": "2021-08-21T11:56:01.000+00:00",
  "lastAccessTime": "2021-07-18T09:02:38.000+00:00",
  "salutation": "Ms",
  "serviceAccount": [
    {
      "accountId": 1,
      "userId": "fleadley0",
      "creationTime": "2021-12-03T23:22:22.000+00:00",
      "lastAccessTime": "2022-04-18T10:01:59.000+00:00",
      "userVerified": "Y",
      "svcName": "svc1",
      "svcDescription": "Service 1"
    },
    {
      "accountId": 2,
      "userId": "fleadley0",
      "creationTime": "2021-12-03T23:22:22.000+00:00",
      "lastAccessTime": "2022-04-18T10:02:59.000+00:00",
      "userVerified": "Y",
      "svcName": "svc2",
      "svcDescription": "Service 2"
    }
  ],
  "userActivities": [
    {
      "action": "login",
      "description": "user logged in from web",
      "transactionTime": "2022-07-22T01:21:22.000+00:00",
      "device": "tablet",
      "clientIp": "36.96.14.13",
      "location": "LOC 9 - Kefar Shemaryahu"
    },
    {
      "action": "login",
      "description": "user logged in from web",
      "transactionTime": "2022-07-22T00:21:22.000+00:00",
      "device": "tablet",
      "clientIp": "36.96.14.13",
      "location": "LOC 8 - Kefar Shemaryahu"
    },
    {
      "action": "login",
      "description": "user logged in from web",
      "transactionTime": "2022-07-22T00:21:22.000+00:00",
      "device": "tablet",
      "clientIp": "36.96.14.13",
      "location": "LOC 8 - Kefar Shemaryahu"
    },
    {
      "action": "login",
      "description": "user logged in from web",
      "transactionTime": "2022-07-21T23:21:22.000+00:00",
      "device": "tablet",
      "clientIp": "36.96.14.13",
      "location": "LOC 7 - Kefar Shemaryahu"
    },
    {
      "action": "login",
      "description": "user logged in from web",
      "transactionTime": "2022-07-21T23:21:22.000+00:00",
      "device": "tablet",
      "clientIp": "36.96.14.13",
      "location": "LOC 7 - Kefar Shemaryahu"
    },
    {
      "action": "login",
      "description": "user logged in from web",
      "transactionTime": "2022-07-21T22:21:22.000+00:00",
      "device": "tablet",
      "clientIp": "36.96.14.13",
      "location": "LOC 6 - Kefar Shemaryahu"
    },
    {
      "action": "login",
      "description": "user logged in from web",
      "transactionTime": "2022-07-21T22:21:22.000+00:00",
      "device": "tablet",
      "clientIp": "36.96.14.13",
      "location": "LOC 6 - Kefar Shemaryahu"
    },
    {
      "action": "login",
      "description": "user logged in from web",
      "transactionTime": "2022-07-21T21:21:22.000+00:00",
      "device": "tablet",
      "clientIp": "36.96.14.13",
      "location": "LOC 5 - Kefar Shemaryahu"
    },
    {
      "action": "login",
      "description": "user logged in from web",
      "transactionTime": "2022-07-21T21:21:22.000+00:00",
      "device": "tablet",
      "clientIp": "36.96.14.13",
      "location": "LOC 5 - Kefar Shemaryahu"
    },
    {
      "action": "login",
      "description": "user logged in from web",
      "transactionTime": "2022-07-21T20:21:22.000+00:00",
      "device": "tablet",
      "clientIp": "36.96.14.13",
      "location": "LOC 4 - Kefar Shemaryahu"
    }
  ]
}
```