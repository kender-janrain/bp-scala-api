package com.janrain.bp.v1.model

import spray.json._
import spray.httpx.marshalling.Marshaller
import spray.http.{HttpBody, ContentType}

case class BusUpdateRequestV1(admin: String, secret: String, configs: Iterable[BusUpdateConfigV1])
case class BusUpdateConfigV1(busName: String, permissions: Map[String, Set[String]], retentionTimeSeconds: Int = 300,  retentionStickyTimeSeconds: Int = 28800)

case class BusUpdateResponseV1()

object BusUpdateJsonProtocolV1 extends DefaultJsonProtocol {
	def permissions(config: BusUpdateConfigV1): List[JsField] = {
		config.permissions.map { permission =>
			val (username, ps) = permission
			(username, JsString(ps.mkString(",")))
		}.toList
	}

	implicit val BusUpdateRequestMarshallerV1 = Marshaller.of[BusUpdateRequestV1](ContentType.`application/json`) { (value, contentType, ctx) =>
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