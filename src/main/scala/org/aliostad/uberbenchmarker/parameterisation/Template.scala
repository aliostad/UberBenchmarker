package org.aliostad.uberbenchmarker.parameterisation

import java.nio.ByteBuffer
import java.nio.file.{Files, Paths}

import scala.collection.mutable
import scala.io.{BufferedSource, Source}

class Template(val headers: Map[String, String],val body: Either[String, ByteBuffer]) {

}

object Template {

  private val templatePattern = """(?:(.+:.+)+\r\n)?(.*)""".r

  def apply(path: String): Template = {
    val headers = mutable.HashMap[String, String]()
    var bytes = Files.readAllBytes(Paths.get(path))
    val bb = ByteBuffer.wrap(bytes)

    new Template(headers.toMap, Left(""))
  }
}