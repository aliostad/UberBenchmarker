package org.aliostad.uberbenchmarker

import java.text.SimpleDateFormat
import java.util.Date

object FileUtils {

  val reportFolderFormat: String = "yyyy-MM-dd_HH-mm-ss.S" // there is no Microsecond?
  private val formatter = new SimpleDateFormat(reportFolderFormat)

  def getDefaultReportFolderName(date: Date): String = formatter.format(date)

}
