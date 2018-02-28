package org.aliostad.uberbenchmarker.tests

import org.aliostad.uberbenchmarker.internal.HeaderParser
import org.scalatest.FlatSpec

class HeaderParserTests extends FlatSpec{

  "Header parser" must "parse simple one-line header fine" in {
    var text = "ali: ostad"
    var hs = HeaderParser.parseHeaders(text.getBytes)
    assert(hs.size == 1)
    assert(hs("ali") == "ostad")
  }

  "Header parser" must "parse a header whose value contains a colon" in {
    var text = "ali: ost:ad"
    var hs = HeaderParser.parseHeaders(text.getBytes)
    assert(hs.size == 1)
    assert(hs("ali") == "ost:ad")
  }

  "Header parser" must "parse multiple headers with \\n newline although not legal in HTTP" in {
    var text = "Sonic: Youth\nDayDream: Nation"
    var hs = HeaderParser.parseHeaders(text.getBytes)
    assert(hs.size == 2)
    assert(hs("Sonic") == "Youth")
    assert(hs("DayDream") == "Nation")
  }

  "Header parser" must "parse multiple headers with \\r\\n newline" in {
    var text = "Sonic: Youth\r\nDayDream: Nation"
    var hs = HeaderParser.parseHeaders(text.getBytes)
    assert(hs.size == 2)
    assert(hs("Sonic") == "Youth")
    assert(hs("DayDream") == "Nation")
  }
}
