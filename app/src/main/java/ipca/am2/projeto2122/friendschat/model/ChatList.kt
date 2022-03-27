package ipca.am2.projeto2122.friendschat.model

class ChatList {
    private var id: String? = null

    constructor()

    constructor(id: String){
        this.id = id
    }

    fun getID(): String?{
        return id

    }

    fun setID(id: String){
        this.id = id
    }
}