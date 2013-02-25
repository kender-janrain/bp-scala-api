package com.janrain

import spray.json.DefaultJsonProtocol

case class BusListRequest(admin: String, secret: String, entities: Set[String])
case class BusListResponse() //todo: ??

object BusListJsonProtocol extends DefaultJsonProtocol {
  implicit def BusListRequestFormat = jsonFormat3(BusListRequest)
}