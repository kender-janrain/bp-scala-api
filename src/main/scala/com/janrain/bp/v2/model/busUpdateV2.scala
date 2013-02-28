package com.janrain.bp.v2.model

import spray.json.DefaultJsonProtocol

case class BusUpdateRequestV2(admin: String, secret: String, configs: Set[BusUpdateRequestConfigV2])
case class BusUpdateRequestConfigV2(BUS_NAME: String, OWNER: String, RETENTION_TIME_SECONDS: String, RETENTION_STICKY_TIME_SECONDS: String)

object BusUpdateJsonProtocolV2 extends DefaultJsonProtocol {
	implicit val BusUpdateRequestConfigV2Format = jsonFormat4(BusUpdateRequestConfigV2)
	implicit val BusUpdateRequestV2Format = jsonFormat3(BusUpdateRequestV2)
}