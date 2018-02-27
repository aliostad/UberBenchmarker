package org.aliostad.uberbenchmarker.internal

class WindowedBufferSearch(bytes: Array[Byte]) {

  private var len = bytes.length

  private def getWindows(size: Int): IndexedSeq[(Int, Array[Byte])] = {
    for (i <- 0 to (len-size))
      yield (i, bytes.slice(i, i+size))
  }

  def find(asciiSymbols: String): Int = {

    var matchBytes = asciiSymbols.getBytes("ascii")

    def byteArraysEqual(b1: Array[Byte], b2: Array[Byte]): Boolean = {
      if (b1.length != b2.length)
        false
      if (b1.length == 0)
        true
      for (i <- b1.indices) {
        if (b1(i) != b2(i))
          false
      }
      true
    }

    for(w <- getWindows(matchBytes.length))
      if (byteArraysEqual(w._2, matchBytes)) w._1

    // not found
    -1
  }
}
