package fr.epita.android.hellogames

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WSInterface {
    @GET("game/list")
    fun listGames(): Call<List<GameObject>>

    @GET("game/details")
    fun getGamesbyid(@Query("game_id") id: Int): Call<GameDetailObject>

}