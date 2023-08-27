import laika.ast.Path.Root
import laika.ast.Styles
import laika.helium.config.{HeliumIcon, IconLink, ThemeNavigationSection, TextLink}

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

val Scala213 = "2.13.11"
val Scala3   = "3.3.0"
ThisBuild / crossScalaVersions := Seq(Scala213, Scala3)
ThisBuild / scalaVersion       := Scala213 // the default Scala

lazy val root = tlCrossRootProject.aggregate(`monix-newtypes-cats`)

lazy val `monix-newtypes-cats` = crossProject(JVMPlatform, JSPlatform)
  .crossType(CrossType.Pure)
  .in(file("monix-newtypes-cats"))
  .settings(
    name := "monix-newtypes-cats",
    libraryDependencies ++= Seq(
      "io.monix"       %%% "newtypes-core"    % "0.2.3",
      "org.typelevel"  %%% "cats-core"        % "2.10.0",
      "org.scalacheck" %%% "scalacheck"       % "1.17.0"   % Test,
      "org.scalameta"  %%% "munit"            % "1.0.0-M8" % Test,
      "org.scalameta"  %%% "munit-scalacheck" % "1.0.0-M8" % Test,
    ),
  )

lazy val docs = project
  .in(file("site"))
  .enablePlugins(TypelevelSitePlugin)
  .settings(
    laikaConfig ~= { _.withRawContent },
    tlSiteHelium     := tlSiteHelium
      .value
      .site
      .topNavigationBar(
        homeLink = IconLink.internal(Root / "monix-newtypes-cats.md", HeliumIcon.home),
        navLinks = tlSiteApiUrl.value.toList.map { url =>
          IconLink.external(
            url.toString,
            HeliumIcon.api,
            options = Styles("svg-link"),
          )
        } ++ List(
          IconLink.external(
            scmInfo.value.fold("https://github.com/massimosiani")(_.browseUrl.toString),
            HeliumIcon.github,
            options = Styles("svg-link"),
          )
        ),
      )
      .site
      .mainNavigation(appendLinks =
        Seq(
          ThemeNavigationSection(
            "monix/newtypes",
            TextLink.external("https://newtypes.monix.io/docs/", "monix/newtypes"),
          )
        )
      ),
    tlSiteApiPackage := Some("monix.newtypes.integrations"),
  )
