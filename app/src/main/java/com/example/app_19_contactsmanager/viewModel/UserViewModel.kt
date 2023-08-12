package com.example.app_19_contactsmanager.viewModel

import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_19_contactsmanager.room.User
import com.example.app_19_contactsmanager.room.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel(), androidx.databinding.Observable {

    val users = repository.users
    private var isUpdateOrDelete = false
    private lateinit var userToUpdateOrDelete : User

    @Bindable
    val inputName = MutableLiveData<String?>()

    @Bindable
    val inputEmail = MutableLiveData<String?>()

    @Bindable
    val saveOrUpdateText = MutableLiveData<String>()

    @Bindable
    val clearOrDeleteText = MutableLiveData<String>()

    init {
        saveOrUpdateText.value = "Save"
        clearOrDeleteText.value = "Delete"
    }

    fun saveOrUpdate(){

        if(isUpdateOrDelete){
            userToUpdateOrDelete.name = inputName.value!!
            userToUpdateOrDelete.email = inputEmail.value!!

            update(userToUpdateOrDelete)
        }else {
            var name = inputName.value!!
            var email = inputEmail.value!!

            insert(User(0, name, email))

            inputName.value = null
            inputEmail.value = null
        }
    }

    fun clearOrDelete(){
        if(isUpdateOrDelete){
            delete(userToUpdateOrDelete)
        }else{
            clearAll()
        }
    }

    fun insert(user : User) = viewModelScope.launch {
        repository.insert(user)
    }

    fun clearAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    fun update(user : User) = viewModelScope.launch {
        repository.update(user)

        inputName.value = null
        inputEmail.value = null
        isUpdateOrDelete = false
        saveOrUpdateText.value = "Save"
        clearOrDeleteText.value = "Delete"
    }

    fun delete(user : User) = viewModelScope.launch {
        repository.delete(user )

        inputName.value = null
        inputEmail.value = null
        isUpdateOrDelete = false
        saveOrUpdateText.value = "Save"
        clearOrDeleteText.value = "Delete"
    }

    override fun addOnPropertyChangedCallback(callback: androidx.databinding.Observable.OnPropertyChangedCallback?) {
    }

    override fun removeOnPropertyChangedCallback(callback: androidx.databinding.Observable.OnPropertyChangedCallback?) {
    }

    fun initUpdateOrDelete(user: User) {
        inputName.value = user.name
        inputEmail.value = user.email
        isUpdateOrDelete = true
        userToUpdateOrDelete = user
        saveOrUpdateText.value = "Update"
        clearOrDeleteText.value = "Delete"
    }

}