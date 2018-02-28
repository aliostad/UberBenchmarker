package org.aliostad.uberbenchmarker.tests

import org.scalatest.FlatSpec

import org.aliostad.uberbenchmarker.parameterisation.Template

class TemplateTests extends FlatSpec {

  "Template" must "be able to parse a text file with headers" in {
    var t = Template("./src/test/files/TextFile.template")
    assert(t.headers("ali") == "ostad")
    assert(t.body.isLeft)

    t.body match {
      case Left(a) => assert(a.startsWith("This") && a.endsWith("goof"))
      case _ => fail()
    }
  }

  "Template" must "be able to parse a text file with no headers" in {
    var t = Template("./src/test/files/TextFileNoHeaders.template")
    assert(t.headers.isEmpty)
    assert(t.body.isLeft)

    t.body match {
      case Left(a) => assert(a.startsWith("This"))
      case _ => fail()
    }
  }

  "Template" must "be able to parse a binary file with headers" in {
    var t = Template("./src/test/files/Binary.template")
    assert(t.headers("ali") == "ostad")
    assert(t.body.isRight)
  }

}
