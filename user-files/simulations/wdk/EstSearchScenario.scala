package wdk

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class EstSearchScenario(
  product: String
) extends WdkScenario(product) {

  val DEBUG = 1

	val headers_0 = Map("Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")

	val headers_4 = Map(
		"Accept" -> "application/json, text/javascript, */*; q=0.01",
		"Pragma" -> "no-cache",
		"X-Requested-With" -> "XMLHttpRequest")

	val headers_9 = Map(
		"Accept" -> "application/json, text/javascript, */*; q=0.01",
		"X-Requested-With" -> "XMLHttpRequest")


	val scn = scenario("EstSearch")
    .exec(setAuthTktCookie)
		.exec(
		      http("processQuestion")
			.post("/processQuestion.do")
			.headers(headers_0)
      .formParam("questionFullName", "GeneQuestions.GenesByESTOverlap")
      .formParam("array(libraryIdGenes)", "ORESTES cDNA library; Eimeria tenella sporulated oocysts")
      .formParam("array(libraryIdGenes)", "Eimeria S5-2 Sporozoite stage")
      .formParam("array(libraryIdGenes)", "ORESTES cDNA library; Eimeria tenella sporozoites")
      .formParam("array(libraryIdGenes)", "Eimeria tenella sporozoites subtracted cDNA library")
      .formParam("array(libraryIdGenes)", "EtH11 (Sporozoite)")
      .formParam("array(libraryIdGenes)", "E. tenella subtracted cDNA unsporulated oocysts")
      .formParam("array(libraryIdGenes)", "ORESTES cDNA library; Eimeria tenella unsporulated oocysts")
      .formParam("array(libraryIdGenes)", "Eimeria tenella Wisconsin first generation merozoite")
      .formParam("array(libraryIdGenes)", "Eimeria tenella sporulated oocysts subtracted cDNA library (Sporulated oocysts)")
      .formParam("array(libraryIdGenes)", "EtH1 (second generation merozoite)")
      .formParam("array(libraryIdGenes)", "Eimeria tenella Houghton sporozoite")
      .formParam("array(libraryIdGenes)", "ORESTES cDNA library; Eimeria tenella sporoblast-phase oocysts")
      .formParam("array(libraryIdGenes)", "Eimeria tenella M5-6 cDNA Neg Selected (Merozoite)")
      .formParam("array(libraryIdGenes)", "Eimeria tenella S5-2 cDNA Neg Selected (Sporozoite stage)")
      .formParam("array(libraryIdGenes)", "ORESTES cDNA library; Eimeria tenella merozoites")
      .formParam("array(libraryIdGenes)", "Eimeria tenella EtmzflcDNA (Second generation merozoite)")
      .formParam("array(libraryIdGenes)", "Eimeria M5-6 Merozoite stage")
      .formParam("array(libraryIdGenes)", "Eimeria tenella M5-6 Excised cDNA (LS18)")
      .formParam("array(libraryIdGenes)", "Eimeria tenella S5-2 Excised cDNA (LS18)")
      .formParam("array(libraryIdGenes)", "Normalized Eimeria tenella cDNA library (unsporulated oocyst; sporulated oocyst; sporozoite; merozoite)")
      .formParam("array(libraryIdGenes)", "ORESTES cDNA library; Eimeria tenella sporulating oocysts")
      .formParam("array(libraryIdGenes)", "Eimeria M5-6 Merozoite stage subtracted")
      .formParam("array(libraryIdGenes)", "E. tenella subtracted cDNA sporulated oocysts")
      .formParam("array(libraryIdGenes)", "Eimeria tenella Houghton unsporulated oocyst")
      .formParam("array(libraryIdGenes)", "Toxoplasma gondii")
      .formParam("array(libraryIdGenes)", "RH tachyzoite cDNA library")
      .formParam("array(libraryIdGenes)", "XTG Sugano cDNA library (tachyzoite stage)")
      .formParam("array(libraryIdGenes)", "Nc-1 pZ Tachyzoite cDNA Library")
      .formParam("array(libraryIdGenes)", "differentially expressed gene in tachyzoite stage of Neospora caninum Korea isolate Nc-Kr2 (Tachyzoites)")
      .formParam("array(libraryIdGenes)", "Nc-1 Tachyzoite cDNA Library")
      .formParam("array(libraryIdGenes)", "Nc-LIV Tachyzoite cDNA Library")
      .formParam("array(libraryIdGenes)", "Eimeria brunetti")
      .formParam("array(libraryIdGenes)", "Sporozoites of Eimeria brunetti")
      .formParam("array(libraryIdGenes)", "Eimeria falciformis")
      .formParam("array(libraryIdGenes)", "Eimeria falciformis cDNAs (parasite gametogenesis)")
      .formParam("array(libraryIdGenes)", "Eimeria maxima")
      .formParam("array(libraryIdGenes)", "Eimeria maxima Nantong (NT) strain subtractive cDNA library of unsporulated oocysts constructed by SSH (unsproluted stage)")
      .formParam("array(libraryIdGenes)", "sporulated oocysts subtracted cDNA library")
      .formParam("array(libraryIdGenes)", "Eimeria maxima Weybridge")
      .formParam("array(libraryIdGenes)", "ORESTES cDNA library; Eimeria maxima merozoites")
      .formParam("array(libraryIdGenes)", "Eimeria maxima Guelph excysted sporozoite")
      .formParam("array(libraryIdGenes)", "ORESTES cDNA library; Eimeria maxima unsporulated oocysts")
      .formParam("array(libraryIdGenes)", "Eimeria maxima Shanghai (SH) strain subtractive cDNA library of unsporulated oocysts constructed by SSH (unsproluted stage)")
      .formParam("array(libraryIdGenes)", "ORESTES cDNA library; Eimeria maxima sporulated oocysts")
      .formParam("array(libraryIdGenes)", "Eimeria maxima merozoite life stage cDNA library")
      .formParam("array(libraryIdGenes)", "ORESTES cDNA library; Eimeria maxima sporoblast-phase oocysts")
      .formParam("array(libraryIdGenes)", "ORESTES cDNA library; Eimeria maxima sporulating oocysts")
      .formParam("array(libraryIdGenes)", "Eimeria maxima Houghton")
      .formParam("weight", "10")
      .formParam("value(bp_overlap_gte)", "100")
      .formParam("array(best_alignment_only)", "Yes")
      .formParam("value(max_number_best_alignments)", "1")
      .formParam("array(high_confidence_only)", "No")
      .formParam("value(min_percent_identity)", "90")
      .formParam("value(min_percent_est_aligned)", "20")
      .formParam("value(min_number_est_aligned)", "1")
      .formParam("questionSubmit", "Get Answer")
      .formParam("customName", "")
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
      .check(regex("Number of ESTs").exists)
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