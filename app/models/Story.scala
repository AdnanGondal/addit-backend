package models

import java.sql.Timestamp

case class Story(id: Int, title: String, url: String)

case class StoryWithScore(id: Int,title: String,url: String,created_at: Timestamp,updated_at: Timestamp,score: Int)

case class Stories(stories: Seq[Story])

