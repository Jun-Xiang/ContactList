package com.example.contactlist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contactlist.data.model.Contact
import com.example.contactlist.databinding.ItemLayoutContactBinding

class ContactAdapter(private var items: List<Contact>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var listener: Listener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemLayoutContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        if(holder is ContactItemViewHolder) {
            holder.bind(item)
        }
    }

    override fun getItemCount(): Int = items.size

    inner class ContactItemViewHolder(private val binding: ItemLayoutContactBinding) :
        RecyclerView.ViewHolder(binding.root){
            fun bind(item: Contact) {
                binding.tvName.text = item.name
                binding.tvPhone.text = item.phone

                binding.llContact.setOnClickListener {
                    listener?.onItemClicked(item)
                }
            }
        }

    interface Listener {
        fun onItemClicked(item: Contact)
    }
}