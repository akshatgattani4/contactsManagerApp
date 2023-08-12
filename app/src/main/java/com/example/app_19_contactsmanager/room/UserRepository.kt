package com.example.app_19_contactsmanager.room

class UserRepository(private val dao : UserDAO) {

    val users = dao.getAllUsers()

    suspend fun insert(user : User) : Long{
        return dao.insertUser(user)
    }

    suspend fun delete(user : User){
        return dao.deleteUser(user)
    }

    suspend fun update(user : User){
        return dao.updateUser(user)
    }

    suspend fun deleteAll(){
        return dao.deleteAll()
    }
}