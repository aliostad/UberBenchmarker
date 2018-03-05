package org.aliostad.uberbenchmarker.providers

import java.nio.file.{Files, Paths}
import scala.collection.JavaConversions._

class SeparatedFileValueProvider(fileName: String, isTsv: Boolean = true) {

  val csvPattern = """(?<=^|,)(\"(?:[^\"]|\"\")*\"|[^,]*)""".r
  val lines = Files.readAllLines(Paths.get(fileName)).toList
  val header = lines.head
  val data = lines.tail

  val takeAsTsv = isTsv || header.contains("\t")




}

object SeparatedFileValueProvider {
  def apply(fileName: String, isTsv: Boolean = true): SeparatedFileValueProvider = new SeparatedFileValueProvider(fileName, isTsv)
}