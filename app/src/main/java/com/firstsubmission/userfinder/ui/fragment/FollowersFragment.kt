package com.firstsubmission.userfinder.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.firstsubmission.userfinder.R
import com.firstsubmission.userfinder.adapter.UserFollowsAdapter
import com.firstsubmission.userfinder.data.model.Entity
import com.firstsubmission.userfinder.databinding.FragmentFollowBinding
import com.firstsubmission.userfinder.ui.activity.DetailUserActivity
import com.firstsubmission.userfinder.ui.viewmodel.FollowersViewModel
import com.firstsubmission.userfinder.utils.DataMapper

class FollowersFragment : Fragment(R.layout.fragment_follow) {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding
    private lateinit var viewModel: FollowersViewModel
    private lateinit var adapter: UserFollowsAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFollowBinding.bind(view)

        username = requireActivity().intent.getStringExtra(DetailUserActivity.EXTRA_USERNAME).toString()
        val numOfFollowers = requireActivity().intent.getIntExtra(DetailUserActivity.EXTRA_FOLLOWERS, 0)

        adapter = UserFollowsAdapter()

        binding?.apply {
            rvUsers.setHasFixedSize(true)
            rvUsers.layoutManager = LinearLayoutManager(activity)
            rvUsers.adapter = adapter

            if (numOfFollowers == 0){
                tvNoDataFollows.visibility = View.VISIBLE
            } else {
                tvNoDataFollows.visibility = View.GONE
            }
        }
        showLoading(true)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[FollowersViewModel::class.java]
        searchFollowers()

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

    private fun searchFollowers(){
        viewModel.setListFollowers(username)
        viewModel.getListFollowers().observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setList(DataMapper.mapResponsesToEntities(it))
                showLoading(false)
            } else {
                viewModel.setListFollowers(username)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

}