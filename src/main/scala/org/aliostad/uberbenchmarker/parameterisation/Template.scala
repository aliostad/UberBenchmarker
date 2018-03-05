package org.aliostad.uberbenchmarker.parameterisation

import java.nio.file.{Files, Paths}
import org.aliostad.uberbenchmarker.internal.{HeaderParser, WindowedBufferSearch}
import org.aliostad.uberbenchmarker._

class Template(val headers: Map[String, String], val body: Either[String, Array[Byte]]) {

}

object Template {

  val httpEmptyLine: Array[Byte] = "\r\n\r\n".getBytes(DefaultEncoding)

  def apply(path: String): Template = {

    var bytes = Files.readAllBytes(Paths.get(path))
    var wbs = new WindowedBufferSearch(bytes)

    def isText(bbs: Array[Byte]): Boolean = {
      val subset = bbs.take(500)
      subset.count(b => b >= 32 && b < 125) * 1.0 / subset.length > 0.5
    }

    def getBody(bbs: Array[Byte]) : Either[String, Array[Byte]] = {
      if(isText(bbs))
        Left(new String(bbs, DefaultEncoding))
      else
        Right(bbs)
    }

    wbs.find(httpEmptyLine) match {
      case -1 => new Template(HeaderParser.parseHeaders(bytes), Right(Array[Byte]()))
      case 0 => new Template(Map[String, String](), getBody(bytes.slice(httpEmptyLine.length, bytes.length)))
      case i => {
        var headerBytes = bytes.slice(0, i)
        var body = bytes.slice(i+httpEmptyLine.length, bytes.length)
        new Template(HeaderParser.parseHeaders(headerBytes), getBody(body))
      }
    }
  }
}