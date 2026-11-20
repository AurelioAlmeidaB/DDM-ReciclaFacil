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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Recupera os valores enviados pela primeira tela
        binding.txtResumoEndereco.text = intent.getStringExtra("endereco")
        binding.txtResumoMateriais.text = intent.getStringExtra("materiais")
        binding.txtResumoPeriodo.text = intent.getStringExtra("periodo")

        binding.btnVoltar.setOnClickListener {
            finish()
        }
    }
}
