package com.example.aplicationtestinglayout

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.aplicationtestinglayout.adapter.TarefaAdapter
import com.example.aplicationtestinglayout.adapter.TaskItemClickListener
import com.example.aplicationtestinglayout.databinding.FragmentTaskListFragmentBinding
import com.example.aplicationtestinglayout.model.Tarefas
import com.example.aplicationtestinglayout.viewModel_remoteBD.MainViewModel
import com.example.cardview.repository.Repository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.sql.DataSource


class TaskList_fragment : Fragment(), TaskItemClickListener {

    val mainViewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentTaskListFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTaskListFragmentBinding.inflate(inflater, container, false)


        val adapter = TarefaAdapter(this, mainViewModel)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)

        mainViewModel.listTarefas()
        lifecycleScope.launch {
            mainViewModel.myQueryResponse.collect {
                response -> adapter.setData(response)
            }
        }

        return binding.root
    }

    override fun onTaskClicked(tarefas: Tarefas) {
        mainViewModel.tarefaSelecionada = tarefas
        findNavController().navigate(R.id.ListToCreation)
    }

}

