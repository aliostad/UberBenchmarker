package org.aliostad.uberbenchmarker.scheduling

import scala.concurrent.{ExecutionContext, Future}

case class CancellationToken(cancelled: Future[Unit]) {

  def isCancelled : Boolean = cancelled.isCompleted

}

class CancellationTokenSource(timeoutMiilis: Option[Long] = None)(context: ExecutionContext) {

  private var thread: Thread = null
  private val cancelled = Future {

    thread = Thread.currentThread()
    timeoutMiilis match {
      case None => thread.join()
      case Some(timeout) => thread.join(timeout)
    }
  } (context)

  def cancel: Unit = thread.interrupt()
  val token = CancellationToken(cancelled)

}

