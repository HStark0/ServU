package com.app.servu

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar

class ProviderListActivity : AppCompatActivity() {

    companion object {
        private val allProviders by lazy { createAllProviders() }

        fun getProvidersForCategory(category: String): List<Provider> {
            return allProviders.filter { it.category.equals(category, ignoreCase = true) }
        }
        
        fun searchProviders(query: String): List<Provider> {
             if (query.isBlank()) return emptyList()
             return allProviders.filter {
                 it.name.contains(query, ignoreCase = true) || 
                 it.specialty.contains(query, ignoreCase = true) ||
                 it.category.contains(query, ignoreCase = true)
             }
        }

        private fun createAllProviders(): List<Provider> {
            return listOf(
                Provider(
                    id = "1", name = "Matheus Santos", specialty = "Eletricista Residencial", rating = 4.8f, category = "Eletricista",
                    description = "Especialista em instalações e manutenções elétricas residenciais. Segurança e eficiência são minhas prioridades.",
                    profileImageResId = R.drawable.ic_profile_placeholder, backgroundImageResId = R.drawable.ic_placeholder,
                    services = listOf(
                        ProviderService("Instalação de Tomadas", "R$ 120,00", "Aprox. 1h", R.drawable.eletricista_instala__es),
                        ProviderService("Troca de Disjuntor", "R$ 250,00", "Aprox. 1.5h", R.drawable.manutencao_corretiva)
                    )
                ),
                Provider(
                    id = "2", name = "Antônio Souza", specialty = "Eletricista Predial", rating = 4.6f, category = "Eletricista",
                    description = "Vasta experiência em sistemas elétricos de grande porte, quadros de força e iluminação de emergência.",
                    profileImageResId = R.drawable.ic_profile_placeholder, backgroundImageResId = R.drawable.ic_placeholder,
                    services = listOf(
                        ProviderService("Análise de Quadro de Força", "R$ 450,00", "Aprox. 3h", R.drawable.manutencao_corretiva),
                        ProviderService("Instalação de Luminárias", "R$ 200,00", "Aprox. 2h", R.drawable.eletricista_ilumina__o)
                    )
                ),
                Provider(
                    id = "3", name = "Maria Oliveira", specialty = "Limpeza Padrão", rating = 4.9f, category = "Diarista",
                    description = "Limpeza detalhada e organização para sua casa ou escritório. Produtos de limpeza inclusos.",
                    profileImageResId = R.drawable.ic_profile_placeholder, backgroundImageResId = R.drawable.ic_placeholder,
                    services = listOf(
                        ProviderService("Limpeza Padrão (3h)", "R$ 150,00", "Aprox. 3h", R.drawable.diarista),
                        ProviderService("Limpeza Pós-obra", "R$ 400,00", "Aprox. 5h", R.drawable.diarista)
                    )
                ),
                 Provider(
                    id = "4", name = "Lúcia Pereira", specialty = "Faxina Pesada", rating = 4.7f, category = "Diarista",
                    description = "Especialista em faxinas pesadas e organização de ambientes. Deixo tudo brilhando!",
                    profileImageResId = R.drawable.ic_profile_placeholder, backgroundImageResId = R.drawable.ic_placeholder,
                    services = listOf(
                        ProviderService("Faxina Completa (5h)", "R$ 220,00", "Aprox. 5h", R.drawable.diarista),
                        ProviderService("Limpeza de Vidros e Janelas", "R$ 180,00", "Aprox. 2h", R.drawable.diarista)
                    )
                ),
                Provider(
                    id = "5", name = "Carlos Ferreira", specialty = "Reparos Hidráulicos", rating = 4.5f, category = "Encanador",
                    description = "Solução para vazamentos, desentupimentos e instalações hidráulicas em geral.",
                    profileImageResId = R.drawable.ic_profile_placeholder, backgroundImageResId = R.drawable.ic_placeholder,
                    services = listOf(
                        ProviderService("Reparo de Vazamento", "R$ 180,00", "Aprox. 1.5h", R.drawable.encanador1___principal_nas_partes_do_projeto),
                        ProviderService("Desentupimento de Pia", "R$ 150,00", "Aprox. 1h", R.drawable.encanador1___principal_nas_partes_do_projeto)
                    )
                ),
                 Provider(
                    id = "6", name = "Roberto Lima", specialty = "Instalação de Torneiras e Pias", rating = 4.8f, category = "Encanador",
                    description = "Instalação de louças e metais com acabamento de primeira. Serviço rápido e limpo.",
                    profileImageResId = R.drawable.ic_profile_placeholder, backgroundImageResId = R.drawable.ic_placeholder,
                    services = listOf(
                        ProviderService("Instalação de Torneira Gourmet", "R$ 130,00", "Aprox. 1h", R.drawable.encanador1___principal_nas_partes_do_projeto),
                        ProviderService("Instalação de Pia de Cozinha", "R$ 280,00", "Aprox. 2.5h", R.drawable.encanador1___principal_nas_partes_do_projeto)
                    )
                ),
                Provider(
                    id = "7", name = "Sérgio Mendes", specialty = "Paisagismo e Manutenção", rating = 4.9f, category = "Jardineiro",
                    description = "Criação e manutenção de jardins, gramados e canteiros. Transformo seu espaço verde.",
                    profileImageResId = R.drawable.ic_profile_placeholder, backgroundImageResId = R.drawable.ic_placeholder,
                    services = listOf(
                        ProviderService("Corte de Grama (até 50m²)", "R$ 100,00", "Aprox. 2h", R.drawable.jardineiro),
                        ProviderService("Projeto de Paisagismo Simples", "R$ 600,00", "Aprox. 8h", R.drawable.jardineiro)
                    )
                ),
                Provider(
                    id = "8", name = "Fábio Costa", specialty = "Controle de Pragas", rating = 4.7f, category = "Jardineiro",
                    description = "Aplicação de defensivos e soluções orgânicas para controle de pragas em jardins e hortas.",
                    profileImageResId = R.drawable.ic_profile_placeholder, backgroundImageResId = R.drawable.ic_placeholder,
                    services = listOf(
                        ProviderService("Pulverização contra Fungos", "R$ 180,00", "Aprox. 1.5h", R.drawable.jardineiro),
                        ProviderService("Controle de Formigas Cortadeiras", "R$ 150,00", "Aprox. 1h", R.drawable.jardineiro)
                    )
                ),
                Provider(
                    id = "9", name = "Ricardo Alves", specialty = "Móveis Convencionais", rating = 4.8f, category = "Montador de Móveis",
                    description = "Montagem e desmontagem de móveis de todas as lojas. Rapidez e cuidado com seu patrimônio.",
                    profileImageResId = R.drawable.ic_profile_placeholder, backgroundImageResId = R.drawable.ic_placeholder,
                    services = listOf(
                        ProviderService("Montagem de Guarda-Roupa", "R$ 200,00", "Aprox. 2.5h", R.drawable.montador_de_m_veis),
                        ProviderService("Instalação de Painel de TV", "R$ 90,00", "Aprox. 1h", R.drawable.montador_de_m_veis)
                    )
                ),
                Provider(
                    id = "10", name = "Fernando Dias", specialty = "Móveis Planejados", rating = 4.9f, category = "Montador de Móveis",
                    description = "Especialista em montagem de cozinhas, closets e móveis planejados de alta complexidade.",
                    profileImageResId = R.drawable.ic_profile_placeholder, backgroundImageResId = R.drawable.ic_placeholder,
                    services = listOf(
                        ProviderService("Montagem de Cozinha Completa", "R$ 800,00", "Aprox. 8h", R.drawable.montador_de_m_veis),
                        ProviderService("Montagem de Closet", "R$ 550,00", "Aprox. 6h", R.drawable.montador_de_m_veis)
                    )
                ),
                Provider(
                    id = "11", name = "Bruno Guedes", specialty = "Instalação e Limpeza", rating = 4.9f, category = "Técnico em Ar Condicionado",
                    description = "Instalação e higienização completa de aparelhos de ar condicionado Split e de janela.",
                    profileImageResId = R.drawable.ic_profile_placeholder, backgroundImageResId = R.drawable.ic_placeholder,
                    services = listOf(
                        ProviderService("Instalação de Split (até 12000 BTUs)", "R$ 450,00", "Aprox. 3h", R.drawable.tecnico_de_ar_condicionado),
                        ProviderService("Higienização Completa", "R$ 150,00", "Aprox. 1h", R.drawable.tecnico_de_ar_condicionado)
                    )
                ),
                Provider(
                    id = "12", name = "Leandro Martins", specialty = "Manutenção Corretiva", rating = 4.7f, category = "Técnico em Ar Condicionado",
                    description = "Diagnóstico e conserto de defeitos, recarga de gás e reparos em sistemas de ar condicionado.",
                    profileImageResId = R.drawable.ic_profile_placeholder, backgroundImageResId = R.drawable.ic_placeholder,
                    services = listOf(
                        ProviderService("Recarga de Gás", "R$ 250,00", "Aprox. 1.5h", R.drawable.tecnico_de_ar_condicionado),
                        ProviderService("Conserto de Placa Eletrônica", "R$ 350,00", "Aprox. 2h", R.drawable.tecnico_de_ar_condicionado)
                    )
                ),
                 Provider(
                    id = "13", name = "Márcio Chaves", specialty = "Atendimento 24h", rating = 4.9f, category = "Chaveiro",
                    description = "Abertura de portas residenciais e automotivas, cópias de chaves e troca de segredos. Atendimento rápido e confiável.",
                    profileImageResId = R.drawable.chaveiro, backgroundImageResId = R.drawable.ic_placeholder,
                    services = listOf(
                        ProviderService("Abertura de Porta Residencial", "R$ 120,00", "Aprox. 30min", R.drawable.chaveiro),
                        ProviderService("Cópia de Chave Simples", "R$ 15,00", "Aprox. 5min", R.drawable.chaveiro)
                    )
                )
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_provider_list)

        val category = intent.getStringExtra("category") ?: "Prestadores"
        
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.title = category
        toolbar.setNavigationOnClickListener { finish() }

        val recyclerView = findViewById<RecyclerView>(R.id.provider_recycler_view)
        
        val providers = getProvidersForCategory(category)
        
        val adapter = ProviderAdapter(providers) { provider ->
            val intent = Intent(this, ProviderProfileActivity::class.java)
            intent.putExtra("provider", provider)
            startActivity(intent)
        }
        recyclerView.adapter = adapter
    }
}