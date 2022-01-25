package controllers

import io.circe.Decoder.Result
import io.circe.{Decoder, Encoder, HCursor, Json}
import models.StoryDAO
import play.api.mvc.{AbstractController, BaseController, ControllerComponents}

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext
import io.circe.generic.auto._
import io.circe.syntax._
import io.circe.parser.decode
import io.circe.syntax.EncoderOps
import play.api.libs.circe.Circe
import models.JsonFormats

import java.sql.Timestamp



  @Singleton
class ApiController @Inject()(val cc: ControllerComponents, storyDAO: StoryDAO)(implicit ec: ExecutionContext) extends AbstractController(cc) with BaseController with Circe {

    implicit val TimestampFormat : Encoder[Timestamp] with Decoder[Timestamp] = new Encoder[Timestamp] with Decoder[Timestamp] {
      override def apply(a: Timestamp): Json = Encoder.encodeLong.apply(a.getTime)

      override def apply(c: HCursor): Result[Timestamp] = Decoder.decodeLong.map(s => new Timestamp(s)).apply(c)
    }

  def listStories() = Action.async( req => {
    val futureStories = storyDAO.allWithScore()
    for (stories <- futureStories) yield (Ok(stories.asJson))
  })

}
