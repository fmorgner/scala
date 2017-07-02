package pair

class Pair[+T,+S](val first: T, val second: S) {
    def replaceFirst[U >: T](newFst: U): Pair[U,S] = new Pair(newFst, second)
    def replaceSecond[U >: S](newSnd: U): Pair[T,U] = new Pair(first, newSnd)
}

object Pair {
    def swap[T,S](pair: Pair[T,S]) = new Pair(pair.second, pair.first)
}

class PairM[T,S](private[this] var f: T, private[this] var s: S) {
    def first = f
    def second = s
    def replaceFirst (newFst: T) { f = newFst }
    def replaceSecond (newSnd: S) { s = newSnd }
}

/*
 * Type parameters of PairM can be neither covariant nor contravariant,
 * because first and second are read from and written to.
 */

object PairM {
    def swap[T](pair: Pair[T,T]) = {
        val temp = pair.first
        pair.replaceFirst(pair.second)
        pair.replaceSecond(temp)
    }
}

/*
 * A mutable swap can only be performed if both elements of the pair
 * are of the same type.
 */
