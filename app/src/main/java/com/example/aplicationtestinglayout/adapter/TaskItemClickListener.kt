package com.example.aplicationtestinglayout.adapter

import com.example.aplicationtestinglayout.model.Tarefas

interface TaskItemClickListener {

    fun onTaskClicked(tarefas: Tarefas)

}