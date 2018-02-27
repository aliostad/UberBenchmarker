package org.aliostad.uberbenchmarker.parameterisation

class Tokeniser(val text: String) {

  def replace(kvs: Map[String, String]): String = {
    var t = text
    kvs.foreach(kv => {
      t = t.replace("{{{" + kv._1 + "}}}", kv._2)
    })
    t
  }
}

object Tokeniser {
  def apply(text: String): Tokeniser = new Tokeniser(text)
}