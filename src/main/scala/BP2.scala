import concurrent.Await
import scala.concurrent.duration._

object BP2 {
	import com.janrain.bp.v2.Backplane2Provisioning._
	val timeout = 5 seconds

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
}
