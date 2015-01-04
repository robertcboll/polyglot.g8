import sbt._

object Versions {

  val junit       = "$version_junit$"
  val scalatest   = "$version_scalatest$"
  
  val slf4j       = "$version_slf4j$"
  val guava       = "$version_guava$"
}

object Dependencies {

  val junit       = "junit"         %   "junit"     % Versions.junit      % Test
  val scalatest   = "org.scalatest" %%  "scalatest" % Versions.scalatest  % Test
  val test        = Seq(junit, scalatest)

  val slf4j       = "org.slf4j"       % "slf4j-api"       % Versions.slf4j
  val slf4jSimple = "org.slf4j"       % "slf4j-simple"    % Versions.slf4j % Test
  val javalogging = Seq(slf4j, slf4jSimple)

  val guava       = "com.google.guava"  % "guava" % Versions.guava
 
  object Projects {
    val core        = test ++ javalogging ++ Seq(guava)
  }
}

