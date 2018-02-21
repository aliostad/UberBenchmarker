name := "UberBenchmarker"

version := "0.1"

scalaVersion := "2.12.4"

libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.2.18"
libraryDependencies += "com.github.scopt" %% "scopt" % "3.6.0"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.0" % "test"

lazy val root = (project in file(".")).
  enablePlugins(BuildInfoPlugin).
  settings(
    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
    buildInfoPackage := "org.aliostad.uberbenchmarker"
  )