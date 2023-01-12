import com.softwaremill.SbtSoftwareMillCommon.commonSmlBuildSettings

lazy val commonSettings = commonSmlBuildSettings ++ Seq(
  organization := "com.softwaremill.http",
  scalaVersion := "2.13.10"
)

val logback = "ch.qos.logback" % "logback-classic" % "1.4.5"
val http4sVersion = "0.23.17"
val jettyVersion = "11.0.13"

lazy val rootProject = (project in file("."))
  .settings(commonSettings)
  .settings(publishArtifact := false, name := "cancellable-http")
  .aggregate(serverHttp4s, serverAkkaHttp, serverJetty, asyncHttpClient, httpClient, okHttpClient)

lazy val serverHttp4s: Project = (project in file("server-http4s"))
  .settings(commonSettings)
  .settings(
    name := "server-http4s",
    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-ember-server" % http4sVersion,
      "org.http4s" %% "http4s-dsl" % http4sVersion,
      logback
    )
  )

lazy val serverAkkaHttp: Project = (project in file("server-akka-http"))
  .settings(commonSettings)
  .settings(
    name := "server-akka-http",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-stream" % "2.6.20",
      "com.typesafe.akka" %% "akka-http" % "10.2.10",
      logback
    )
  )

lazy val serverJetty: Project = (project in file("server-jetty"))
  .settings(commonSettings)
  .settings(
    name := "server-jetty",
    libraryDependencies ++= Seq(
      "org.eclipse.jetty" % "jetty-server" % jettyVersion,
      "org.eclipse.jetty.http2" % "http2-server" % jettyVersion,
      logback
    )
  )

lazy val asyncHttpClient = (project in file("async-http-client"))
  .settings(commonSettings)
  .settings(
    name := "async-http-client",
    libraryDependencies ++= Seq(
      "org.asynchttpclient" % "async-http-client" % "2.12.3",
      logback
    )
  )

lazy val httpClient = (project in file("http-client"))
  .settings(commonSettings)
  .settings(
    name := "http-client",
    libraryDependencies += logback
  )

lazy val okHttpClient = (project in file("ok-http-client"))
  .settings(commonSettings)
  .settings(
    name := "ok-http-client",
    libraryDependencies ++= Seq(
      "com.squareup.okhttp3" % "okhttp" % "4.10.0",
      logback
    )
  )
