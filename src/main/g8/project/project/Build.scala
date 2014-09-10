import sbt._


object PluginDef extends Build {
  
  override def projects = Seq(root)

  lazy val root = Project("plugins", file(".")) dependsOn RootProject(uri("ssh://git@github.com/robertcboll/sbt-base.git"))
}

