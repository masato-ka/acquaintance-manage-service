acquaintance-manage-service
====

Overview

## Description

 This service provide acquaintance manager service using Azure Face Detection service.
 The service check the acquaintance or unknown person via face image.
 
  See in below swagger document after running this service.
  http://localhost:8080/swagger-ui.html


## Build

~~~~
$ git clone https://github.com/masato-ka/acquaintance-manage-service
$ cd acquaintance-manage-service
$ mvn package -DskipTests=true
~~~~

And create jar file in acquaintance-manage-service/target folder.

## Run the service.

* Startup as dev environment

The service start up with H2 DB(in memory db)

~~~

$java -Dazure.face.api.subscription=<Your Azure subscription> -Dazure.face.api.groupId=<Any group id> -Dazure.face.api.ServerUrl=Your host domain -jar target/acquaintance-manage-service-0.0.1-SNAPSHOT.jar 

~~~

* Startup as production environment

The service start up with CLEARDB(MySQL) and The service get db setting from environment value as CLEARDB_DATABASE_URL. Thus, you can start up this service on Heroku. If you use your own cloud, You should be setting CLEARDB_DATABASE_URL to environment value.

for example 

~~~
CLEARDB_DATABASE_URL=mysql://<username>:<password>@<host>/<databse>?reconnect=true 
~~~

~~~

$java -Dspring.profiles.active=production -Dazure.face.api.subscription=<Your Azure subscription> -Dazure.face.api.groupId=<Any group id> -Dazure.face.api.ServerUrl=Your host domain -jar target/acquaintance-manage-service-0.0.1-SNAPSHOT.jar 

CLEARDB_DATABASE_URL~~~

 * Enviroment valiable examples 
 
 |Env value                  | example value|
 |:--------------------------|------------:|
 |spring.profiles.active     | production  |
 |azure.face.api.groupId     | MyFrends    |
 |azure.face.api.ServerUrl   |https://westus.api.cognitive.microsoft.com|
 
 See in detail.
 [Azure official documents](https://docs.microsoft.com/ja-jp/azure/cognitive-services/face/face-api-how-to-topics/howtoidentifyfacesinimage)
 
## Usage

This service provide REST API Interface. You can using service via the API.


### 1. Create user
 
 
### 2. Search user

## Licence

[MIT LICENCE](https://github.com/masato-ka/geo-hash-potate/blob/master/LICENSE.txt)


## Author

[masato-ka](https://twitter.com/masato_ka)

Last modify : Aug/18/2017