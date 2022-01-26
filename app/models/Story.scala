package models

import cats.instances.boolean
import play.api.data.Form
import play.api.data.Forms._

import java.sql.Timestamp

case class Story(id: Int, title: String, url: String)

case class StoryInput(title: String, url: String)

case class StoryWithScore(id: Int,title: String,url: String,created_at: Timestamp,updated_at: Timestamp,score: Int)

case class Stories(stories: Seq[Story])

object StoryForm {
  val form = Form(
    mapping(
      "title" -> nonEmptyText,
      "url" -> nonEmptyText
    )(StoryInput.apply)(StoryInput.unapply)
  )
}