package com.example.contactlist.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactlist.R
import com.example.contactlist.data.model.Contact
import com.example.contactlist.data.repository.ContactRepository
import com.example.contactlist.databinding.FragmentHomeBinding
import com.example.contactlist.ui.ContactAdapter

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels { HomeViewModel.Provider(ContactRepository.contactRepository) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBindView()
        setupFragmentListener()
    }

    private fun onBindView() {
        viewModel.contacts.observe(viewLifecycleOwner) { contacts ->
            setupAdapter(contacts)
        }

        binding.fabAdd.setOnClickListener {
            navigateToAddContact()
        }
    }

    private fun setupAdapter(contacts: List<Contact>) {
        val contactAdapter = ContactAdapter(contacts)
        contactAdapter.listener = object: ContactAdapter.Listener {
            override fun onItemClicked(item: Contact) {
                navigateToEditContact(item.id!!)
            }
        }
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvContacts.adapter = contactAdapter
        binding.rvContacts.layoutManager = layoutManager
    }

    private fun navigateToAddContact() {
        val action = HomeFragmentDirections.actionHomeToAddContact("Test", 12312)
        NavHostFragment.findNavController(this).navigate(action)
    }

    private fun navigateToEditContact(id: Int) {
        val action = HomeFragmentDirections.actionHomeToEditContact(id)
        NavHostFragment.findNavController(this).navigate(action)
    }

    private fun setupFragmentListener() {
        setFragmentResultListener("new_contact") { _, result ->
            if (result.getBoolean("refresh")) {
                viewModel.refresh()
            }
        }
        setFragmentResultListener("finished_edit") { _, result ->
            if (result.getBoolean("refresh")) {
                viewModel.refresh()
            }
        }
    }
}