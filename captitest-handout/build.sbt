name := "captitest"

version := "1.0"

scalaVersion := "2.10.6"

compileOrder := CompileOrder.JavaThenScala

// https://mvnrepository.com/artifact/org.testng/testng
libraryDependencies += "org.testng" % "testng" % "6.9.10"

libraryDependencies ++= Seq(
  "junit"          % "junit"     % "4.9"   withSources(),
  "org.scalatest" %% "scalatest" % "2.2.6" withSources(),
  "org.specs2"    %% "specs2-core" % "3.8.4" % "test" withSources()
)
