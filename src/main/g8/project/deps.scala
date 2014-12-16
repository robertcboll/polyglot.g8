import sbt._
import sbtbase._
import plugin.Migrations.Migration


object Versions {

  val slf4j       = "$version_slf4j$"
  val logback     = "$version_logback$"

  val guava       = "$version_guava$"

  val postgres    = "$version_postgres$"
}

object Dependencies {

  val slf4j           = "org.slf4j"           %   "slf4j-api"         %   Versions.slf4j
  val logback         = "ch.qos.logback"      %   "logback-classic"   %   Versions.logback  % Test
  val javalogging     = Seq(slf4j, logback)

  val guava           = "com.google.guava"    %   "guava"             %   Versions.guava

  val postgres        = "org.postgresql"      %   "postgresql"        %   Versions.postgres % Migration

  val migrations      = Seq(postgres)
  
  object Projects {
    val core          = Seq()
  }
}

