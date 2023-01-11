import com.softwaremill.SbtSoftwareMillCommon.commonSmlBuildSettings

lazy val commonSettings = commonSmlBuildSettings ++ Seq(
  organization := "com.softwaremill.http",
  scalaVersion := "2.13.10"
)

val logback = "ch.qos.logback" % "logback-classic" % "1.4.5"
val scalaTest = "org.scalatest" %% "scalatest" % "3.2.15" % Test
val http4sVersion = "0.23.17"

lazy val rootProject = (project in file("."))
  .settings(commonSettings: _*)
  .settings(publishArtifact := false, name := "cancellable-http")
  .aggregate(core)

lazy val core: Project = (project in file("core"))
  .settings(commonSettings: _*)
  .settings(
    name := "core",
    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-ember-server" % http4sVersion,
      "org.http4s" %% "http4s-dsl" % http4sVersion,
      logback,
      scalaTest
    )
  )

lazy val asyncHttpClient = (project in file("async-http-client"))
  .settings(commonSettings: _*)
  .settings(
    name := "async-http-client",
    libraryDependencies ++= Seq(
      "org.asynchttpclient" % "async-http-client" % "2.12.3",
      logback
    )
  )

lazy val httpClient = (project in file("http-client"))
  .settings(commonSettings: _*)
  .settings(
    name := "http-client",
    libraryDependencies += logback
  )
