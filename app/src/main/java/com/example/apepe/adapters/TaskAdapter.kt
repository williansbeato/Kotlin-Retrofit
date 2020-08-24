package com.example.apepe.adapters

import android.content.Context
import android.graphics.Color.GREEN
import android.graphics.Color.RED
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.apepe.R
import com.example.apepe.model.Task
import kotlinx.android.synthetic.main.cartao_edit.view.*
import kotlinx.android.synthetic.main.cartao_view.view.*

class TaskAdapter( var tasks: MutableList<Task>, val listener: TaskAdapterListener, context: Context):
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    var taskSelecionada: Task? = null



//    override fun getItemViewType(position: Int): Int {
//
////        val task = tasks[position]
////        return if (task == taskSelecionada) {
////            R.layout.
////        } else {
////            R.layout.card_show_task
////        }
//
//    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun fillView(task: Task){

            var cartao = itemView as CardView

            if (task==taskSelecionada){
                itemView.txtTitle.setText(task.title)
                itemView.txtDescricao.setText(task.description)

                itemView.btSave.setOnClickListener {
                   if(taskSelecionada != null && taskSelecionada!!.id == 0L){
                       task.title = itemView.txtTitle.text.toString()
                       task.description = itemView.txtDescricao.text.toString()
                       task.status = false
                       with(this@TaskAdapter){
                           listener.taskSalvar(task)
                       }
                       notifyItemInserted(tasks.indexOf(task))

                   }else{
                       task.title = itemView.txtTitle.text.toString()
                       task.description = itemView.txtDescricao.text.toString()

                       with(this@TaskAdapter){
                           listener.taskEditar(task)
                       }
                       notifyItemChanged(tasks.indexOf(task))
                   }
                }

                itemView.btDelete.setOnClickListener {
                    taskSelecionada = null

                    with(this@TaskAdapter){
                        listener.taskExcluir(task)
                    }
                    notifyItemRemoved(tasks.indexOf(task))

                }

            }
            else{
                itemView.txtitulo.text = task.title
                if (task.status){
//                  cartao.setBackgroundColor(GREEN)
                    cartao.txtitulo.setTextColor(GREEN)
//                    cartao.ivFeito.visibility = View.VISIBLE

                }else{
//                    cartao.setCardBackgroundColor(RED)
                    cartao.txtitulo.setTextColor(RED)
//                    cartao.ivNaofeito.visibility = View.VISIBLE

                }

                itemView.setOnLongClickListener {
                    if (task.status){
                        task.status = false
                    }else{
                        task.status = true
                    }
                    listener.taskEditar(task)
                    notifyItemChanged(tasks.indexOf(task))
                    true

                }

                itemView.setOnClickListener {
                    taskSelecionada=task
                    notifyItemChanged(tasks.indexOf(task))
                }

                itemView.btCompartilhar.setOnClickListener {
                    listener.taskCompart(task)
                }


            }
        }
    }






    fun addTask(task: Task) {
        taskSelecionada=task
        tasks.add(0, task)
        notifyItemInserted(0)

    }



    override fun getItemViewType(position: Int): Int {

        var task=tasks[position]
        return if(task==taskSelecionada)
            R.layout.cartao_edit
        else
            R.layout.cartao_view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
      ViewHolder(
          LayoutInflater
              .from(parent.context)
              .inflate(viewType, parent, false)
      )



    override fun getItemCount() = tasks.size

    override fun onBindViewHolder(holder: TaskAdapter.ViewHolder, position: Int) {
       val task= tasks[position]
        holder.fillView(task)
    }


}