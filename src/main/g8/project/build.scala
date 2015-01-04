import sbt._
import Keys._

object Build extends sbt.Build {

  import sbtbase._
  import Templates._

  /*                   */
  /* common settings   */
  /*                   */
  val javaVersion = "$version_java$"

  override lazy val settings = super.settings ++ JvmVersion.settings(javaVersion) ++ Publish.settings ++
    Seq(
      organization := "$package$",
      name := "$name$",
      scalaVersion := "$version_scala$"
    )

  /*                      */
  /* project definitions  */
  /*                      */
  import Dependencies.Projects

  lazy val root = RootProject("$name$")
    .settings(giter8.ScaffoldPlugin.scaffoldSettings: _*)
    .settings(Package.recommends("" /* add dpkg deps here */): _*)
    .aggregate(/* add new modules here */)

  /*
  // documentation project
  lazy val docs = DocProject("docs", deps = api, core, client, service, scala)
    .settings(Docs.pamflet: _*) // or other
  */

  /*
  // java project
  lazy val java = JavaProject("$name$-java", deps = scala)
    .settings(libraryDependencies ++= Projects.javaDeps)
  */

  /*
  // scala project
  lazy val scala = ScalaProject("$name$-scala", deps = java)
    .settings(libraryDependencies ++= Projects.scalaDeps)
  */

  /*
  // server project
  lazy val server = JavaProject("$name$-server", deps = java, scala)
    .settings(Tools.runnable: _*)
    .settings(Package.server: _*)
    .settings(mainClass := Some("mainClass"))
    .settings(libraryDependencies ++= Projects.appDeps)
  */

  /*
  // app project
  lazy val app = JavaProject("$name$-app", deps = java, scala)
    .settings(Package.app: _*)
    .settings(mainClass := Some("mainClass"))
    .settings(libraryDependencies ++= Projects.appDeps)
  */


  /* 
    // for sequential (integration) tests
    import de.johoop.jacoco4sbt.JacocoPlugin._

    .settings(parallelExecution in IntegrationTest := false)
    .settings(parallelExecution in itJacoco.Config := false)
   */
}

