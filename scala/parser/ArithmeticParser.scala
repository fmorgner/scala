package ch.mse.app.parsers.arith

import scala.util.parsing.combinator._

object ArithmeticParser extends RegexParsers {

    def numericLit = """\d+""".r

    // 3) see discussion below
    def expr: Parser[Int] = (
        term ~ rep("+" ~ term | "-" ~ term) ^^ {
            case x ~ lst => lst.foldLeft(x) {
                case (s, "+" ~ y) => s + y
                case (s, "-" ~ y) => s - y
            }
        })

    def term: Parser[Int] = (
        factor ~ rep("*" ~ factor | "/" ~ factor) ^^ {
            case x ~ lst => lst.foldLeft(x) {
                case (s, "*" ~ y) => s * y
                case (s, "/" ~ y) => s / y
            }
        })

    def factor: Parser[Int] = "(" ~> expr <~ ")" | numericLit ^^ (x => x.toInt)

    def main(args: Array[String]) {
        println(parseAll(expr, "1+2*3*7-1"))
        println(parseAll(expr, "5-1-1"))
        println(parseAll(expr, "5/2*2-3/2"))
    }

}

/*
 * Discussion:
 * 
 * 1)   def expr : Parser[Int] = (
 *           expr ~ ("+" ~> term) ^^ { case x ~ y => x + y }
 *         | term ~ "-" ~ expr ^^ { case x ~ "-" ~ y => x - y }
 *         | term
 *      )
 *     
 *      does not work, because it is left-recursive
 *     
 * 2)   def expr : Parser[Int] = (
 *           term ~ ("+" ~> expr) ^^ { case x ~ y => x + y }
 *         | term ~ "-" ~ expr ^^ { case x ~ "-" ~ y => x - y }
 *         | term
 *      )
 *      
 *      results in 5 when parsing 5-1-1, because it's right-associative
 *      (5-(1-1))
 *      
 * 3)   The version above works.
 * 
 */
