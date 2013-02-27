import com.janrain.bp.v2.model.ClientUpdateRequestConfigV2
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
}
