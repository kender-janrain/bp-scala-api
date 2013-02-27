package com.janrain.bp.v2.model

import spray.json.DefaultJsonProtocol

case class UserUpdateRequestV2(admin: String, secret: String, configs: List[UserUpdateRequestConfigV2])
case class UserUpdateRequestConfigV2(USER: String, PWDHASH: String)

object UserUpdateJsonProtocolV2 extends DefaultJsonProtocol {
	implicit val UserUpdateRequestFormat = jsonFormat2(UserUpdateRequestConfigV2)
	implicit val UserUpdateRequestConfigFormat = jsonFormat3(UserUpdateRequestV2)
}
