package com.example.app_19_contactsmanager.ViewUI

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.app_19_contactsmanager.R
import com.example.app_19_contactsmanager.databinding.CardViewBinding
import com.example.app_19_contactsmanager.room.User

class MyAdapter(private val userList: List<User>, private val clickListener : (User) -> Unit) : RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val layoutInflator = LayoutInflater.from(parent.context)

        val binding : CardViewBinding = DataBindingUtil.inflate(layoutInflator, R.layout.card_view, parent, false)

        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(userList[position], clickListener)
    }


}

class MyViewHolder(val binding : CardViewBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(user : User, clickListener: (User) -> Unit){
        binding.nameTxt.text = user.name
        binding.emailTxt.text = user.email
        binding.listItemsLayout.setOnClickListener(){
            clickListener(user)
        }
    }
}