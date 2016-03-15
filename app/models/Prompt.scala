package models

import play.api.data.Form
import play.api.data.Forms._

object Prompt {
	val form = Form(
		mapping(
			"Prompt" -> nonEmptyText
		) (Prompt.apply)(Prompt.unapply))
}
case class Prompt (content: String) {

}
