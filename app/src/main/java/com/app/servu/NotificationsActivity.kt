package com.app.servu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar

class NotificationsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener { finish() }

        val recyclerView = findViewById<RecyclerView>(R.id.notifications_recycler_view)
        
        val notifications = listOf(
            Notification("Promoção Imperdível!", "A área de Faxina está com 5% de desconto. Aproveite!", "Há 2 horas", R.drawable.ic_promo),
            Notification("Agendamento Confirmado", "Seu serviço de Instalação Elétrica foi agendado com sucesso para 28/07 às 15:00.", "Ontem", R.drawable.ic_calendar),
            Notification("Serviço Concluído", "O serviço de Manutenção de Encanamento foi concluído. Não se esqueça de avaliar o prestador!", "25/07", R.drawable.ic_check_circle),
            Notification("Nova Mensagem", "Você tem uma nova mensagem de Matheus Santos.", "24/07", R.drawable.ic_chat_bubble)
        )

        recyclerView.adapter = NotificationAdapter(notifications)
    }
}