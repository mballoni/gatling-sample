package com.lab.load.config

import java.lang.System.{getProperty, setProperty}
import java.nio.file.{Files, Paths}

import scala.Option.apply

object Configuration {
  var path = apply(getProperty("config.file.path"))
    .orElse(apply("src/test/resources/default.properties"))
    .map(fileLocation => Paths.get(fileLocation))
    .get

  Files.lines(path)
    .forEach(line => {
      val keyValue = line.split("=")
      println("Setting up line: " + line)
      setProperty(keyValue.apply(0), keyValue.apply(1))
    })

  def get(propertyName: String) = getProperty(propertyName)

}