import sbt._
import com.ondeck.sbt._
import MigrationsPlugin.Keys.Migration

object Versions {

  val slf4j       = "1.7.7"
  val logback     = "1.1.2"

  val guava       = "18.0"

  val postgres    = "9.3-1102-jdbc41"
}

object Dependencies {

  val slf4j           =   "org.slf4j"           %   "slf4j-api"         %   Versions.slf4j
  val logback         =   "ch.qos.logback"      %   "logback-classic"   %   Versions.logback  % Test
  val javalogging     =   Seq(slf4j, logback)

  val guava           =   "com.google.guava"    %   "guava"             %   Versions.guava

  val postgres        =   "org.postgresql"      %   "postgresql"        %   Versions.postgres % Migration


  val migrations      =   Seq(postgres)
}

