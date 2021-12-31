package com.example.covid.chatbot.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.covid.chatbot.R
import com.example.covid.chatbot.data.Message
import com.example.covid.chatbot.utils.BotResponse
import com.example.covid.chatbot.utils.Constants.OPEN_GOOGLE
import com.example.covid.chatbot.utils.Constants.OPEN_SEARCH
import com.example.covid.chatbot.utils.Constants.RECEIVE_ID
import com.example.covid.chatbot.utils.Constants.SEND_ID
import com.example.covid.chatbot.utils.Time
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: MessagingAdapter
    private lateinit var messageRecyclerView: RecyclerView
    private lateinit var messageEditText: EditText
    private lateinit var sendMessageButton: Button
    private val botList = listOf("Peter", "Francersca", "Luigi","Igor")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        messageRecyclerView = findViewById(R.id.rv_messages)
        messageEditText = findViewById(R.id.et_message)
        sendMessageButton = findViewById(R.id.btn_send)

        recyclerView()
        clickEvents()

        val random = (0..3).random()
        customMessage("Hello! Today you're speaking with ${botList[random]}, how may I help you?")


    }

    private fun customMessage(message: String) {

        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main){
                val timeStamp = Time.timeStamp()
                adapter.insertMessage(Message(message,RECEIVE_ID, timeStamp))
                messageRecyclerView.scrollToPosition(adapter.itemCount-1)


            }
        }
    }


    private fun clickEvents() {
        sendMessageButton.setOnClickListener {
            sendMessage()
        }
        messageEditText.setOnClickListener {
            GlobalScope.launch{
                delay(100)
                withContext(Dispatchers.Main){
                    messageRecyclerView.scrollToPosition(adapter.itemCount -1)

                }
            }
        }
    }

    private fun sendMessage(){
        val message = messageEditText.text.toString()
        val timeStamp = Time.timeStamp()

        if(message.isNotEmpty()){
            messageEditText.setText("")

            adapter.insertMessage(Message(message, SEND_ID,timeStamp))
            messageRecyclerView.scrollToPosition(adapter.itemCount -1)

            botResponse(message)
        }
    }

    private fun botResponse(message: String) {
        val timeStamp = Time.timeStamp()

        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main){
                val response = BotResponse.basicResponses(message)
                adapter.insertMessage(Message(response, RECEIVE_ID, timeStamp))
                messageRecyclerView.scrollToPosition(adapter.itemCount -1)

                when(response){
                    OPEN_GOOGLE ->{
                        val site = Intent(Intent.ACTION_VIEW)
                        site.data = Uri.parse("https://www.google.com/")
                        startActivity(site)
                    }
                    OPEN_SEARCH ->{
                        val site = Intent(Intent.ACTION_VIEW)
                        val searchTerm: String? = message.substringAfter("search")
                        site.data = Uri.parse("https://www.google.com/search?&q=${searchTerm}")
                        startActivity(site)

                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main){
                messageRecyclerView.scrollToPosition(adapter.itemCount -1)

            }

        }
    }
    private fun recyclerView() {
        adapter = MessagingAdapter()
        messageRecyclerView.adapter = adapter
        messageRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
    }
}