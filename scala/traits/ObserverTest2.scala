
package ch.mse.app

object ObserverTest2 {

    class Model(private var value: Int) {
        def getValue = value
        def setValue(value: Int) = { this.value = value }
    }

    trait Observer {
        def update(subject: Any)
    }

    trait Observable extends Model {
        @volatile
        private var observers: List[Observer] = Nil
        def addObserver(obs: Observer) = synchronized {
            observers = obs :: observers
        }
        def removeObserver(obs: Observer) = synchronized {
            observers = observers.filterNot(_ == obs)
        }
        def notifyObservers = observers.foreach(_.update(this))
        override def setValue(value: Int) = {
            notifyObservers
            super.setValue(value)
        }
    }

    class Counter extends Observer {
        var counter = 0
        def update(subject: Any) = {
            println(s"update called with $subject")
            counter += 1
        }
    }

    def main(args: Array[String]): Unit = {
        val m = new Model(0) with Observable
        val c = new Counter
        m.addObserver(c)
        m.setValue(3);
        m.setValue(5);
        println(c.counter)
    }

}
