package com.janrain.bp.v2.model

import spray.json.{JsString, JsObject, DefaultJsonProtocol}
import spray.httpx.marshalling.Marshaller
import spray.http.HttpBody

case class GrantRevokeRequestV2(admin: String, secret: String, grants: Map[String, Set[String]])

object GrantRevokeJsonProtocol2 extends DefaultJsonProtocol {
	import spray.http.ContentType._
	implicit val GrantRevokeRequestV2Marshaller = Marshaller.of[GrantRevokeRequestV2](`application/json`) { (value, contentType, ctx) =>
		val jsonAst = JsObject(
			"admin" -> JsString(value.admin),
			"secret" -> JsString(value.secret),
			"grants" -> JsObject(
				value.grants.toList.map {
					case (clientId, busses) => clientId -> JsString(busses.mkString(" "))
				}
			)
		)
		ctx.marshalTo(HttpBody(contentType, jsonAst.toString))
	}
}