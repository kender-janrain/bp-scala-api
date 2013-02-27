package com.janrain.bp.v2.model

import spray.json.DefaultJsonProtocol

case class ClientUpdateRequestV2(admin: String, secret: String, configs: Set[ClientUpdateRequestConfigV2])
case class ClientUpdateRequestConfigV2(USER: String, PWDHASH: String, SOURCE_URL: String, REDIRECT_URI: String)

object ClientUpdateJsonProtocolV2 extends DefaultJsonProtocol {
	implicit val ClientUpdateRequestConfigV2Format = jsonFormat4(ClientUpdateRequestConfigV2)
	implicit val ClientUpdateRequestV2Format = jsonFormat3(ClientUpdateRequestV2)
}