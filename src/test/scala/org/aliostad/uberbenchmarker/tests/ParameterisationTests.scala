package org.aliostad.uberbenchmarker.tests

import org.aliostad.uberbenchmarker.parameterisation.Tokeniser
import org.scalatest.FlatSpec

class ParameterisationTests extends FlatSpec {

  "A tokeniser" should "really replace stuff" in {
    val t = Tokeniser("suooma {{{ID}}}")
    val id = "foofoo"
    assert(t.replace(Map("ID" -> id)) == "suooma foofoo")
  }

}
