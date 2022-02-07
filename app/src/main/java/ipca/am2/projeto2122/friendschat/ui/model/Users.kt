package ipca.am2.projeto2122.friendschat.ui.model

import android.graphics.Bitmap
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.auth.User
import ipca.am2.projeto2122.friendschat.ui.model.Users as Users

class Users {

    var id                  : String? = null
    private var email       : String = ""

    private var uid         : String = ""
    private var username    : String = ""
    private var phoneNumber : String = ""
    private var fullName    : String = ""
    private var profile     : String = ""
    private var cover       : String = ""
    private var status      : String = ""
    private var search      : String = ""

    constructor()

    constructor(
        email: String,

        uid: String,
        username: String,
        phoneNumber: String,
        fullName: String,
        profile: String,
        cover: String,
        status: String,
        search: String

    )
    {
        this.email      = email

        this.uid        = uid
        this.username   = username
        this.phoneNumber= phoneNumber
        this.fullName   = fullName
        this.profile    = profile
        this.cover      = cover
        this.status     = status
        this.search     = search
    }

    fun toHash() : HashMap<String, Any>{
        val hashMap = HashMap<String,Any>()
        hashMap["email"] = email
        return hashMap
    }
    companion object {
        fun fromHash( hashMap: QueryDocumentSnapshot): Users {
            val user = Users(
                hashMap["email"] as String,
                "", "", "", "",
                "", "", "", ""
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

    fun getPhoneNumber(): String?{
        return phoneNumber
    }

    fun setPhoneNumber(phoneNumber: String){
        this.phoneNumber = phoneNumber
    }

    fun getFullName(): String?{
        return fullName
    }

    fun setFullName(fullName: String){
        this.fullName = fullName
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