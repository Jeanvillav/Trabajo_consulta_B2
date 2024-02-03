import monix.eval.Task
import monix.reactive.Observable
import monix.execution.Scheduler.Implicits.global
import monix.reactive.Consumer

object ReactiveProgrammingExample extends App {

  // Creamos un observable que emite una secuencia de números del 1 al 5
  val source: Observable[Int] = Observable(1, 2, 3, 4, 5)

  // Aplicamos una transformación: multiplicamos cada número por 10
  val transformed: Observable[Int] = source.map(_ * 10)

  // Filtramos solo los números pares
  val filtered: Observable[Int] = transformed.filter(_ % 2 == 0)

  // Tomamos los primeros 3 elementos
  val taken: Observable[Int] = filtered.take(3)

  // Creamos un consumer que imprimirá cada elemento recibido
  val printConsumer: Consumer[Int, Unit] = Consumer.foreach[Int](println)

  // Conectamos el observable con el consumer para consumir los resultados
  val task: Task[Unit] = taken.consumeWith(printConsumer)

  // Ejecutamos la tarea y manejamos el resultado
  task.runAsync {
    case Left(ex) =>
      println(s"Error al ejecutar la tarea: $ex")
    case Right(_) =>
      println("Tarea completada exitosamente")
  }
}
