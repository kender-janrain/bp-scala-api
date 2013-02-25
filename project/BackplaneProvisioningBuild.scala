import sbt._
import sbt.Keys._

object BackplaneProvisioningBuild extends Build {
  sbtVersion := "0.12.2"

  lazy val backplaneProvisioning = Project(
    id = "backplane-provisioning",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      name := "Backplane Provisioning",
      organization := "com.janrain",
      version := "0.1-SNAPSHOT",
      scalaVersion := "2.10.0",
      libraryDependencies ++= Seq (
        "io.spray" % "spray-client" % "1.1-M7",
        "io.spray" % "spray-httpx" % "1.1-M7",
        "io.spray" %%  "spray-json" % "1.2.3",
        "com.typesafe.akka" %% "akka-actor" % "2.1.0"
      ),
      resolvers ++= Seq(
        "spray repo" at "http://repo.spray.io"
       )
      // add other settings here
    )

  )
}
