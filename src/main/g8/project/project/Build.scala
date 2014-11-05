import sbt._
import Keys._

object PluginDef extends Build {
  
  override lazy val settings = super.settings ++ Seq(
    resolvers += "ondeck" at "https://build.ondeck.local/artifactory/repo",
    addSbtPlugin("com.ondeck.sbt" % "sbt-base" % "0.3")
    )
}

