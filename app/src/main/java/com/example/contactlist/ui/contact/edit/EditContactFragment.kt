package com.example.contactlist.ui.contact.edit

import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.contactlist.ui.contact.base.BaseContactFragment
import androidx.navigation.fragment.navArgs
import com.example.contactlist.data.model.Contact
import com.example.contactlist.data.repository.ContactRepository

class EditContactFragment : BaseContactFragment() {
    private val viewModel: EditContactViewModel by viewModels{
        EditContactViewModel.Provider(ContactRepository.contactRepository)
    }
    val navArgs: EditContactFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onViewCreated(navArgs.id)
        binding.btnSave.text = "Update"
        onBindView()
    }

    fun onBindView() {
        viewModel.name.observe(viewLifecycleOwner) {
            binding.etName.setText(it)
        }
        viewModel.phone.observe(viewLifecycleOwner) {
            binding.etPhone.setText(it)
        }

        binding.btnSave.setOnClickListener {
            viewModel.update(navArgs.id, Contact(navArgs.id, binding.etName.text.toString(), binding.etPhone.text.toString()))
            val bundle = Bundle()
            bundle.putBoolean("refresh", true)
            setFragmentResult("finished_edit", bundle)
            NavHostFragment.findNavController(this).popBackStack()
        }
    }
}