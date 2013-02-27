package com.janrain.bp.v2.model

import spray.json.DefaultJsonProtocol

case class UserUpdateRequestV2(admin: String, secret: String, configs: List[UserUpdateRequestConfigV2])
case class UserUpdateRequestConfigV2(USER: String, PWDHASH: String)

object UserUpdateJsonProtocolV2 extends DefaultJsonProtocol {
	implicit val UserUpdateRequestV2Format = jsonFormat2(UserUpdateRequestConfigV2)
	implicit val UserUpdateRequestConfigV2Format = jsonFormat3(UserUpdateRequestV2)
}
