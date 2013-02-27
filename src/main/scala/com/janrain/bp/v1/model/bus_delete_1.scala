package com.janrain.bp.v1.model

import spray.json.DefaultJsonProtocol

case class BusDeleteRequestV1(admin: String, secret: String, entities: Set[String])

object BusDeleteJsonProtocolV1 extends DefaultJsonProtocol {
	implicit def BusDeleteRequestFormatV1 = jsonFormat3(BusDeleteRequestV1)
}