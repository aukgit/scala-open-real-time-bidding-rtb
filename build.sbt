name := "scala-rtb-example"

version := "1.0"

scalaVersion := "2.13.1"

lazy val log4Version = "2.11.0"

libraryDependencies += "org.apache.logging.log4j" % "log4j-api" % "2.13.1"
libraryDependencies += "org.apache.logging.log4j" % "log4j-core" % "2.13.1"

lazy val akkaVersion = "2.6.4"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
  "org.scalatest" %% "scalatest" % "3.1.0" % Test
)
