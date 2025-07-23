# Friends-Chat

![Kotlin](https://img.shields.io/badge/Kotlin-0095D5.svg?logo=kotlin&logoColor=white) ![Firebase](https://img.shields.io/badge/Firebase-ffca28.svg?logo=firebase&logoColor=black) ![Android](https://img.shields.io/badge/Android-3DDC84.svg?logo=android&logoColor=white)

##Descrição
Friends-Chat é uma aplicação de chat para Android desenvolvida em Kotlin. A aplicação permite que os utilizadores se registem, façam login, procurem outros utilizadores e conversem em tempo real. Utiliza o Firebase como backend para autenticação e armazenamento de mensagens.


## Funcionalidades
- Autenticação de Utilizadores: Registo e login com Firebase Authentication.
- Chat em Tempo Real: Troca de mensagens instantâneas entre utilizadores.
- Lista de Conversas: Exibição de todas as conversas ativas do utilizador.
- Pesquisa de Utilizadores: Permite procurar e iniciar conversas com novos utilizadores.
- Gestão de Perfil: Ecrã para gerir informações do utilizador.
- Verificação de Força da Palavra-passe: Feedback sobre a segurança da palavra-passe.

## Estrutura do Projeto
O projeto está organizado nos seguintes pacotes:

- ui/activity/intro: Contém as Activities de introdução, como SplashActivity e WelcomeActivity.
- ui/activity/entry: Contém as Activities de login e registo (LoginActivity, RegisterActivity).
- ui/fragments/chat: Inclui o fragmento principal de chat (ChatFragment).
- ui/fragments/search: Inclui o fragmento de pesquisa de utilizadores (SearchFragment).
- ui/fragments/settings: Inclui o fragmento de configurações do utilizador (SettingsFragment).
- adapters: Contém os adaptadores para RecyclerView (ChatAdapter, UserAdapter).
- model: Classes de dados como Users, Chat, ChatList, Photo, PrefEmail.
- database: Utilitários para armazenamento local, como Preference.
- constants: Valores constantes usados na aplicação.
- security: Classes relacionadas com segurança, como PasswordStrength e StrengthLevel.

## Componentes
- **Activities**
  - SplashActivity.kt, WelcomeActivity.kt  
  - LoginActivity.kt, RegisterActivity.kt  
  - MainActivity.kt, MessageChatActivity.kt  
- **Fragments**
  - ChatFragment.kt, SearchFragment.kt, SettingsFragment.kt  
- **Adapters**
  - ChatAdapter.kt, UserAdapter.kt  
- **Model**
  - Users.kt, Chat.kt, ChatList.kt, Photo.kt, PrefEmail.kt  
- **Database**
  - Preference.kt  
- **Constants**
  - Constants.kt  
- **Security**
  - PasswordStrength.kt, StrengthLevel.kt  

## Tecnologias
- Kotlin  
- Firebase Authentication  
- Firebase Realtime Database / Firestore  
- AndroidX (ViewBinding, RecyclerView, Lifecycle)  
- Kotlin Coroutines  
- Gradle  

## Estrutura do Projeto
<img width="789" height="894" alt="Capturar" src="https://github.com/user-attachments/assets/939e0401-2e71-4afd-ada5-a7113cfbfc89" />

## Funcionalidades
- Registo e login de utilizadores (Email/Password)  
- Chat em tempo real entre utilizadores  
- Lista de conversas ativas  
- Pesquisa e início de conversa com novos utilizadores  
- Gestão de perfil de utilizador  
- Verificação de força de palavra-passe  

## Como executar
1. Clonar o repositório  
   ```bash
   git clone https://github.com/OL-sergio/Friends-Chat.git

## Configuraçao de projeto
1. Copiar google-services.json para app/
2. No console do Firebase:
	- Criar projeto e app Android com pacote ipca.am2.projeto2122.friendschat
 	- Ativar método Email/Password em Authentication
 	- Configurar Realtime Database ou Firestore em modo de teste
3. Abrir o projeto no Android Studio
4. Sincronizar o Gradle
5. Executar em emulador ou dispositivo físico
   
