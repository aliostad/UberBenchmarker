package org.aliostad.uberbenchmarker.scheduling

import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global

class UberExtecutionContext extends ExecutionContext {

  override def execute(runnable: Runnable): Unit = global.execute(runnable)

  override def reportFailure(cause: Throwable): Unit = global.reportFailure(cause)
}
