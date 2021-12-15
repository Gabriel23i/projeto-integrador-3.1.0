package com.example.aplicationtestinglayout.data

import androidx.room.*
import com.example.aplicationtestinglayout.model.Tarefas
import kotlinx.coroutines.flow.Flow


@Dao
interface TarefaDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTarefa(tarefas: Tarefas)

    @Query("SELECT * FROM tarefa_table ORDER BY id ASC")
    fun queryAllTarefas(): Flow<List<Tarefas>>

    @Query("SELECT * FROM tarefa_table WHERE id = :id")
    fun queryById(id : Int): Flow<Tarefas?>

    @Update
    suspend fun updateRoom (task: Tarefas)

    @Delete
    suspend fun deleteTarefasRoom(task: Tarefas)

}