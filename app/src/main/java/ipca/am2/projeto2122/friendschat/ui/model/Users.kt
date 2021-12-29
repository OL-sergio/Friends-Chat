package ipca.am2.projeto2122.friendschat.ui.model

import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.auth.User
import ipca.am2.projeto2122.friendschat.ui.model.Users as Users

class Users {

    var email : String = ""


    constructor(email: String) {
        this.email = email
    }

    fun toHash() : HashMap<String, Any>{
        val hashMap = HashMap<String,Any>()
        hashMap["email"] = email
        return hashMap
    }


    companion object {
        fun fromHash( hashMap: QueryDocumentSnapshot): Users {
            val user = Users(
                hashMap["email"] as String
            )
            return user
        }
    }
}