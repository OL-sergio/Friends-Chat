package ipca.am2.projeto2122.friendschat.database

import android.content.Context
import android.content.SharedPreferences

class Preference(context: Context) {

    var longinPrefer : String

    get() = preferencesShared.getString("LOGIN_PREFERENCE","") as String
        set(value) {

            val editor : SharedPreferences.Editor = preferencesShared.edit()
            editor.putString("LOGIN_PREFERENCE", value)
            editor.apply()
        }

    private var preferencesShared : SharedPreferences = context
        .getSharedPreferences("EMAIL_PREFERENCE", Context.MODE_PRIVATE)
}

