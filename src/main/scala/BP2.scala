import com.janrain.bp.v2.model.{BusUpdateRequestConfigV2, ClientUpdateRequestConfigV2}
import concurrent.Await
import scala.concurrent.duration._

object BP2 {
	import com.janrain.bp.v2.Backplane2Provisioning._
	val timeout = 5.seconds

	object user {
		def update(users: (String, String)*) = {
			Await.result(userUpdate(users.toSet), timeout)
		}

		def list(users: String*) = {
			Await.result(userList(users.toSet), timeout)
		}

		def delete(users: String*) = {
			Await.result(userDelete(users.toSet), timeout)
		}
	}

	object client {
		val config = clientConfig _
		def update(configs: ClientUpdateRequestConfigV2*) = {
			Await.result(clientUpdate(configs.toSet), timeout)
		}

		def list(entities: String*) = {
			Await.result(clientList(entities.toSet), timeout)
		}

		def delete(entities: String*) = {
			Await.result(clientDelete(entities.toSet), timeout)
		}
	}

	object bus {
		def config(busName: String, owner: String, retentionTimeSeconds: Int = 60, retentionStickyTimeSeconds: Int = 28800) = {
			busConfig(busName, owner, retentionTimeSeconds, retentionStickyTimeSeconds)
		}
		def update(configs: BusUpdateRequestConfigV2*) = {
			Await.result(busUpdate(configs.toSet), timeout)
		}

		def list(entities: String*) = {
			Await.result(busList(entities.toSet), timeout)
		}

		def delete(entities: String*) = {
			Await.result(busDelete(entities.toSet), timeout)
		}
	}

	object grant {
		def add(grants: (String, Set[String])*) = {
			Await.result(grantAdd(grants.toMap), timeout)
		}

		def revoke(grants: (String, Set[String])*) = {
			Await.result(grantRevoke(grants.toMap), timeout)
		}

		def list(entities: String*) = {
			assert(entities.length > 0, "must specify at least one client")
			Await.result(grantList(entities.toSet), timeout)
		}
	}
}
