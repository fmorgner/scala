package queue

class Animal
class Bird extends Animal

object QueueTest {

    def main(args: Array[String]) {
        val q = Queue[Bird]()
        val q1 = q.enqueue(new Bird)
        val q2 = q1.enqueue(new Animal)
        q2.dequeue
    }

}