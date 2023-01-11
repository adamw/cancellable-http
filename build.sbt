import com.softwaremill.SbtSoftwareMillCommon.commonSmlBuildSettings

lazy val commonSettings = commonSmlBuildSettings ++ Seq(
  organization := "com.softwaremill.http",
  scalaVersion := "2.13.10"
)

val scalaTest = "org.scalatest" %% "scalatest" % "3.2.15" % Test

lazy val rootProject = (project in file("."))
  .settings(commonSettings: _*)
  .settings(publishArtifact := false, name := "cancellable-http")
  .aggregate(core)

lazy val core: Project = (project in file("core"))
  .settings(commonSettings: _*)
  .settings(
    name := "core",
    libraryDependencies ++= Seq(
      "com.softwaremill.sttp.tapir" %% "tapir-netty-server" % "1.2.5",
      "ch.qos.logback" % "logback-classic" % "1.4.5",
      scalaTest
    )
  )

lazy val asyncHttpClient = (project in file("async-http-client"))
  .settings(commonSettings: _*)
  .settings(
    name := "async-http-client",
    libraryDependencies += "org.asynchttpclient" % "async-http-client" % "2.12.3"
  )
  .dependsOn(core)
