package com.janrain.bp.v2.model

import spray.json.DefaultJsonProtocol

case class GrantListRequestV2(admin: String, secret: String, entities: Set[String])

object GrantListJsonProtocolV2 extends DefaultJsonProtocol {
	implicit val GrantListRequestV2Format = jsonFormat3(GrantListRequestV2)
}