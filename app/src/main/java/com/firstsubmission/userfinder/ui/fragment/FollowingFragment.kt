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
import com.firstsubmission.userfinder.adapter.UserFollowsAdapter
import com.firstsubmission.userfinder.data.model.Entity
import com.firstsubmission.userfinder.databinding.FragmentFollowBinding
import com.firstsubmission.userfinder.ui.activity.DetailUserActivity
import com.firstsubmission.userfinder.ui.viewmodel.FollowingViewModel
import com.firstsubmission.userfinder.utils.DataMapper

// TODO: Rename parameter arguments, choose names that match

class FollowingFragment : Fragment(R.layout.fragment_follow) {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding
    private lateinit var viewModel: FollowingViewModel
    private lateinit var adapter: UserFollowsAdapter
    private lateinit var username: String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFollowBinding.bind(view)

        username = requireActivity().intent.getStringExtra(DetailUserActivity.EXTRA_USERNAME).toString()
        val numOfFollowing = requireActivity().intent.getIntExtra(DetailUserActivity.EXTRA_FOLLOWING, 0)

        adapter = UserFollowsAdapter()

        binding?.apply {
            rvUsers.setHasFixedSize(true)
            rvUsers.layoutManager = LinearLayoutManager(activity)
            rvUsers.adapter = adapter

            if (numOfFollowing == 0){
                tvNoDataFollows.visibility = View.VISIBLE
            } else {
                tvNoDataFollows.visibility = View.GONE
            }
        }
        showLoading(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FollowingViewModel::class.java]
        searchFollowing()

        adapter.setOnItemClickCallback(object: UserFollowsAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Entity) {
                Intent(requireContext(), DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.username)
                    it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailUserActivity.EXTRA_AVATAR_URL, data.avatarUrl)
                    it.putExtra(DetailUserActivity.EXTRA_HTML_URL, data.htmlUrl)
                    startActivity(it)
                }
            }
        })
    }


    private fun searchFollowing(){
        viewModel.setListFollowing(username)
        viewModel.getListFollowing().observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setList(DataMapper.mapResponsesToEntities(it))
                showLoading(false)

            }
        }
    }


    private fun showLoading(state: Boolean) {
        binding?.apply {
            if (state) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}