package com.janrain

import spray.json._
import spray.httpx.marshalling.Marshaller
import spray.http.{HttpBody, ContentType}

case class BusUpdateRequest(admin: String, secret: String, configs: Iterable[BusUpdateConfig])
case class BusUpdateConfig(busName: String, permissions: Map[String, Set[String]], retentionTimeSeconds: Int = 300,  retentionStickyTimeSeconds: Int = 28800)

case class BusUpdateResponse()

object BusUpdateJsonProtocol extends DefaultJsonProtocol {
  def permissions(config: BusUpdateConfig): List[JsField] = {
    config.permissions.map { permission =>
      val (username, ps) = permission
      (username, JsString(ps.mkString(",")))
    }.toList
  }

  implicit def BusUpdateRequestMarshaller = Marshaller.of[BusUpdateRequest](ContentType.`application/json`) { (value, contentType, ctx) =>
    val jsonAst = JsObject(
      "admin" -> JsString(value.admin),
      "secret" -> JsString(value.secret),
      "configs" -> JsArray(value.configs.map { config =>
        JsObject(List(
          "BUS_NAME" -> JsString(config.busName),
          "RETENTION_TIME_SECONDS" -> JsString(config.retentionTimeSeconds.toString),
          "RETENTION_STICKY_TIME_SECONDS" -> JsString(config.retentionStickyTimeSeconds.toString)
        ) ++ permissions(config))
      }.toSeq: _*)
    )
    ctx.marshalTo(HttpBody(contentType, jsonAst.toString))
  }
}