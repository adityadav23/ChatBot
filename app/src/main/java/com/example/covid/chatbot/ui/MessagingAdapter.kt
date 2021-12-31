package com.example.covid.chatbot.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.covid.chatbot.R
import com.example.covid.chatbot.data.Message
import com.example.covid.chatbot.utils.Constants.RECEIVE_ID
import com.example.covid.chatbot.utils.Constants.SEND_ID

class MessagingAdapter : RecyclerView.Adapter<MessagingAdapter.MessageViewHolder>(){

    var messageList = mutableListOf<Message>()

    inner class MessageViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val messageTextView: TextView = view.findViewById(R.id.tv_message)
        val botMessageTextView: TextView = view.findViewById(R.id.tv_bot_message)
        init{
            view.setOnClickListener {
                messageList.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.message_item,
            parent, false))
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {

        val currentMessage = messageList[position]

        when(currentMessage.id){
            SEND_ID -> {
                holder.messageTextView.apply {
                    text = currentMessage.message
                    visibility = View.VISIBLE
                }
                holder.botMessageTextView.apply {
                    visibility = View.GONE
                }
            }
            RECEIVE_ID -> {
                holder.botMessageTextView.apply {
                    text = currentMessage.message
                    visibility = View.VISIBLE
                }
                holder.messageTextView.apply {
                    visibility = View.GONE
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    fun insertMessage(message: Message){
        this.messageList.add(message)
        notifyItemInserted(messageList.size)
    }

}