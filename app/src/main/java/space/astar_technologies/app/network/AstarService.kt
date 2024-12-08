package space.astar_technologies.app.network

import retrofit2.http.GET
import retrofit2.http.Path
import space.astar_technologies.app.data.Article
import space.astar_technologies.app.data.Launch
import space.astar_technologies.app.data.Rocket

interface AstarService {
    @GET("/api/v1/articles")
    suspend fun getArticles(): List<Article>

    @GET("/api/v1/articles/{id}")
    suspend fun getArticle(@Path("id") id: Int): Article?

    @GET("/api/v1/rockets")
    suspend fun getRockets(): List<Rocket>

    @GET("/api/v1/rockets/{id}")
    suspend fun getRocket(@Path("id") id: Int): Rocket?

    @GET("/api/v1/launches")
    suspend fun getLaunches(): List<Launch>

    @GET("/api/v1/launches/{id}")
    suspend fun getLaunch(@Path("id") id: Int): Launch?
}