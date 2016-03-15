package reference

import anorm.SqlParser._
import anorm._
import models.Prompt
import models.user.{UserOptions, User}

object DBReference {

	val getUserFromUsername = SQL("select * from users where username = {user}")
	val getUserFromEmail = SQL("select * from users where email = {email}")
	val getUserParser = for {
		username <- str("username")
		email <- str("email")
		password <- str("password")
		accountType <- int("accountType")
	} yield new User(username, email, password, accountType)

	val userExists = SQL("select 1 from users where users.email = {email}")

	val submitPrompt = SQL("insert into prompts (user, content) values ({user}, {content});")

	val getAllPromptsForUser = SQL("select * from prompts where user = {user}")
	val getPromptParser = for {
		content <- str("content")
	} yield new Prompt(content)

	val insertUser = SQL(
		"""
		  insert into users (username, email, password, accountType)
		  values ({username}, {email}, {password}, {accountType})
		"""
	)

	val getUserOptions = SQL("select * from userOptions where username = {username}")

	val getUserOptionsParser = for {
		submissionsOpen <- bool("submissionsOpen")
	} yield new UserOptions(submissionsOpen)

}
