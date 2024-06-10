package com.firstsubmission.userfinder.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.firstsubmission.userfinder.R
import com.firstsubmission.userfinder.adapter.UserAdapter
import com.firstsubmission.userfinder.data.model.Entity
import com.firstsubmission.userfinder.databinding.FragmentFavoriteBinding
import com.firstsubmission.userfinder.ui.activity.DetailUserActivity
import com.firstsubmission.userfinder.ui.viewmodel.FavoriteViewModel

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var usersAdapter: UserAdapter
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        usersAdapter = UserAdapter()
        usersAdapter.notifyDataSetChanged()
        favoriteViewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]

        usersAdapter.setOnItemClickCallback(object: UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Entity) {
                Intent(requireContext(), DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.username)
                    it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailUserActivity.EXTRA_AVATAR_URL, data.avatarUrl)
                    startActivity(it)
                }
            }

        })
        binding.apply {
            rvUsers.setHasFixedSize(true)
            rvUsers.layoutManager = LinearLayoutManager(requireContext())
            rvUsers.adapter = usersAdapter

            favoriteViewModel.getFavoriteUser()?.observe(requireActivity()) {
                if (it != null) {
                    usersAdapter.setList(it)
                    if (it.isEmpty()) {
                        tvNoData.visibility = View.VISIBLE
                    } else {
                        tvNoData.visibility = View.GONE
                    }
                }

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}