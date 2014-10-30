import sbt._


object PluginDef extends Build {
  
  override def projects = Seq(root)

  lazy val root = Project("plugins", file(".")) dependsOn 
    RootProject(uri("ssh://stash.ondeck.local:7999/sch/sbt-base.git#v0.1"))
}

