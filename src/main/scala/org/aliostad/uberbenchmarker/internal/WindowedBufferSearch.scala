package org.aliostad.uberbenchmarker.internal

class WindowedBufferSearch(bytes: Array[Byte]) {

  private var len = bytes.length

  private def getWindows(size: Int): IndexedSeq[(Int, Array[Byte])] = {
    for (i <- 0 to (len-size))
      yield (i, bytes.slice(i, i+size))
  }

  def find(matchBytes: Array[Byte]): Int = {

    def byteArraysEqual(b1: Array[Byte], b2: Array[Byte]): Boolean = {
      b1.length match {
        case b if b != b2.length => false
        case 0 => false
        case _ => {
          for (i <- b1.indices)
            if (b1(i) != b2(i))
              return false
          true
        }
      }
    }

    for(w <- getWindows(matchBytes.length))
      if (byteArraysEqual(w._2, matchBytes))
        return w._1

    -1
  }
}
