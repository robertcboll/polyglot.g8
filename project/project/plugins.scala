import sbt._
import Keys._

object PluginDef extends Build {
   
  override def projects = Seq(root)

  lazy val root = Project("plugins", file(".")) dependsOn 
    ProjectRef(uri("https://github.com/n8han/giter8.git#7e18719d6b8504f0c0ada1c8931725142e9054fb"), "giter8-plugin")
}

