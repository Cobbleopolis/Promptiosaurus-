package models.user

import util.DBUtil

class User(val username: String, val email: String, val password: String, val accountType: Int) {

	def getUserOptions: UserOptions = DBUtil.getUserOptions(this)

}
