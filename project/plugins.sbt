logLevel := Level.Warn

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += Resolver.url("GitHub repository", url("http://shaggyyeti.github.io/releases"))(Resolver.ivyStylePatterns)

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.3.8")

addSbtPlugin("default" % "sbt-sass" % "0.1.9")

addSbtPlugin("com.typesafe.sbt" % "sbt-coffeescript" % "1.0.0")

//addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.2")

//addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "0.7.4")

//addSbtPlugin("se.marcuslonnberg" % "sbt-docker" % "1.3.0")
//
//addSbtPlugin("com.tapad" % "sbt-docker-compose" % "1.0.0")

//addSbtPlugin("com.arpnetworking" % "sbt-typescript" % "0.1.10")