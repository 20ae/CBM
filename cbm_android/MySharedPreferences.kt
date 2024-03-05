package ac.duksung.cbm_android

import android.content.Context
import android.content.SharedPreferences

object MySharedPreferences {
    private val MY_ACCOUNT : String = "account"

    fun setUserId(context: Context, input: String) {
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = prefs.edit()
        editor.putString("MY_ID", input)
        editor.commit()
    }

    fun getUserId(context: Context): String {
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        return prefs.getString("MY_ID", "").toString()
    }

    fun setGroupType(context: Context, input: String) {
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = prefs.edit()
        editor.putString("MY_TYPE", input)
        editor.commit()
    }

    fun getGroupType(context: Context): String {
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        return prefs.getString("MY_TYPE", "").toString()
    }

    fun setUserName(context: Context, input: String) {
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = prefs.edit()
        editor.putString("MY_NAME", input)
        editor.commit()
    }

    fun getUserName(context: Context): String {
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        return prefs.getString("MY_NAME", "").toString()
    }

    fun setUserPKey(context: Context, input: String) {
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = prefs.edit()
        editor.putString("MY_USERPKEY", input)
        editor.commit()
    }

    fun getUserPKey(context: Context): String {
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        return prefs.getString("MY_USERPKEY", "").toString()
    }

    fun setTestPKey(context: Context, input: String) {
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = prefs.edit()
        editor.putString("MY_TESTPKEY", input)
        editor.commit()
    }

    fun getTestPKey(context: Context): String {
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        return prefs.getString("MY_TESTPKEY", "").toString()
    }

    fun clearUser(context: Context) {
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = prefs.edit()
        editor.clear()
        editor.commit()
    }
    fun setTestLogPKey(context: Context, input: String) {
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = prefs.edit()
        editor.putString("MY_TESTLOGPKEY", input)
        editor.commit()
    }

    fun getTestLogPKey(context: Context): String {
        val prefs: SharedPreferences =
            context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        return prefs.getString("MY_TESTLOGPKEY", "").toString()
    }
}