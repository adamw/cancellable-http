import com.softwaremill.SbtSoftwareMillCommon.commonSmlBuildSettings

lazy val commonSettings = commonSmlBuildSettings ++ Seq(
  organization := "com.softwaremill.http",
  scalaVersion := "2.13.10"
)

val logback = "ch.qos.logback" % "logback-classic" % "1.4.5"
val http4sVersion = "0.23.17"

lazy val rootProject = (project in file("."))
  .settings(commonSettings: _*)
  .settings(publishArtifact := false, name := "cancellable-http")
  .aggregate(serverHttp4s, serverAkkaHttp, asyncHttpClient, httpClient)

lazy val serverHttp4s: Project = (project in file("server-http4s"))
  .settings(commonSettings: _*)
  .settings(
    name := "server-http4s",
    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-ember-server" % http4sVersion,
      "org.http4s" %% "http4s-dsl" % http4sVersion,
      logback
    )
  )

lazy val serverAkkaHttp: Project = (project in file("server-akka-http"))
  .settings(commonSettings: _*)
  .settings(
    name := "server-akka-http",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-stream" % "2.6.20",
      "com.typesafe.akka" %% "akka-http" % "10.2.10",
      logback
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
