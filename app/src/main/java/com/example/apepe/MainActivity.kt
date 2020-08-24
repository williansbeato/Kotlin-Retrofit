package com.example.apepe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.apepe.adapters.TaskAdapter
import com.example.apepe.adapters.TaskAdapterListener
import com.example.apepe.database.dao.TaskDao
import com.example.apepe.model.Task
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), TaskAdapterListener {

    private lateinit var adapter: TaskAdapter
    private lateinit var dao: TaskDao
    private lateinit var tasks : MutableList<Task>
    private lateinit var service : TaskDao
    private lateinit var retrofit : Retrofit
    private var msgPrincipal = "Terminei o bagulho"
    private var msgSecundaria = "Se liga no role que eu fiz : "



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        retrofit()
//        bancao()

        btAdd.setOnClickListener {
            val task = Task ("", "", false)
            task.id = 0L
            adapter.addTask(task)
        }

        carregador()

    }

    private fun retrofit() {
        retrofit =Retrofit.Builder()
            .baseUrl("http://192.168.0.102:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(TaskDao::class.java)
        tasks = mutableListOf()

        service.getAll().enqueue(object : Callback<List<Task>>{
            override fun onFailure(call: Call<List<Task>>, t: Throwable) {
                Log.e("API error", t.message, t)
            }

            override fun onResponse(call: Call<List<Task>>, response: Response<List<Task>>) {
                tasks= response.body()!!.toMutableList()
                carregador()
            }
        }

        )




    }


//    fun bancao (){
//        val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "task-bancao")
//            .allowMainThreadQueries()
//            .build()
//        dao = db.taskDao()
//
//
//    }

    fun carregador (){

//        tasks = dao.getAll().toMutableList()
        adapter = TaskAdapter(tasks.toMutableList(), this, applicationContext)
        rvLista.adapter = adapter
        rvLista.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }


    override fun taskSalvar(task: Task) {
//        dao.insert(task)
        service.insert(task).enqueue(object : Callback<Task>{

            override fun onFailure(call: Call<Task>, t: Throwable) {   }

            override fun onResponse(call: Call<Task>, response: Response<Task>) {
                val inserirTask = response.body()!!
                task.id = inserirTask.id
                tasks.add(0, task)
            }
        })




        carregador()
    }

    override fun taskExcluir(task: Task) {
//        dao.delete(task)
        carregador()
    }

    override fun taskEditar(task: Task) {
//        dao.update(task)
        carregador()
    }

    override fun taskCompart(task: Task) {
        val compartilha = Intent(Intent.ACTION_SEND)

        with(compartilha){
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, msgPrincipal)
            putExtra(Intent.EXTRA_TEXT, msgSecundaria+task.title)
        }
        startActivity(compartilha)
    }


}