package models

case class Story(id: Int, title: String, url: String)

case class Stories(stories: Seq[Story])

