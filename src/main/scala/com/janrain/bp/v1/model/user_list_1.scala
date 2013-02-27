package com.janrain.bp.v1.model

import spray.json.DefaultJsonProtocol

case class UserListRequestV1(admin: String, secret: String, entities: Set[String] = Set.empty)
case class UserListResponseUserV1(USER: String, PWDHASH: String)

object UserListJsonProtocolV1 extends DefaultJsonProtocol {
	implicit val UserListRequestFormatV1 = jsonFormat3(UserListRequestV1)
	implicit val UserListResponseUserFormatV1 = jsonFormat2(UserListResponseUserV1)
}
