package com.janrain.bp.v2.model

import spray.json.DefaultJsonProtocol

case class UserListRequest2(admin: String, secret: String, entities: Set[String] = Set.empty)
case class UserListResponseUser2(USER: String, PWDHASH: String)

object UserListJsonProtocol2 extends DefaultJsonProtocol {
	implicit val UserListRequestFormat = jsonFormat3(UserListRequest2)
	implicit val UserListResponseUserFormat = jsonFormat2(UserListResponseUser2)
}
