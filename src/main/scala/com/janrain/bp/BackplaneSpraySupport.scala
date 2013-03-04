package com.janrain.bp

import akka.actor.{Props, ActorSystem}
import spray.can.client.HttpClient
import spray.client.HttpConduit
import spray.io.IOExtension
import spray.http.{OAuth2BearerToken, HttpHeaders, HttpRequest, HttpResponse}
import spray.httpx.RequestBuilding
import spray.client.HttpConduit._

trait BackplaneSpraySupport { self: BackplaneConfig =>
	implicit def system: ActorSystem

	private lazy val ioBridge = IOExtension(system).ioBridge()
	private lazy val httpClient = system.actorOf(Props(new HttpClient(ioBridge)))

	private lazy val conduit = system.actorOf(
		Props(new HttpConduit(httpClient, host, port))
	)

	def bpSendReceive = HttpConduit.sendReceive(conduit)
	def bpSendReceiveWithToken(bearerToken: String) =
		addHeader(HttpHeaders.Authorization(OAuth2BearerToken(bearerToken))) ~>
		bpSendReceive

	def debugResponse(response: HttpResponse): HttpResponse = {
		println(response.toString)
		response
	}
}