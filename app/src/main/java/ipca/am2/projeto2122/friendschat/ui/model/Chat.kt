package ipca.am2.projeto2122.friendschat.ui.model


class Chat {
    private var senderID            : String?   = ""
    private var receiverID          : String?   = ""
    private var messageID           : String?   = ""
    private var message             : String?   = ""
    private var isSeen              : Boolean?  = false
    private var uRl                 : String?   = ""

    constructor()
    constructor(
        senderID      : String?,
        messageID     : String?,
        receiverID    : String?,
        message       : String?,
        isSeen        : Boolean?,
        uRl           : String?
    ) {
        this.senderID = senderID
        this.receiverID = receiverID
        this.messageID = messageID
        this.message = message
        this.isSeen = isSeen
        this.uRl = uRl
    }

        fun getSenderID(): String? {
            return senderID
        }

        fun setSenderID(senderID: String?) {
            this.senderID = senderID!!
        }

        fun getReceiverID(): String? {
            return receiverID
        }

        fun setReceiverID(receiverID: String?) {
            this.receiverID = receiverID!!
        }

        fun getMessageId(): String? {
            return messageID
        }

        fun setMessageId(messageID: String?) {
            this.messageID = messageID!!
        }

        fun getMessage(): String? {
            return message
        }

        fun setMessage(message: String?) {
            this.message = message!!
        }

        fun isIsSeen(): Boolean? {
            return isSeen
        }

        fun setIsSeen(isSeen: Boolean?) {
            this.isSeen = isSeen!!
        }

        fun getUrl(): String? {
            return uRl
        }

        fun setUrl(uRl: String?) {
            this.uRl = uRl!!
        }

}