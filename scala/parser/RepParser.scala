package exercise_3

import scala.util.parsing.combinator._

/*
 * Im Unterricht haben wir die Definition des rep(p) Parsers gesehen,
 * welcher einen gegebenen Parser 0 bis n mal anwendet und als Resultat
 * eine Liste zurückgibt.
 */

object RepParser extends RegexParsers {
    
    /*
     * In Scala kann ein Methodenaufruf auch infix geschrieben werden.
     * a * b entspricht dem Aufruf der Methode * auf dem Objekt a mit
     * Argument b: (a).*(b)
     * 
     * Formulieren Sie den Methodenrumpf der Methode rep explizit mit
     * Methodenaufrufen, also ohne Infix-Notation:
     */
    
    def grep[T](p: Parser[T]): Parser[List[T]] =
        p.~(grep(p)).^^({ case ~(x,xs) => xs.::{x} }).|(success(List()))
    
    /*
     * In dieser Aufgabe soll ein Parsergenerator definiert werden,
     * welcher einen gegebenen Parser exakt n mal anwendet und als
     * Resultat eine Liste der Resultate der n Anwendungen des Parsers
     * zurückgibt.
     */
        
    def grepN[T](n: Int, p: Parser[T]): Parser[List[T]] =
        if (n > 0)
            p ~ grepN(n - 1, p) ^^ { case x ~ xs => x :: xs }
        else
            success(List())
    
    def main(args: Array[String]) {
        val g = "GGGGGG"
        println(parse(grep("G".r), g))
        println(parse(grepN(9, "G".r), g))
    }
  
}

/*
 * Die Funktion rep[T](p: Parser[List[T]]) gibt eine Funktion zurück.
 * Geben Sie den Typ dieser Funktion an (Parameter- und Resultattypen)
 * und geben Sie an, wozu diese Funktion verwendet werden kann.
 * 
 * Antwort: Der Rückgabewert von rep ist ja Parser[List[T]], und Parser[T]
 * ist eine Funktion von Input => ParseResult[T]. Das heisst, in unserem Fall
 * wäre der Typ dieser besagten Funktion Input => ParseResult[List[T]]. Diese
 * Funktion kann verwendet werden, um einen Mittels Aufruf an parse einen Input
 * auf ein ParseResult[List[T]] zu parsen. Aus diesem ParseResult ist ersichtlich,
 * ob das Parsen erfolgreich war und falls ja, kann man das Ergebnis mittels
 * get abholen.
 * 
 * Antwort Gruntz: Sehr geehrter Herr Meili,
 * diese (Einleitungs)-Frage gab ja auch nur 1 Punkt, und als Resultat habe
 * ich tatsächlich
 *
 *     Input => ParseResult[List[T]]
 *
 * erwartet wie sie das auch geschrieben haben, und als Verwendugszweck hatte
 * ich mir notiert:
 *
 *     Diese Methode wird verwendet um einen Input-Strom in ein Resultat zu
 *     konvertieren. Das Resultat ist eine Liste.
 */
