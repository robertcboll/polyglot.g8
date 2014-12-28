import sbt._
import Keys._

object Package {

  import com.typesafe.sbt.{SbtNativePackager => Packager}
  import Packager.{Linux, Debian, packageArchetype}

  import com.typesafe.sbt.packager 
  import packager.archetypes.ServerLoader
  import packager.Keys._


  val appUser = "$app_user$"
  val appGroup = "$app_group$"
  val appMaintainer = "$app_maintainer$"
  val appDescription = "$app_description$"

  lazy val packaging = Seq(
    maintainer in Linux := appMaintainer,
    packageSummary in Linux := appDescription,
    packageDescription := appDescription,
    
    daemonUser in Linux := appUser,
    daemonGroup in Linux := appUser,

    serverLoading in Debian := ServerLoader.SystemV,
    packageBin in Debian <<= debianJDebPackaging in Debian
  )

  lazy val app = packageArchetype.java_application ++ packaging
  lazy val server = packageArchetype.java_server ++ packaging

  def recommends(projects: String*) = {
    packageArchetype.java_application ++ packaging ++ Seq(
      debianPackageRecommends in Debian <<= (version in Linux) { (v) =>
        projects map { id: String =>
          s"\$id (>= \$v)"
        }
      }
    )
  }
}

