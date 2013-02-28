# Backplane Scala API #

This API currently supports Backplane v1 Provisioning. It is presented
as a Scala API and SBT console helpers

Requires: SBT 0.12.2

## SBT Console ##

    git clone https://github.com/kender-janrain/bp-scala-api.git
    sbt console

### Provisioning Backplane 1 ###

see BP1.scala for a list of operations

    scala> import BP1._
    import BP1._

    scala> user.list.map(_._1).mkString(", ")
    res1: String = kender1

    scala> user.update("test1", "pass")
    res2: spray.http.HttpResponse = HttpResponse(StatusCode(200, OK),HttpBody(ContentType(MediaType(application/json),Some(HttpCharset(UTF-8))),{"test1":"BACKPLANE_UPDATE_SUCCESS"}...),List(server: Jetty(8.1.9.v20130131), transfer-encoding: chunked, Content-Type: application/json; charset=UTF-8, ec2-instance-id: n/a, x-bp-instance: xxx-MAVEN_BUILD_VERSION),HTTP/1.1)

    scala> bus.list()
    res3: Map[String,com.janrain.BusListResponse] = Map(test1 -> BusListResponse(test1,Map(kender1 -> Set(POST)),300,28800))

    scala> bus.update(bus.config("test2", Map("kender1" -> Set("POST", "GETALL"))))
    res4: spray.http.HttpResponse = HttpResponse(StatusCode(200, OK),HttpBody(ContentType(MediaType(application/json),Some(HttpCharset(UTF-8))),{"test2":"BACKPLANE_UPDATE_SUCCESS"}...),List(server: Jetty(8.1.9.v20130131), transfer-encoding: chunked, Content-Type: application/json; charset=UTF-8, ec2-instance-id: n/a, x-bp-instance: xxx-MAVEN_BUILD_VERSION),HTTP/1.1)

    scala> bus.list()
    res5: Map[String,com.janrain.BusListResponse] = Map(test1 -> BusListResponse(test1,Map(kender1 -> Set(POST)),300,28800), test2 -> BusListResponse(test2,Map(kender1 -> Set(POST, GETALL)),300,28800))

### Provisioning Backplane 2 ###

Essentially works the same as backplane 1, but with backplane 2 versions of each operation

see BP2.scala for a list of operations.

    scala> import BP2._
    import BP2._

    scala> client.update(client.config("client1", "client1", "http://localhost/source", "http://localhost/redirect"))
    res0: Map[String,String] = Map(client1 -> BACKPLANE_UPDATE_SUCCESS)

    scala> bus.list()
    res1: Map[String,com.janrain.bp.v2.model.BusListResponseConfigV2] = Map(b1 -> BusListResponseConfigV2(b1,kender2,60,28800))

    scala> grant.add("client1" -> Set("b1"))
    res2: Map[String,String] = Map(client1 -> GRANT_UPDATE_SUCCESS)

## To Do ##

* Implement publish and subscribing to messages
* Configurablity!
