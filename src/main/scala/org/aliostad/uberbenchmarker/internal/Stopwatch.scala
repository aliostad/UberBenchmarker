package org.aliostad.uberbenchmarker.internal

class Stopwatch(started: Boolean = false) {
  private var startTime: Option[Long] = if (started) Some(System.nanoTime()) else None

  def isStarted : Boolean = startTime.nonEmpty

  def restart(): Unit = startTime = Some(System.nanoTime())

  def elapsedNanos: Option[Long] = startTime.map(System.nanoTime() - _)

  def start(): Unit = startTime = Some(System.nanoTime())
}

object Stopwatch {

  def startNew: Stopwatch = new Stopwatch(true)

  def apply(): Stopwatch = new Stopwatch
}
