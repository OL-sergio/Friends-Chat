package ipca.am2.projeto2122.friendschat.ui.model

import android.graphics.Bitmap
import com.google.firebase.firestore.QueryDocumentSnapshot

class Photo {
    var description : String = ""
    var imageUrl    : String = ""
    var bitmap      : Bitmap? = null
    var id          : String? = null

    constructor(description: String, imageUrl: String ) {
        this.description    = description
        this.imageUrl       = imageUrl
    }

    fun toHash() : HashMap<String, Any>{
        val hashMap = HashMap<String,Any>()
            hashMap.put("description"   , description)
            hashMap.put("imageUrl"      , imageUrl)
        return hashMap
    }


    companion object {
        fun fromHash( hashMap: QueryDocumentSnapshot): Photo {
            val photo = Photo(
                hashMap["description"] as String,
                hashMap["imageUrl"] as String
            )
            return photo
        }
    }

}