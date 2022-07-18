package com.example.contactlist.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.contactlist.data.model.Contact
import com.example.contactlist.data.repository.ContactRepository
import java.lang.IllegalArgumentException

class HomeViewModel(private val contactRepository: ContactRepository): ViewModel() {
    private val _contacts: MutableLiveData<List<Contact>> = MutableLiveData()
    val contacts: LiveData<List<Contact>> = _contacts

    init {
        getContacts()
    }

    private fun getContacts() {
        val response = contactRepository.getContacts()
        _contacts.value = response
    }

    fun refresh() {
        getContacts()
    }

    class Provider(private val repository: ContactRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                return HomeViewModel(repository) as T
            }
            throw IllegalArgumentException("Invalid ViewModel")
        }
    }
}