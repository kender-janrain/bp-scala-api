package com.janrain

import akka.actor._
import spray.io.IOExtension
import spray.can.client.HttpClient
import spray.client.HttpConduit
import spray.httpx.SprayJsonSupport._
import HttpConduit._
import concurrent.ExecutionContext.Implicits.global
import spray.http.HttpResponse

object BackplaneProvisioning {
	val admin = "bpadmin"
	val secret = "bpadmin"
	val host = "localhost"
	val port = 9000

	val debug = false

	import com.janrain._

	implicit val system = ActorSystem()
	val ioBridge = IOExtension(system).ioBridge()
	val httpClient = system.actorOf(Props(new HttpClient(ioBridge)))
	val conduit = system.actorOf(
		props = Props(new HttpConduit(httpClient, host, port)),
		name = "http-conduit"
	)

	private def debugResponse(response: HttpResponse): HttpResponse = {
		if (debug) println(response.toString)
		response
	}

	def userList(entities: Set[String]) = {
		import UserListJsonProtocol._
		val pipeline = (sendReceive(conduit) ~> debugResponse ~> unmarshal[Map[String, UserListResponseUser]])
		pipeline(Post("/v1/provision/user/list", UserListRequest(admin, secret, entities)))
	}

	def userUpdate(users: (String, String)*) = {
		import UserUpdateJsonProtocol._
		val pipeline = (sendReceive(conduit))
		val request = UserUpdateRequest(
			admin, secret,
			users.map(u => UserUpdateRequestConfig(u._1, u._2)).toList
		)
		pipeline(Post("/v1/provision/user/update", request))
	}

	def userDelete(users: Set[String]) = {
		import UserDeleteJsonProtocol._
		val pipeline = (sendReceive(conduit))

		pipeline(Post("/v1/provision/user/delete", UserDeleteRequest(
			admin, secret, users
		)))
	}

	def busList(entities: Set[String]) = {
		import BusListJsonProtocol._
		val pipeline = (
			sendReceive(conduit) ~>
				unmarshal[Map[String, BusListResponse]]
			)

		pipeline(Post("/v1/provision/bus/list", BusListRequest(
			admin, secret, entities
		)))
	}

	def busUpdate(configs: BusUpdateConfig*) = {
		import BusUpdateJsonProtocol._
		val pipeline = (sendReceive(conduit))

		pipeline(Post("/v1/provision/bus/update", BusUpdateRequest(
			admin, secret, configs
		)))
	}

	def busDelete(entities: Set[String]) = {
		import BusDeleteJsonProtocol._
		val pipeline = (sendReceive(conduit))

		pipeline(Post("/v1/provision/bus/delete", BusDeleteRequest(
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
