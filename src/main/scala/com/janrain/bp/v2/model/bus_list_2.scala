package com.janrain.bp.v2.model

import spray.json.DefaultJsonProtocol

case class BusListRequestV2(admin: String, secret: String, entities: Set[String])
case class BusListResponseConfigV2(BUS_NAME: String, OWNER: String, RETENTION_TIME_SECONDS: String, RETENTION_STICKY_TIME_SECONDS: String)

object BusListJsonProtocolV2 extends DefaultJsonProtocol {
	implicit val BusListRequestV2Format = jsonFormat3(BusListRequestV2)
	implicit val BusListResponseConfigV2Format = jsonFormat4(BusListResponseConfigV2)
}
