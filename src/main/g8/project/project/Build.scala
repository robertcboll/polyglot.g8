import sbt._
import Keys._

object PluginDef extends Build {
  
  override lazy val settings = super.settings ++ Seq(
    resolvers += Resolver.url("ondeck", url("https://build.ondeck.local/artifactory/ondeck-ivy-release"))(Resolver.ivyStylePatterns),
    addSbtPlugin("com.ondeck.sbt" % "sbt-base" % "$sbt_base_version$")
    )
}

