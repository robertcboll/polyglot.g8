import sbt._
import Keys._

object Package {

  import com.typesafe.sbt.{SbtNativePackager => Packager}
  import Packager.{Linux, Debian, packageArchetype}

  import com.typesafe.sbt.packager 
  import packager.archetypes.ServerLoader
  import packager.Keys._

  import sbtbase.Packaging


  val appUser = "$app_user$"
  val appGroup = "$app_group$"
  val appMaintainer = "$app_maintainer$"
  val appDescription = "$app_description$"

  lazy val settings = Packaging.settings ++ Seq(
    maintainer in Linux := appMaintainer,
    packageSummary in Linux := appDescription,
    packageDescription := appDescription,
    
    daemonUser in Linux := appUser,
    daemonGroup in Linux := appUser,

    serverLoading in Debian := ServerLoader.SystemV,
    packageBin in Debian <<= debianJDebPackaging in Debian
  )

  lazy val app = packageArchetype.java_application ++ settings
  lazy val server = packageArchetype.java_server ++ settings
  
  def recommends(projects: String*) = {
    app ++ Seq(
      debianPackageRecommends in Debian <<= (version in Linux) { (v) =>
        projects map { id: String =>
          s"\$id (>= \$v)"
        }
      }
    )
  }
}

