resolvers += Resolver.url("bintray-sbt-plugins",
  url("http://dl.bintray.com/sbt/sbt-plugin-releases/"))(Resolver.ivyStylePatterns)

addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.7.0")