package exercise_1

object Test {

    trait A {
        println(">>A")
        def print = { println("A") }
    }
    trait B {
        println(">>B")
        def print = { println("B") }
    }
    trait C extends A with B {
        println(">>C")
        override def print = { println("C"); super.print }
    }
    trait D extends B with A {
        println(">>D")
        override def print = { println("D"); super.print }
    }
    class E extends D with C {
        println(">>E")
        override def print = { println("E"); super.print }
    }
    /*
     * L(E): E C D A B AnyRef Any
     */
    
    /*
     * Question: Is it guaranteed that the call of super.foo in
     * the method Derived.foo directly calls Base.foo?
     */
    
    trait Base {
        def foo = " Base"
    }
    trait Derived extends Base {
        override def foo = super.foo + " Derived"
    }
    trait Molester extends Base {
        override def foo = super.foo + " Molester"
    }

    def main(args: Array[String]) {
        val e = new E
        e.print
        /*When mixing in Molester before Derived, the super.foo
         * call of Derived calls foo of Molester instead of foo
         * of Base (this is a counter example)
         */
        val bar = new Object with Molester with Derived
        println(bar.foo)
    }

}

/*
 * e.print output:
 *     
 *     >>B
 *     >>A
 *     >>D
 *     >>C
 *     >>E
 * 
 * println(bar.foo) output:
 * 
 *     E
 *     C
 *     D
 *     A
*/
