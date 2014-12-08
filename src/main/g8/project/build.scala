import sbt._
import Keys._


object Build extends sbt.Build {

  import sbtbase._
  import Templates._
  import ExtraSettings._
  import plugin.Migrations._

  import com.typesafe.sbt.SbtGit._
  import GitKeys._


  val repoBase = "$repo_base$"
  val resolverBase = "$resolver_base$"
  val gitverBase = "$gitver_base$"
  val releaseBase = "$release_base$"
  val mavenRelease = $maven_release$


  override lazy val settings = super.settings ++
    Seq(
      organization := "$package$",
      name := "$name$",
      scalaVersion := "$version_scala$",
      resolvers += "$resolver_name$" at s"\$repoBase\$resolverBase",
      credentials += Credentials(Path.userHome / ".sbt" / ".credentials"),
      publishTo <<= (version, gitHeadCommit) { (version, gitHeadCommit) =>
        val resolverType = if (mavenRelease) Resolver.mavenStylePatterns else Resolver.ivyStylePatterns

        val gitver = !gitHeadCommit.isEmpty && version.endsWith(gitHeadCommit.get)

        val publishRepoBase = if (gitver) gitverBase else releaseBase

        Some(Resolver.url("publish", url(s"\$repoBase\$publishRepoBase"))(resolverType))
      },
      publishMavenStyle := mavenRelease
    )

  import Dependencies.Projects

  lazy val root = RootProject("$name$")
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

 /*
  // artifactory 
  import com.typesafe.sbt.SbtGit._
  import GitKeys._
  
  val repo = ""
  val repoName = ""
  val creds = Credentials(Path.userHome / ".sbt" / ".credentials")

  lazy val artifactory = Seq(
    resolvers += "internal" at repo + "repo"
    )

  lazy val mavenPublish = Seq(
    publishTo <<= (version, gitHeadCommit) { (version, gitHeadCommit) =>
      val snapshotName = s"\$repoName-gitver"
      val releaseName = s"\$repoName-releases"

      val snapshots = Some(Resolver.url(snapshotName, url(repo + snapshotName))(Resolver.mavenStylePatterns))
      val releases = Some(Resolver.url(releaseName, url(repo + releaseName))(Resolver.mavenStylePatterns))

      gitHeadCommit match {
        case Some(commit) => 
          if (version.endsWith(commit)) snapshots
          else releases
        case None => releases
      }
    },
    publishMavenStyle := true,
    credentials += creds
    )

  lazy val ivyPublish = Seq(
    publishTo <<= (version, gitHeadCommit) { (version, gitHeadCommit) =>
      val snapshotName = s"\$repoName-ivy-gitver"
      val releaseName = s"\$repoName-ivy-release"

      val snapshots = Some(Resolver.url(snapshotName, url(artifactory + snapshotName))(Resolver.ivyStylePatterns))
      val releases = Some(Resolver.url(releaseName, url(artifactory + releaseName))(Resolver.ivyStylePatterns))

      gitHeadCommit match {
        case Some(commit) => 
          if (version.endsWith(commit)) snapshots
          else releases
        case None => releases
      }
    },
    publishMavenStyle := false,
    credentials += creds
    )

 */
}

