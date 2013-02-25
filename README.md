# Backplane Scala API #

This API currently supports Backplane v1 Provisioning. It is presented
as a Scala API and SBT console helpers

Requires: SBT 0.12.2

## SBT Console ##

    git clone https://github.com/kender-janrain/bp-scala-api.git
    sbt console
    import BP._

see BP.scala for console interface

### Examples ###

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

## To Do ##

* A lot of polish
* Configurablity!
