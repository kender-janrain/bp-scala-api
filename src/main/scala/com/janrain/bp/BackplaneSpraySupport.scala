package com.janrain.bp

import akka.actor.{Props, ActorSystem}
import spray.can.client.HttpClient
import spray.client.HttpConduit
import spray.io.IOExtension

object BackplaneSpraySupport extends BackplaneConfig {
	implicit val system = ActorSystem()

	val ioBridge = IOExtension(system).ioBridge()
	val httpClient = system.actorOf(Props(new HttpClient(ioBridge)))
	val conduit = system.actorOf(
		props = Props(new HttpConduit(httpClient, host, port)),
		name = "http-conduit"
	)
}