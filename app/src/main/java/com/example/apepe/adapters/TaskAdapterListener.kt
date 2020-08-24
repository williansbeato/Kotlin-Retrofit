package com.example.apepe.adapters

import com.example.apepe.model.Task

interface TaskAdapterListener {
//    fun onTaskSelected(task: Task)
    fun taskSalvar(task: Task)
    fun taskExcluir(task: Task)
    fun taskEditar(task: Task)
    fun taskCompart(task: Task)
}