package com.lab.load

import com.lab.load.config.Protocol
import com.lab.load.user.User
import io.gatling.core.Predef._

import scala.concurrent.duration._


class SimulationTest extends Simulation {

  val userFeeder = csv("users").circular

  val r = scala.util.Random

  val userScenario = scenario("UserScenario")
    .feed(userFeeder)
    .forever(
      exec(_.set("userId", r.nextInt(1000000)))

        .exec(User.create)

        .randomSwitch(
          50.0 -> pause(2 seconds, 3 seconds).exec(User.get)
        )

        .exec(session => {
          val str = session.get("foundUserName").asOption[String]
            .getOrElse[String]("")
          //      println(str)
          session
        })
    )


  setUp(
    userScenario.inject(
      atOnceUsers(50), //warm up
      nothingFor(20 seconds),
      rampUsers(100) over (30 seconds),
      rampUsers(100) over (10 seconds),
      atOnceUsers(150)
    )
  )
    .maxDuration(5 minutes)
    .protocols(Protocol.HTTP)
}