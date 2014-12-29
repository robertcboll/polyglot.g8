import sbt._
import Keys._

object Build extends sbt.Build {

  import sbtbase._
  import Templates._

  /*                   */
  /* common settings   */
  /*                   */
  val javaVersion = "$version_java$"

  override lazy val settings = super.settings ++ JvmVersion.settings(javaVersion) ++ Publish.deploy ++
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
  import com.typesafe.sbt.SbtSite.site

  lazy val docs = DocProject("docs", deps = api, core, client, service, scala)
    .settings(site.pamfletSupport(): _*) // or other
  */

  /*
  // migrations project
  lazy val migrations = MigrationsProject("migrations")
    .settings(libraryDependencies ++= Projects.migrations)
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
    .settings(libraryDependencies ++= Projects.appDeps)
    .settings(mainClass := Some("mainClass"))
  */

  /*
  // app project
  lazy val app = JavaProject("$name$-app", deps = java, scala)
    .settings(Package.app: _*)
    .settings(libraryDependencies ++= Projects.appDeps)
    .settings(mainClass := Some("mainClass"))
  */


  /* 
    // for sequential (integration) tests
    import de.johoop.jacoco4sbt.JacocoPlugin._

    .settings(parallelExecution in IntegrationTest := false)
    .settings(parallelExecution in itJacoco.Config := false)
   */
}

