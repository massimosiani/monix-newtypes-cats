// https://typelevel.org/sbt-typelevel/faq.html#what-is-a-base-version-anyway
ThisBuild / tlBaseVersion := "0.0" // your current series x.y

ThisBuild / organization     := "io.github.massimosiani"
ThisBuild / organizationName := "Massimo Siani"
ThisBuild / startYear        := Some(2022)
ThisBuild / licenses         := Seq(License.Apache2)
ThisBuild / developers       := List(
  // your GitHub handle and name
  tlGitHubDev("massimosiani", "Massimo Siani")
)

// publish to s01.oss.sonatype.org (set to true to publish to oss.sonatype.org instead)
ThisBuild / tlSonatypeUseLegacyHost := false

// publish website from this branch
ThisBuild / tlSitePublishBranch := Some("main")

val Scala213 = "2.13.10"
ThisBuild / crossScalaVersions := Seq(Scala213, "3.1.1")
ThisBuild / scalaVersion       := Scala213 // the default Scala

lazy val root = tlCrossRootProject.aggregate(`monix-newtypes-cats`)

lazy val `monix-newtypes-cats` = crossProject(JVMPlatform, JSPlatform)
  .crossType(CrossType.Pure)
  .in(file("monix-newtypes-cats"))
  .settings(
    name := "monix-newtypes-cats",
    libraryDependencies ++= Seq(
      "io.monix"       %%% "newtypes-core"    % "0.2.3",
      "org.typelevel"  %%% "cats-core"        % "2.8.0",
      "org.scalacheck" %%% "scalacheck"       % "1.17.0" % Test,
      "org.scalameta"  %%% "munit"            % "0.7.29" % Test,
      "org.scalameta"  %%% "munit-scalacheck" % "0.7.29" % Test,
    ),
  )

lazy val docs = project.in(file("site")).enablePlugins(TypelevelSitePlugin)
