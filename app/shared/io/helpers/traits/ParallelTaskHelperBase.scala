package shared.io.helpers.traits

import shared.io.helpers.EmptyValidateHelper

trait ParallelTaskHelperBase {
  def runInThreadsSequence(taskName : String, tasks : Seq[() => Unit]) : Unit = {
    if (EmptyValidateHelper.isItemsEmptyDirect(tasks)) {
      return;
    }

    var index = 1
    val threads = tasks.map(task => {
      val thread = new Thread(() => task)
      thread.setName(s"${ taskName } - ${ index }")
      index += 1
      thread.start()

      thread
    })

    threads.foreach(task => task.join())
  }

  def runInThreads(taskName : String, tasks : (() => Unit)*) : Unit =
    runInThreadsSequence(taskName, tasks)
}

