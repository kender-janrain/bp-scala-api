package com.janrain.bp.v1.model

import spray.json.DefaultJsonProtocol

case class UserUpdateRequestV1(admin: String, secret: String, configs: List[UserUpdateRequestConfigV1])
case class UserUpdateRequestConfigV1(USER: String, PWDHASH: String)

object UserUpdateJsonProtocolV1 extends DefaultJsonProtocol {
	implicit val UserUpdateRequestFormatV1 = jsonFormat2(UserUpdateRequestConfigV1)
	implicit val UserUpdateRequestConfigFormatV1 = jsonFormat3(UserUpdateRequestV1)
}
