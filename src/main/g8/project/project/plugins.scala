import sbt._
import Keys._

object PluginDef extends Build {
  
  override def projects = Seq(root)

  lazy val root = Project("plugins", file(".")) dependsOn 
    RootProject(uri("ssh://git@github.com/robertcboll/sbt-base.git#v$version_sbt_base$"))
}

