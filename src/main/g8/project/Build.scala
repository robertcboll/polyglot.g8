import sbt._
import Keys._


object Build extends sbt.Build {

  import com.ondeck.sbt._
  import Templates._
  import ExtraSettings._
  import ToolSettings._
  import MigrationsPlugin._

  override lazy val settings = super.settings ++ mavenPublish ++
    Seq(
      organization := "$org$",
      name := "$name$",
      scalaVersion := "2.11.4"
    )

  import Dependencies.Projects

  lazy val root = RootProject("$name$-root")
    .aggregate(/* add new modules here */)
    .settings(migrations: _*)
    .settings(giter8.ScaffoldPlugin.scaffoldSettings: _*)
    .settings(libraryDependencies ++= Dependencies.migrations)

  import com.typesafe.sbt.SbtNativePackager._
  import NativePackagerKeys._

  /* 
  // documentation project
  lazy val docs = DocProject("$name$-docs", deps = Seq(api, core, client, service, scala))
  */

  /*
  // java project
  lazy val java = JavaProject("$name$-java", deps = Seq(scala))
    .settings(libraryDependencies ++= Dependencies.javaDeps)
  */

  /*
  // scala project
  lazy val scala = ScalaProject("$name$-scala", deps = Seq(java))
    .settings(libraryDependencies ++= Dependencies.scalaDeps)
  */

  /*
  // app project
  lazy val runnable = JavaProject("$name$-app", deps = Seq(java, scala))
    .settings(libraryDependencies ++= Dependencies.appDeps)
    .settings(runnable: _*)
    .settings(targz: _*)
    .settings(mainClass := Some("mainClass"))
  */
}

