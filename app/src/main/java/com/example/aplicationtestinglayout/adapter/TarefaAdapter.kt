package com.example.aplicationtestinglayout.adapter

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aplicationtestinglayout.R
import com.example.aplicationtestinglayout.model.Tarefas
import com.example.aplicationtestinglayout.viewModel_remoteBD.MainViewModel

class TarefaAdapter(

    private val taskItemClickListener: TaskItemClickListener,
    private val mainViewModel: MainViewModel
    )
    : RecyclerView.Adapter<TarefaAdapter.TarefaViewHolder>()
    {

    private var listaTarefas = emptyList<Tarefas>()

    class TarefaViewHolder(view: View): RecyclerView.ViewHolder(view){
        val textNome = view.findViewById<TextView>(R.id.titulo)
        val textDesc = view.findViewById<TextView>(R.id.descri)
        val textData = view.findViewById<TextView>(R.id.data)
        val buttonDeletar = view.findViewById<Button>(R.id.buttonDeletar)

        // Definir o icone e cor da task
        val textDono = view.findViewById<TextView>(R.id.DonoProv)
        // Definir o icone e cor da task

        val textStatus = view.findViewById<TextView>(R.id.HoraTarefa)
        val ImageType = view.findViewById<ImageView>(R.id.ColorViewTasks)
        val typeImage = view.findViewById<ImageView>(R.id.TypeImage)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarefaViewHolder {

        val layoutAdapter = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_layout, parent, false)

        return TarefaViewHolder(layoutAdapter)

    }

    override fun onBindViewHolder(holder: TarefaViewHolder, position: Int) {

        val tarefa = listaTarefas[position]

        holder.textNome.text = tarefa.name
        holder.textDesc.text = tarefa.description
        holder.textDono.text = tarefa.assignetTo
        holder.textData.text = tarefa.dueDate
        holder.textStatus.text = tarefa.status

        if (holder.textDono.text == "0") {
            holder.ImageType.setImageResource(R.drawable.recycle_task_color_green)
            holder.typeImage.setImageResource(R.drawable.habitow)

        }
        else if(holder.textDono.text == "1"){
            holder.ImageType.setImageResource(R.drawable.recycle_task_collor_red)
            holder.typeImage.setImageResource(R.drawable.clipboardsw)
        }
        else if (holder.textDono.text == "2"){
            holder.ImageType.setImageResource(R.drawable.recycle_task_color_yellow)
            holder.typeImage.setImageResource(R.drawable.goalw)
        }
        else if (holder.textDono.text == "3"){
            holder.ImageType.setImageResource(R.drawable.background3)
            holder.typeImage.setImageResource(R.drawable.ic_special_task)
        }

        holder.buttonDeletar.setOnClickListener {
            mainViewModel.deleteTarefa(tarefa)
        }

        holder.itemView.setOnClickListener {
            taskItemClickListener.onTaskClicked(tarefa)
        }

    }

    override fun getItemCount(): Int {
        return listaTarefas.size
    }

    fun setData(tarefas: List<Tarefas>){

        this.listaTarefas = tarefas
        notifyDataSetChanged()

    }

}