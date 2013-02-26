package com.janrain

import spray.json.DefaultJsonProtocol

case class UserListRequest(admin: String, secret: String, entities: Set[String] = Set.empty)
case class UserListResponseUser(USER: String, PWDHASH: String)

object UserListJsonProtocol extends DefaultJsonProtocol {
  implicit val UserListRequestFormat = jsonFormat3(UserListRequest)
  implicit val UserListResponseUserFormat = jsonFormat2(UserListResponseUser)
}
