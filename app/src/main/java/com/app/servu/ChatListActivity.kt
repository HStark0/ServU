package com.app.servu

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar

class ChatListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_list)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        val chatbotItem = findViewById<View>(R.id.chatbot_item)
        chatbotItem.setOnClickListener {
            // Open the specific conversation screen
            val intent = Intent(this, ConversationActivity::class.java)
            // You can pass extras here to specify which chat to open, e.g.:
            // intent.putExtra("chatId", "chatbot")
            startActivity(intent)
        }

        // TODO: Setup RecyclerView for provider chats
    }
}