ThisBuild / version := "1.0"
ThisBuild / scalaVersion := "2.13.7"

resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"

lazy val `play-scalikejdbc-sample` = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := "play-scalikejdbc-sample",
    libraryDependencies ++= Seq(
      guice,
      jdbc,
      "org.postgresql"   % "postgresql"                     % "42.3.1",
      "org.scalikejdbc" %% "scalikejdbc"                    % "3.5.0",
      "org.scalikejdbc" %% "scalikejdbc-config"             % "3.5.0",
      "org.scalikejdbc" %% "scalikejdbc-play-initializer"   % "2.8.0-scalikejdbc-3.5",
      "org.scalikejdbc" %% "scalikejdbc-play-dbapi-adapter" % "2.8.0-scalikejdbc-3.5"
    )
  )
