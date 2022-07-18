package com.example.contactlist.ui.contact.add

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.contactlist.data.model.Contact
import com.example.contactlist.data.repository.ContactRepository
import com.example.contactlist.ui.contact.base.BaseViewModel
import java.lang.IllegalArgumentException

class AddContactViewModel(private val repository: ContactRepository) : BaseViewModel() {

    fun save() {
        if(name.value.isNullOrEmpty() && phone.value.isNullOrEmpty()) {

        } else repository.addContact(Contact(name = name.value!!, phone = phone.value!!))
    }

    class Provider(private val repository: ContactRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(AddContactViewModel::class.java)) {
                return AddContactViewModel(repository) as T
            }
            throw IllegalArgumentException("Invalid ViewModel")
        }

    }
}