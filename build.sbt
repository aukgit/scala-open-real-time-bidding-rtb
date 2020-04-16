import sbt.Keys._

name := "scala-open-rtb-example"
version := "1.0"
scalaVersion := "2.13.1"

lazy val log4Version = "2.11.0"
lazy val akkaVersion = "2.6.4"
lazy val akkaHttpVersion = "10.1.11"
lazy val circeVersion = "0.12.3"

// More details at https://github.com/tototoshi/slick-joda-mapper
lazy val slickVersion = "3.3.2"
lazy val slickJodaMapperVersion = "2.4"

libraryDependencies ++= Seq(
  ///////////////////////////////////////////////////
  // Akka core
  ///////////////////////////////////////////////////

  // "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-remote" % akkaVersion,
  "com.typesafe.akka" %% "akka-persistence" % akkaVersion,
  "com.typesafe.akka" %% "akka-persistence-query" % akkaVersion,
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "de.heikoseeberger" %% "akka-http-circe" % "1.31.0",
  "com.typesafe.akka" %% "akka-http-caching" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-stream" % "2.5.26",
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,

  ///////////////////////////////////////////////////
  // Serialization frameworks
  ///////////////////////////////////////////////////

  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,

  ///////////////////////////////////////////////////
  // Serialization frameworks : AKKA Enhanced Serializing
  ///////////////////////////////////////////////////

  "io.altoo" %% "akka-kryo-serialization" % "1.0.0" excludeAll (ExclusionRule("com.esotericsoftware", "kryo-shaded")),
  "com.esotericsoftware" % "kryo" % "4.0.2",

  ///////////////////////////////////////////////////
  // Logging frameworks
  ///////////////////////////////////////////////////

  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "org.apache.logging.log4j" % "log4j-api" % "2.13.1",
  "org.apache.logging.log4j" % "log4j-core" % "2.13.1",
  "io.sentry" % "sentry-log4j2" % "1.7.30",
  "org.slf4j" % "slf4j-nop" % "1.7.26",
  "com.typesafe.play" %% "play-logback" % "2.8.1",

  ///////////////////////////////////////////////////
  // Persistent or ORM frameworks
  ///////////////////////////////////////////////////

  "com.typesafe.slick" %% "slick" % slickVersion,
  "com.typesafe.slick" %% "slick-codegen" % slickVersion,
  "org.xerial" % "sqlite-jdbc" % "3.30.1",

  ///////////////////////////////////////////////////
  // Unit Test Frameworks
  ///////////////////////////////////////////////////

  "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
  "org.scalatest" %% "scalatest" % "3.1.0" % Test,
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test,
  "com.typesafe.play" %% "play-specs2" % "2.8.1" % Test,

  ///////////////////////////////////////////////////
  // Scala Core Packages
  ///////////////////////////////////////////////////

  "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  // "org.scala-lang" % "scala-reflect" % "2.10.0",
  "org.scala-lang" % "scala-compiler" % scalaVersion.value,
  "org.scala-lang" % "scala-library" % scalaVersion.value,

  ///////////////////////////////////////////////////
  // Play framework
  ///////////////////////////////////////////////////

  "com.typesafe.play" %% "twirl-api" % "1.5.0",
  "com.typesafe.play" %% "play-json" % "2.8.1",
  "com.typesafe.play" %% "play-server" % "2.8.1",
  "com.typesafe.play" %% "filters-helpers" % "2.8.1",
  "com.typesafe.play" %% "play-slick" % "5.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0" % Test,

  ///////////////////////////////////////////////////
  // Others
  ///////////////////////////////////////////////////

  "org.webjars" %% "webjars-play" % "2.8.0"
)

lazy val root = (project in file("."))
  .enablePlugins(
    PlayService,
    PlayLayoutPlugin,
    Common,
    PlayScala)
  .settings(
    name := "scala-open-rtb-example",
    version := "2.8.x",
    scalaVersion := "2.13.1",
    libraryDependencies ++= Seq(
      guice,
      "org.joda" % "joda-convert" % "2.2.1",
      "net.logstash.logback" % "logstash-logback-encoder" % "6.2",
      "io.lemonlabs" %% "scala-uri" % "1.5.1",
      "net.codingwell" %% "scala-guice" % "4.2.6",
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test,
      "com.typesafe.play" %% "play-slick" % "5.0.0",
      "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0"
    ),

    scalacOptions ++= Seq(
      "-encoding",
      "utf8",
      "-deprecation",
      "-feature",
      "-unchecked",
      "-Xfatal-warnings"
    )
  )



lazy val gatlingVersion = "3.3.1"
lazy val gatling = (project in file("gatling"))
  .enablePlugins(GatlingPlugin)
  .settings(
    scalaVersion := scalaVersion.value,
    libraryDependencies ++= Seq(
      "io.gatling.highcharts" % "gatling-charts-highcharts" % gatlingVersion % Test,
      "io.gatling" % "gatling-test-framework" % gatlingVersion % Test
    )
  )

// Documentation for this project:
//    sbt "project docs" "~ paradox"
//    open docs/target/paradox/site/index.html
lazy val docs = (project in file("docs"))
  .enablePlugins(ParadoxPlugin).
  settings(
    scalaVersion := scalaVersion.value
  )
