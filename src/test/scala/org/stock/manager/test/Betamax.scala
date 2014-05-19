package org.stock.manager.test

import org.specs2.mutable.Around
import co.freeside.betamax.{MatchRule, Recorder, TapeMode}
import org.specs2.execute.AsResult
import co.freeside.betamax.proxy.jetty.ProxyServer
import java.util.Comparator
import co.freeside.betamax.message.Request

class NullableBodyMatcher extends Comparator[Request] {
  override def compare(a: Request, b: Request) = {
    if (a.hasBody) {
      val bodyA = scala.io.Source.fromInputStream(a.getBodyAsBinary).mkString
      val bodyB = scala.io.Source.fromInputStream(b.getBodyAsBinary).mkString
      bodyA.compareTo(bodyB)
    } else
      0
  }
}

class Betamax(tape: String, mode: Option[TapeMode] = Some(TapeMode.READ_ONLY), list: Seq[Comparator[Request]] = Seq(MatchRule.method, MatchRule.uri)) extends Around {
  import collection.JavaConversions._
  def around[T: AsResult](t: => T) = Betamax.around(t, tape, mode, list)
}

object Betamax {
  def apply(tape: String, mode: Option[TapeMode] = Some(TapeMode.READ_ONLY), list: Seq[Comparator[Request]] = Seq(MatchRule.method, MatchRule.uri)) = new Betamax(tape, mode, list)

  def around[T: AsResult](t: => T, tape: String, mode: Option[TapeMode], list: java.util.List[Comparator[Request]]) = {
    synchronized {
      val recorder = new Recorder
      val proxyServer = new ProxyServer(recorder)
      import collection.JavaConversions._
      recorder.insertTape(tape, Map("match" -> list))
      recorder.getTape.setMode(mode.getOrElse(recorder.getDefaultMode()))
      proxyServer.start()
      try {
        AsResult(t)
      } finally {
        recorder.ejectTape()
        proxyServer.stop()
      }
    }
  }
}
