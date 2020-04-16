package repositories

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future}
import java.sql.Date
import slick.jdbc.PostgresProfile.api._
import models.{Idea, IdeaDTO, User}

class IdeaRepository {
  private val Ideas = TableQuery[IdeasTable]
  val db = Database.forConfig("database")

  private class IdeasTable(tag: Tag) extends Table[Idea](tag, "ideas") {
    def id = column[Option[Int]]("id", O.PrimaryKey, O.AutoInc)
    def title = column[String]("title")
    def description = column[Option[String]]("description")
    def created_date = column[Date]("created_date")
    def user_id = column[Int]("user_id")
    def * =
      (id, title, description, created_date, user_id) <> ((Idea.apply _).tupled, Idea.unapply)
  }

  def list: Future[Seq[Idea]] = db.run(Ideas.result)

  def create(idea: IdeaDTO, user: User) = db.run(Ideas += Idea.fromDTO(idea, user)).map { _ =>
    ()
  }

  def read(id: Int) = db.run(Ideas.filter(_.id === id).result.headOption)

  def update(id: Int, idea: IdeaDTO, user: User) =
    db.run(
      Ideas.filter(i => i.id === id && i.user_id === user.id).update(Idea.fromDTO(idea, user))
    )

  def delete(id: Int, user: User) = db.run(Ideas.filter(i => i.id === id && i.user_id === user.id).delete)
}
