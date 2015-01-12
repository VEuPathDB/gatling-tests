package wdk

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

object GeneTextSearchScenario {

  val product = "TrichDB" // How can I get this passed in from the Simulation class????

  /** print debug messages to console
      0 off, 1 for a little, 2 for more verbosity
  */
  val DEBUG = 1

  val searchTerms = csv("gene-terms.csv").circular

  val querySubstrings = Map(
    "ToxoDB" -> Map(
      "textFields" -> "Gene+ID%2CAlias%2CGene+product%2CGO+terms+and+definitions%2CGene+notes%2CUser+comments%2CProtein+domain+names+and+descriptions%2CEC+descriptions%2CCommunity+annotation%2CMetabolic+pathway+names+and+descriptions",
      "organisms"  -> "Eimeria%2CGregarina%2CNeospora%2CToxoplasma%2CEimeria+acervulina%2CEimeria+brunetti%2CEimeria+falciformis%2CEimeria+maxima%2CEimeria+mitis%2CEimeria+necatrix%2CEimeria+praecox%2CEimeria+tenella%2CEimeria+acervulina+Houghton%2CEimeria+brunetti+Houghton%2CEimeria+falciformis+Bayer+Haberkorn+1970%2CEimeria+maxima+Weybridge%2CEimeria+mitis+Houghton%2CEimeria+necatrix+Houghton%2CEimeria+praecox+Houghton%2CEimeria+tenella+strain+Houghton%2CGregarina+niphandrodes%2CGregarina+niphandrodes+Unknown+strain%2CNeospora+caninum%2CNeospora+caninum+Liverpool%2CToxoplasma+gondii%2CToxoplasma+gondii+GT1%2CToxoplasma+gondii+ME49%2CToxoplasma+gondii+RH%2CToxoplasma+gondii+VEG%2CApicomplexa"
    ),
    "TrichDB" -> Map(
      "textFields" -> "Gene+ID%2CAlias%2CGene+product%2CGO+terms+and+definitions%2CGene+notes%2CUser+comments%2CProtein+domain+names+and+descriptions%2CEC+descriptions",
      "organisms"  -> "Trichomonas%2CTrichomonas+vaginalis%2CTrichomonas+vaginalis+G3%2CTrichomonadida"
    )
  )

  def processQuestionUrl(searchTerm: String, timestamp: String) : String = {
    "/processQuestionSetsFlat.do?" +
      "questionFullName=GeneQuestions.GenesByTextSearch" +
      "&array%28text_search_organism%29=" + 
        querySubstrings(product)("organisms") + 
      "&array%28text_fields%29=" +
        querySubstrings(product)("textFields") + 
      "&array%28whole_words%29=no&value%28max_pvalue%29=-30" +
      "&value%28text_expression%29=" + searchTerm + 
      "&value%28timestamp%29=" + 
        timestamp + 
      "&questionSubmit=Get+Answer&go.x=0&go.y=0"
  }

  val randIntFeeder = new Feeder[String] {
    private val RNG = new scala.util.Random
    // always return true as this feeder can be polled infinitively
    override def hasNext = true
    override def next: Map[String, String] = {
      val timestamp = RNG.nextInt(100000000).toString
      Map("timestamp" -> timestamp)
    }
  }

	val headers_0 = Map("Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")

	val headers_4 = Map(
		"Accept" -> "application/json, text/javascript, */*; q=0.01",
		"Pragma" -> "no-cache",
		"X-Requested-With" -> "XMLHttpRequest")

	val headers_9 = Map(
		"Accept" -> "application/json, text/javascript, */*; q=0.01",
		"X-Requested-With" -> "XMLHttpRequest")


	val scn = scenario("GeneTextSearchBodies")
	  .feed(randIntFeeder)
	  .feed(searchTerms)
		.exec(
		      http("processQuestionSetsFlat")
			.get(processQuestionUrl("${feed_searchTerm}","${timestamp}"))
			.headers(headers_0)
		)
		.exec(
            http("showStrategy (fetch session metadata)")
			.post("/showStrategy.do")
			.check(jsonPath("$..currentView.strategy").ofType[String].saveAs("strategy_id"))
			.check(jsonPath("$..currentView.step").ofType[String].saveAs("step_id"))
			.check(jsonPath("$..state.*.checksum").saveAs("checksum"))
			.headers(headers_4)
			.formParam("state", "")
		)
		.exec(
            http("showSummaryView (result list)")
			.get("/showSummaryView.do?strategy=${strategy_id}&step=${step_id}&view=_default&_=1420837122682")
      .check(regex("${feed_expectedHit}").exists)
		)
		.exec(
            http("showResultSize")
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
}