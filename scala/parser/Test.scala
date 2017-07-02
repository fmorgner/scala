package ch.mse.app.logic

import scala.collection.mutable.HashMap

object Test {

  def main(args: Array[String]) {
    val exampleProgram = """
       a := m;
      |b := n;
      |if (a < 0) then a := (0-a) endif;
      |if (b < 0) then b := (0-b) endif;
      |while (b /= 0) do
      | x := b;
      | b := (a mod b);
      | a := x
      |endwhile;
      |ggt := a""".stripMargin

    val map = new HashMap[String, Int](); map("m") = 144; map("n") = 12
    println("Initial state: " + map)
    
    val ast = IMLParser.parse(exampleProgram)
    println("AST:           " + ast)
    
    val end = Interpreter.interpret(ast, map)
    println("End state:     " + end)
  }
}