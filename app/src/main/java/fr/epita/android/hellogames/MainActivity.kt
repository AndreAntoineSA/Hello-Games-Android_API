package fr.epita.android.hellogames

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MainActivity : AppCompatActivity() {
    val baseURL = "https://androidlessonsapi.herokuapp.com/api/"
    private fun gamePage(gameId: Int) {
        val explicitIntent = Intent(this, GameDetailActivity::class.java)
        explicitIntent.putExtra("GAME_ID", gameId)
        explicitIntent.putExtra("GAME_BASE_URL", this.baseURL)

        startActivity(explicitIntent)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data: List<GameObject>
        val callback: Callback<List<GameObject>> = object : Callback<List<GameObject>> {
            override fun onFailure(call: Call<List<GameObject>>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<List<GameObject>>,
                response: Response<List<GameObject>>
            ) {
                Log.d("xxx", "WS ok")
                if (response.code() == 200) {
                    //precode
                    val res = response.body()
                    Toast.makeText(this@MainActivity, "Got" + res!!.size, Toast.LENGTH_SHORT).show()
                    Glide
                        .with(this@MainActivity).load(res[0].picture).into(imageButton1)
                    imageButton1.tag = res[0]
                    Glide
                        .with(this@MainActivity).load(res[1].picture).into(imageButton2)
                    imageButton2.tag = res[1]
                    Glide
                        .with(this@MainActivity).load(res[2].picture).into(imageButton3)
                    imageButton3.tag = res[2]
                    Glide
                        .with(this@MainActivity).load(res[3].picture).into(imageButton4)
                    imageButton4.tag = res[3]

                }
            }
        }

        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL).addConverterFactory(jsonConverter).build()
        val service: WSInterface = retrofit.create(WSInterface::class.java)

        service.listGames().enqueue(callback)

        imageButton1.setOnClickListener {
            val game: GameObject = imageButton1.tag as GameObject
            this.gamePage(game.id)
            Toast.makeText(this, "button 1", Toast.LENGTH_SHORT).show()
        }

        imageButton2.setOnClickListener {
            val game: GameObject = imageButton2.tag as GameObject
            this.gamePage(game.id)
            Toast.makeText(this, "button 2", Toast.LENGTH_SHORT).show()
        }
        imageButton3.setOnClickListener {
            val game: GameObject = imageButton3.tag as GameObject
            this.gamePage(game.id)
            Toast.makeText(this, "button 3", Toast.LENGTH_SHORT).show()
        }
        imageButton4.setOnClickListener {
            val game: GameObject = imageButton4.tag as GameObject
            this.gamePage(game.id)
            Toast.makeText(this, "button 4", Toast.LENGTH_SHORT).show()
        }


    }


}