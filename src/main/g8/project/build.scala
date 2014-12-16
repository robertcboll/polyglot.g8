import sbt._
import Keys._

object Build extends sbt.Build {

  import sbtbase._
  import Templates._

  import com.typesafe.sbt.SbtGit._
  import GitKeys._

  /*                   */
  /* common settings   */
  /*                   */
  val repoBase = "$repo_base$"
  val resolverBase = "$resolver_base$"
  val gitverBase = "$gitver_base$"
  val releaseBase = "$release_base$"
  val mavenRelease = $maven_release$

  val javaVersion = "$version_java$"

  override lazy val settings = super.settings ++ JvmVersion.settings(javaVersion) ++
    Seq(
      organization := "$package$",
      name := "$name$",
      scalaVersion := "$version_scala$",

      updateOptions := updateOptions.value.withCachedResolution(true),

      resolvers += "$resolver_name$" at s"\$repoBase\$resolverBase",
      publishMavenStyle := mavenRelease,
      credentials += Credentials(Path.userHome / ".sbt" / ".credentials"),
      publishTo <<= (version, gitHeadCommit) { (version, gitHeadCommit) =>
        val resolverType = if (mavenRelease) Resolver.mavenStylePatterns else Resolver.ivyStylePatterns

        val gitver = !gitHeadCommit.isEmpty && version.endsWith(gitHeadCommit.get)

        val publishRepoBase = if (gitver) gitverBase else releaseBase

        Some(Resolver.url("publish", url(s"\$repoBase\$publishRepoBase"))(resolverType))
      }
    )

  /*                      */
  /* packaging settings   */
  /*                      */
  import com.typesafe.sbt.SbtNativePackager._
  import NativePackagerKeys._

  import com.typesafe.sbt.packager._
  import archetypes.ServerLoader

  val appUser = "$maintainer$"
  val appMaintainer = "$package_summary$"
  val appDescription = "$package_description$"

  val packaging = packageArchetype.java_server ++ Seq(
    maintainer in Linux := appMaintainer,
    packageSummary in Linux := appDescription,
    packageDescription := appDescription,

    daemonUser in Linux := appUser,
    daemonGroup in Linux := appUser,
    
    serverLoading in Debian := ServerLoader.SystemV,
    packageBin in Debian <<= debianJDebPackaging in Debian,
    mappings in Universal <++= sourceDirectory map { src => Seq(
      src / "main" / "resources" / "reference.conf" -> "conf/application.conf",
      src / "main" / "resources" / "logback.xml" -> "conf/logback.xml"
      )
    }
  )

  /*                      */
  /* project definitions  */
  /*                      */
  import Dependencies.Projects

  lazy val root = RootProject("$name$")
    .settings(giter8.ScaffoldPlugin.scaffoldSettings: _*)
    .settings(plugin.Migrations.migrations: _*)
    .aggregate(/* add new modules here */)
    .settings(libraryDependencies ++= Dependencies.migrations)

  /*
  // documentation project
  import com.typesafe.sbt.SbtSite.site

  lazy val docs = DocProject("$name$-docs", deps = api, core, client, service, scala)
    .settings(site.pamfletSupport(): _*) // or other
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
  // app project
  lazy val runnable = JavaProject("$name$-app", deps = java, scala)
    .settings(libraryDependencies ++= Projects.appDeps)
    .settings(Tools.runnable: _*)
    .settings(packaging: _*)
    .settings(mainClass := Some("mainClass"))
  */

  /* 
    // for sequential (integration) tests
    import de.johoop.jacoco4sbt.JacocoPlugin._

    .settings(parallelExecution in IntegrationTest := false)
    .settings(parallelExecution in itJacoco.Config := false)
   */
}

