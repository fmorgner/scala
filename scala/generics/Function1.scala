
package function

trait Function1[-T, +R] extends AnyRef {

    // definiert einen neuen Namen für this, aber anstelle von self
    // könnte man im Code auch Function1.this schreiben.
    self =>

    // Apply the body of this function to the argument.
    def apply(arg: T): R

    // Composes two instances of Function1 in a new Function1,
    // with this function applied last.
    def compose[A](g: Function1[A, T]): Function1[A, R] = {
        new Function1[A, R] {
            def apply(arg: A) = self.apply(g.apply(arg))
        }
    }

    // Composes two instances of Function1 in a new Function1,
    // with this function applied first.
    def andThen[A](g: Function1[R, A]): Function1[T, A] = {
        new Function1[T, A] {
            def apply(arg: T) = g.apply(self.apply(arg))
        }
    }

}

// Alternative implementation

trait Function1Alt[T, R] {
    def apply(v1: T): R
    def compose[A](g: A => T): A => R = { x => apply(g(x)) }
    def andThen[A](g: R => A): T => A = { x => g(apply(x)) }
    override def toString() = "<function1>"
}
