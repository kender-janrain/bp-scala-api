package com.janrain.bp.v2

import com.janrain.bp.BackplaneSpraySupport._
import concurrent.ExecutionContext.Implicits.global
import model._
import spray.client.HttpConduit._
import spray.httpx.SprayJsonSupport._

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
		(sendReceive(conduit) ~> unmarshal[Map[String, UserListResponseV2]]).apply(
			Post("/v2/provision/user/list", UserListRequestV2(admin, secret, users))
		)
	}

	def userDelete(users: Set[String]) = {
		import UserDeleteJsonProtocolV2._
		(sendReceive(conduit) ~> unmarshal[Map[String, String]]).apply(
			Post("/v2/provision/user/delete", UserDeleteRequestV2(admin, secret, users))
		)
	}

	def clientConfig(user: String, pwdHash: String, sourceUrl: String, redirectUrl: String) = {
		ClientUpdateRequestConfigV2(user, pwdHash, sourceUrl, redirectUrl)
	}

	def clientUpdate(configs: Set[ClientUpdateRequestConfigV2]) = {
		import ClientUpdateJsonProtocolV2._
		(sendReceive(conduit) ~> unmarshal[Map[String, String]]).apply(
			Post("/v2/provision/client/update", ClientUpdateRequestV2(admin, secret, configs))
		)
	}

	def clientList(entities: Set[String]) = {
		import ClientListJsonProtocolV2._
		(sendReceive(conduit) ~> unmarshal[Map[String, ClientListResponseConfigV2]]).apply(
			Post("/v2/provision/client/list", ClientListRequestV2(admin, secret, entities))
		)
	}

	def clientDelete(entities: Set[String]) = {
		import ClientDeleteJsonProtocolV2._
		(sendReceive(conduit) ~> unmarshal[Map[String, String]]).apply(
			Post("/v2/provision/client/delete", ClientDeleteRequestV2(admin, secret, entities))
		)
	}

	def busConfig(busName: String, owner: String, retentionTimeSeconds: Int = 60, retentionStickyTimeSeconds: Int = 28800) = {
		BusUpdateRequestConfigV2(busName, owner, retentionTimeSeconds.toString, retentionStickyTimeSeconds.toString)
	}

	def busUpdate(configs: Set[BusUpdateRequestConfigV2]) = {
		import BusUpdateJsonProtocolV2._
		(sendReceive(conduit) ~> unmarshal[Map[String, String]]).apply(
			Post("/v2/provision/bus/update", BusUpdateRequestV2(admin, secret, configs))
		)
	}

	def busList(entities: Set[String]) = {
		import BusListJsonProtocolV2._
		(sendReceive(conduit) ~> unmarshal[Map[String, BusListResponseConfigV2]]).apply(
			Post("/v2/provision/bus/list", BusListRequestV2(admin, secret, entities))
		)
	}

	def busDelete(entities: Set[String]) = {
		import BusDeleteJsonProtocolV2._
		(sendReceive(conduit) ~> unmarshal[Map[String, String]]).apply(
			Post("/v2/provision/bus/delete", BusDeleteRequestV2(admin, secret, entities))
		)
	}

	def grantAdd(grants: Map[String, Set[String]]) = {
		import GrantAddJsonProtocolV2._
		(sendReceive(conduit) ~> unmarshal[Map[String, String]]).apply(
			Post("/v2/provision/grant/add", GrantAddRequestV2(admin, secret, grants))
		)
	}

	def grantRevoke(grants: Map[String, Set[String]]) = {
		import GrantRevokeJsonProtocol2._
		(sendReceive(conduit) ~> unmarshal[Map[String, String]]).apply(
			Post("/v2/provision/grant/revoke", GrantRevokeRequestV2(admin, secret, grants))
		)
	}

	def grantList(entities: Set[String]) = {
		import GrantListJsonProtocolV2._
		(sendReceive(conduit) ~> unmarshal[Map[String, Map[String, String]]]).apply(
			Post("/v2/provision/grant/list", GrantListRequestV2(admin, secret, entities))
		)
	}
}
