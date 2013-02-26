import com.janrain.{BusUpdateConfig, BackplaneProvisioning}
import concurrent.Await
import scala.concurrent.duration._

object BP {
  import BackplaneProvisioning._
  import concurrent.ExecutionContext.Implicits.global

  val timeout = 5 seconds

  object user {
    def list(entities: String*) = {
      Await.result(userList(entities.toSet), timeout)
    }

    def update(user: String, pass: String) = {
      Await.result(userUpdate(user -> pass), timeout)
    }

    def delete(user: String) = {
      Await.result(userDelete(Set(user)), timeout)
    }
  }

  object bus {
    def list(entities: String*) = {
      Await.result(busList(entities.toSet), timeout)
    }

    def update(configs: BusUpdateConfig*) = {
      Await.result(busUpdate(configs:_*), timeout)
    }

    def config(busName: String, permissions: Map[String, Set[String]], retentionTimeSeconds: Int = 300,  retentionStickyTimeSeconds: Int = 28800) =
      BusUpdateConfig(busName, permissions, retentionTimeSeconds, retentionStickyTimeSeconds)

    def delete(busses: String*) = {
      Await.result(busDelete(busses.toSet), timeout)
    }
  }

  object admin {
    def add(username: String, password: String) = {
      Await.result(adminAdd(username, password), timeout)
    }

    def update(debugMode: Boolean, defaultMessagesMax: Int) = {
      Await.result(adminUpdate(debugMode, defaultMessagesMax), timeout)
    }
  }

  def bpp_exit = {
    BackplaneProvisioning.system.shutdown()
    System.exit(0)
  }
}
