package com.example.githubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import com.bumptech.glide.Glide
import com.example.githubuser.viewmodel.DetailViewModel
import com.example.githubuser.R
import com.example.githubuser.adapter.SectionsPagerAdapter
import com.example.githubuser.databinding.ActivityUserDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()


    companion object {
        const val EXTRA_LOGIN = "extra_login"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_followers,
            R.string.tab_following
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val usernameData = intent.getStringExtra(EXTRA_LOGIN).toString()
        detailViewModel.getUserData(usernameData)


        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailViewModel.username.observe(this) { username ->
            binding.tvUsernameDetail.text = username
        }

        detailViewModel.name.observe(this) { name ->
            binding.tvNameDetail.text = name
        }

        detailViewModel.picture.observe(this) { picture ->
            Glide.with(this@UserDetailActivity)
                .load(picture).circleCrop().into(binding.ivProfileDetail)
        }

        detailViewModel.following.observe(this) { following ->
            binding.tvFollowing.text = "$following Following"
        }

        detailViewModel.followers.observe(this) { followers ->
            binding.tvFollowers.text = "$followers Followers"
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        binding.viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        detailViewModel.getUserFollowers(usernameData)
        detailViewModel.getUserFollowing(usernameData)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }
}