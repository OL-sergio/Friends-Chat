package ipca.am2.projeto2122.friendschat.ui.fragments.chat

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import ipca.am2.projeto2122.friendschat.databinding.FragmentChatBinding


class ChatFragment : Fragment() {

    private var _binding : FragmentChatBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentChatBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

}