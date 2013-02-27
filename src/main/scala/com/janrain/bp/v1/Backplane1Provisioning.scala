package com.janrain.bp.v1

import model._
import spray.client.HttpConduit
import spray.httpx.SprayJsonSupport._
import HttpConduit._
import concurrent.ExecutionContext.Implicits.global
import spray.http.HttpResponse
import spray.http.HttpResponse

object Backplane1Provisioning {
	import com.janrain.bp.BackplaneSpraySupport._

	def userList(entities: Set[String]) = {
		import UserListJsonProtocolV1._
		val pipeline = (sendReceive(conduit) ~> unmarshal[Map[String, UserListResponseUserV1]])
		pipeline(Post("/v1/provision/user/list", UserListRequestV1(admin, secret, entities)))
	}

	def userUpdate(users: (String, String)*) = {
		import UserUpdateJsonProtocolV1._
		val pipeline = (sendReceive(conduit) ~> unmarshal[Map[String, String]])
		val request = UserUpdateRequestV1(
			admin, secret,
			users.map(u => UserUpdateRequestConfigV1(u._1, u._2)).toList
		)
		pipeline(Post("/v1/provision/user/update", request))
	}

	def userDelete(users: Set[String]) = {
		import UserDeleteJsonProtocolV1._
		val pipeline = (sendReceive(conduit) ~> unmarshal[Map[String, String]])

		pipeline(Post("/v1/provision/user/delete", UserDeleteRequestV1(
			admin, secret, users
		)))
	}

	def busList(entities: Set[String]) = {
		import BusListJsonProtocolV1._
		val pipeline = (
			sendReceive(conduit) ~>
				unmarshal[Map[String, BusListResponseV1]]
			)

		pipeline(Post("/v1/provision/bus/list", BusListRequestV1(
			admin, secret, entities
		)))
	}

	def busUpdate(configs: BusUpdateConfigV1*) = {
		import BusUpdateJsonProtocolV1._
		val pipeline = (sendReceive(conduit))

		pipeline(Post("/v1/provision/bus/update", BusUpdateRequestV1(
			admin, secret, configs
		)))
	}

	def busDelete(entities: Set[String]) = {
		import BusDeleteJsonProtocolV1._
		val pipeline = (sendReceive(conduit))

		pipeline(Post("/v1/provision/bus/delete", BusDeleteRequestV1(
			admin, secret, entities
		)))
	}

	def adminAdd(username: String, password: String) = {
		import spray.http.FormData
		val pipeline = (sendReceive(conduit))

		pipeline(Post("/adminadd", FormData(Map("username" -> username, "password" -> password))))
	}

	def adminUpdate(debugMode: Boolean, defaultMessagesMax: Int) = {
		import spray.http.FormData
		val pipeline = (sendReceive(conduit))

		pipeline(Post("/adminupdate", FormData(Map(
			"debug_mode" -> debugMode.toString,
			"default_messages_max" -> defaultMessagesMax.toString
		))))
	}
}
