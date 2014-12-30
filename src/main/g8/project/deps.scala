import sbt._

import sbtbase.Tests.Keys.AllTests
import sbtbase.plugin.Migrations.Keys.Migration

object Versions {

  val junit       = "$version_junit$"
  val scalatest   = "$version_scalatest$"
  
  val slf4j       = "$version_slf4j$"
  val logback     = "$version_logback$"

  val guava       = "$version_guava$"

  val postgres    = "$version_postgres$"
}

object Dependencies {

  val junit       = "junit"         %   "junit"     % Versions.junit      % AllTests
  val scalatest   = "org.scalatest" %%  "scalatest" % Versions.scalatest  % AllTests
  val test        = Seq(junit, scalatest)

  val slf4j       = "org.slf4j"       % "slf4j-api"       % Versions.slf4j
  val logback     = "ch.qos.logback"  % "logback-classic" % Versions.logback  % AllTests
  val javalogging = Seq(slf4j, logback)

  val guava       = "com.google.guava"  % "guava" % Versions.guava

  val postgres    = "org.postgresql"  % "postgresql"  % Versions.postgres % Migration
  
  object Projects {
    val migrations  = Seq(postgres)

    val core        = Seq(test)
  }
}

