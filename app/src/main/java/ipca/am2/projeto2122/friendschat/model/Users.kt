package ipca.am2.projeto2122.friendschat.model

class Users {

    private var uid         : String = ""
    private var userName    : String = ""
    private var email       : String = ""
    private var phoneNumber : String = ""
    private var fullName    : String = ""
    private var profile     : String = ""
    private var cover       : String = ""
    private var status      : String = ""
    private var search      : String = ""

    constructor()

    constructor(

        uid: String,
        userName: String,
        email: String,
        phoneNumber: String,
        fullName: String,
        profile: String,
        cover: String,
        status: String,
        search: String

    )
    {
        this.uid         = uid
        this.userName    = userName
        this.email       = email
        this.phoneNumber = phoneNumber
        this.fullName    = fullName
        this.profile     = profile
        this.cover       = cover
        this.status      = status
        this.search      = search
    }

    fun getUID(): String?{
        return uid
    }

    fun setUID(uid: String){
        this.uid = uid
    }

    fun getUsername(): String?{
        return userName
    }

    fun setUsername(userName: String){
        this.userName = userName
    }

    fun getEmail(): String?{
        return email
    }

    fun setEmail(email: String){
        this.email = email
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