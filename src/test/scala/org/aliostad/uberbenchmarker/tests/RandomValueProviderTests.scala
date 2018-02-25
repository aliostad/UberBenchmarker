package org.aliostad.uberbenchmarker.tests

import org.aliostad.uberbenchmarker.internal.RandomGen
import org.aliostad.uberbenchmarker.providers.RandomValueProvider
import org.scalatest.FlatSpec

class RandomValueProviderTests extends FlatSpec {

  class Detergent(i: Int, d: Double) extends RandomGen {

    val notAGuid = "ThisIsNotAGuid"
    var lastIntMin: Option[Int] = None
    var lastIntMax: Option[Int] = None
    var lastDoubleMin: Option[Double] = None
    var lastDoubleMax: Option[Double] = None


    override def getRandomInt(min: Option[Int], max: Option[Int]): Int = {
      lastIntMin = min
      lastIntMax = max
      i
    }

    override def getRandomDouble(min: Option[Double], max: Option[Double]): Double = {
      lastDoubleMax = max
      lastDoubleMin = min
      d
    }

    override def getRandomGuid: String = notAGuid
  }

  implicit val detergent: Detergent = new Detergent(42, 4.2)
  val anyOddIndex = 14

  "RandomValueProvider" should "do figure out I did not set min and max and send back integer" in {
    val key = "RAND_INTEGER"
    val mapin = Map(key -> "")
    val rvp = new RandomValueProvider()
    val mapout = rvp.provide(anyOddIndex, mapin)
    assert(mapout(key) == "42")
    assert(detergent.lastIntMax.isEmpty)
    assert(detergent.lastIntMin.isEmpty)
  }

  "RandomValueProvider" should "do figure out I did set min and max and send back integer" in {
    val key = "RAND_INTEGER:[12:24]"
    val mapin = Map(key -> "")
    val rvp = new RandomValueProvider()
    val mapout = rvp.provide(anyOddIndex, mapin)
    assert(mapout(key) == "42")
    assert(detergent.lastIntMin.get == 12)
    assert(detergent.lastIntMax.get == 24)
  }

  "RandomValueProvider" should "do figure out I did set min and max and send back double" in {
    val key = "RAND_DOUBLE:[1.2:2.4]"
    val mapin = Map(key -> "")
    val rvp = new RandomValueProvider()
    val mapout = rvp.provide(anyOddIndex, mapin)
    assert(mapout(key) == "4.2")
    assert(detergent.lastDoubleMin.get == 1.2)
    assert(detergent.lastDoubleMax.get == 2.4)
  }

  "RandomValueProvider" should "send back guid" in {
    val key = "RAND_GUID"
    val mapin = Map(key -> "")
    val rvp = new RandomValueProvider()
    val mapout = rvp.provide(anyOddIndex, mapin)
    assert(mapout(key) == detergent.notAGuid)
  }

}
