package com.example.aurelioalmeida_paulomazuque_ativ17nov

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aurelioalmeida_paulomazuque_ativ17nov.databinding.ActivityResumoBinding

class ResumoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResumoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityResumoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Recupera os valores enviados pela primeira tela
        binding.txtResumoEndereco.text = intent.getStringExtra(EXTRA_ENDERECO)
        binding.txtResumoMateriais.text = intent.getStringExtra(EXTRA_MATERIAIS)
        binding.txtResumoPeriodo.text = intent.getStringExtra(EXTRA_PERIODO)

        binding.btnVoltar.setOnClickListener {
            finish()
        }
    }

    // Chaves dos dados enviados pela MainActivity (as duas telas usam as mesmas)
    companion object {
        const val EXTRA_ENDERECO = "endereco"
        const val EXTRA_MATERIAIS = "materiais"
        const val EXTRA_PERIODO = "periodo"
    }
}
