package ch.mse.app.logic

import scala.util.parsing.combinator.RegexParsers

object IMLParser extends RegexParsers {

  def numericLit = """\d+""".r
  def identifier = """\w+""".r

  def arithExpr : Parser[ArithExpr] = {
    numericLit ^^ { case lit   => LitAExpr(lit.toInt) } |
    identifier ^^ { case id    => IdAExpr(id) } |
    "(" ~> arithExpr ~ ("+"|"-"|"*"|"div"|"mod") ~ (arithExpr <~ ")") ^^ {
      case exp1 ~   "+" ~ exp2 => DyaAExpr(Plus, exp1, exp2)
      case exp1 ~   "-" ~ exp2 => DyaAExpr(Minus, exp1, exp2)
      case exp1 ~   "*" ~ exp2 => DyaAExpr(Times, exp1, exp2)
      case exp1 ~ "div" ~ exp2 => DyaAExpr(Div, exp1, exp2)
      case exp1 ~ "mod" ~ exp2 => DyaAExpr(Mod, exp1, exp2)
    }
  }

  def boolExpr : Parser[BoolExpr] = {
    "true|false".r ^^ { case lit     => LitBExpr(lit.toBoolean) } |
    "NOT" ~> boolExpr ^^ { case expr => NegBExpr(expr) } |
    "(" ~> boolExpr ~ ("cand" | "cor") ~ (boolExpr <~ ")") ^^ {
      case exp1 ~ "cand" ~ exp2      => DyaBExpr(CondAnd, exp1, exp2)
      case exp1 ~ "cor"  ~ exp2      => DyaBExpr(CondOr, exp1, exp2)
    } |
    "(" ~> arithExpr ~ ("<="|"<"|">="|">"|"/="|"==") ~ (arithExpr <~ ")") ^^ {
      case exp1 ~ "<=" ~ exp2 => RelBExpr(LessEq, exp1, exp2)
      case exp1 ~  "<" ~ exp2 => RelBExpr(Less, exp1, exp2)
      case exp1 ~ ">=" ~ exp2 => RelBExpr(GreaterEq, exp1, exp2)
      case exp1 ~  ">" ~ exp2 => RelBExpr(Greater, exp1, exp2)
      case exp1 ~ "/=" ~ exp2 => RelBExpr(NotEq, exp1, exp2)
      case exp1 ~ "==" ~ exp2 => RelBExpr(Equal, exp1, exp2)
    }
  }

  def cmd : Parser[Command] = {
    (identifier <~ ":=") ~ arithExpr ^^ { case id ~ exp => AssiCmd(id, exp) } |
    "if" ~> (boolExpr <~ "then") ~ cpsCmd ~ (("else" ~> cpsCmd)?) <~ "endif" ^^ {
      case cond ~ then ~ Some(otherwise) => CondCmd(cond, then, otherwise)
      case cond ~ then ~ None => CondCmd(cond, then, SkipCmd)
    } |
    "while" ~> (boolExpr <~ "do") ~ cpsCmd <~ "endwhile" ^^ {
      case cond ~ cmds => WhileCmd(cond, cmds)
    } |
    "skip" ^^ { case _ => SkipCmd }
  }

  def cpsCmd : Parser[Command] = {
    repsep(cmd, ";") ^^ {
      case cmds => CpsCmd(cmds)
    }
  }

  def program = cpsCmd

  def parse(input: CharSequence) = parseAll(program, input).get
}
