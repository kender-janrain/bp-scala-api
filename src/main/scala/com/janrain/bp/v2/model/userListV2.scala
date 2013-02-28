package com.janrain.bp.v2.model

import spray.json.DefaultJsonProtocol

case class UserListRequestV2(admin: String, secret: String, entities: Set[String] = Set.empty)
case class UserListResponseV2(USER: String, PWDHASH: String)

object UserListJsonProtocol2 extends DefaultJsonProtocol {
	implicit val UserListRequestV2Format = jsonFormat3(UserListRequestV2)
	implicit val UserListResponseV2Format = jsonFormat2(UserListResponseV2)
}
