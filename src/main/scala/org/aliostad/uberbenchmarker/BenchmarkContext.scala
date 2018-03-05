package org.aliostad.uberbenchmarker

import java.io.File
import java.util.Calendar

import org.aliostad.uberbenchmarker.parameterisation.Template

case class BenchmarkContext(
           commandParameters: CommandParameters,
           logFile: File,
           reportFolder: File,
           responseFolder: Option[File],
           template: Option[Template]

                           )

object BenchmarkContext {

  private var logFileName = "run.log"
  private var responseFolderName = "response"

  def build(cp: CommandParameters) : Either[BenchmarkContext, String] = {


    try{

      // report folder ________________________
      var reportFolder = cp.reportFolder match {
        case "" => new File(FileUtils.getDefaultReportFolderName(Calendar.getInstance.getTime))
        case s: String => new File(s)
      }

      if (!reportFolder.exists())
        reportFolder.mkdirs()

      // log file ________________________
      var logFile = cp.logFile match {
        case "" => new File(reportFolder, logFileName)
        case s => new File(s)
      }

      if (!logFile.exists())
        logFile.createNewFile()

      // response folder ________________________
      var responseFolder = if (cp.saveResponse) {
        cp.responseFolder match {
          case "" => Some(new File(responseFolderName))
          case s => Some(new File(s))
        }
      } else None

      responseFolder.foreach(x => if (!x.exists()) x.mkdir())

      // template ________________________
      val template = if(cp.templateFile.length == 0) None else
        Some(Template(cp.templateFile))

      cp.



      Left(BenchmarkContext(cp,
        logFile,
        reportFolder,
        responseFolder,
        template))


    }
    catch {
      case e: Exception => Right(e.toString)
    }

  }

}
