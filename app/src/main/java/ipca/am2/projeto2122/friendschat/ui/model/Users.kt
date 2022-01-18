package ipca.am2.projeto2122.friendschat.ui.model

import android.graphics.Bitmap
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.auth.User
import ipca.am2.projeto2122.friendschat.ui.model.Users as Users

class Users {


    private var email       : String = ""
    private var imageURL    : String = ""
    var bitmap              : Bitmap? = null
    var id                  : String? = null

    constructor(email: String, imageURL: String) {
        this.email      = email
        this.imageURL   = imageURL
    }

    fun toHash() : HashMap<String, Any>{
        val hashMap = HashMap<String,Any>()
        hashMap["email"] = email
        hashMap["imageURL"] = imageURL
        return hashMap
    }



    companion object {
        fun fromHash( hashMap: QueryDocumentSnapshot): Users {
            val user = Users(
                hashMap["email"] as String,
                hashMap["imageURL"] as String

            )
            return user
        }
    }
}