addCommandAlias("fix", "; all compile:scalafix test:scalafix it:scalafix; scalafmtAll; scalafmtSbt")
addCommandAlias(
  "up2date",
  "reload plugins; dependencyUpdates; reload return; dependencyUpdates",
)

Global / onChangedBuildSource := ReloadOnSourceChanges
Test / turbo                  := true

ThisBuild / scalafixScalaBinaryVersion := (if (tlIsScala3.value) "3.3" else "2.13")
ThisBuild / turbo                      := true
