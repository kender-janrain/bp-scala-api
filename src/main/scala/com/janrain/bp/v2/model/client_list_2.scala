package com.janrain.bp.v2.model

import spray.json.DefaultJsonProtocol

case class ClientListRequestV2(admin: String, secret: String, entities: Set[String])
case class ClientListResponseConfigV2(USER: String, PWDHASH: String, SOURCE_URL: String, REDIRECT_URI: String)

object ClientListJsonProtocolV2 extends DefaultJsonProtocol {
	implicit val ClientListRequestV2Format = jsonFormat3(ClientListRequestV2)
	implicit val ClientListResponseConfigV2Format = jsonFormat4(ClientListResponseConfigV2)
}