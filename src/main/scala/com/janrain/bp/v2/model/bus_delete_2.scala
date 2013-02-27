package com.janrain.bp.v2.model

import spray.json.DefaultJsonProtocol

case class BusDeleteRequestV2(admin: String, secret: String, entities: Set[String])

object BusDeleteJsonProtocolV2 extends DefaultJsonProtocol {
	implicit val BusDeleteRequestV2Format = jsonFormat3(BusDeleteRequestV2)
}