package org.aliostad.uberbenchmarker.providers

trait ValueProvider {

  def provide(index: Int, tokens: Map[String, String]) : Map[String, String]

}
