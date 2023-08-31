package com.example.calculadoradebitcoin

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.app.AlertDialog
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import org.json.JSONObject
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    var	cotacaoBitcoin:Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buscarCotacao()

        findViewById<Button>(R.id.btn_calcular).setOnClickListener	{
            calcular()
        }

    }

    private fun buscarCotacao() {
        val tarefaAssincrona = TarefaAssincrona { resposta ->
            runOnUiThread {
                if (resposta.isNotEmpty())
                    try {
                        val cotacaoBitcoin =
                            JSONObject(resposta).getJSONObject("ticker").getDouble("last")
                        val f = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
                        val cotacaoFormatada = f.format(cotacaoBitcoin)
                        findViewById<TextView>(R.id.txt_cotacao).setText("$cotacaoFormatada")

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
            }
        }

        tarefaAssincrona.execute()
    }

    private fun exibirAlerta(mensagem: String, context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Resposta da API")
        builder.setMessage(mensagem)
        builder.setPositiveButton("OK") { _, _ -> }
        val alert = builder.create()
        alert.show()
    }

    @SuppressLint("SetTextI18n")
    fun	calcular(){

        if(findViewById<TextView>(R.id.txt_valor).text.isEmpty())	{
            findViewById<TextView>(R.id.txt_valor).error = "Preencha um valor"
            return
        }

        val	valor_digitado = findViewById<EditText>(R.id.txt_valor).text.toString()
            .replace(",",	".")
            .toDouble()

        val	resultado = if(cotacaoBitcoin > 0) valor_digitado / cotacaoBitcoin else 0.0

        findViewById<TextView>(R.id.txt_qtd_bitcoins).text = "%.8f".format(resultado)
    }
}




