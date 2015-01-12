/** Assets served by WDK requires generating a guest user for each request which will
 cause Oracle to start rejecting database connections at high request rates.
**/

package assets

import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class StaticAssets extends Simulation {

  val httpProtocol = http.baseURL("http://toxodb.org").userAgentHeader("EuPathDB Gatling Agent")
  val httpProtocol_w1 = http.baseURL("http://w1.toxodb.org").userAgentHeader("EuPathDB Gatling Agent")
  val httpProtocol_w2 = http.baseURL("http://w2.toxodb.org").userAgentHeader("EuPathDB Gatling Agent")

  val assets = csv("static-assets.csv").random

  val scn = scenario("Google")
    .feed(assets)
    .exec(http("${asset}")
      .get("${asset}")
      .check(status.is(200))
    )

  setUp(
    scn
    .inject(
    //  atOnceUsers(1)
    rampUsers(10000) over(20 seconds)
    )
  ).protocols(httpProtocol)


}