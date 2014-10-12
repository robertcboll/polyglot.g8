import sbt._
import io.steeltoe.sbt._
import MigrationsPlugin.Keys.Migration

object Versions {

  val slf4j       = "$slf4j_version$"
  val logback     = "$logback_version$"

  val guava       = "$guava_version$"

  val postgres    = "$postgres_version$"
}

object Dependencies {

  val slf4j           =   "org.slf4j"           %   "slf4j-api"         %   Versions.slf4j
  val logback         =   "ch.qos.logback"      %   "logback-classic"   %   Versions.logback  % Test
  val javalogging     =   Seq(slf4j, logback)

  val guava           =   "com.google.guava"    %   "guava"             %   Versions.guava

  val postgres        =   "org.postgresql"      %   "postgresql"        %   Versions.postgres % Migration


  val migrations      =   Seq(postgres)
}

