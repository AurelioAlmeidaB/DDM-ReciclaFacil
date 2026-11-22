package com.example.aurelioalmeida_paulomazuque_ativ17nov

import android.content.Intent
import android.os.Bundle
import android.widget.CheckBox
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import com.example.aurelioalmeida_paulomazuque_ativ17nov.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Lista das CheckBox de materiais, usada em mais de um lugar
    private val caixas: List<CheckBox> by lazy {
        listOf(binding.cbPapel, binding.cbPlastico, binding.cbVidro, binding.cbMetal)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnAgendar.setOnClickListener {
            val enderecoOk = verificaEndereco()
            val materiaisOk = verificaCheckBox()
            val periodoOk = verificaRadioButton()

            // Só libera o resumo se tudo estiver preenchido
            binding.btnResumo.isEnabled = enderecoOk && materiaisOk && periodoOk
        }

        binding.btnResumo.setOnClickListener {
            abrirResumo()
        }

        // Se mudar alguma escolha depois de agendar, precisa agendar de novo
        binding.edtEndereco.doAfterTextChanged { binding.btnResumo.isEnabled = false }
        for (cb in caixas) {
            cb.setOnCheckedChangeListener { _, _ -> binding.btnResumo.isEnabled = false }
        }
        binding.rgPeriodo.setOnCheckedChangeListener { _, _ -> binding.btnResumo.isEnabled = false }
    }

    // Ao girar a tela (ou trocar o tema) a Activity é recriada. Os campos de entrada
    // se salvam sozinhos, mas os TextViews de resultado e o botão "Ver resumo" não.
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putCharSequence(ESTADO_ENDERECO, binding.txtEndereco.text)
        outState.putCharSequence(ESTADO_MATERIAIS, binding.txtMateriais.text)
        outState.putCharSequence(ESTADO_PERIODO, binding.txtPeriodo.text)
        outState.putBoolean(ESTADO_RESUMO_LIBERADO, binding.btnResumo.isEnabled)
    }

    // Restaurado aqui (e não no onCreate) porque o super devolve os campos de entrada,
    // o que dispara os listeners acima e desativaria o botão "Ver resumo".
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        binding.txtEndereco.text = savedInstanceState.getCharSequence(ESTADO_ENDERECO)
        binding.txtMateriais.text = savedInstanceState.getCharSequence(ESTADO_MATERIAIS)
        binding.txtPeriodo.text = savedInstanceState.getCharSequence(ESTADO_PERIODO)
        binding.btnResumo.isEnabled = savedInstanceState.getBoolean(ESTADO_RESUMO_LIBERADO)
    }

    private fun verificaEndereco(): Boolean {
        val endereco = binding.edtEndereco.text.toString().trim()
        if (endereco.isEmpty()) {
            binding.edtEndereco.error = getString(R.string.erro_endereco)
            binding.txtEndereco.text = getString(R.string.endereco_nao_informado)
            return false
        }
        binding.txtEndereco.text = getString(R.string.resultado_endereco, endereco)
        return true
    }

    private fun verificaCheckBox(): Boolean {
        val elementos = caixas.filter { it.isChecked }
            .map { it.text.toString() }

        if (elementos.isEmpty()) {
            binding.txtMateriais.text = getString(R.string.nenhum_material)
            return false
        }
        binding.txtMateriais.text = getString(R.string.resultado_materiais, elementos.joinToString(", "))
        return true
    }

    private fun verificaRadioButton(): Boolean {
        val selecionado = listOf(binding.rbManha, binding.rbTarde, binding.rbNoite)
            .firstOrNull { it.isChecked }
        if (selecionado != null) {
            binding.txtPeriodo.text = getString(R.string.resultado_periodo, selecionado.text.toString())
            return true
        } else {
            binding.txtPeriodo.text = getString(R.string.nenhum_periodo)
            return false
        }
    }

    // Passa os valores dos TextViews para a segunda tela
    private fun abrirResumo() {
        val intent = Intent(this, ResumoActivity::class.java)
        intent.putExtra(ResumoActivity.EXTRA_ENDERECO, binding.txtEndereco.text.toString())
        intent.putExtra(ResumoActivity.EXTRA_MATERIAIS, binding.txtMateriais.text.toString())
        intent.putExtra(ResumoActivity.EXTRA_PERIODO, binding.txtPeriodo.text.toString())
        startActivity(intent)
    }

    companion object {
        private const val ESTADO_ENDERECO = "estado_endereco"
        private const val ESTADO_MATERIAIS = "estado_materiais"
        private const val ESTADO_PERIODO = "estado_periodo"
        private const val ESTADO_RESUMO_LIBERADO = "estado_resumo_liberado"
    }
}
