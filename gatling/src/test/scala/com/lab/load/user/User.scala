package com.lab.load.user

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object User {

  val get = http("USER_GET")
    .get("/users/${userId}")
    .asJSON
    .check(status.is(200))
    .check(jsonPath("$.name").exists.saveAs("foundUserName"))

  val create = http("USER_CREATE")
    .post("/users/${userId}")
    .body(ElFileBody("userBody.json"))
    .asJSON
    .check(status.is(201))

}
