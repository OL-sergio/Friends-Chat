package ipca.am2.projeto2122.friendschat.ui.model

import android.graphics.Bitmap
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.auth.User
import ipca.am2.projeto2122.friendschat.ui.model.Users as Users

class Users {


    private var email       : String = ""
    var imageURL            : String = ""
    var bitmap              : Bitmap? = null
    var id                  : String? = null
    private var uid         : String = ""
    private var username    : String = ""
    private var profile     : String = ""
    private var cover       : String = ""
    private var status      : String = ""
    private var search      : String = ""

    constructor(
        email: String,
        imageURL: String,
        uid: String,
        username: String,
        profile: String,
        cover: String,
        status: String,
        search: String

    )
    {
        this.email      = email
        this.imageURL   = imageURL
        this.uid        = uid
        this.username   = username
        this.profile    = profile
        this.cover      = cover
        this.status     = status
        this.search     = search
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
                hashMap["imageURL"] as String,
                "",
                "",
                "",
                "",
                "",
                ""

            )
            return user
        }
    }


    fun getUID(): String?{
        return uid
    }

    fun setUID(uid: String){
        this.uid = uid
    }

    fun getUsername(): String?{
        return username
    }

    fun setUsername(username: String){
        this.username = username
    }

    fun getProfile(): String?{
        return profile
    }

    fun setProfile(profile: String){
        this.profile = profile
    }

    fun getCover(): String?{
        return cover
    }

    fun setCover(cover: String){
        this.cover = cover
    }

    fun getStatus(): String?{
        return status
    }

    fun setStatus(status: String){
        this.status = status
    }

    fun getSearch(): String?{
        return search
    }

    fun setSearch(search: String){
        this.search = search
    }

}