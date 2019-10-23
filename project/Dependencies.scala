import sbt.Keys._
import sbt._

object Dependencies extends AutoPlugin {

  object autoImport {
    implicit final class DependenciesProject(val project: Project) extends AnyVal {
      def withDependencies: Project =
        project.settings(dependencySettings)
    }
  }

  private val dependencySettings: Seq[Def.Setting[_]] = {
    libraryDependencies ++= Seq(
      "com.google.cloud" %  "google-cloud-bigquery" % "1.98.0",
      "org.typelevel"    %% "cats-core"             % "2.0.0",
      "org.specs2"       %% "specs2-core"           % "4.6.0" % Test
    )
  }

}
