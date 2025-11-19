package com.app.servu

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar

class ProviderListActivity : AppCompatActivity() {

    companion object {
        private val _allProviders by lazy { createAllProviders() } 

        fun getProvidersForCategory(category: String): List<Provider> {
            return _allProviders.filter { it.category.equals(category, ignoreCase = true) }
        }

        fun searchProviders(query: String): List<Provider> {
             if (query.isBlank()) return emptyList()
             return _allProviders.filter {
                 it.name.contains(query, ignoreCase = true) || 
                 it.specialty.contains(query, ignoreCase = true) ||
                 it.category.contains(query, ignoreCase = true)
             }
        }
        
        fun getAllProviders(): List<Provider> = _allProviders

        private fun createAllProviders(): List<Provider> {
            return listOf(
                // Eletricista
                Provider(
                    id = "1", name = "Matheus Santos", specialty = "Eletricista Residencial", rating = 4.8f, category = "Eletricista",
                    description = "Especialista em instalações e manutenções elétricas residenciais. Segurança e eficiência são minhas prioridades.",
                    profileImageResId = R.drawable.eletricista, backgroundImageResId = R.drawable.ic_placeholder,
                    services = listOf(
                        ProviderService("Instalação de Tomadas", "R$ 120,00", "Aprox. 1h", R.drawable.eletricista_instala__es),
                        ProviderService("Troca de Disjuntor", "R$ 250,00", "Aprox. 1.5h", R.drawable.manutencao_corretiva)
                    )
                ),
                Provider(
                    id = "2", name = "Antônio Souza", specialty = "Eletricista Predial", rating = 4.6f, category = "Eletricista",
                    description = "Vasta experiência em sistemas elétricos de grande porte, quadros de força e iluminação de emergência.",
                    profileImageResId = R.drawable.eletricista, backgroundImageResId = R.drawable.ic_placeholder,
                    services = listOf(
                        ProviderService("Análise de Quadro de Força", "R$ 450,00", "Aprox. 3h", R.drawable.manutencao_corretiva),
                        ProviderService("Instalação de Luminárias", "R$ 200,00", "Aprox. 2h", R.drawable.eletricista_ilumina__o)
                    )
                ),
                // Diarista
                Provider(
                    id = "3", name = "Maria Oliveira", specialty = "Limpeza Padrão", rating = 4.9f, category = "Diarista",
                    description = "Limpeza detalhada e organização para sua casa ou escritório. Produtos de limpeza inclusos.",
                    profileImageResId = R.drawable.diarista, backgroundImageResId = R.drawable.ic_placeholder,
                    services = listOf(
                        ProviderService("Limpeza Padrão (3h)", "R$ 150,00", "Aprox. 3h", R.drawable.diarista),
                        ProviderService("Limpeza Pós-obra", "R$ 400,00", "Aprox. 5h", R.drawable.diarista)
                    )
                ),
                 Provider(
                    id = "4", name = "Lúcia Pereira", specialty = "Faxina Pesada", rating = 4.7f, category = "Diarista",
                    description = "Especialista em faxinas pesadas e organização de ambientes. Deixo tudo brilhando!",
                    profileImageResId = R.drawable.diarista, backgroundImageResId = R.drawable.ic_placeholder,
                    services = listOf(
                        ProviderService("Faxina Completa (5h)", "R$ 220,00", "Aprox. 5h", R.drawable.diarista),
                        ProviderService("Limpeza de Vidros e Janelas", "R$ 180,00", "Aprox. 2h", R.drawable.diarista)
                    )
                ),
                // Encanador
                Provider(
                    id = "5", name = "Carlos Ferreira", specialty = "Reparos Hidráulicos", rating = 4.5f, category = "Encanador",
                    description = "Solução para vazamentos, desentupimentos e instalações hidráulicas em geral.",
                    profileImageResId = R.drawable.encanador1___principal_nas_partes_do_projeto, backgroundImageResId = R.drawable.ic_placeholder,
                    services = listOf(
                        ProviderService("Reparo de Vazamento", "R$ 180,00", "Aprox. 1.5h", R.drawable.encanador1___principal_nas_partes_do_projeto),
                        ProviderService("Desentupimento de Pia", "R$ 150,00", "Aprox. 1h", R.drawable.encanador1___principal_nas_partes_do_projeto)
                    )
                ),
                 Provider(
                    id = "6", name = "Roberto Lima", specialty = "Instalação de Torneiras e Pias", rating = 4.8f, category = "Encanador",
                    description = "Instalação de louças e metais com acabamento de primeira. Serviço rápido e limpo.",
                    profileImageResId = R.drawable.encanador1___principal_nas_partes_do_projeto, backgroundImageResId = R.drawable.ic_placeholder,
                    services = listOf(
                        ProviderService("Instalação de Torneira Gourmet", "R$ 130,00", "Aprox. 1h", R.drawable.encanador1___principal_nas_partes_do_projeto),
                        ProviderService("Instalação de Pia de Cozinha", "R$ 280,00", "Aprox. 2.5h", R.drawable.encanador1___principal_nas_partes_do_projeto)
                    )
                ),
                // Jardineiro
                Provider(
                    id = "7", name = "Sérgio Mendes", specialty = "Paisagismo e Manutenção", rating = 4.9f, category = "Jardineiro",
                    description = "Criação e manutenção de jardins, gramados e canteiros. Transformo seu espaço verde.",
                    profileImageResId = R.drawable.jardineiro, backgroundImageResId = R.drawable.ic_placeholder,
                    services = listOf(
                        ProviderService("Corte de Grama (até 50m²)", "R$ 100,00", "Aprox. 2h", R.drawable.jardineiro),
                        ProviderService("Projeto de Paisagismo Simples", "R$ 600,00", "Aprox. 8h", R.drawable.jardineiro)
                    )
                ),
                Provider(
                    id = "8", name = "Fábio Costa", specialty = "Controle de Pragas", rating = 4.7f, category = "Jardineiro",
                    description = "Aplicação de defensivos e soluções orgânicas para controle de pragas em jardins e hortas.",
                    profileImageResId = R.drawable.jardineiro, backgroundImageResId = R.drawable.ic_placeholder,
                    services = listOf(
                        ProviderService("Pulverização contra Fungos", "R$ 180,00", "Aprox. 1.5h", R.drawable.jardineiro),
                        ProviderService("Controle de Formigas Cortadeiras", "R$ 150,00", "Aprox. 1h", R.drawable.jardineiro)
                    )
                ),
                // Montador de Móveis
                 Provider(
                    id = "9", name = "Ricardo Alves", specialty = "Móveis Convencionais", rating = 4.8f, category = "Montador de Móveis",
                    description = "Montagem e desmontagem de móveis de todas as lojas. Rapidez e cuidado com seu patrimônio.",
                    profileImageResId = R.drawable.montador_de_m_veis, backgroundImageResId = R.drawable.ic_placeholder,
                    services = listOf(
                        ProviderService("Montagem de Guarda-Roupa", "R$ 200,00", "Aprox. 2.5h", R.drawable.montador_de_m_veis),
                        ProviderService("Instalação de Painel de TV", "R$ 90,00", "Aprox. 1h", R.drawable.montador_de_m_veis)
                    )
                ),
                Provider(
                    id = "10", name = "Fernando Dias", specialty = "Móveis Planejados", rating = 4.9f, category = "Montador de Móveis",
                    description = "Especialista em montagem de cozinhas, closets e móveis planejados de alta complexidade.",
                    profileImageResId = R.drawable.montador_de_m_veis, backgroundImageResId = R.drawable.ic_placeholder,
                    services = listOf(
                        ProviderService("Montagem de Cozinha Completa", "R$ 800,00", "Aprox. 8h", R.drawable.montador_de_m_veis),
                        ProviderService("Montagem de Closet", "R$ 550,00", "Aprox. 6h", R.drawable.montador_de_m_veis)
                    )
                ),
                // Técnico em Ar Condicionado
                Provider(
                    id = "11", name = "Bruno Guedes", specialty = "Instalação e Limpeza", rating = 4.9f, category = "Técnico em Ar Condicionado",
                    description = "Instalação e higienização completa de aparelhos de ar condicionado Split e de janela.",
                    profileImageResId = R.drawable.tecnico_de_ar_condicionado, backgroundImageResId = R.drawable.ic_placeholder,
                    services = listOf(
                        ProviderService("Instalação de Split (até 12000 BTUs)", "R$ 450,00", "Aprox. 3h", R.drawable.tecnico_de_ar_condicionado),
                        ProviderService("Higienização Completa", "R$ 150,00", "Aprox. 1h", R.drawable.tecnico_de_ar_condicionado)
                    )
                ),
                Provider(
                    id = "12", name = "Leandro Martins", specialty = "Manutenção Corretiva", rating = 4.7f, category = "Técnico em Ar Condicionado",
                    description = "Diagnóstico e conserto de defeitos, recarga de gás e reparos em sistemas de ar condicionado.",
                    profileImageResId = R.drawable.tecnico_de_ar_condicionado, backgroundImageResId = R.drawable.ic_placeholder,
                    services = listOf(
                        ProviderService("Recarga de Gás", "R$ 250,00", "Aprox. 1.5h", R.drawable.tecnico_de_ar_condicionado),
                        ProviderService("Conserto de Placa Eletrônica", "R$ 350,00", "Aprox. 2h", R.drawable.tecnico_de_ar_condicionado)
                    )
                ),
                // Chaveiro
                 Provider(
                    id = "13", name = "Márcio Chaves", specialty = "Atendimento 24h", rating = 4.9f, category = "Chaveiro",
                    description = "Abertura de portas residenciais e automotivas, cópias de chaves e troca de segredos. Atendimento rápido e confiável.",
                    profileImageResId = R.drawable.chaveiro, backgroundImageResId = R.drawable.ic_placeholder,
                    services = listOf(
                        ProviderService("Abertura de Porta Residencial", "R$ 120,00", "Aprox. 30min", R.drawable.chaveiro),
                        ProviderService("Cópia de Chave Simples", "R$ 15,00", "Aprox. 5min", R.drawable.chaveiro)
                    )
                ),
                 // Designer Gráfico
                Provider(
                    id = "14", name = "Juliana Lima", specialty = "Identidade Visual & Logos", rating = 4.9f, category = "Designer Gráfico",
                    description = "Crio marcas memoráveis e identidades visuais que contam a sua história. Foco em design minimalista e moderno.",
                    profileImageResId = R.drawable.designer_grafico2, backgroundImageResId = R.drawable.ic_placeholder,
                    services = listOf(
                        ProviderService("Criação de Logo", "R$ 950,00", "Aprox. 5 dias", R.drawable.designer_grafico2),
                        ProviderService("Design de Cartão de Visita", "R$ 250,00", "Aprox. 2 dias", R.drawable.designer_grafico2)
                    )
                ),
                Provider(
                    id = "15", name = "Lucas Mendes", specialty = "Social Media & Web Design", rating = 4.8f, category = "Designer Gráfico",
                    description = "Especialista em criar artes para redes sociais e layouts para sites que engajam e convertem.",
                    profileImageResId = R.drawable.designer_grafico2, backgroundImageResId = R.drawable.ic_placeholder,
                    services = listOf(
                        ProviderService("Pacote de 10 Posts para Instagram", "R$ 600,00", "Aprox. 4 dias", R.drawable.designer_grafico2),
                        ProviderService("Layout de Landing Page", "R$ 1.200,00", "Aprox. 7 dias", R.drawable.designer_grafico2)
                    )
                ),
                // Cuidador de Idosos
                Provider(
                    id = "16", name = "Sônia Alves", specialty = "Acompanhamento e Companhia", rating = 5.0f, category = "Cuidador de Idosos",
                    description = "Ofereço companhia, auxílio em tarefas diárias e acompanhamento em consultas. Cuidado com carinho e paciência.",
                    profileImageResId = R.drawable.cuidador_de_idosos, backgroundImageResId = R.drawable.ic_placeholder,
                    services = listOf(
                        ProviderService("Diária (8 horas)", "R$ 180,00", "8h", R.drawable.cuidador_de_idosos),
                        ProviderService("Acompanhamento em Consulta", "R$ 90,00", "Aprox. 3h", R.drawable.cuidador_de_idosos)
                    )
                ),
                Provider(
                    id = "17", name = "Jorge Ferreira", specialty = "Cuidados Pós-operatórios", rating = 4.9f, category = "Cuidador de Idosos",
                    description = "Técnico de enfermagem com experiência em cuidados de baixa complexidade, medicação e auxílio na recuperação.",
                    profileImageResId = R.drawable.cuidador_de_idosos, backgroundImageResId = R.drawable.ic_placeholder,
                    services = listOf(
                        ProviderService("Pernoite (12 horas)", "R$ 250,00", "12h", R.drawable.cuidador_de_idosos),
                        ProviderService("Administração de Medicação", "R$ 50,00", "Aprox. 30min", R.drawable.cuidador_de_idosos)
                    )
                ),
                 // Costureira
                Provider(
                    id = "18", name = "Ana Costa", specialty = "Ajustes e Reparos", rating = 4.8f, category = "Costureira",
                    description = "Realizo ajustes de barras, trocas de zíper e pequenos reparos em geral. Deixo suas roupas com o caimento perfeito.",
                    profileImageResId = R.drawable.costureira, backgroundImageResId = R.drawable.ic_placeholder,
                    services = listOf(
                        ProviderService("Ajuste de Barra (Calça)", "R$ 25,00", "Aprox. 45min", R.drawable.costureira),
                        ProviderService("Troca de Zíper (Jaqueta)", "R$ 50,00", "Aprox. 1.5h", R.drawable.costureira)
                    )
                ),
                Provider(
                    id = "19", name = "Beatriz Santos", specialty = "Criação Sob Medida", rating = 4.9f, category = "Costureira",
                    description = "Desenvolvo e confecciono peças exclusivas, desde vestidos de festa a roupas casuais, totalmente sob medida.",
                    profileImageResId = R.drawable.costureira, backgroundImageResId = R.drawable.ic_placeholder,
                    services = listOf(
                        ProviderService("Confecção de Vestido Simples", "R$ 450,00", "Aprox. 3 dias", R.drawable.costureira),
                        ProviderService("Criação de Camisa Social", "R$ 300,00", "Aprox. 2 dias", R.drawable.costureira)
                    )
                ),
                // Limpeza de Piscinas
                Provider(
                    id = "20", name = "Renato Dias", specialty = "Manutenção Semanal", rating = 4.7f, category = "Limpeza de Piscinas",
                    description = "Serviço completo de limpeza, tratamento químico e aspiração de piscinas. Planos semanais e quinzenais.",
                    profileImageResId = R.drawable.limpador_de_piscina, backgroundImageResId = R.drawable.ic_placeholder,
                    services = listOf(
                        ProviderService("Limpeza Semanal (até 30 mil L)", "R$ 120,00", "Aprox. 1h", R.drawable.limpador_de_piscina),
                        ProviderService("Tratamento de Choque (Água Verde)", "R$ 250,00", "Aprox. 2h", R.drawable.limpador_de_piscina)
                    )
                ),
                 // Bico para Reformas
                Provider(
                    id = "21", name = "Paulo Rocha", specialty = "Marido de Aluguel", rating = 4.8f, category = "Bico para Reformas",
                    description = "Pequenos reparos, instalações de prateleiras, cortinas, suportes de TV, pinturas e muito mais. Soluções rápidas para sua casa.",
                    profileImageResId = R.drawable.pedreiro, backgroundImageResId = R.drawable.ic_placeholder,
                    services = listOf(
                        ProviderService("Instalação de Prateleira", "R$ 70,00", "Aprox. 45min", R.drawable.pedreiro),
                        ProviderService("Pintura de Parede (até 9m²)", "R$ 350,00", "Aprox. 4h", R.drawable.pedreiro)
                    )
                ),
                // Transporte
                 Provider(
                    id = "22", name = "João Ernesto", specialty = "Freteiro", rating = 4.5f, category = "Transporte",
                    description = "Serviços de frete e mudança com agilidade e segurança.",
                    profileImageResId = R.drawable.ic_truck_transport, backgroundImageResId = R.drawable.ic_placeholder,
                    services = listOf(
                        ProviderService("Frete de Carga Pequena", "R$ 150,00", "Aprox. 1h", R.drawable.ic_truck_transport)
                    )
                ),
                // Aulas
                Provider(
                    id = "23", name = "Prof.ª Clara Bastos", specialty = "Aulas de Reforço (Matemática)", rating = 4.9f, category = "Aulas",
                    description = "Ajudo alunos com dificuldades em matemática a alcançarem seu potencial máximo. Aulas individuais e em grupo.",
                    profileImageResId = R.drawable.ic_lessons_book, backgroundImageResId = R.drawable.ic_placeholder,
                    services = listOf(
                        ProviderService("Aula Particular (1h)", "R$ 80,00", "1h", R.drawable.ic_lessons_book),
                        ProviderService("Pacote Mensal (4 aulas)", "R$ 280,00", "4h/mês", R.drawable.ic_lessons_book)
                    )
                ),
                // Beleza
                Provider(
                    id = "24", name = "Estúdio Glamour", specialty = "Maquiagem e Penteados", rating = 5.0f, category = "Beleza",
                    description = "Produções completas para casamentos, formaturas e eventos especiais. Realce sua beleza natural.",
                    profileImageResId = R.drawable.ic_beauty_woman, backgroundImageResId = R.drawable.ic_placeholder,
                    services = listOf(
                        ProviderService("Maquiagem Social", "R$ 150,00", "Aprox. 1.5h", R.drawable.ic_beauty_woman),
                        ProviderService("Penteado para Festas", "R$ 120,00", "Aprox. 1h", R.drawable.ic_beauty_woman)
                    )
                ),
                // Junior Assis - Eletricista
                Provider(
                    id = "25", name = "Junior Assis", specialty = "Manutenção Elétrica", rating = 4.7f, category = "Eletricista",
                    description = "Manutenção elétrica corretiva e preventiva para garantir a segurança do seu lar.",
                    profileImageResId = R.drawable.eletricista, backgroundImageResId = R.drawable.ic_placeholder,
                    services = listOf(
                        ProviderService("Revisão Elétrica Completa", "R$ 350,00", "Aprox. 2.5h", R.drawable.manutencao_corretiva),
                        ProviderService("Troca de Fiação Antiga", "R$ 700,00", "Aprox. 6h", R.drawable.eletricista_instala__es)
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