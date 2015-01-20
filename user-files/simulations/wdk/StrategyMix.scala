package wdk

import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class StrategyMix extends Simulation {

  val host = System.getProperty("host")

  val hostProducts = Map[String,String](
    "amoebadb.org"        -> "AmoebaDB",
    "cryptodb.org"        -> "CryptoDB",
    "eupathdb.org"        -> "EuPathDB",
    "giardiadb.org"       -> "GiardiaDB",
    "hostdb.org"          -> "HostDB",
    "microsporidiadb.org" -> "MicrosporidiaDB",
    "piroplasmadb.org"    -> "PiroplasmaDB",
    "plasmodb.org"        -> "PlasmoDB",
    "toxodb.org"          -> "ToxoDB",
    "trichdb.org"         -> "TrichDB",
    "tritrypdb.org"       -> "TriTrypDB",
    "fungidb.org"         -> "FungiDB",
    "orthomcl.og"         -> "OrthoMCL"
  )

  val pattern = """([^\.]+\.[^\.]+)$""".r
  val matchList = pattern.findAllIn(host).matchData.toList
  val domain = matchList(0).group(1)
  val webapp = System.getProperty("webapp")
  val product = hostProducts(domain)

  val httpProtocol = http
    .baseURL("http://" + host + "/" + webapp)
    .acceptHeader("text/html, */*; q=0.01")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("EuPathDB Gatling Agent")

  val geneTextSearch = new GeneTextSearchScenario(product)
  val estSearch = new EstSearchScenario(product)
  val publicStrategy = new PublicStrategyScenario(product)
  
  setUp(
    estSearch.scn
       .inject(atOnceUsers(1))
      //  .inject(rampUsers(5) over (30 seconds))
      // .inject(constantUsersPerSec(1) during(30 seconds))
      .protocols(httpProtocol)
  )
}