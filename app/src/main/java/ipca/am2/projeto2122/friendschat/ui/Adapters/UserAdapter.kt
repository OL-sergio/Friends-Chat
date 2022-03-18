package ipca.am2.projeto2122.friendschat.ui.Adapters

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import ipca.am2.projeto2122.friendschat.R
import ipca.am2.projeto2122.friendschat.ui.Activitys.MessageChatActivity
import ipca.am2.projeto2122.friendschat.ui.model.Users


class UserAdapter(
    mContext: Context,
    mUsers: List<Users>,
    isChatCheck: Boolean
    ): RecyclerView.Adapter<UserAdapter.ViewHolder?>(){

    private val mContext    : Context
    private val mUsers      : List<Users>
    private var isChatCheck : Boolean

    init {
        this.mContext       = mContext
        this.mUsers         = mUsers
        this.isChatCheck    = isChatCheck
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
       val view: View = LayoutInflater.from(mContext)
           .inflate(R.layout.layout_search_for_user, viewGroup,false)
        return UserAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val user: Users = mUsers[position]

        holder.userNameTextView.text = user.getUsername().toString()
        Picasso.get().load(user.getProfile()).placeholder(R.drawable.ic_user_account).into(holder.profileImageView)

            if (isChatCheck){
                if (user.getStatus() == "online"){
                    holder.onlineImageView.visibility = View.VISIBLE
                    holder.offlineImageView.visibility = View.GONE

                }else{
                    holder.onlineImageView.visibility = View.GONE
                    holder.offlineImageView.visibility = View.VISIBLE

                }
            }else{
                holder.offlineImageView.visibility = View.GONE
                holder.onlineImageView.visibility = View.GONE

            }

        holder.itemView.setOnClickListener {

            val options = arrayOf<CharSequence>(
                "Send a message"
            )

            val builder : AlertDialog.Builder = AlertDialog.Builder(mContext)
            builder.setTitle("What do you want?")

            builder.setItems(options, DialogInterface.OnClickListener { dialog, position ->
                if (position == 0 ){
                    val intent = Intent(mContext, MessageChatActivity::class.java )
                    intent.putExtra("visitUser_id", user.getUID())
                    mContext.startActivity(intent)
                }
            })
            builder.show()
        }
    }

    override fun getItemCount(): Int {
      return mUsers.size
    }

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        var userNameTextView : TextView
        var profileImageView : CircleImageView
        var onlineImageView  : CircleImageView
        var offlineImageView : CircleImageView
        var lastMessageText  : TextView

        init {
            userNameTextView = itemView.findViewById(R.id.layout_search_username)
            profileImageView = itemView.findViewById(R.id.layout_search_profile_image)
            onlineImageView  = itemView.findViewById(R.id.layout_search_image_online)
            offlineImageView = itemView.findViewById(R.id.layout_search_image_offline)
            lastMessageText  = itemView.findViewById(R.id.layout_search_last_message)
        }
    }
}





