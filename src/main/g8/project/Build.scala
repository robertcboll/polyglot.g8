import sbt._
import Keys._


object Build extends sbt.Build {

  import com.typesafe.sbt.SbtGit._
  import GitKeys._

  lazy val artifactory = "https://build.ondeck.local/artifactory/"

  override lazy val settings = super.settings ++
    Seq(
      organization := "$org$",
      name := "$name$",
      scalaVersion := "2.11.4",
      resolvers += "ondeck" at artifactory + "repo",
      publishMavenStyle := true,
      credentials += Credentials(Path.userHome / ".sbt" / ".credentials"),
      publishTo <<= (version, gitHeadCommit) { (version, gitHeadCommit) =>
        val snapshots = "ondeckcapital-snapshot-non-unique"
        val releases = "ondeckcapital-release"
        gitHeadCommit match {
          case Some(commit) => Some(snapshots at artifactory + snapshots)
          case None => Some(releases at artifactory + releases)
        }
      }
    )

  import com.ondeck.sbt._
  import Templates._
  import MigrationsPlugin._

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

