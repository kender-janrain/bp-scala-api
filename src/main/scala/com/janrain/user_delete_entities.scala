package com.janrain

import spray.json.DefaultJsonProtocol

case class UserDeleteRequest(admin: String, secret: String, entities: Set[String])

object UserDeleteJsonProtocol extends DefaultJsonProtocol {
	implicit val UserDeleteRequestFormat = jsonFormat3(UserDeleteRequest)
}