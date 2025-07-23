# Friends-Chat

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
   
