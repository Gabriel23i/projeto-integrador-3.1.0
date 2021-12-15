package com.example.aplicationtestinglayout

import android.app.TimePickerDialog
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.aplicationtestinglayout.databinding.FragmentCrationTaskFragmentBinding
import com.example.aplicationtestinglayout.model.Tarefas
import com.example.aplicationtestinglayout.viewModel_remoteBD.MainViewModel
import com.example.cardview.fragment.DatePickerFragment
import com.example.cardview.fragment.TimePickerListener
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.timer


class CrationTask_fragment : Fragment(), TimePickerListener, AdapterView.OnItemSelectedListener {

    private val mainViewModel: MainViewModel by activityViewModels()

    private var _tarefaSelecionada: Tarefas? = null
    private val tarefaSelecionada get() = _tarefaSelecionada!!

    private var _binding: FragmentCrationTaskFragmentBinding? = null
    private val binding get() = _binding!!

    var escolha = ""
    var dadosValidados: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCrationTaskFragmentBinding.inflate(inflater, container, false)

        carregarDados()
        specialTasks()

        fun selecionaTask(){
            if (binding.buttonHabito.isChecked){
                escolha = "0"
            }else if (binding.buttonMeta.isChecked){
                escolha = "2"
            }else if (binding.buttonTarefa.isChecked){
                escolha = "1"
            }
        }

        mainViewModel.selectedDateLiveData.observe(viewLifecycleOwner, {
            selectedDate -> binding.inputData.setText(selectedDate.toString())
        })

        binding.inputData.setOnClickListener{

            DatePickerFragment(this).show(parentFragmentManager, "DatePicker")

        }

        binding.BotaoSalvarTarefa.setOnClickListener{

            validaForm(binding.taskTitleForm.text.toString(), binding.descricaoTask.text.toString(), binding.inputData.text.toString(), binding.imputHora.text.toString())

            if (dadosValidados == true)
            {
                selecionaTask()
                inserirNoBanco()
                mainViewModel.contador++
                mainViewModel.tarefaSelecionada = null
            }
        }

        binding.BotaoCancelarTarefa.setOnClickListener{

            binding.taskTitleForm.text = null
            binding.descricaoTask.text = null
            binding.inputData.text = null
            binding.imputHora.text = null

        }

        binding.buttonHabito.setOnClickListener{
            mudaCorHabito()
        }
        binding.buttonTarefa.setOnClickListener{
            mudaCorTarefa()
        }
        binding.buttonMeta.setOnClickListener{
            mudaCorMeta()
        }

        binding.imputHora.setOnClickListener{

            fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

            val cal = Calendar.getInstance()
            var hora: String
            val timeSetListener = TimePickerDialog.OnTimeSetListener{timePicker: TimePicker?, hour: Int, minute: Int ->

                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                hora = SimpleDateFormat ("HH:mm").format(cal.time)

                binding.imputHora.text = hora.toEditable()
            }
            TimePickerDialog (context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        return _binding?.root
    }

    private fun validaForm(titulo: String, descricao: String, data: String, hora: String) {

        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val date = formatter.format(Date())
        val inputData = binding.inputData.text.toString()
        Log.d("Data", date.toString())
        Log.d("Data", inputData.toString())

        if ((TextUtils.isEmpty(titulo) || TextUtils.isEmpty(descricao) || TextUtils.isEmpty(data) || TextUtils.isEmpty(hora))){

            return Toast.makeText(context, "Algum campo solicitado está em branco!", Toast.LENGTH_SHORT).show()

        }
        else if (titulo.length > 50 || descricao.length > 150){

            return Toast.makeText(context, "Foram excedidas as quantidas maximas de caracteres", Toast.LENGTH_SHORT).show()
        }
        else if (inputData < date){
            return Toast.makeText(context, "Data Inválida", Toast.LENGTH_SHORT).show()
        }
        else{
            dadosValidados = true
        }
    }


    private fun specialTasks() {

        var contadorSpecial = mainViewModel.contador

        if (contadorSpecial == 10){


            val titulo = "Beber Agua"
            val desc = "Beber Agua é importante, Hidrate-se"
            val dono = "3"
            val status = "0"

            val taskSpecial = Tarefas(0, titulo, desc, dono,
                mainViewModel.selectedDateLiveData.value!!,
                status)
            mainViewModel.addTarefa(taskSpecial)

        }
        else if (contadorSpecial == 20){

            val titulo = "Que tal uma caminhada? "
            val desc = "Ao caminhar você contribui para sua própria saúde"
            val dono = "3"
            val status = "0"

            val taskSpecial = Tarefas(0, titulo, desc, dono,
                mainViewModel.selectedDateLiveData.value!!,
                status)
            mainViewModel.addTarefa(taskSpecial)

        }
        else if (contadorSpecial == 30){

            val titulo = "Dormir cedo ajuda na sua saúde"
            val desc = "Uma boa noite de sono melhora seu \n humor e qualidade de vida"
            val dono = "3"
            val status = "0"

            val taskSpecial = Tarefas(0, titulo, desc, dono,
                mainViewModel.selectedDateLiveData.value!!,
                status)
            mainViewModel.addTarefa(taskSpecial)

        }

        else if (contadorSpecial == 30){

            val titulo = "Alogamentos ajudam!!"
            val desc = "Alongamentos evitam lesões e problemas vasculares"
            val dono = "3"
            val status = "0"

            val taskSpecial = Tarefas(0, titulo, desc, dono,
                mainViewModel.selectedDateLiveData.value!!,
                status)
            mainViewModel.addTarefa(taskSpecial)

        }
        else if (contadorSpecial > 20 )
            {mainViewModel.contador = 0}
    }

    fun mudaCorHabito() {
        binding.buttonHabito.setBackgroundResource(R.drawable.selectbuttons_bg_habito)
        binding.buttonMeta.setBackgroundResource(R.drawable.selectbuttons_bg_white_set)
        binding.buttonTarefa.setBackgroundResource(R.drawable.selectbuttons_bg_white_set)
        binding.buttonMeta.isChecked = false
        binding.buttonTarefa.isChecked = false
    }

    fun mudaCorMeta() {
        binding.buttonMeta.setBackgroundResource(R.drawable.selectbuttons_bg_meta)
        binding.buttonHabito.setBackgroundResource(R.drawable.selectbuttons_bg_white_set)
        binding.buttonTarefa.setBackgroundResource(R.drawable.selectbuttons_bg_white_set)
        binding.buttonHabito.isChecked = false
        binding.buttonTarefa.isChecked = false
    }

    fun mudaCorTarefa() {
        binding.buttonTarefa.setBackgroundResource(R.drawable.selectbuttons_bg_tarefa)
        binding.buttonMeta.setBackgroundResource(R.drawable.selectbuttons_bg_white_set)
        binding.buttonHabito.setBackgroundResource(R.drawable.selectbuttons_bg_white_set)
        binding.buttonMeta.isChecked = false
        binding.buttonHabito.isChecked = false
    }

    private fun inputCheck(
        titulo: String, desc: String, dono: String,
        data: String, status: String
    ): Boolean{
        return !(TextUtils.isEmpty(titulo) &&
                TextUtils.isEmpty(desc) &&
                TextUtils.isEmpty(dono) &&
                TextUtils.isEmpty(data) &&
                TextUtils.isEmpty(status)
                )
    }

    fun inserirNoBanco(){

        val titulo = binding.taskTitleForm.text.toString()
        val desc = binding.descricaoTask.text.toString()
        val dono = escolha
        val data = binding.inputData.text.toString()
        val status = binding.imputHora.text.toString()

        if(inputCheck(titulo, desc, dono, data, status)){
            _tarefaSelecionada = mainViewModel.tarefaSelecionada
            var atualizarCriar = ""
            if (_tarefaSelecionada != null) {
                val tarefas = Tarefas(tarefaSelecionada.id, titulo, desc, dono,
                    mainViewModel.selectedDateLiveData.value!!,
                    status
                )
                mainViewModel.updateTarefa(tarefas)
                atualizarCriar = "Tarefa Atualizada!"
            }else{
                val tarefas = Tarefas(0, titulo, desc, dono,
                    mainViewModel.selectedDateLiveData.value!!,
                    status
                )
                mainViewModel.addTarefa(tarefas)
                atualizarCriar = "Tarefa Adicionada!"
            }
            Toast.makeText(
                context, atualizarCriar,
                Toast.LENGTH_LONG
            ).show()

            findNavController().navigate(R.id.CreationTaskToList)
        }else{
            Toast.makeText(
                context, "Preencha todos os campos!",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun carregarDados() {
        _tarefaSelecionada = mainViewModel.tarefaSelecionada
        if (_tarefaSelecionada != null) {
            binding.taskTitleForm.setText(tarefaSelecionada.name)
            binding.descricaoTask.setText(tarefaSelecionada.description)
            binding.inputData.setText(tarefaSelecionada.dueDate)
        } else {
            binding.taskTitleForm.text = null
            binding.descricaoTask.text = null
            binding.inputData.text = null

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onTimeSelected(date: Date) {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val formatedDate = formatter.format(date).toString()
        mainViewModel.selectedDateLiveData.postValue(formatedDate)
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val itemAtPosition: String = p0?.getItemAtPosition(p2) as String
        Log.d("Developer", "itemAtPosition: $itemAtPosition")
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }
}


