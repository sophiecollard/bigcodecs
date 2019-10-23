lazy val scala211 = "2.11.12"
lazy val scala212 = "2.12.10"
lazy val scala213 = "2.13.0"
lazy val supportedScalaVersions = List(scala211, scala212, scala213)

// Taken from https://github.com/circe/circe/blob/master/build.sbt
def priorTo213(scalaVersion: String): Boolean =
  CrossVersion.partialVersion(scalaVersion) match {
    case Some((2, minor)) if minor < 13 => true
    case _                              => false
  }

lazy val root = project
  .in(file("."))
  .aggregate(core)
  .settings(crossScalaVersions := Nil, publish / skip := true)

lazy val core = project
  .in(file("core"))
  .settings(crossScalaVersions := supportedScalaVersions)
  .settings(metadataSettings)
  .settings(scalaSettings)
  .settings(scalacSettings)
  .settings(resolverSettings)
  .settings(pluginSettings)
  .settings(testSettings)
  .withDependencies

lazy val metadataSettings = Seq(
  organization := "com.github.sophiecollard",
  organizationHomepage := Some(url("https://github.com/sophiecollard")),
  name := "bigcodecs"
)

lazy val pluginSettings = Seq(
  addCompilerPlugin("org.typelevel" % "kind-projector" % "0.11.0" cross CrossVersion.full)
)

lazy val resolverSettings = Seq(
  resolvers += Resolver.sonatypeRepo("releases") // required by kind-projector plugin
)

lazy val scalaSettings = Seq(
  scalaVersion := scala213
)

lazy val scalacSettings = Seq(
  scalacOptions ++= Seq(
    "-deprecation",
    "-feature",
    "-language:higherKinds",
    "-Xfatal-warnings"
  ) ++ {
    if (priorTo213(scalaVersion.value))
      Seq("-Ypartial-unification") // required by cats for Scala 2.11 and 2.12
    else
      Nil
  }
)

lazy val testSettings = Seq(
  parallelExecution in Test := false,
  scalacOptions in Test ++= Seq("-Yrangepos") // required by specs2
)
