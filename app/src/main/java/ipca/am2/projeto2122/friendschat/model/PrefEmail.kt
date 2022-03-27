package ipca.am2.projeto2122.friendschat.model

import com.google.firebase.firestore.QueryDocumentSnapshot
import ipca.am2.projeto2122.friendschat.model.PrefEmail as PrefEmail

class PrefEmail {


    var id                  : String? = null
    private var email       : String = ""

    constructor()

    constructor( email: String) {

        this.email = email
    }

    fun setEmailHash() : HashMap<String, Any>{
        val hashMap = HashMap<String,Any>()
        hashMap["email"] = email
        return hashMap
    }
    companion object {
        fun fromHash( hashMap: QueryDocumentSnapshot): PrefEmail {
            val email = PrefEmail(
                hashMap["email"] as String

            )
            return email
        }


    }
}