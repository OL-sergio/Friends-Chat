package ipca.am2.projeto2122.friendschat.ui.Adapters

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import ipca.am2.projeto2122.friendschat.R
import ipca.am2.projeto2122.friendschat.ui.Constants.Constants.Companion.CHATS
import ipca.am2.projeto2122.friendschat.ui.Constants.Constants.Companion.EMPTY_STRING
import ipca.am2.projeto2122.friendschat.ui.Constants.Constants.Companion.SEEN
import ipca.am2.projeto2122.friendschat.ui.Constants.Constants.Companion.SEND_IMAGE
import ipca.am2.projeto2122.friendschat.ui.Constants.Constants.Companion.TAG_CHAT
import ipca.am2.projeto2122.friendschat.ui.model.Chat




class ChatAdapter (
    mContext    : Context,
    mChatList   : List<Chat>,
    imageURL    : String
        ): RecyclerView.Adapter<ChatAdapter.ViewHolder?>() {

            private val mContext    : Context
            private val mChatList   : List<Chat>
            private val imageURL    : String
            private var firebaseUser : FirebaseUser = FirebaseAuth.getInstance().currentUser!!

            init {
                this.mChatList  = mChatList
                this.mContext   = mContext
                this.imageURL   = imageURL
            }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        return if (position == 1) {
            val view: View = LayoutInflater.from(mContext).inflate(
                R.layout.activity_message_item_right
                ,parent,false)
            ViewHolder(view)
        }else {
            val view: View = LayoutInflater.from(mContext).inflate(
                R.layout.activity_message_item_left
                ,parent,false)
            ViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return mChatList.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var userProfileImage    : CircleImageView?  = null
        var showTextMessage     : TextView?         = null
        var leftImageView       : ImageView?        = null
        var rightImageView      : ImageView?        = null
        var textIsSeen          : TextView?         = null

        init {
            userProfileImage = itemView.findViewById(R.id.imageView_settings_user_profile)
            showTextMessage  = itemView.findViewById(R.id.textView_show_message)
            leftImageView    = itemView.findViewById(R.id.imageView_view_Image_message_item_left)
            rightImageView   = itemView.findViewById(R.id.imageView_view_Image_message_item_right)
            textIsSeen       = itemView.findViewById(R.id.textView_isSeen_message_item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (mChatList[position].getSenderID().equals(firebaseUser.uid)){
            1
        }else{
            0
        }
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val chat : Chat = mChatList[position]

        Picasso.get().load(imageURL).into(holder.userProfileImage)

        if (chat.getMessage().equals(SEND_IMAGE) && !chat.getUrl().equals(EMPTY_STRING)) {
            //Image message - right side
            if (chat.getSenderID().equals(firebaseUser.uid)){
                holder.showTextMessage!!.visibility = View.GONE
                holder.rightImageView!!.visibility = View.VISIBLE
                Picasso.get().load(chat.getUrl()).into(holder.rightImageView)

            }
            // Image message - left side
            else if (!chat.getSenderID().equals(firebaseUser.uid)) {
                holder.showTextMessage!!.visibility = View.GONE
                holder.leftImageView!!.visibility = View.VISIBLE
                Picasso.get().load(chat.getUrl()).into(holder.leftImageView)
            }
        }else{
            holder.showTextMessage!!.text = chat.getMessage()
            Log.d(TAG_CHAT, chat.getMessage().toString())
            holder.showTextMessage!!.visibility = View.VISIBLE


        }
    }
}


