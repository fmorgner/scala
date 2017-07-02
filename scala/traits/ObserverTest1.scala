
package ch.mse.app

object ObserverTest1 {

    class Model(private var value: Int) {
        def getValue = value
        def setValue(value: Int) = { this.value = value }
    }

    trait Observer {
        def update(subject: Any)
    }

    trait Observable {
        @volatile
        private var observers: List[Observer] = Nil
        def addObserver(obs: Observer) = synchronized {
            observers = obs :: observers
        }
        def removeObserver(obs: Observer) = synchronized {
            observers = observers.filterNot(_ == obs)
        }
        def notifyObservers = observers.foreach(_.update(this))
    }
    
    /*
     * Is this solution thread safe?
     *     Yes, because synchronized was used to add and remove observers.
     * 
     * What happens if an observer is removed during a notification?
     * (Java often throws a ConcurrentModificationException)
     *     Nothing, because @volatile was used for var observers.
     */

    class Counter extends Observer {
        var counter = 0
        def update(subject: Any) = counter += 1
    }

    def main(args: Array[String]): Unit = {
        val m = new Model(0) with Observable {
            override def setValue(x: Int) = {
                super.setValue(x)
                notifyObservers
            }
        }
        val c = new Counter
        m.addObserver(c)
        m.setValue(3);
        m.setValue(5);
        println(c.counter)
    }

}