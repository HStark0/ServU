package com.app.servu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class ConversationActivity : AppCompatActivity() {

    private val messages = mutableListOf<Message>()
    private lateinit var messageAdapter: MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversation)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener { finish() }

        val messagesRecyclerView = findViewById<RecyclerView>(R.id.messages_recycler_view)
        val sendButton = findViewById<MaterialButton>(R.id.send_button)
        val messageEditText = findViewById<TextInputEditText>(R.id.message_edit_text)

        messageAdapter = MessageAdapter(messages)
        messagesRecyclerView.layoutManager = LinearLayoutManager(this)
        messagesRecyclerView.adapter = messageAdapter

        sendButton.setOnClickListener {
            val messageText = messageEditText.text.toString()
            if (messageText.isNotEmpty()) {
                // 1. Add user's message
                messages.add(Message(messageText, isSentByUser = true))
                messageAdapter.notifyItemInserted(messages.size - 1)
                messageEditText.text?.clear()

                // 2. Add bot's response
                val botResponse = """
                Olá, [Nome do Cliente].
                Este é o chatbot, como podemos lhe ajudar?
                1 - Falar com atendente de suporte;
                2 - Duvidas;
                3 - Serviços agendados;
                4 - Outros.
                """.trimIndent()
                messages.add(Message(botResponse, isSentByUser = false))
                messageAdapter.notifyItemInserted(messages.size - 1)

                // 3. Scroll to the new message
                messagesRecyclerView.scrollToPosition(messages.size - 1)
            }
        }
    }
}