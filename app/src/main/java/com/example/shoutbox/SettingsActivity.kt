package com.example.shoutbox

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.navigation.NavigationView

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)


        val loginInput = findViewById<EditText>(R.id.loginInput)
        val confirmButton: Button = findViewById(R.id.confirmButton)





        confirmButton.setOnClickListener {
            val login: String = loginInput.text.toString().trim()

            if (login.isNullOrBlank()){
                Toast.makeText(
                    applicationContext,
                    "Please enter username first! ",
                    Toast.LENGTH_LONG
                ).show()
            }
            else{
                confirmFirstLaunch()
                saveLogin(login)
                goToShoutbox()
            }

        }


        val nav_view: NavigationView = findViewById<NavigationView>(R.id.nav_view)
        nav_view.setNavigationItemSelectedListener { menuItem ->

            if (menuItem.itemId == R.id.button_shoutbox){
                goToShoutbox()
            }

            true
        }
    }

    fun goToShoutbox(){
        val intent = Intent(this@SettingsActivity, MainActivity::class.java)
        startActivity(intent)
    }

    fun confirmFirstLaunch() {
        val sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("LAUNCH_KEY", false)
        editor.apply()
    }

    fun saveLogin(login: String) {
        val sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("LOGIN_KEY", login)
        editor.apply()
    }

}