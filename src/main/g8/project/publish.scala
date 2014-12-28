import sbt._
import Keys._

object Publish {

  import sbtbase._
  import com.typesafe.sbt.SbtGit.GitKeys.gitHeadCommit
 
  val repoBase = "$repo_base$"
  val mavenResolverBase = "$maven_resolver_base$"
  val ivyResolverBase = "$ivy_resolver_base$"
  val gitverBase = "$gitver_base$"
  val releaseBase = "$release_base$"
  val mavenRelease = $maven_release$

  lazy val deploy = Seq(
    updateOptions := updateOptions.value.withCachedResolution(true),

    resolvers += Resolver.url("$ivy_resolver_name$", url(s"\$repoBase\$ivyResolverBase"))(Resolver.ivyStylePatterns),
    resolvers += "$maven_resolver_name$" at s"\$repoBase\$mavenResolverBase",

    publishMavenStyle := mavenRelease,
    credentials += Credentials(Path.userHome / ".sbt" / ".credentials"),
    publishTo <<= (version, gitHeadCommit) { (version, gitHeadCommit) =>
      val resolverType = if (mavenRelease) Resolver.mavenStylePatterns else Resolver.ivyStylePatterns
      val gitver = !gitHeadCommit.isEmpty && version.endsWith(gitHeadCommit.get)
      val publishRepoBase = if (gitver) gitverBase else releaseBase

      Some(Resolver.url("publish", url(s"\$repoBase\$publishRepoBase"))(resolverType))
    }
  )
}

