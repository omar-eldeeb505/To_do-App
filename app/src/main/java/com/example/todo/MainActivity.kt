package com.example.todo

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import java.util.Locale

class MainActivity : AppCompatActivity() {
lateinit var shardPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        Handler(Looper.getMainLooper()).postDelayed({startActivity(Intent(this@MainActivity,HomeActivity::class.java))
                                                    finish()
                                                    },2000)
        getLanguageShardSetting()

        getModeSharedSetting()
    }

    private fun getModeSharedSetting() {
        shardPreferences = getSharedPreferences(CONST.SHARD_NAME, MODE_PRIVATE)
        if (shardPreferences.getString(
                CONST.MODE_CODE,
                CONST.LIGHT_MODE_CODE
            ) == CONST.LIGHT_MODE_CODE
        ) {
            changeToLightMode()
        } else if (shardPreferences.getString(
                CONST.MODE_CODE,
                CONST.DARK_MODE_CODE
            ) == CONST.DARK_MODE_CODE
        ) {
            changeToDarkMode()
        }
    }


    private fun getLanguageShardSetting() {
        shardPreferences = getSharedPreferences(CONST.SHARD_NAME, MODE_PRIVATE)
        if (shardPreferences.getString(CONST.LANGUAGE_CODE, "en") == "en") {
            setLocale(this, "en")
        } else if (shardPreferences.getString(CONST.LANGUAGE_CODE, "ar") == "ar") {
            setLocale(this, "ar")
        }
    }


    fun setLocale(context: Context,languageCode:String){

        val locale= Locale(languageCode)
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(languageCode))


    }
    fun changeToLightMode(){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

    }
    fun changeToDarkMode(){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

    }








}