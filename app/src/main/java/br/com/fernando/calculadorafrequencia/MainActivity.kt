package br.com.fernando.calculadorafrequencia

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.fernando.calculadorafrequencia.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    //variavel binding para vincular no XML os componentes
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //chama a funcao do botao para calcular frequencia
        calcularFrequencia()
    }

    //inicia o listener e chama a funcao onClick para chamar outra funcao da frequencia
    fun calcularFrequencia(){
        binding.btnCalcular.setOnClickListener {
            initCalcFrequencia()
        }
    }

    //funcao  que calcula a frequencia do aluno
    fun calcularFrequenciaAluno(aulasTotais: String, presencasTotais: String ){
        val aulas = aulasTotais.toDouble()
        val presencas = presencasTotais.toDouble()

        //verifica divisao por zero
        if (aulas == 0.0){
            warningError(R.string.warning_zero)
        } else {
                val result = (presencas / aulas * 100).roundToInt() // arredonda o resulta pra INT
            if (result <= 100){
                binding.tvResult.text = result.toString().plus(getText(R.string.percent))
                Toast.makeText(this, "Sua frequencia no período foi de $result%", Toast.LENGTH_SHORT).show()
            } else {
                binding.tvResult.text = ""
                warningError(R.string.mais_que_total)
            }
        }
    }

    //valida campos vazios e/ou nulos
    fun initCalcFrequencia(){
        val totalAulas = binding.edtTotalAulas.text.toString()
        val totalPresenca = binding.edtTotalPresencas.text.toString()
        val validarCampos = validadeNullOrEmpty(totalPresenca, totalAulas)

        //caso true, inicia o calculo com os parametros, senao retorna snack warning
        if (validarCampos){
            calcularFrequenciaAluno(totalAulas, totalPresenca)
        } else {
            warningError(R.string.campos_vazios)
        }
    }

    //funcao que mostra mensagens de warnings usando snackbar do material design
    fun warningError(mensagem: Int){
        val constraintLayout = binding.lincial
        val snack = Snackbar.make(constraintLayout, mensagem,
            Snackbar.LENGTH_LONG).setAction("Action", null)
        snack.setActionTextColor(Color.WHITE)
        val snackView = snack.view
        snackView.setBackgroundColor(Color.BLACK)
        snack.show()
    }

    //funcao que valida campos null ou vazios e retorna true ou false
    fun validadeNullOrEmpty(presenca: String, totalaula: String): Boolean{
        var validacao = true
        if (presenca == null || presenca.equals("")){
            validacao = false
        } else if (totalaula == null || totalaula.equals("")) {
            validacao = false
        }
        return validacao
    }
}