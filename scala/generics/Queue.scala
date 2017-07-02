package queue

class Queue[+A] private (val elems: List[A]) {
    def dequeue = (elems.head, new Queue(elems.tail))
    def enqueue[B >: A](elem: B) = new Queue(elems :+ elem)
}

object Queue {
    def apply[A](elems: A*) = new Queue[A](elems.toList)
}

/*
 * Usage:
 * 
 *     scala> val q = Queue[Bird]()
 *     q: Queue[Bird] = Queue@5c50d2c4
 * 
 *     scala> val q1 = q.enqueue(new Bird)
 *     q1: Queue[Bird] = Queue@7fa00fdf
 * 
 *     scala> val q2 = q1.enqueue(new Animal)
 *     q2: Queue[Animal] = Queue@4fe93738
 * 
 *     scala> q2.dequeue
 *     res1: (Animal, Queue[Animal]) = (Bird@2551e7fb,Queue@245522e1)
 */
