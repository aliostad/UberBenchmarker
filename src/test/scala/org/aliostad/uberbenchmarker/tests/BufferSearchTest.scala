package org.aliostad.uberbenchmarker.tests

import org.aliostad.uberbenchmarker.internal.WindowedBufferSearch
import org.scalatest.FlatSpec

class BufferSearchTest extends FlatSpec{

  val text =
    """
      | - What is the fastest way to read an entire file into a String in Scala?
      | - This one?
      | - Really??
      | - No.
    """.stripMargin

  "Buffer search" must "find the bytes of interest" in {
    var search = new WindowedBufferSearch(text.getBytes)
    assert(search.find("This one?".getBytes) > 0)
  }

  "Buffer search" must "return -1 if bytes of interest not found" in {
    var search = new WindowedBufferSearch(text.getBytes)
    assert(search.find("This one?!".getBytes) == -1)
  }

}
