package com.example.cardview.repository

import com.example.aplicationtestinglayout.api.RetrofitInstance
import com.example.aplicationtestinglayout.data.TarefaDao
import com.example.aplicationtestinglayout.model.Tarefas
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class Repository (private val tarefaDao: TarefaDao) {

    //Requisições Retrofit
    suspend fun listTarefas(): Response<List<Tarefas>> {
        return RetrofitInstance.api.listTasks()
    }

    suspend fun addTarefa(tarefas: Tarefas): Response<Tarefas>{
        return RetrofitInstance.api.addTarefa(tarefas)
    }

    suspend fun updateTarefa(tarefas: Tarefas): Response<Tarefas>{
        return RetrofitInstance.api.updateTarefa(tarefas)
    }

    suspend fun deleteTarefa(valor: Int): Response<Tarefas>{
        return RetrofitInstance.api.deleteTarefa(valor)
    }
    //Requisições Retrofit

    fun queryAllTarefas(): Flow<List<Tarefas>> {
        return tarefaDao.queryAllTarefas()
    }

    suspend fun insertTarefas(tarefas: Tarefas){
        tarefaDao.insertTarefa(tarefas)
    }

    fun queryById(id: Int): Flow<Tarefas?>{
        return tarefaDao.queryById(id)
    }

    suspend fun updateRoom(tarefas: Tarefas){
        tarefaDao.updateRoom(tarefas)
    }

    suspend fun deleteTarefaRoom(tarefas: Tarefas){
        return tarefaDao.deleteTarefasRoom(tarefas)
    }
}