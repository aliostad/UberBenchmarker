package org.aliostad.uberbenchmarker.providers

import org.aliostad.uberbenchmarker.internal.RandomGen

import scala.util.matching.Regex

class RandomValueProvider(implicit val randomGen: RandomGen) extends ValueProvider {

  private val randomPattern = """RAND_(INTEGER|GUID|DOUBLE)(?::\[(.+):(.+)\])?""".r

  private def getRandomInteger(lower: String, upper: String): String = {
    def toInt(s: String) : Option[Int] = s match {
      case "" => None
      case i => if(i==null) None else Some(i.toInt)
    }

    randomGen.getRandomInt(toInt(lower), toInt(upper)).toString
  }

  private def getRandomDouble(lower: String, upper: String): String = {
    def toDouble(s: String) : Option[Double] = s match {
      case "" => None
      case i => Some(i.toDouble)
    }

    randomGen.getRandomDouble(toDouble(lower), toDouble(upper)).toString
  }

  override def provide(index: Int, tokens: Map[String, String]) : Map[String, String] = {
    tokens.map(x => {
      val mch$ = randomPattern findFirstMatchIn  x._1
      if (mch$.isEmpty)
        x
      else {
        mch$.get.group(1) match {
          case "INTEGER" => (x._1, getRandomInteger(mch$.get.group(2), mch$.get.group(3)))
          case "DOUBLE" => (x._1, getRandomDouble(mch$.get.group(2), mch$.get.group(3)))
          case "GUID" => (x._1, randomGen.getRandomGuid)
          case _ => ("", "")
        }
      }
    })
  }
}
