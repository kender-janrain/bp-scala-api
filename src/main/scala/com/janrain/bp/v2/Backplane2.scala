
package com.janrain.bp.v2

import concurrent.ExecutionContext.Implicits.global

import com.janrain.bp.{BackplaneSpraySupport, BackplaneConfig}
import spray.client.HttpConduit._
import spray.httpx._
import spray.http._
import spray.http.HttpHeaders.RawHeader
import spray.json._
import java.net.URL


object Backplane2 extends DefaultJsonProtocol {
	sealed trait Grant
	case class ClientCredentialsGrant() extends Grant

	case class Message(bus: String, channel: String, `type`: String, payload: Map[String, String], sticky: String)
	case class SingleMessage(message: Message)

	case class Token(accessToken: String, refreshToken: String, scope: String, expiresIn: Option[Int])

	implicit val messageFormat = jsonFormat5(Message)
	implicit val singleMessageFormat = jsonFormat1(SingleMessage)
	implicit object tokenformat extends RootJsonFormat[Token] {
		def read(json: JsValue): Token = {
			json.asJsObject.getFields("access_token", "refresh_token", "scope", "expires_in") match {
				case Seq(accessToken, refreshToken, scope, expiresIn) => Token(
					accessToken.convertTo[String],
					refreshToken.convertTo[String],
					scope.convertTo[String],
					expiresIn.convertTo[Option[Int]])
			}
		}

		def write(obj: Token): JsValue = sys.error("not implemented")
	}

}

abstract class Backplane2 extends BackplaneConfig with BackplaneSpraySupport with SprayJsonSupport {
	import Backplane2._

	def authenticate = sys.error("not implemented")
	def authorize = sys.error("not implemented")

	/**
	 * Get an authenticated token
	 */
	def token(clientId: String, clientSecret: String, grant: Grant = ClientCredentialsGrant(), scope: Option[String] = None) = {
		var params = Map[String, String](
			"grant_type" -> (grant match {
				case ClientCredentialsGrant() => "client_credentials"
			})
		)
		scope.map { scope => params += "scope" -> scope }

		(addCredentials(BasicHttpCredentials(clientId, clientSecret)) ~> bpSendReceive ~> unmarshal[Token]).apply(
			Post("/v2/token", FormData(params))
		)
	}

	/**
	 * Get an anonymous token
	 */
	def token(bus: String) = {
		(bpSendReceive ~> debugResponse ~> unmarshal[Token]).apply(
			Get("/v2/token", FormData(Map("bus" -> bus)))
		)
	}

	/**
	 * Post a messasge to a bus.
	 * @param accessToken the authenticated access token
	 * @param bus the channel's bus
	 * @param channel the channel for the message
	 * @param messageType the type of message
	 * @param payload the message's payload
	 * @param sticky sticky flag
	 */
	def postMessage(accessToken: String, bus: String, channel: String, messageType: String, payload: Map[String, String], sticky: Boolean = false) ={
		val pipeline = bpSendReceiveWithToken(accessToken)
		implicit val printer = CompactPrinter
		pipeline(Post("/v2/message", SingleMessage(Message(bus, channel, messageType, payload, sticky.toString))))
	}

	/**
	 * Read a message's contents
	 * @param accessToken
	 * @param messageLocation The URL of the message. The host and port in the URL should match
	 *                        this client's host and port
	 */
	def getMessage(accessToken: String, messageLocation: String) = {
		val pipeline = bpSendReceiveWithToken(accessToken) ~> debugResponse ~> unmarshal[Message]
		implicit val printer = CompactPrinter
		val messageUrl = new URL(messageLocation)
//		assert(messageUrl.getHost == host, "message URL's host must be " + host)
//		assert(messageUrl.getPort == port, "message URL's port must be " + port)
		pipeline(Get(messageUrl.getPath))
	}
}
