package org.aliostad.uberbenchmarker.tests

import org.scalatest.FlatSpec
import java.io.File
import scala.io.Source

class TemplateTests extends FlatSpec {

  "This" must "work" in {
    var f = new File("./src/test/files/TextFile.template")
    assert(f.exists())
  }

}
