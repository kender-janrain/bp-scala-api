package com.janrain.bp.v2

import com.janrain.bp.BackplaneSpraySupport._
import spray.client.HttpConduit._
import spray.httpx.SprayJsonSupport._
import model._

import concurrent.ExecutionContext.Implicits.global

object Backplane2Provisioning {

	def userUpdate(users: Set[(String, String)]) = {
		import UserUpdateJsonProtocolV2._
		(sendReceive(conduit) ~> unmarshal[Map[String, String]]).apply(
			Post("/v2/provision/user/update", UserUpdateRequestV2(admin, secret, users.map {
				case (user, password) => UserUpdateRequestConfigV2(user, password)
			}.toList))
		)
	}

	def userList(users: Set[String]) = {
		import UserListJsonProtocol2._
		(sendReceive(conduit) ~> unmarshal[Map[String, UserListResponseUser2]]).apply(
			Post("/v2/provision/user/list", UserListRequest2(admin, secret, users))
		)
	}

	def userDelete(users: Set[String]) = {
		import UserDeleteJsonProtocolV2._
		(sendReceive(conduit) ~> unmarshal[Map[String, String]]).apply(
			Post("/v2/provision/user/delete", UserDeleteRequestV2(admin, secret, users))
		)
	}
}
