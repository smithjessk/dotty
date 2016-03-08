package dotty.tools.dotc.Resident

import dotty.tools.dotc.Resident
import dotty.tools.dotc.core.Contexts

object ClassDefinitions {
  def main() {
    val residentContext = (new ContextBase).initialCtx
    val resident = new Resident
    resident.process(Array.empty[String], residentContext)
  }
}
