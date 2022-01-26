package controllers

import io.circe.Decoder.Result
import io.circe.{Decoder, Encoder, HCursor, Json}
import models.{Story, StoryDAO, StoryForm, StoryInput}
import play.api.mvc.{AbstractController, BaseController, ControllerComponents}

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import io.circe.generic.auto._
import io.circe.syntax._
import io.circe.parser.decode
import io.circe.syntax.EncoderOps
import play.api.libs.circe.Circe



  @Singleton
class ApiController @Inject()(val cc: ControllerComponents, storyDAO: StoryDAO)(implicit ec: ExecutionContext) extends AbstractController(cc) with BaseController with Circe {
  import models.JsonFormats._


    def listStories() = Action.async( req => {
      val futureStories = storyDAO.allWithScore()
      for (stories <- futureStories) yield (Ok(stories.asJson))
    })

    def addStory() = Action.async( implicit req => {
      StoryForm.form.bindFromRequest.fold(
        formWithErrors => {
          Future.successful(BadRequest("Invalid user input: "+formWithErrors))
        },
        userData => {
          print(userData.title)
          val newStory = Story(0,userData.title,userData.url)
          val futureInsert = storyDAO.insert(newStory).map(_ => Ok("Success")).recover {
            case e => {
              logger.error("DATABASE ERROR" + e)
              InternalServerError("Cannot Write in Database")
            }
          }
          futureInsert
        }

      )
    })

}
