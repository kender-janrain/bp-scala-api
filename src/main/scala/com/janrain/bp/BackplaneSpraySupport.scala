package com.janrain.bp

import akka.actor.{Props, ActorSystem}
import spray.can.client.HttpClient
import spray.client.HttpConduit
import spray.io.IOExtension
import spray.http.HttpResponse
import com.janrain.bp.BackplaneSpraySupport._
import spray.http.HttpResponse

object BackplaneSpraySupport extends BackplaneConfig {
	implicit val system = ActorSystem()

	val ioBridge = IOExtension(system).ioBridge()
	val httpClient = system.actorOf(Props(new HttpClient(ioBridge)))
	val conduit = system.actorOf(
		props = Props(new HttpConduit(httpClient, host, port)),
		name = "http-conduit"
	)

	def debugResponse(response: HttpResponse): HttpResponse = {
		if (debug) println(response.toString)
		response
	}
}