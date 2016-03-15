package util

import controllers.Application._
import models.user.{UserOptions, User}
import models.{NewUserData, RegisterData, Prompt}
import play.api.Play.current
import play.api.db.DB
import reference.DBReference

object DBUtil {

	def getUserFromUsername(username: String): User = {
		DB.withConnection(implicit conn => {
			DBReference.getUserFromUsername.on("user" -> username).as(DBReference.getUserParser.singleOpt)
		}.orNull)
	}

	def getUserFromEmail(email: String): User = {
		DB.withConnection(implicit conn => {
			DBReference.getUserFromUsername.on("email" -> email).as(DBReference.getUserParser.singleOpt)
		}.orNull)
	}

	def getPromptsForUser(user: User): List[Prompt] = {
		DB.withConnection(implicit conn => {
			DBReference.getAllPromptsForUser.on("user" -> user.username).as(DBReference.getPromptParser.*)
		})
	}

	def registerUser(newUserData: NewUserData): Unit = {
		DB.withConnection(implicit conn => {
			DBReference.insertUser.on(
				"username" -> newUserData.username, 
				"email" -> newUserData.email,
				"password" -> newUserData.password,
				"accountType" -> newUserData.accountType
			).executeInsert()
		})
	}

	def submitPrompt(user: User, promptMessage: String): Unit = {
		DB.withConnection(implicit conn => {
			DBReference.submitPrompt.on(
				"user" -> user.username,
				"content" -> promptMessage
			).executeInsert()
		})
	}

	def getUserOptions(user: User): UserOptions ={
		DB.withConnection(implicit conn => {
			DBReference.getUserOptions.on("username" -> user.username).as(DBReference.getUserOptionsParser.singleOpt)
		}.orNull)
	}

}
