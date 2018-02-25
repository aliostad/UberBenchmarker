package org.aliostad.uberbenchmarker.tests

import org.aliostad.uberbenchmarker.CommandParameters
import org.scalatest.FlatSpec

class CommandParsingTests extends FlatSpec {

  "Command params" must "complain if URL not passed concurrency" in {
    val cp = CommandParameters.parse(List("-f", "shua", "-c", "20"))
    assert(cp.isEmpty)
  }

  "Command params" must "read concurrency" in {
    val cp = CommandParameters.parse(List("-u", "shua", "-c", "20"))
    assert(cp.nonEmpty)
    assert(cp.get.concurrency == 20)
  }

  "Command params" must "read numberOfRequests" in {
    val cp = CommandParameters.parse(List("-u", "shua", "-n", "10"))
    assert(cp.nonEmpty)
    assert(cp.get.numberOfRequests == 10)
  }

  "Command params" must "read numberOfSeconds" in {
    val cp = CommandParameters.parse(List("-u", "shua", "-N", "42"))
    assert(cp.nonEmpty)
    assert(cp.get.numberOfSeconds.nonEmpty)
    assert(cp.get.numberOfSeconds.get == 42)
  }

  "Command params" must "have no numberOfSeconds vy default " in {
    val cp = CommandParameters.parse(List("-u", "shua"))
    assert(cp.nonEmpty)
    assert(cp.get.numberOfSeconds.isEmpty)
  }

  "Command params" must "set isTsv correctly " in {
    val cp = CommandParameters.parse(List("-u", "shua", "-a"))
    assert(cp.get.isTsv)
  }

  "Command params" must "set isDryRun correctly " in {
    val cp = CommandParameters.parse(List("-u", "shua", "-d"))
    assert(cp.get.isDryRun)
  }

  "Command params" must "set suffle correctly " in {
    val cp = CommandParameters.parse(List("-u", "shua", "-U"))
    assert(cp.get.shuffleData)
  }



}
