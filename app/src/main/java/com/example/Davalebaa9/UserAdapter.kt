package com.example.Davalebaa9

import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class UserAdapter(
    private val onClick: (User) -> Unit
) : ListAdapter<User, UserAdapter.VH>(DIFF_UTIL) {

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class VH(private val view: View) : RecyclerView.ViewHolder(view) {

        private var avatar: ImageView = view.findViewById(R.id.ivUserAvatar)
        private var name: TextView = view.findViewById(R.id.tvUserName)
        private var email: TextView = view.findViewById(R.id.tvUserEmail)

        fun bind(user: User) {
            name.text = "${user.first_name} ${user.last_name}"
            email.text = user.email
            Glide.with(avatar)
                .load(user.avatar)
                .into(avatar)

            view.setOnClickListener { onClick(user) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}

class Glide {

}
