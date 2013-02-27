package com.janrain.bp.v2.model

import spray.json.DefaultJsonProtocol

case class UserDeleteRequestV2(admin: String, secret: String, entities: Set[String])

object UserDeleteJsonProtocolV2 extends DefaultJsonProtocol {
	implicit val UserDeleteRequestFormatV2 = jsonFormat3(UserDeleteRequestV2)
}