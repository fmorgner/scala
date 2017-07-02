package ch.mse.app.logic

import scala.collection.mutable.Map

object Interpreter {

  type Value = Int
  type State = Map[String, Value]

  def eval(expr: BoolExpr, state: State): Boolean = expr match {
    case LitBExpr(value)               => value
    case NegBExpr(expr)                => !eval(expr, state)
    case DyaBExpr(CondAnd, op1, op2)   => eval(op1, state) && eval(op2, state)
    case DyaBExpr(CondOr, op1, op2)    => eval(op1, state) || eval(op2, state)
    case RelBExpr(Less, op1, op2)      => eval(op1, state) < eval(op2, state)
    case RelBExpr(GreaterEq, op1, op2) => eval(op1, state) >= eval(op2, state)
    case RelBExpr(Equal, op1, op2)     => eval(op1, state) == eval(op2, state)
    case RelBExpr(NotEq, op1, op2)     => eval(op1, state) != eval(op2, state)
    case RelBExpr(Greater, op1, op2)   => eval(op1, state) > eval(op2, state)
    case RelBExpr(LessEq, op1, op2)    => eval(op1, state) <= eval(op2, state)
  }

  def eval(expr: ArithExpr, state: State): Value = expr match {
    case LitAExpr(value)           => value
    case IdAExpr(ident)            => state(ident)
    case DyaAExpr(Times, op1, op2) => eval(op1, state) * eval(op2, state)
    case DyaAExpr(Div, op1, op2)   => eval(op1, state) / eval(op2, state)
    case DyaAExpr(Mod, op1, op2)   => eval(op1, state) % eval(op2, state)
    case DyaAExpr(Plus, op1, op2)  => eval(op1, state) + eval(op2, state)
    case DyaAExpr(Minus, op1, op2) => eval(op1, state) - eval(op2, state)
  }

  def interpret(cmd: Command, state: State): State = cmd match {
    case SkipCmd => state
    case AssiCmd(ident, expr) =>
      state(ident) = eval(expr, state); state
    case MultiAssiCmd(idents, exprs) =>
      idents.zip(exprs.map(eval(_, state))).foldLeft(state) {
        (s, p) => s(p._1) = p._2; state
      }; state
    case CpsCmd(cmds)                   => cmds.foldLeft(state)((s, c) => interpret(c, s))
    case CondCmd(cond, then, otherwise) => if (eval(cond, state)) interpret(then, state) else interpret(otherwise, state)
    case WhileCmd(cond, docmd)          =>
      while (eval(cond, state)) interpret(docmd, state); state
    case ForCmd(ident, from, to, cmd) =>
      for (i <- eval(from, state) to eval(to, state)) {
        state(ident) = i
        interpret(cmd, state)
      }; state
  }

}