package com.example.apepe.database.dao

import androidx.room.*
import com.example.apepe.model.Task
import retrofit2.Call
import retrofit2.http.*

@Dao
interface TaskDao {

//    @Query("SELECT * FROM tasks ORDER BY id DESC")
//    fun getAll(): List<Task>

    @GET("tasks")
    fun getAll(): Call<List<Task>>

//    @Insert
//    fun insert(task: Task): Long

    @GET("tasks/{id}")
    fun get(@Path("id") id:Long): Call<Task>

//    @Update
//    fun update(task: Task)

    @Headers("Content-Type: application/json")
    @POST("tasks")
    fun insert(@Body task: Task): Call<Task>

//    @Delete
//    fun delete(task: Task)

    @Headers("Content-Type: application/json")
    @PATCH("tasks/{id}")
    fun update(@Path("id") id:Long, @Body task: Task): Call<Task>

    @DELETE("tasks/{id}")
    fun delete(@Path("id") id:Long): Call<Void>
}