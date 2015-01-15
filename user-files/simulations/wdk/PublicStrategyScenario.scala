package wdk

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class PublicStrategyScenario(
  product: String
) extends WdkScenario(product) {

  val DEBUG = 1

  val imIDs = Map[String, Array[Map[String, String]]](
    "ToxoDB" -> Array[Map[String, String]](
        Map(
          "name" -> "Prot Cod, Sig Pep, EST Ev",
          "imID" -> "cc5c9876caa70f82"
        ),
        Map(
          "name" -> "Kinase, TM, EST, Proteomics",
          "imID" -> "69c54f71428dc4e7"
        ),
        Map(
          "name" -> "Genes with AP-2 like motif",
          "imID" -> "48481038291ab7fa"
        ),
        Map(
          "name" -> "Best Vaccine Ever",
          "imID" -> "2ec3b0524c013934"
        )
    ),
    "TrichDB" -> Array[Map[String, String]](
        Map(
          "name" -> "Protein Coding genes with Signal Peptide having EST Evidence",
          "imID" -> "e169486bb70cf197"
        ),
        Map(
          "name" -> "Kinase, TM, EST, Proteomics",
          "imID" -> "4503d86d31b44e19"
        ),
        Map(
          "name" -> "Genes with AP-2 like motif",
          "imID" -> "e892ef6112576c45"
        )
      )
  )
  
  // product is not in scope for the Feeder trait but imIDs is. WTF?
  // workaround by copying value
  val prod = product 
  val strategyIdFeeder = new Feeder[String] {
    // always return true as this feeder can be polled infinitively
    override def hasNext = true
    override def next: Map[String, String] = {
      val randIdx = randInt(0, imIDs(prod).size)
      imIDs(prod)(randIdx)
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

	val scn = scenario("PublicStrategy")
	  .feed(strategyIdFeeder)
	  .doIf(System.getProperty("authtoken") != null) {
      exec(
        addCookie(Cookie("auth_tkt", System.getProperty("authtoken")))
      )
	  }
		.exec(
		      http("public strategy: ${name}")
			.get("/im.do?s=${imID}")
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
			.check(status.is(200))
//      .check(regex("div class=\"attribute-summary\"").exists)
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