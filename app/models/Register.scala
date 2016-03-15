package models

import org.mindrot.jbcrypt.BCrypt
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.validation.{Invalid, Valid, ValidationError, Constraint}
import util.DBUtil

import scala.collection.mutable

object Register {

	val usernameCheckConstraint: Constraint[String] = Constraint("constraints.usernamecheck")({
		username =>
			var errors: mutable.Buffer[ValidationError] = mutable.Buffer.empty[ValidationError]

			if (DBUtil.getUserFromUsername(username) != null)
				errors += ValidationError("Username already exists")

			if(errors.isEmpty)
				Valid
			else
				Invalid(errors)
	})

	val formConstraint: Constraint[RegisterData] = Constraint("constraints.thatThing")({
		data =>
			var errors: mutable.Buffer[ValidationError] = mutable.Buffer.empty[ValidationError]

			if (data.password == data.confirmPassword)
				errors += ValidationError("Passwords do not match")

			if (errors.isEmpty)
				Valid
			else
				Invalid(errors)
	})

	val form = Form(
		mapping(
			"Username" -> nonEmptyText(maxLength = 64),
			"Password" -> nonEmptyText,
			"Confirm Password" -> nonEmptyText,
			"Email" -> email,
			"Confirm Email" -> email
		)(RegisterData.apply)(RegisterData.unapply)
//			verifying formConstraint
			verifying("Passwords do not match", f => f.password == f.confirmPassword)
			verifying("Emails do not match", f => f.email == f.confirmEmail)
			verifying("Username already exists", f => DBUtil.getUserFromUsername(f.username) == null)
	)

}

case class RegisterData(username: String, password: String, confirmPassword: String, email: String, confirmEmail: String) {
	def getNewUserData: NewUserData = {
		val salt = BCrypt.gensalt()
		val pass = BCrypt.hashpw(password, salt)
		new NewUserData(username, email, pass, 1)
	}
}

class NewUserData(val username: String, val email: String, val password: String, val accountType: Int)