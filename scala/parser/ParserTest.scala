package ch.mse.app.parsers

object ParserTest {

    import RegexpParsers.keyword

    val ID = """[a-zA-Z]([a-zA-Z0-9]|_[a-zA-Z0-9])*""".r

    def stars: Parser[Any] = "*" | "*" ~ stars

    def classPrefix = "class" ~ "(" ~ ")"

    def main(args: Array[String]) {
        val res = classPrefix.apply("class()".toStream.map(x => new Character(x)));
        println(res)

        val res2 = stars.apply("***)".toStream.map(x => new Character(x)));
        println(res2)

    }
    
}