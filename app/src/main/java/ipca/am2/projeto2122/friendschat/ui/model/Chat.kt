package ipca.am2.projeto2122.friendschat.ui.model

import android.content.IntentSender

class Chat {
    private var senderId        : String?   = null
    private var senderMessage   : String?   = null
    private var receiverMessage : String?   = null
    private var isSeen          : Boolean?  = false
    private var uRl             : String?   = null
    private var messageID       : String?   = null


    constructor()

    constructor(
        senderId        : String?,
        senderMessage   : String?,
        receiver        : String?,
        isSeen          : Boolean?,
        uRl             : String?,
        messageID       : String?
    ) {
        this.senderId           = senderId
        this.senderMessage      = senderMessage
        this.receiverMessage    = receiver
        this.isSeen             = isSeen
        this.uRl                = uRl
        this.messageID          = messageID
    }


    fun getSender(): String? {
        return senderId
    }
    fun setSender(senderId: String?){
        this.senderId = senderId
    }

    fun getMessage(): String? {
        return senderMessage
    }
    fun setMessage(senderMessage: String?){
        this.senderMessage = senderMessage
    }

    fun getReceiver(): String? {
        return receiverMessage
    }
    fun setReceiver(receiver: String?){
        this.receiverMessage = receiver
    }

    fun isIsSeen(): Boolean? {
        return isSeen
    }
    fun setIsSeen(isSeen: Boolean?){
        this.isSeen = isSeen
    }

    fun getUrl(): String? {
        return uRl
    }
    fun setUrl(url: String?){
        this.uRl = url
    }

    fun getMessageId(): String? {
        return messageID
    }
    fun setMessageId(messageID: String?){
        this.messageID = messageID
    }


}