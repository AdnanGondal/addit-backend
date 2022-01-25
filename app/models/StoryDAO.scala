package models

import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.mvc.{AbstractController, ControllerComponents}
import slick.jdbc.JdbcProfile
import java.util.Date
import java.sql.Timestamp
import io.circe.Decoder.Result
import io.circe.{Decoder, Encoder, HCursor, Json}

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class StoryDAO @Inject() (protected val dbConfigProvider: DatabaseConfigProvider,
                          cc: ControllerComponents
                         )(implicit ec: ExecutionContext)
  extends AbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile]{

  import profile.api._

  implicit val TimestampFormat : Encoder[Timestamp] with Decoder[Timestamp] = new Encoder[Timestamp] with Decoder[Timestamp] {
    override def apply(a: Timestamp): Json = Encoder.encodeLong.apply(a.getTime)

    override def apply(c: HCursor): Result[Timestamp] = Decoder.decodeLong.map(s => new Timestamp(s)).apply(c)
  }


  private class StoriesTable(tag: Tag) extends Table[Story](tag,"stories") {
    def id = column[Int]("id",O.PrimaryKey)
    def title = column[String]("title")
    def url = column[String]("url")

    override def * = (id,title,url) <>
      (Story.tupled, Story.unapply)
  }

  private val stories = TableQuery[StoriesTable]

  private val storiesWithScore = sql"""SELECT stories.*,
                                       |    GREATEST(0, SUM(CASE direction WHEN 'up' THEN 1 ELSE -1 END)) AS score
                                       |    FROM stories
                                       |    LEFT JOIN votes ON votes.story_id = stories.id
                                       |    GROUP BY stories.id
                                       |    ORDER BY score DESC;""".stripMargin.as[(Int,String,String,Timestamp,Timestamp,Int)]

  def all(): Future[Seq[Story]] = db.run(stories.result)

  def allWithScore(): Future[Vector[(Int,String,String,Timestamp,Timestamp,Int)]] = db.run(storiesWithScore)
}
