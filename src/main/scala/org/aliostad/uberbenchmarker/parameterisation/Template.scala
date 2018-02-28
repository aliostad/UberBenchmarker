package org.aliostad.uberbenchmarker.parameterisation

import java.nio.file.{Files, Paths}
import org.aliostad.uberbenchmarker.internal.{HeaderParser, WindowedBufferSearch}

class Template(val headers: Map[String, String], val body: Either[String, Array[Byte]]) {

}

object Template {

  val httpEmptyLine: Array[Byte] = "\r\n\r\n".getBytes("ascii")

  def apply(path: String): Template = {

    var bytes = Files.readAllBytes(Paths.get(path))
    var wbs = new WindowedBufferSearch(bytes)
    wbs.find(httpEmptyLine) match {
      case -1 => new Template(HeaderParser.parseHeaders(bytes), Right(Array[Byte]()))
      case 0 => new Template(Map[String, String](), Right(Array[Byte]()))
      case i => {
        var headerBytes = bytes.slice(0, i)
        var body = bytes.slice(i+httpEmptyLine.length, bytes.length)
        try {
          new Template(HeaderParser.parseHeaders(headerBytes), Left(new String(body, "utf-8")))
        }
        catch {
          case _: Throwable => // not a string
            new Template(HeaderParser.parseHeaders(headerBytes), Right(body))
        }
      }
    }
  }
}