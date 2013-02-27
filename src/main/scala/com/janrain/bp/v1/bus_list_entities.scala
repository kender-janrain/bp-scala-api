package com.janrain.bp.v1

import spray.json.DefaultJsonProtocol
import spray.httpx.unmarshalling.Unmarshaller
import spray.http.{MediaTypes, HttpBody, ContentType}

case class BusListRequest(admin: String, secret: String, entities: Set[String])
case class BusListResponse(busName: String, permissions: Map[String, Set[String]], retentionTimeSeconds: Int, retentionStickyTimeSeconds: Int) //todo: ??

object BusListJsonProtocol extends DefaultJsonProtocol {
	import spray.json._

	implicit val BusListRequestFormat = jsonFormat3(BusListRequest)

	implicit object BusListResponseFormat extends RootJsonFormat[BusListResponse] {
		def write(obj: BusListResponse): JsValue = JsObject()

		def read(json: JsValue): BusListResponse = {
			val jo = json.asJsObject
			def value(key: String) = {
				val field = jo.fields.find(_._1 == key).getOrElse(sys.error("%s not found".format(key)))
				field._2.convertTo[String]
			}

			lazy val busPermissions = {
				jo.fields
					.filter(f => !List("BUS_NAME", "RETENTION_TIME_SECONDS", "RETENTION_STICKY_TIME_SECONDS").contains(f._1))
					.map {
					case (user, permissions) => (user, permissions.convertTo[String].split(",").toSet)
				}
			}

			BusListResponse(
				busName = value("BUS_NAME"),
				retentionTimeSeconds = value("RETENTION_TIME_SECONDS").toInt,
				retentionStickyTimeSeconds = value("RETENTION_STICKY_TIME_SECONDS").toInt,
				permissions = busPermissions
			)
		}
	}
}