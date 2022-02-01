package ipca.am2.projeto2122.friendschat.ui.Adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import ipca.am2.projeto2122.friendschat.R

import ipca.am2.projeto2122.friendschat.ui.model.Chat


class ChatAdapter (
    mContext    : Context,
    mChatList   : List<Chat>,
    imageURL    : String
        ): RecyclerView.Adapter<ChatAdapter.ViewHolder?>() {

            private val mContext    : Context
            private val mChatList   : List<Chat>
            private val imageURL    : String
            var fireBaseUser : FirebaseUser = FirebaseAuth.getInstance().currentUser!!

            init {
                this.mChatList  = mChatList
                this.mContext   = mContext
                this.imageURL   = imageURL
            }


    override fun getItemViewType(position: Int): Int {
       return if (mChatList[position].getSender().equals(fireBaseUser!!.uid)){
           1
       }else{
           0
       }
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var userProfileImage   : CircleImageView? = null
        var showTextMessage    : TextureView? = null
        var leftImageView      : ImageView? = null
        var rightImageView     : ImageView? = null
        var textIsSeen         : TextureView? = null

          init {
            userProfileImage = itemView.findViewById(R.id.imageView_settings_user_profile)
            showTextMessage  = itemView.findViewById(R.id.textView_show_message)
            leftImageView    = itemView.findViewById(R.id.imageView_view_Image_message_item_left)
            rightImageView   = itemView.findViewById(R.id.imageView_view_Image_message_item_right)
            textIsSeen       = itemView.findViewById(R.id.textView_isSeen_message_item)

        }

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




    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chat : Chat = mChatList[position]

        Picasso.get().load(imageURL).into(holder.userProfileImage)

        if (chat.getMessage().equals("sent you an image") && !chat.getUrl().equals("")) {



        }
    }

    override fun getItemCount(): Int {
        return mChatList.size
    }
}


