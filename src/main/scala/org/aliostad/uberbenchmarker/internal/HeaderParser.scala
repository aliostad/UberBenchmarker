package org.aliostad.uberbenchmarker.internal

import scala.collection.mutable

object HeaderParser {
  def parseHeaders(bytes: Array[Byte]): Map[String, String] = {
    val headers = mutable.HashMap[String, String]()
    var s = new String(bytes, "utf-8") // in reality must be ascii but they are the same in first 128
    s.split("\n").map(_.trim).filter(_.length > 0).
      map(line => (line.substring(0, line.indexOf(':')), line.substring(line.indexOf(':')+1) )).
      map(kv => (kv._1.trim, kv._2.trim)).
      foreach(kv => headers(kv._1) = kv._2)
    headers.toMap
  }

}
