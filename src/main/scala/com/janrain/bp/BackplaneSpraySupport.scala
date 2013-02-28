package com.janrain.bp

import akka.actor.{Props, ActorSystem}
import spray.can.client.HttpClient
import spray.client.HttpConduit
import spray.io.IOExtension
import spray.http.HttpResponse

object BackplaneSpraySupport {
	implicit private val system = ActorSystem()
	private val ioBridge = IOExtension(system).ioBridge()
	private val httpClient = system.actorOf(Props(new HttpClient(ioBridge)))
}

trait BackplaneSpraySupport { self: BackplaneConfig =>
	import BackplaneSpraySupport._

	private val conduit = system.actorOf(
		Props(new HttpConduit(httpClient, host, port))
	)

	def bpSendReceive = HttpConduit.sendReceive(conduit)

	def debugResponse(response: HttpResponse): HttpResponse = {
		println(response.toString)
		response
	}
}