package com.janrain.bp.v1.model

import spray.json.DefaultJsonProtocol

case class UserDeleteRequestV1(admin: String, secret: String, entities: Set[String])

object UserDeleteJsonProtocolV1 extends DefaultJsonProtocol {
	implicit val UserDeleteRequestFormatV1 = jsonFormat3(UserDeleteRequestV1)
}