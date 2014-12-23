import sbt._
import Keys._

object Packaging {

  /*                      */
  /* packaging settings   */
  /*                      */
  import com.typesafe.sbt.SbtNativePackager._
  import NativePackagerKeys._

  import com.typesafe.sbt.packager._
  import archetypes.ServerLoader

  val appUser = "$app_user$"
  val appGroup = "$app_group$"
  val appDescription = "$app_description$"
  val appMaintainer = "$app_maintainer$"

  lazy val packaging = Seq(
    maintainer in Linux := appMaintainer,
    packageSummary in Linux := appDescription,
    packageDescription := appDescription,

    daemonUser in Linux := appUser,
    daemonGroup in Linux := appUser,
    
    serverLoading in Debian := ServerLoader.SystemV,
    packageBin in Debian <<= debianJDebPackaging in Debian
  )

  lazy val serverPackaging = packaging ++ packageArchetype.java_server

  lazy val appPackaging = packaging ++ packageArchetype.java_application
}

