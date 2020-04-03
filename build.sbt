name := "scala-rtb-example"

version := "1.0"

scalaVersion := "2.13.1"

lazy val log4Version = "2.11.0"

libraryDependencies += "io.sentry" % "sentry-log4j2" % "1.7.30"

val akkaVersion = "2.6.4"
val circeVersion = "0.12.3"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
  "org.scalatest" %% "scalatest" % "3.1.0" % Test,
  "org.apache.logging.log4j" % "log4j-api" % "2.13.1",
  "org.apache.logging.log4j" % "log4j-core" % "2.13.1",
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "io.rbricks" %% "itemized" % "0.2.0"
)
