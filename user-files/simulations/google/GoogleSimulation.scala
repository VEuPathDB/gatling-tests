/** Trivial example Simulation, using external GoogleScenario object **/

import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class GoogleSimulation extends Simulation {

  val httpProtocol = http.baseURL("https://google.com")

  setUp(
    GoogleScenario.scn
    .inject(atOnceUsers(1))
  ).protocols(httpProtocol)

}