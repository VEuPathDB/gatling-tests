package wdk

import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class StrategyMix extends Simulation {

  val httpProtocol = http
    .baseURL("http://toxodb.org/toxo")
    .acceptHeader("text/html, */*; q=0.01")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("EuPathDB Gatling Agent")


  setUp(
    GeneTextSearchScenario.scn
      .inject(atOnceUsers(1))
//      .inject(rampRate(10 usersPerSec) to(100 usersPerSec) during(5 minutes))
      .protocols(httpProtocol)
  )
}