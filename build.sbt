name := """addit-backend"""
organization := "adnan"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.8"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-slick" % "5.0.0",
  "org.postgresql" % "postgresql" % "42.2.12"
)

libraryDependencies ++= Seq(evolutions, jdbc)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "adnan.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "adnan.binders._"
