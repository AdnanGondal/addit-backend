package models

import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.mvc.{AbstractController, ControllerComponents}
import slick.ast.ColumnOption.AutoInc
import slick.jdbc.{GetResult, JdbcProfile}

import java.util.Date
import java.sql.Timestamp
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class StoryDAO @Inject() (protected val dbConfigProvider: DatabaseConfigProvider,
                          cc: ControllerComponents
                         )(implicit ec: ExecutionContext)
  extends AbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile]{

  import profile.api._
  import JsonFormats._


  private class StoriesTable(tag: Tag) extends Table[Story](tag,"stories") {
    def id = column[Int]("id",O.PrimaryKey,AutoInc)
    def title = column[String]("title")
    def url = column[String]("url")

    override def * = (id,title,url) <>
      (Story.tupled, Story.unapply)
  }

  private val stories = TableQuery[StoriesTable]

  def all(): Future[Seq[Story]] = db.run(stories.result)

  implicit val getStoryResult = GetResult(r => StoryWithScore(r.nextInt, r.nextString, r.nextString,
    r.nextTimestamp(), r.nextTimestamp(), r.nextInt()))

  private val storiesWithScore = sql"""SELECT stories.*,
                                      |    GREATEST(0, SUM(CASE direction WHEN 'up' THEN 1 ELSE -1 END)) AS score
                                      |    FROM stories
                                      |    LEFT JOIN votes ON votes.story_id = stories.id
                                      |    GROUP BY stories.id
                                      |    ORDER BY score DESC;""".stripMargin.as[StoryWithScore]

  def allWithScore(): Future[Vector[StoryWithScore]] = db.run(storiesWithScore)

  def insert(story: Story): Future[Unit] = db.run(stories
  += story).map {_ => ()}
}
