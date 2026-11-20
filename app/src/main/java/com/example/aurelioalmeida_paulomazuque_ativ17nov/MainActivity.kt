package com.example.aurelioalmeida_paulomazuque_ativ17nov

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import com.example.aurelioalmeida_paulomazuque_ativ17nov.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
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
        val caixa = listOf(binding.cbPapel, binding.cbPlastico, binding.cbVidro, binding.cbMetal)
        for (cb in caixa) {
            cb.setOnCheckedChangeListener { _, _ -> binding.btnResumo.isEnabled = false }
        }
        binding.rgPeriodo.setOnCheckedChangeListener { _, _ -> binding.btnResumo.isEnabled = false }
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
        val caixa = listOf(binding.cbPapel, binding.cbPlastico, binding.cbVidro, binding.cbMetal)

        val elementos = caixa.filter { it.isChecked }
            .map { it.text.toString() }

        if (elementos.isEmpty()) {
            binding.txtMateriais.text = getString(R.string.nenhum_material)
            return false
        }
        binding.txtMateriais.text = getString(R.string.resultado_materiais, elementos.joinToString(", "))
        return true
    }

    private fun verificaRadioButton(): Boolean {
        val idSelecionado = binding.rgPeriodo.checkedRadioButtonId
        if (idSelecionado != -1) {
            val radioButton = findViewById<RadioButton>(idSelecionado)
            binding.txtPeriodo.text = getString(R.string.resultado_periodo, radioButton.text.toString())
            return true
        } else {
            binding.txtPeriodo.text = getString(R.string.nenhum_periodo)
            return false
        }
    }

    // Passa os valores dos TextViews para a segunda tela
    private fun abrirResumo() {
        val intent = Intent(this, ResumoActivity::class.java)
        intent.putExtra("endereco", binding.txtEndereco.text.toString())
        intent.putExtra("materiais", binding.txtMateriais.text.toString())
        intent.putExtra("periodo", binding.txtPeriodo.text.toString())
        startActivity(intent)
    }
}
