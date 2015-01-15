package wdk

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class WdkScenario(
  val product: String
) {

  val RNG = new scala.util.Random

  // return Integer between a inclusive and b exclusive
  def randInt(a:Int, b:Int) = {
    RNG.nextInt(b-a) + a
  }

  // return random integer
  val randIntFeeder = new Feeder[String] {
    // always return true as this feeder can be polled infinitively
    override def hasNext = true
    override def next: Map[String, String] = {
      //val randInt = RNG.nextInt(100000000).toString
      Map("feed_randInt" -> randInt(0, Int.MaxValue).toString)
    }
  }

}