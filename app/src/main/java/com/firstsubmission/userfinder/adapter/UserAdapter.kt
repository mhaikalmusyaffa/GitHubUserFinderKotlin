package com.firstsubmission.userfinder.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firstsubmission.userfinder.data.model.Entity
import com.firstsubmission.userfinder.databinding.ItemUsersBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val list = ArrayList<Entity>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setList(userEntity: List<Entity>) {
        list.clear()
        list.addAll(userEntity)
        notifyDataSetChanged()
    }

    fun clearList() {
        list.clear()
    }

    inner class UserViewHolder(private val binding: ItemUsersBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(userEntity: Entity) {
            binding.apply {
                root.setOnClickListener {
                    onItemClickCallback?.onItemClicked(userEntity)
                }
                tvItemUsername.text = userEntity.username
                tvItemUserUrl.text = userEntity.htmlUrl
                Glide.with(itemView)
                    .load(userEntity.avatarUrl)
                    .centerCrop()
                    .into(ivItemProfile)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {

        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Entity)
    }
}