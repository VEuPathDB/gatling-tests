
import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class GeneTextSearch extends Simulation {

  /** print debug messages to console
      0 off, 1 for a little, 2 for more verbosity
  */
  val DEBUG = 1

  val randIntFeeder = new Feeder[String] {
    private val RNG = new scala.util.Random
    // always return true as this feeder can be polled infinitively
    override def hasNext = true
    override def next: Map[String, String] = {
      val timestamp = RNG.nextInt(100000000).toString
      Map("timestamp" -> timestamp)
    }
  }

  // use a searchTerm that will also be matched in the summary results
  val searchTerm = "polymerase"
  
	val httpProtocol = http
		.baseURL("http://toxodb.org/toxo")
		//.inferHtmlResources()
		.acceptHeader("text/html, */*; q=0.01")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("en-us")
		.connection("keep-alive")
		.contentTypeHeader("application/x-www-form-urlencoded; charset=UTF-8")
		.userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:32.0) Gecko/20100101 Firefox/32.0")

	val headers_0 = Map("Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")

	val headers_1 = Map("Accept" -> "*/*")

	val headers_2 = Map("X-Requested-With" -> "XMLHttpRequest")

	val headers_4 = Map(
		"Accept" -> "application/json, text/javascript, */*; q=0.01",
		"Pragma" -> "no-cache",
		"X-Requested-With" -> "XMLHttpRequest")

	val headers_6 = Map(
		"Pragma" -> "no-cache",
		"X-Requested-With" -> "XMLHttpRequest")

	val headers_8 = Map(
		"Accept" -> "*/*",
		"X-Requested-With" -> "XMLHttpRequest")

	val headers_9 = Map(
		"Accept" -> "application/json, text/javascript, */*; q=0.01",
		"X-Requested-With" -> "XMLHttpRequest")


	val scn = scenario("GeneTextSearchBodies")
	  .feed(randIntFeeder)
		.exec(
		      http("request_0")
			.get("/processQuestionSetsFlat.do?questionFullName=GeneQuestions.GenesByTextSearch&array%28text_search_organism%29=Eimeria%2CGregarina%2CNeospora%2CToxoplasma%2CEimeria+acervulina%2CEimeria+brunetti%2CEimeria+falciformis%2CEimeria+maxima%2CEimeria+mitis%2CEimeria+necatrix%2CEimeria+praecox%2CEimeria+tenella%2CEimeria+acervulina+Houghton%2CEimeria+brunetti+Houghton%2CEimeria+falciformis+Bayer+Haberkorn+1970%2CEimeria+maxima+Weybridge%2CEimeria+mitis+Houghton%2CEimeria+necatrix+Houghton%2CEimeria+praecox+Houghton%2CEimeria+tenella+strain+Houghton%2CGregarina+niphandrodes%2CGregarina+niphandrodes+Unknown+strain%2CNeospora+caninum%2CNeospora+caninum+Liverpool%2CToxoplasma+gondii%2CToxoplasma+gondii+GT1%2CToxoplasma+gondii+ME49%2CToxoplasma+gondii+RH%2CToxoplasma+gondii+VEG%2CApicomplexa&array%28text_fields%29=Gene+ID%2CAlias%2CGene+product%2CGO+terms+and+definitions%2CGene+notes%2CUser+comments%2CProtein+domain+names+and+descriptions%2CEC+descriptions%2CCommunity+annotation%2CMetabolic+pathway+names+and+descriptions&array%28whole_words%29=no&value%28max_pvalue%29=-30&value%28text_expression%29=" + searchTerm + "&value%28timestamp%29=" + "${timestamp}" + "&questionSubmit=Get+Answer&go.x=0&go.y=0")
			.headers(headers_0)
		)
		.exec(
            http("request_4")
			.post("/showStrategy.do")
			.check(jsonPath("$..currentView.strategy").ofType[String].saveAs("strategy_id"))
			.check(jsonPath("$..currentView.step").ofType[String].saveAs("step_id"))
			.check(jsonPath("$..state.*.checksum").saveAs("checksum"))
			.headers(headers_4)
			.formParam("state", "")
		)
		.exec(
            http("request_7")
			.post("/showSummary.do")
			.headers(headers_6)
			.formParam("strategy", "${strategy_id}")
			.formParam("step", "${step_id}")
			.formParam("resultsOnly", "true")
			.formParam("strategy_checksum", "124cbfd3290303a00864be7578a0fd6a")
			.formParam("noskip", "1")
		)
		.exec(
            http("request_8")
			.get("/showSummaryView.do?strategy=${strategy_id}&step=${step_id}&view=_default&_=1420837122682")
      .check(regex(searchTerm).exists)
		)
		.exec(
            http("request_9")
			.get("/showResultSize.do?step=${step_id}&_=1420837122683")
			.headers(headers_9)
		)
    .exec { session =>
      if (DEBUG > 0) {
      println("strategy id: " + session("strategy_id").as[String] +
        ", step id: " + session("step_id").as[String] + 
        ", checksum: " + session("checksum").as[String])
      }
      if (DEBUG > 1) {
        println(session)
      }
      session
    }

	setUp(scn.inject(
	  //rampUsers(50) over (5 seconds)
	  atOnceUsers(2)
	)).protocols(httpProtocol)

}