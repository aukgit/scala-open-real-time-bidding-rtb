package services

import com.google.inject.Inject
import models.{Todo, TodoList}

import scala.concurrent.Future

class TodoService @Inject() (items: TodoList) {

  def addItem(item: Todo): Future[String] = {
    items.add(item)
  }

  def deleteItem(id: Long): Future[Int] = {
    items.delete(id)
  }

  def updateItem(item: Todo): Future[Int] = {
    items.update(item)
  }

  def getItem(id: Long): Future[Option[Todo]] = {
    items.get(id)
  }

  def listAllItems: Future[Seq[Todo]] = {
    items.listAll
  }
}