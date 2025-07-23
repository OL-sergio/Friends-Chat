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
app/src/main/java/ipca/am2/projeto2122/friendschat/
├─ MainActivity.kt
├─ MessageChatActivity.kt
├─ adapters/
│ ├─ ChatAdapter.kt
│ └─ UserAdapter.kt
├─ constants/
│ └─ Constants.kt
├─ database/
│ └─ Preference.kt
├─ model/
│ ├─ Chat.kt
│ ├─ ChatList.kt
│ ├─ Photo.kt
│ ├─ PrefEmail.kt
│ └─ Users.kt
├─ security/
│ ├─ PasswordStrength.kt
│ └─ StrengthLevel.kt
└─ ui/
├─ activity/
│ ├─ intro/
│ │ ├─ SplashActivity.kt
│ │ └─ WelcomeActivity.kt
│ └─ entry/
│ ├─ LoginActivity.kt
│ └─ RegisterActivity.kt
└─ fragments/
├─ chat/
│ └─ ChatFragment.kt
├─ search/
│ └─ SearchFragment.kt
└─ settings/
└─ SettingsFragment.kt


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
   
