package main

import function.Function1

class Animal
class Bird extends Animal

object Test {
    
    def main(args: Array[String]) {
        val f: Function1[Animal, Animal] = new Function1[Animal, Animal] {
            def apply(a: Animal) = a
        }
        val f1: Function1[Bird, Animal] = f
        //val f2: Function1[Animal, Bird] = f
        //val f3: Function1[Any, Animal] = f
        val f4: Function1[Animal, Any] = f
    }
    
}

/*
 *  T,  R: all assignments are wrong
 * -T,  R: val f1 assignment works
 *  T, -R: val f2 assignment works
 * -T, -R: val f1 & val f2 assignments work
 * 
 * +T,  R: val f3 assignment works
 *  T, +R: val f4 assignment works
 * +T, +R: val f3 & val f4 assignments work
 * -T, +R: val f1 & val f4 assignments work
 * +T, -R: val f2 & val f3 assignments work
 */

/*
 * -T, +R is the solution of choice:
 *     The argument of the function you assign can be more generic => contravariant
 *     The return type of the function you assign can be more specific => covariant
 *     
 * Note:
 * Argument type: contravariant position!
 * Return type: covariant position!
 */
