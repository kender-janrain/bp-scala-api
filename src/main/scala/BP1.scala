import com.janrain.bp.LocalhostBackplaneConfig
import com.janrain.bp.v1.model.BusUpdateConfigV1
import com.janrain.bp.v1.{Backplane1Provisioning}
import concurrent.{Future, Await}
import scala.concurrent.duration._

object BP1 {
	object LocalBackplane1Provisioning extends Backplane1Provisioning with LocalhostBackplaneConfig
	import LocalBackplane1Provisioning._
	import concurrent.ExecutionContext.Implicits.global

	private def bpResult[T](f: Future[T]) = {
		Await.result(f, 5.seconds)
	}

	object user {
		def list(entities: String*) = bpResult {
			userList(entities.toSet)
		}

		def update(user: String, pass: String) = bpResult {
			userUpdate(user -> pass)
		}

		def delete(user: String) = bpResult {
			userDelete(Set(user))
		}
	}

	object bus {
		def list(entities: String*) = bpResult {
			busList(entities.toSet)
		}

		def update(configs: BusUpdateConfigV1*) = bpResult {
			busUpdate(configs:_*)
		}

		def config(busName: String, permissions: Map[String, Set[String]], retentionTimeSeconds: Int = 300,  retentionStickyTimeSeconds: Int = 28800) =
			BusUpdateConfigV1(busName, permissions, retentionTimeSeconds, retentionStickyTimeSeconds)

		def delete(busses: String*) = bpResult {
			busDelete(busses.toSet)
		}
	}

	object admin {
		def add(username: String, password: String) = bpResult {
			adminAdd(username, password)
		}

		def update(debugMode: Boolean, defaultMessagesMax: Int) = bpResult {
			adminUpdate(debugMode, defaultMessagesMax)
		}
	}
}
