package com.janrain

import spray.json.DefaultJsonProtocol

case class UserUpdateRequest(admin: String, secret: String, configs: List[UserUpdateRequestConfig])
case class UserUpdateRequestConfig(USER: String, PWDHASH: String)

object UserUpdateJsonProtocol extends DefaultJsonProtocol {
	implicit val UserUpdateRequestFormat = jsonFormat2(UserUpdateRequestConfig)
	implicit val UserUpdateRequestConfigFormat = jsonFormat3(UserUpdateRequest)
}
