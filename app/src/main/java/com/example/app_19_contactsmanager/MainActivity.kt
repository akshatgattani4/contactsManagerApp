package com.example.app_19_contactsmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app_19_contactsmanager.viewModel.UserViewModel
import com.example.app_19_contactsmanager.viewModel.ViewModelFactory
import com.example.app_19_contactsmanager.ViewUI.MyAdapter
import com.example.app_19_contactsmanager.databinding.ActivityMainBinding
import com.example.app_19_contactsmanager.room.User
import com.example.app_19_contactsmanager.room.UserDatabase
import com.example.app_19_contactsmanager.room.UserRepository

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val dao = UserDatabase.getInstance(application).userDAO
        val repository = UserRepository(dao)
        val factory = ViewModelFactory(repository)

        viewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)

        binding.myViewModel = viewModel

        binding.lifecycleOwner = this

        initRecyclerView()


    }

    private fun initRecyclerView() {
        binding.recycler1.layoutManager = LinearLayoutManager(this)
        displayUsers()
    }

    private fun clicked(selectedItem: User) {
        Toast.makeText(this, "${selectedItem.name}", Toast.LENGTH_SHORT).show()

        viewModel.initUpdateOrDelete(selectedItem)
    }

    private fun displayUsers() {
        viewModel.users.observe(this, Observer {
            binding.recycler1.adapter = MyAdapter(
            it, {selectedItem : User -> clicked(selectedItem)}
            )
        })
    }
}