package com.janrain.bp.v2.model

import spray.json.DefaultJsonProtocol

case class ClientDeleteRequestV2(admin: String, secret: String, entities: Set[String])

object ClientDeleteJsonProtocolV2 extends DefaultJsonProtocol {
	implicit val ClientDeleteRequestV2Format = jsonFormat3(ClientDeleteRequestV2)
}