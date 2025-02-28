package com.firstsubmission.userfinder.ui.activity

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.firstsubmission.userfinder.R
import com.firstsubmission.userfinder.adapter.SectionsPagerAdapter
import com.firstsubmission.userfinder.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)

        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)
        bundle.putInt(EXTRA_ID, id)

        binding.apply {
            val sectionsPagerAdapter = SectionsPagerAdapter(this@DetailUserActivity, bundle)
            viewPager.adapter = sectionsPagerAdapter
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()

            btnBack.setOnClickListener {
                onBackPressed()
            }
        }
    }


    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_FOLLOWERS = "extra_followers"
        const val EXTRA_FOLLOWING = "extra_following"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_AVATAR_URL = "extra_avatar_url"
        const val EXTRA_HTML_URL = "extra_html_url"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.profile_tab_title,
            R.string.follower_tab_title,
            R.string.following_tab_title
        )
    }

}