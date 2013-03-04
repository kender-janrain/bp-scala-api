import akka.actor.ActorSystem
import com.janrain.bp.LocalhostBackplaneConfig
import com.janrain.bp.v2.{Backplane2, Backplane2Provisioning}
import com.janrain.bp.v2.model.{BusUpdateRequestConfigV2, ClientUpdateRequestConfigV2}
import concurrent.{Future, Await}
import scala.concurrent.duration._

object BP2 {
	val actorSystem = ActorSystem("BP2")
	object LocalBackplane2Provisioning extends Backplane2Provisioning with LocalhostBackplaneConfig {
		val system = actorSystem
	}

	import LocalBackplane2Provisioning._

	object LocalBackplane2 extends Backplane2 with LocalhostBackplaneConfig {
		val system = actorSystem
	}

	import LocalBackplane2._

	private def bpResult[T](f: Future[T]) = {
		Await.result(f, 5.seconds)
	}

	object user {
		def update(users: (String, String)*) = bpResult {
			userUpdate(users.toSet)
		}

		def list(users: String*) = bpResult {
			userList(users.toSet)
		}

		def delete(users: String*) = bpResult {
			userDelete(users.toSet)
		}
	}

	object client {
		val config = clientConfig _
		def update(configs: ClientUpdateRequestConfigV2*) = bpResult {
			clientUpdate(configs.toSet)
		}

		def list(entities: String*) = bpResult {
			clientList(entities.toSet)
		}

		def delete(entities: String*) = bpResult {
			clientDelete(entities.toSet)
		}
	}

	object bus {
		def config(busName: String, owner: String, retentionTimeSeconds: Int = 60, retentionStickyTimeSeconds: Int = 28800) = {
			busConfig(busName, owner, retentionTimeSeconds, retentionStickyTimeSeconds)
		}
		def update(configs: BusUpdateRequestConfigV2*) = bpResult {
			busUpdate(configs.toSet)
		}

		def list(entities: String*) = bpResult {
			busList(entities.toSet)
		}

		def delete(entities: String*) = bpResult {
			busDelete(entities.toSet)
		}
	}

	object grant {
		def add(grants: (String, Set[String])*) = bpResult {
			grantAdd(grants.toMap)
		}

		def revoke(grants: (String, Set[String])*) = bpResult {
			grantRevoke(grants.toMap)
		}

		def list(entities: String*) = bpResult {
			assert(entities.length > 0, "must specify at least one client")
			grantList(entities.toSet)
		}
	}

	object message {
		def token(bus: String) = bpResult {
			LocalBackplane2.token(bus)
		}

		def token(clientId: String, clientSecret: String) = bpResult {
			LocalBackplane2.token(clientId, clientSecret)
		}

		def post(accessToken: String, bus: String, channel: String, messageType: String, payload: Map[String, String], sticky: Boolean = false) = bpResult {
			postMessage(accessToken, bus, channel, messageType, payload, sticky)
		}

		def get(accessToken: String, messageUrl: String) = bpResult {
			getMessage(accessToken, messageUrl)
		}
	}
}
