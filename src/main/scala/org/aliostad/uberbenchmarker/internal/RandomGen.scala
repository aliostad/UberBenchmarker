package org.aliostad.uberbenchmarker.internal

trait RandomGen {

  def getRandomInt(min: Option[Int], max: Option[Int]): Int

  def getRandomDouble(min: Option[Double], max: Option[Double]): Double

  def getRandomGuid: String
}

object RandomGen {
  implicit val global: RandomGen = new StandardRandomGen
}
