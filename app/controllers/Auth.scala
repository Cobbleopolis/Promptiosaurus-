package controllers

import models.{Login, Register}
import play.api.mvc._
import util.DBUtil

object Auth extends Controller {

    def login = Action { implicit request =>
        val banner = views.html.banner(request.session)
        Ok(views.html.login(banner)(Login.form))
    }

    def submitLogin = Action(implicit request => {
        val banner = views.html.banner(request.session)
        Login.form.bindFromRequest.fold(
            formWithErrors => {
                // binding failure, you retrieve the form containing errors:
                BadRequest(views.html.login(banner)(formWithErrors))
            },
            userData => {
                /* binding success, you get the actual value. */
                Redirect(routes.Application.user(userData.username)).withSession(Security.username -> userData.username)
            }
        )
    })

    def register = Action { implicit request =>
        val banner = views.html.banner(request.session)
        Ok(views.html.register(banner)(Register.form))
    }

    def submitRegister = Action(implicit request => {
        Register.form.bindFromRequest().fold(
            formWithErrors => {
                val banner = views.html.banner(request.session)
                BadRequest(views.html.register(banner)(formWithErrors))
            },
            registerData => {
                DBUtil.registerUser(registerData.getNewUserData)
                Redirect(routes.Application.user(registerData.username)).withSession(Security.username -> registerData.username)
            }
        )
    })

    def logout = Action {
        Redirect(routes.Auth.login()).withNewSession
    }
}
