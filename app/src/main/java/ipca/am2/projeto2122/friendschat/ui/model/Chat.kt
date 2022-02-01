package ipca.am2.projeto2122.friendschat.ui.model

import android.content.IntentSender

class Chat {
    private var sender  : String? = null
    private var message : String? = null
    private var receiver: String? = null
    private var isSeen  : Boolean? = false
    private var url     : String? = null
    private var messageID: String? = null


    constructor()

    constructor(
        sender: String?,
        message: String?,
        receiver: String?,
        isSeen: Boolean?,
        url: String?,
        messageID: String?
    ) {
        this.sender     = sender
        this.message    = message
        this.receiver   = receiver
        this.isSeen     = isSeen
        this.url        = url
        this.messageID  = messageID
    }


    fun getSender(): String? {
        return sender
    }
    fun setSender(sender: String?){
        this.sender = sender
    }

    fun getMessage(): String? {
        return message
    }
    fun setMessage(message: String?){
        this.message = message
    }

    fun getReceiver(): String? {
        return receiver
    }
    fun setReceiver(receiver: String?){
        this.receiver = receiver
    }

    fun isIsSeen(): Boolean? {
        return isSeen
    }
    fun setIsSeen(isSeen: Boolean?){
        this.isSeen = isSeen
    }

    fun getUrl(): String? {
        return url
    }
    fun setUrl(url: String?){
        this.url = url
    }

    fun getMessageId(): String? {
        return messageID
    }
    fun setMessageId(messageID: String?){
        this.messageID = messageID
    }


}