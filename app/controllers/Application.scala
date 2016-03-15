package controllers

import auth.Secured
import controllers.Auth._
import models.{Login, Prompt}
import play.api.mvc._
import util.DBUtil

import scala.collection.mutable.ArrayBuffer

object Application extends Controller with Secured {

	def index = Action { implicit request =>
		val banner = views.html.banner(request.session)
		val arrayBuffer: ArrayBuffer[String] = ArrayBuffer[String]()
		for (i <- 1 to 5)
			arrayBuffer += "Hello " + i
		Ok(views.html.index(banner)("Hello World!", arrayBuffer.toArray))
	}

	//	def user(username: String) = Action {
	//		val user: User = DBUtil.getUserFromUsername(username)
	//		DB.withConnection(implicit conn => {
	//			val prompts: List[Prompt] =
	//                if(user != null)
	//                    DBReference.getAllPromptsForUser.on("user" -> user.email).as(DBReference.getPromptParser.*)
	//                else
	//                    List[Prompt]()
	//			Ok(views.html.user(user, prompts))
	//		})
	//	}

	def user(username: String) = withUser { user => implicit request =>
		val banner = views.html.banner(request.session)
		val requestUser = DBUtil.getUserFromUsername(username)
		val prompts = DBUtil.getPromptsForUser(requestUser)
		Ok(views.html.user(banner)(requestUser, prompts))
	}

	def submit(username: String) = Action { implicit request =>
		val banner = views.html.banner(request.session)
		val user = DBUtil.getUserFromUsername(username)
		Ok(views.html.submit(banner)(user, Prompt.form))
	}

	def submitPrompt(username: String) = Action { implicit request =>
		val banner = views.html.banner(request.session)
		val user = DBUtil.getUserFromUsername(username)
		Prompt.form.bindFromRequest.fold(
			formWithErrors => {
				// binding failure, you retrieve the form containing errors:
				BadRequest(views.html.submit(banner)(user, formWithErrors))
			},
			promptData => {
				/* binding success, you get the actual value. */
				DBUtil.submitPrompt(user, promptData.content)
				Redirect(routes.Application.submit(username))
			}
		)
	}

	def userTest = withUser { user => implicit request =>
		val banner = views.html.banner(request.session)
		Ok(views.html.userTest(banner)(user.username))
	}
}