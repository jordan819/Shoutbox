package com.example.shoutbox

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.comment_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MainActivity : AppCompatActivity() {

    private var isFirstLaunch = false
    private lateinit var login: String


    private lateinit var recycler_view: RecyclerView


    private var exampleList = ArrayList<Comment>()
    private lateinit var adapter: Adapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        checkIfFirstLaunch()
        if (isFirstLaunch){
            goToSettings()
        }
        else{
            loadLogin()
            Toast.makeText(
                applicationContext,
                "Welcome $login",
                Toast.LENGTH_SHORT
            ).show()

            getCurrentData()

            adapter = Adapter(exampleList)

            val button: Button = findViewById(R.id.buttonSendComment)
            val comment_input: EditText = findViewById(R.id.commentInput)

            recycler_view = findViewById(R.id.recycler_view)
            recycler_view.adapter = Adapter(exampleList)
            recycler_view.layoutManager = LinearLayoutManager(this)
            recycler_view.setHasFixedSize(true)
            recycler_view.scrollToPosition(exampleList.size-1)

            button.setOnClickListener{
                addComment()
                comment_input.setText("")
                comment_input.setHint("Type Comment")
                getCurrentData()
            }



        }


        val nav_view:NavigationView = findViewById<NavigationView>(R.id.nav_view)
        nav_view.setNavigationItemSelectedListener { menuItem ->

            if (menuItem.itemId == R.id.button_settings){
                goToSettings()
            }

            true
        }

    }

    fun goToSettings(){
        val intent = Intent(this@MainActivity, SettingsActivity::class.java)
        startActivity(intent)
    }

    private fun checkIfFirstLaunch() {
        val sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE)
        isFirstLaunch = sharedPreferences.getBoolean("LAUNCH_KEY", true)
    }

    private fun loadLogin() {
        val sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE)
        login = sharedPreferences.getString("LOGIN_KEY", "Guest").toString()
    }

    private fun getCurrentData() {
        val api = Retrofit.Builder()
            .baseUrl(JsonApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(JsonApi::class.java)

        api.getComments().enqueue(object : Callback<List<FetchedComment>> {
            override fun onFailure(call: Call<List<FetchedComment>>, t: Throwable) {
                Log.e("MainActivity", "ERROR: $t")
            }

            override fun onResponse(call: Call<List<FetchedComment>>, response: Response<List<FetchedComment>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        for (FetchedComment in it) {
                            exampleList.add(
                                Comment(
                                    FetchedComment.content,
                                    FetchedComment.login,
                                    FetchedComment.date.split("T")[0] + "  " + FetchedComment.date.split("T")[1].split(".")[0]
                                )

                            )

                        }
                    }
                }

                val recycler_view = findViewById<RecyclerView>(R.id.recycler_view)
                if(recycler_view!=null){
                    recycler_view.scrollToPosition(exampleList.size-1)
                }


            }
        })
    }

    private fun getLogin(): String {
        val sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE)
        login = sharedPreferences.getString("LOGIN_KEY", "Guest").toString()
        return login
    }

    private fun addComment(){

        val api = Retrofit.Builder()
            .baseUrl(JsonApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(JsonApi::class.java)

        api.addComment(AddComment(getLogin(),findViewById<EditText>(R.id.commentInput).text.toString())).enqueue(object : Callback<FetchedComment> {
            override fun onFailure(call: Call<FetchedComment>, t: Throwable) {
                Log.e("MainActivity", "ERROR: $t")
            }

            override fun onResponse(call: Call<FetchedComment>, response: Response<FetchedComment>) {
                if (response.isSuccessful) {

                }


                val recycler_view = findViewById<RecyclerView>(R.id.recycler_view)
                if(recycler_view!=null){
                    recycler_view.scrollToPosition(exampleList.size-1)
                }


            }
        })
    }

}