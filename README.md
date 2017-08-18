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

~~~
$java -Dazure.face.api.subscription=<Your Azure subscription> -Dazure.face.api.groupId=<Any group id> -Dazure.face.api.ServerUrl=Your host domain -jar target/acquaintance-manage-service-0.0.1-SNAPSHOT.jar 
~~~

 * Enviroment valiable examples 
 
 |Env value                  | example value|
 |:--------------------------|------------:|
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