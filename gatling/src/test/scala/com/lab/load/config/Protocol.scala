package com.lab.load.config

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object Protocol {
  val HTTP = http
    .baseURL(Configuration.find("base.url").get)
    .doNotTrackHeader("1")
    .inferHtmlResources()
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .userAgentHeader("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:59.0) Gecko/20100101 Firefox/59.0")
}
