import sbt._
import Keys._


object Build extends sbt.Build {

  override lazy val settings = super.settings ++
    Seq(
      organization := "$org$",
      name := "$name$"
    )

  import robb.sbt.Templates._
  import robb.sbt.MigrationsPlugin._

  import com.typesafe.sbt.SbtNativePackager.NativePackagerKeys._
  import com.typesafe.sbt.SbtNativePackager._

  lazy val root = RootProject("$name$-root")
    .aggregate(api, core, client, service, scala, docs)
    .settings(migrations: _*)
    .settings(libraryDependencies ++= Dependencies.migrations)

  lazy val docs = DocProject("$name$-docs", deps = Seq(api, core, client, service, scala))

  lazy val api = JavaProject("$name$-api")
    .settings(libraryDependencies ++= Dependencies.api)

  lazy val core = JavaProject("$name$-core", deps = Seq(api))
    .settings(libraryDependencies ++= Dependencies.core)

  lazy val client = JavaProject("$name$-client", deps = Seq(api))
    .settings(libraryDependencies ++= Dependencies.client)

  lazy val service = JavaProject("$name$-service", deps = Seq(core))
    .settings(runnable: _*)
    .settings(targz: _*)
    .settings(mainClass := Some("$org$.$name$.$name;format="Camel"$"))
    .settings(libraryDependencies ++= Dependencies.service)

  lazy val scala = ScalaProject("$name$-scala")
}

