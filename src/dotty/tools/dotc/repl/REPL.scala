package dotty.tools
package dotc
package repl

import core.Contexts.Context
import reporting.Reporter
import java.io.{BufferedReader, File, FileReader, PrintWriter}

/** A compiler which stays resident between runs.
 *  Usage:
 *
 *  > scala dotty.tools.dotc.Resident <options> <initial files>
 *
 *  dotc> "more options and files to compile"
 *
 *  ...
 *
 *  dotc> :reset  // reset all options to the ones passed on the command line
 *
 *  ...
 *
 *  dotc> :q     // quit
 */
class REPL extends Driver {

  /** The default input reader */
  def input(implicit ctx: Context): InteractiveReader = {
    val emacsShell = System.getProperty("env.emacs", "") != ""
    //println("emacsShell="+emacsShell) //debug
    if (ctx.settings.Xnojline.value || emacsShell) new SimpleReader()
    else InteractiveReader.createDefault()
  }

  /** The defult output writer */
  def output: PrintWriter = new NewLinePrintWriter(new ConsoleWriter, true)

  override def newCompiler(implicit ctx: Context): Compiler =
    new repl.CompilingInterpreter(output, ctx)

  override def sourcesRequired = false

  override def doCompile(compiler: Compiler, fileNames: List[String])(implicit ctx: Context): Reporter = {
    if (fileNames.isEmpty)
      new InterpreterLoop(compiler, input, output).run()
    else
      ctx.error(s"don't now what to do with $fileNames%, %")
    ctx.reporter
  }
}
