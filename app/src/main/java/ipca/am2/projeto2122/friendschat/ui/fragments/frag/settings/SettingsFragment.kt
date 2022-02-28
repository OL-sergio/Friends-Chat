package ipca.am2.projeto2122.friendschat.ui.fragments.frag.settings

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ipca.am2.projeto2122.friendschat.R
import ipca.am2.projeto2122.friendschat.databinding.FragmentSettingsBinding
import ipca.am2.projeto2122.friendschat.ui.model.Photo


class SettingsFragment : Fragment() {


    var photos = arrayListOf<Photo>()

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val mDatabase = Firebase.firestore

    private var mAdapter : RecyclerView.Adapter<*>? = null
    private var mLayoutManager: LinearLayoutManager? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        _binding?.imageViewSettingsUserProfile?.setOnClickListener {
            activity?.let{
                val intent = Intent (it, AddPhotoProfileActivity::class.java)
                it.startActivity(intent)

            }
        }

        mLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewPhotos.layoutManager = mLayoutManager
        mAdapter = PhotoAdapter()
        binding.recyclerViewPhotos.adapter = mAdapter


        mDatabase.collection("imageUserProfile")
            .addSnapshotListener{ documents, e ->
                documents?.let {
                    photos.clear()
                    for (document in it){
                        Log.d(TAG, "${document.id} => ${document.data}")
                        val addPhoto = Photo.fromHash(document)
                        photos.add(addPhoto)

                    }
                    mAdapter!!.notifyDataSetChanged()
                }

            }


        setHasOptionsMenu(true)

    }

    inner class PhotoAdapter :RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {

        inner class ViewHolder (val view: View) : RecyclerView.ViewHolder(view)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
           return ViewHolder(
               LayoutInflater.from(parent.context).inflate(R.layout.row_view_photo_profile, parent,false)


           )

        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            holder.itemView.setOnClickListener {
                activity?.let {
                    val intent = Intent(it, AddPhotoProfileActivity::class.java)
                    it.startActivity(intent)
                }
            }


            holder.view.findViewById<TextView>(R.id.textView_Picture_view_Description).text = photos[position].description

            holder.view.apply {

                val textView_Picture_Description = findViewById<TextView>(R.id.textView_Picture_view_Description)
                textView_Picture_Description.text = photos[position].description

                val imageViewPhoto = findViewById<ImageView>(R.id.row_view_profile_picture)
                Glide.with(this).load(photos[position].imageUrl).into(imageViewPhoto)

            }

        }

        override fun getItemCount(): Int {
          return photos.size
        }

    }
    companion object{
        const val TAG = "SettingsFragment"
    }

}

