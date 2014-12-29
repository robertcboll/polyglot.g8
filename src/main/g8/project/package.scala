import sbt._
import Keys._

object Package {

  import com.typesafe.sbt.{SbtNativePackager => Packager}
  import Packager.{Linux, Debian, packageArchetype}

  import com.typesafe.sbt.packager 
  import packager.Keys._
  import packager.archetypes.ServerLoader
  import packager.debian.JDebPackaging

  import sbtbase.Packaging

  val appUser = "$app_user$"
  val appUserUid = "$app_user_uid$"
  val appGroup = "$app_group$"
  val appGroupGid = "$app_group_gid$"
  val appMaintainer = "$app_maintainer$"
  val appDescription = "$app_description$"

  lazy val settings = Packaging.settings ++ JDebPackaging.projectSettings ++ Seq(
    maintainer in Linux := appMaintainer,
    packageSummary in Linux := appDescription,
    packageDescription := appDescription,
    
    daemonUser in Linux := appUser,
    daemonUserUid in Linux := Some(appUserUid),
    daemonGroup in Linux := appGroup,
    daemonGroupGid in Linux := Some(appGroupGid),

    serverLoading in Debian := ServerLoader.SystemV
  )

  lazy val app = packageArchetype.java_application ++ settings
  lazy val server = packageArchetype.java_server ++ settings
  
  def recommends(projects: String*): Seq[sbt.Def.Setting[_]] = {
    app ++ Seq(
      debianPackageRecommends in Debian <<= (version in Linux) { (v) =>
        projects map { id: String =>
          s"\$id (>= \$v)"
        }
      }
    )
  }
}

