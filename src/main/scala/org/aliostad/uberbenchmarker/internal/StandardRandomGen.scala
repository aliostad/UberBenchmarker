package org.aliostad.uberbenchmarker.internal

import scala.util.Random

class StandardRandomGen extends RandomGen {

  override def getRandomInt(min: Option[Int], max: Option[Int]): Int = {
    val rmin = min.getOrElse(0)
    val rmax = max.getOrElse(Int.MaxValue)
    rmin + Random.nextInt(rmax - rmin)
  }

  override def getRandomDouble(min: Option[Double], max: Option[Double]): Double = {
    val rmin = min.getOrElse(0d)
    val rmax = max.getOrElse(1d)
    rmin + Random.nextDouble() * (rmax - rmin)
  }

  override def getRandomGuid: String = java.util.UUID.randomUUID().toString
}