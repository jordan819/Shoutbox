package com.example.shoutbox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout

class SingleComment : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.comment_layout)

        val comment: ConstraintLayout = findViewById(R.id.test)
        val text: TextView = findViewById(R.id.author)

        text.setOnClickListener(){
            goToSettings()
        }

        comment.setOnClickListener(){
            goToSettings()
        }

    }

    fun click(view: View){
        goToSettings()
    }

    fun goToSettings(){
        val intent = Intent(this@SingleComment, SettingsActivity::class.java)
        startActivity(intent)
    }


}