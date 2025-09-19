# Challenge OnboardingX - Projeto Vivo Now! 💜

API REST desenvolvida para gerenciar processos de onboardings para novos colaboradores da empresa Vivo. 

---

## 📦 Tecnologias Utilizadas
- Java 17+
- Spring Boot
- Spring Security (JWT)
- Spring Data JPA
- PostgreSQL
- Flyway
- Maven

---

## 🗂️ Estrutura do Projeto

```text
src/
└── main/
    └── java/
        └── br/purpletech/vivo/
            ├── config/         # Configurações gerais e DataLoader
            ├── controllers/    # Controllers REST
            ├── dtos/           # Data Transfer Objects
            ├── exceptions/     # Tratamento de erros personalizados
            ├── models/         # Entidades
            ├── repositories/   # Interfaces de acesso ao banco
            ├── security/       # Configuração de autenticação JWT
            ├── services/       # Regras de negócio
            └── VivoApplication.java  # Classe principal da aplicação

```

## Banco de Dados e Migração
* Banco: PostgreSQL
* Migração: Flyway
📁 Migrações
Arquivos .sql ficam em:
src/main/resources/db/migration

---

## 🔐 Autenticação
A API utiliza JWT. Inclua o token no header das requisições protegidas:
Authorization: Bearer <seu_token>

---

## 🔗 Principais Endpoints

### 🔐 Autenticação
* POST /auth/login: Login com email e senha, retorna ID e token JWT.

### 👤 Usuário
* GET /users/{id}: Retorna dados do usuário.

### 📋 Onboarding
* GET /onboardings/{id}: Detalha um onboarding.
* POST /onboardings: Cria novo onboarding.
* PATCH /onboardings/{id}: Atualiza onboarding.
* DELETE /onboardings/{id}: Remove onboarding.
* PUT /onboardings/{id}/next-step: Avança etapa.
* POST /onboardings/{id}/users/{idUser}: Adiciona usuário.
* POST /onboardings/{id}/chat: Cria chats entre participantes.
* GET /onboardings/manager/{id}: Lista onboardings de um gestor.
* GET /onboardings/buddy/{id}: Lista onboardings de um buddy.

### 🧩 Etapas e Tarefas
* POST /onboardings/{id}/steps: Adiciona etapa.
* DELETE /onboardings/{id}/steps/{stepId}: Remove etapa.
* POST /steps/{id}/tasks: Adiciona tarefa.
* DELETE /steps/{id}/tasks/{taskId}: Remove tarefa.
* PUT /tasks/{id}: Atualiza status da tarefa.
* GET /tasks: Lista todas as tarefas.
* POST /tasks: Cria nova tarefa.

### 📊 Relatórios
* GET /onboardings/{id}/reports: Lista relatórios.
* POST /onboardings/{id}/reports: Cria relatório.

### 💬 Chat
* GET /users/{senderId}/chat/{receiverId}: Chat entre dois usuários.
* GET /users/{id}/chat/manager: Chat com gestor.
* GET /users/{id}/chat/buddy: Chat com buddy.
* POST /users/{senderId}/chat/{receiverId}/message: Envia mensagem.

### 👥 Time e Plataformas
* GET /teams/{id}: Retorna time e plataformas.
* GET /platforms: Lista todas as plataformas.
* GET /platforms/{id}: Busca plataforma por ID.
* POST /platforms: Cria nova plataforma.
* DELETE /platforms/{id}: Remove plataforma.

---

## 🛠️ Como Rodar o Projeto Localmente
```
# Clone o repositório
git clone https://github.com/juliana-sn/back-end-vivo.git

# Acesse a pasta
cd back-end-vivo

# Configure o application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/vivo
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.flyway.enabled=true

# Execute o projeto
./mvnw spring-boot:run

```
---

Projeto desenvolvido pela equipe PurpleTech, alunos do 2º ano de Sistemas de Informação na FIAP.
