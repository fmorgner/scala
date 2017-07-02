package ch.mse.app.logic

abstract class ArithOperator
case object Times extends ArithOperator
case object Div extends ArithOperator
case object Mod extends ArithOperator
case object Plus extends ArithOperator
case object Minus extends ArithOperator

abstract class RelOperator
case object Less extends RelOperator
case object GreaterEq extends RelOperator
case object Equal extends RelOperator
case object NotEq extends RelOperator
case object Greater extends RelOperator
case object LessEq extends RelOperator

abstract class BoolOperator
case object CondAnd extends BoolOperator
case object CondOr extends BoolOperator

abstract class ArithExpr
case class LitAExpr(value: Int) extends ArithExpr
case class IdAExpr(ident: String) extends ArithExpr
case class DyaAExpr(operator: ArithOperator, op1: ArithExpr, op2: ArithExpr) extends ArithExpr

abstract class BoolExpr
case class LitBExpr(value: Boolean) extends BoolExpr
case class RelBExpr(op: RelOperator, op1: ArithExpr, op2: ArithExpr) extends BoolExpr
case class NegBExpr(expr: BoolExpr) extends BoolExpr
case class DyaBExpr(operator: BoolOperator, op1: BoolExpr, op2: BoolExpr) extends BoolExpr

abstract class Command
case object SkipCmd extends Command
case class AssiCmd(ident: String, expr: ArithExpr) extends Command
case class MultiAssiCmd(idents: List[String], exprs: List[ArithExpr]) extends Command
case class CpsCmd(cmds: List[Command]) extends Command
case class CondCmd(cond: BoolExpr, then: Command, otherwise: Command) extends Command
case class WhileCmd(cond: BoolExpr, docmd: Command) extends Command
case class ForCmd(ident: String, from: ArithExpr, to: ArithExpr, docmd: Command) extends Command