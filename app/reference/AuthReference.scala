package reference

import play.api.Play
import play.api.libs.ws.WS
import play.api.Play.current

object AuthReference {

	val googleAuth = WS.url("https://www.googleapis.com/oauth2/v3/tokeninfo")
	val googleClientId: String = Play.current.configuration.getString("auth.google.client_id").get

}
