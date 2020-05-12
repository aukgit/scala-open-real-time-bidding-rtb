import sbt.Keys._


lazy val commonSettings = Seq(
  name := "commonSettings",
  organization := organizationName,
  version := version.value,
  scalaVersion := scalaVersionF
)

lazy val organizationName : String = "com.openrtb"
lazy val nameF : String = "scala-open-rtb-example"
lazy val scalaVersionF = "2.13.2"
lazy val log4Version = "2.11.0"
lazy val akkaVersion = "2.6.4"
lazy val akkaHttpVersion = "10.1.11"
lazy val circeVersion = "0.13.0"
lazy val enumeratumCirceVersion = "1.6.0"

// More details at https://github.com/tototoshi/slick-joda-mapper
lazy val slickVersion = "3.3.2"
lazy val slickJodaMapperVersion = "2.4.2"

lazy val allDependencies = Seq(
  ///////////////////////////////////////////////////
  // Akka core
  ///////////////////////////////////////////////////

  "com.typesafe.akka" %% "akka-remote" % akkaVersion,
  "com.typesafe.akka" %% "akka-persistence" % akkaVersion,
  "com.typesafe.akka" %% "akka-persistence-query" % akkaVersion,
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,

  "com.typesafe.akka" %% "akka-http-caching" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-stream" % "2.5.26",
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,

  ///////////////////////////////////////////////////
  // Serialization frameworks (JSON parsing, Circe)
  ///////////////////////////////////////////////////

  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "io.circe" %% "circe-optics" % circeVersion,
  "io.circe" %% "circe-derivation" % "0.13.0-M4",
  "de.heikoseeberger" %% "akka-http-circe" % "1.31.0",
  "com.beachape" %% "enumeratum-circe" % enumeratumCirceVersion,

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
  "org.apache.logging.log4j" % "log4j-slf4j-impl" % "2.4.1",
  "org.apache.logging.log4j" % "log4j-api" % "2.4.1",
  "org.apache.logging.log4j" % "log4j-core" % "2.4.1",
  "com.typesafe.play" %% "play-logback" % "2.8.1",

  ///////////////////////////////////////////////////
  // Persistent or ORM frameworks
  ///////////////////////////////////////////////////

  "com.typesafe.slick" %% "slick" % slickVersion,
  "com.typesafe.slick" %% "slick-codegen" % slickVersion,
  "org.xerial" % "sqlite-jdbc" % "3.30.1",

  ///////////////////////////////////////////////////
  // DateTime, Joda Mapper, Sqlite
  ///////////////////////////////////////////////////

  "org.joda" % "joda-convert" % "2.2.1",
  "com.github.tototoshi" %% "slick-joda-mapper" % slickJodaMapperVersion,
  "joda-time" % "joda-time" % "2.7",

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

  "org.scala-lang" % "scala-reflect" % scalaVersionF,
  "org.scala-lang" % "scala-compiler" % scalaVersionF,
  "org.scala-lang" % "scala-library" % scalaVersionF,

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

  "org.webjars" %% "webjars-play" % "2.8.0",
  "net.debasishg" %% "redisclient" % "3.20",
  "org.apache.commons" % "commons-configuration2" % "2.7",
  "com.github.dwickern" %% "scala-nameof" % "2.0.0" % "provided"
)

lazy val root = (project in file("."))
  .enablePlugins(
    PlayService,
    PlayLayoutPlugin,
    Common,
    PlayScala)
  .settings(
    name := nameF,
    version := "2.8.x",
    scalaVersion := scalaVersionF,
    libraryDependencies ++= allDependencies,
    libraryDependencies ++= Seq(
      guice,
      "net.logstash.logback" % "logstash-logback-encoder" % "6.2",
      "io.lemonlabs" %% "scala-uri" % "1.5.1",
      "net.codingwell" %% "scala-guice" % "4.2.6",
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test,
      "com.typesafe.play" %% "play-slick" % "5.0.0",
      "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0"
    )
  )
