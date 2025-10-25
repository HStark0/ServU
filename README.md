# 🧩 ServU — Aplicativo de Serviços On-Demand

O **ServU** é um aplicativo Android que conecta **clientes e prestadores de serviços** de maneira rápida, segura e prática.  
Em poucos cliques, o usuário pode agendar desde serviços de beleza até reparos domésticos, contribuindo para o fortalecimento da **economia local** e a valorização de profissionais autônomos.

---

## 📱 Visão Geral

O ServU é um **marketplace de serviços on-demand** que oferece:

- 🔍 Busca inteligente por **localização, categoria, preço e avaliação**  
- 📅 **Agendamento** de serviços imediatos ou futuros  
- 💳 **Pagamentos integrados** via PIX, cartão e carteira digital  
- 💬 **Chat interno seguro** entre cliente e prestador  
- ⭐ **Sistema de avaliações e reputação pública**  
- 🧾 **Painel de gestão** para prestadores (agenda, ganhos e relatórios)  
- 🚨 **Botão de emergência** e verificação de identidade  

---

## 💡 Justificativa e Contexto

O projeto surge em um cenário de:

- 📈 Crescimento do setor de serviços no Brasil (+3,1% em 2024, IBGE)  
- ⚡ Adoção massiva do **PIX** (mais de 70% dos brasileiros usam)  
- 👷 Expansão da **economia gig** e do trabalho autônomo  
- 🛠️ Necessidade crescente por conveniência, rapidez e confiança  

O **ServU** posiciona-se como um ecossistema que une tecnologia, segurança e valorização da economia local.

---

## ⚙️ Stack Tecnológica

| Camada | Tecnologia / Ferramenta | Função |
|--------|--------------------------|--------|
| **Frontend (Android)** | Kotlin / Android Studio | Desenvolvimento nativo do app |
| **Backend e Banco de Dados** | Firebase (Auth, Firestore, Storage, Functions) | Autenticação, dados e lógica de servidor |
| **Integrações** | Google Maps SDK, APIs REST (PIX) | Geolocalização e pagamentos |
| **Infraestrutura** | Firebase Hosting / Google Cloud | Hospedagem e monitoramento |
| **Controle de Versão** | GitHub | Repositório, branches `main`, `dev`, `feature` |
| **Design** | Figma | Protótipos, identidade visual e usabilidade |
| **Gerenciamento Ágil** | GitHub Projects / Trello | Organização de tarefas e sprints |

---

## 👥 Equipe e Responsabilidades

### 🧠 **Henderson Lima de Souza** — Tech Lead / Desenvolvedor / Programador
- Define a **arquitetura técnica** e a **stack** do projeto  
- Configura **Firebase**, CI/CD e políticas de segurança  
- Supervisiona a conformidade com a **LGPD**  
- Gera o pacote final (AAB) e publica na **Google Play Store**  
- Suporte técnico e revisão de código  

### 💻 **Christian Rodrigues Sangalli** — Desenvolvedor / Programador
- Implementa telas principais e lógica de negócio  
- Integra Firebase (Auth, Firestore, Storage)  
- Implementa **chat, upload de documentos e listagem de prestadores**  
- Gera builds de teste e corrige bugs  

### 🎨 **Daniel Brilhante Mouta** — UX/UI Designer
- Cria wireframes e protótipos clicáveis no **Figma**  
- Define cores, tipografia, ícones e fluxos de navegação  
- Conduz testes de usabilidade com usuários reais  

### 🗄️ **João Paulo Oliveira Sales** — Banco de Dados
- Modela e configura o **Firestore**  
- Define coleções, campos e regras de segurança  
- Realiza backups automáticos e documenta a estrutura  

### 🧪 **Leonardo Fontes Gomes** — QA / Suporte Banco de Dados
- Cria casos de teste e executa validações funcionais e de regressão  
- Registra bugs e acompanha correções no GitHub Issues  

### 🧩 **David Paiva Oliveira** — QA / Testes de Usabilidade
- Testa usabilidade e fluxo de navegação  
- Realiza checklist e homologação antes da publicação  

---

## 🧭 Metodologia e Planejamento

Metodologia **Ágil (Scrum)**, com sprints quinzenais e entregas incrementais.

### 📅 Cronograma de Sprints

| Sprint | Período | Entregas Principais |
|--------|----------|--------------------|
| **Sprint 1** | 14/10 – 20/10 | Design (Figma), Autenticação, Integração Firebase |
| **Sprint 2** | 21/10 – 26/10 | Busca, Geolocalização, Agendamento e Pagamento PIX |
| **Sprint 3** | 27/10 – 01/11 | Avaliações, Chat em Tempo Real e Notificações Push |
| **Sprint 4** | 02/11 – 05/11 | Painel do Prestador, Testes Finais e Publicação |

---

## 📋 Funcionalidades Principais

### 🔐 Cadastro e Autenticação
- Login com e-mail/senha  
- Upload e verificação de documentos (RG/CNH)  
- Aprovação manual de prestadores  

### 🧭 Busca Inteligente
- Filtros por **categoria, preço, avaliação e localização**  
- Sugestões automáticas com base no histórico  

### 📅 Agendamento e Pagamentos
- Contratação imediata ou agendada  
- Pagamentos via **PIX, cartão e carteira digital**  
- Geração de **recibos automáticos em PDF**

### 💬 Comunicação e Segurança
- Chat interno entre cliente e prestador  
- Botão de emergência com geolocalização ativa  
- Suporte 24h e conformidade com a LGPD  

### ⭐ Avaliações e Fidelização
- Avaliação com notas e comentários  
- Destaque para prestadores bem avaliados  
- Cupons, cashback e programas de pontos  

### 📊 Painel do Prestador
- Visualização de agendamentos e histórico  
- Controle de agenda e ganhos  
- Relatórios de desempenho e reputação  

---

## 🔒 Requisitos Não Funcionais

- 🔐 **Segurança**: Criptografia AES-256, autenticação e backups diários  
- ⚖️ **LGPD**: Consentimento explícito e exclusão de dados sob solicitação  
- ⚡ **Desempenho**: Tempo de resposta ≤ 3s, uptime ≥ 99,5%  
- 🧩 **Escalabilidade**: Infraestrutura em nuvem (Firebase / Google Cloud)  
- ♿ **Acessibilidade**: Compatibilidade com leitores de tela e comandos por voz  
- 📘 **Documentação**: Guias técnicos e funcionais completos  

---

## 🚀 Publicação e Distribuição

- 🧾 **Build Final (AAB)** assinado e publicado no **Google Play Console**  
- ☁️ Testes internos via **Firebase App Distribution**  
- 🧠 Versão **Beta Controlada** antes do lançamento público  

---

## 📊 Métricas de Sucesso (KPIs)

| Indicador | Descrição |
|------------|------------|
| **GMV (Gross Merchandise Volume)** | Volume bruto de transações via app |
| **Taxa de conversão** | Percentual de buscas que viram contratações |
| **LTV / CAC** | Valor de vida útil do cliente vs. custo de aquisição |
| **Taxa de retenção** | Retenção mensal de clientes e prestadores |
| **NPS** | Grau de satisfação geral |
| **Incidentes de segurança** | Uso do botão de emergência e denúncias |

---

## 📈 Futuras Expansões

- 💼 **Assinaturas premium** para prestadores  
- 🌐 **Suporte multilíngue** (inglês e espanhol)  
- 📱 **Versão iOS (Swift / React Native)**  
- 🤝 **Parcerias regionais** com cooperativas e instituições de pagamento  
- 🧰 **API pública** para integração com outros sistemas  

---

## 📜 Licença

Este projeto é de propriedade da **Equipe ServU**.  

---

## 👏 Agradecimentos

Desenvolvido como parte da disciplina  
**Programação para Dispositivos Móveis em Android**,  
com o objetivo de promover **inovação tecnológica, economia local e empreendedorismo digital**.

---

### 💬 Contato

- **Tech Lead:** [Henderson Lima de Souza](limahendersom0@gmail.com)  
- **GitHub:** [github.com/ServU-App](https://github.com/ServU-App)

---
