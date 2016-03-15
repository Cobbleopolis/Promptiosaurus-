
name := "Promptiosaurus"

version := "1.0"

lazy val `promptiosaurus` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

//val defaultDBDumpPath = baseDirectory( _ /"db"/"dump.sql" )


libraryDependencies ++= Seq(
	jdbc,
	cache,
	ws,
    anorm,
	"mysql" % "mysql-connector-java" % "5.1.18",
    "com.typesafe.play" %% "play-mailer" % "2.4.1",
	"org.mindrot" % "jbcrypt" % "0.3m"
)

//dockerfile in docker := {
//	val jarFile: File = sbt.Keys.`package`.in(Compile, packageBin).value
//	val classpath = (managedClasspath in Compile).value
//	val mainclass = mainClass.in(Compile, packageBin).value.getOrElse(sys.error("Expected exactly one main class"))
//	val jarTarget = s"/app/${jarFile.getName}"
//	// Make a colon separated classpath with the JAR file
//	val classpathString = classpath.files.map("/app/" + _.getName)
//		.mkString(":") + ":" + jarTarget
//	new Dockerfile {
//		// Base image
//		from("java")
//		// Add all files on the classpath
//		add(classpath.files, "/app/")
//		// Add the JAR file
//		add(jarFile, jarTarget)
//		// On launch run Java with the classpath and the main class
//		entryPoint("java", "-cp", classpathString, mainclass)
//	}
//}
//
//dockerImageCreationTask := docker.value

//maintainer := "CobbleStone Studios"
//
//// exposing the play ports
//dockerExposedPorts in Docker := Seq(9000, 9443)


unmanagedResourceDirectories in Test <+= baseDirectory(_ / "target/web/public/test")

