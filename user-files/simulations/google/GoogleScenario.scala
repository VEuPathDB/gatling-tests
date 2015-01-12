/** 
Trivial example scenario to check of Google home page.
This scenario is run from the separate GoogleSimulation class.
 **/

import io.gatling.core.Predef._
import io.gatling.http.Predef._

// Note this is an object, not a class.
object GoogleScenario {
  val scn = scenario("Google")
    .exec(http("Home")
      .get("/")
      .check(regex("I'm Feeling Lucky").exists)
    )
}