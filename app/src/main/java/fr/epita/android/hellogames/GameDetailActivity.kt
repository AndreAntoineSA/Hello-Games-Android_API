package fr.epita.android.hellogames

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_game_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GameDetailActivity : AppCompatActivity() {

    val baseURL = "https://androidlessonsapi.herokuapp.com/api/"

    private fun dispGameDetail(gameDetail: GameDetailObject) {
        game_name.text = gameDetail.name
        game_type.text = gameDetail.type
        game_players.text = gameDetail.players.toString()
        game_year.text = gameDetail.year.toString()
        game_content.text = gameDetail.description_en

        Glide
            .with(this)
            .load(gameDetail.picture)
            .into(imageView)

        button.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(gameDetail.url)
            startActivity(openURL)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_detail)
        val originIntent = intent
        // extract data from the intent
        val message = originIntent.getIntExtra("GAME_ID", 0)


        val data: GameDetailObject
        val callback: Callback<GameDetailObject> = object : Callback<GameDetailObject> {
            override fun onFailure(call: Call<GameDetailObject>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<GameDetailObject>,
                response: Response<GameDetailObject>
            ) {
                Log.d("xxx", "WS ok")
                if (response.code() == 200) {
                    //precode
                    val res = response.body()!!

                    dispGameDetail(res)


                }
            }
        }

        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL).addConverterFactory(jsonConverter).build()
        val service: WSInterface = retrofit.create(WSInterface::class.java)

        service.getGamesbyid(message).enqueue(callback)


    }
}