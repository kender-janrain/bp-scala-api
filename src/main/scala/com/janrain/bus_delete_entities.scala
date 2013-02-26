package com.janrain

import spray.json.DefaultJsonProtocol

case class BusDeleteRequest(admin: String, secret: String, entities: Set[String])

object BusDeleteJsonProtocol extends DefaultJsonProtocol {
	implicit def BusDeleteRequestFormat = jsonFormat3(BusDeleteRequest)
}