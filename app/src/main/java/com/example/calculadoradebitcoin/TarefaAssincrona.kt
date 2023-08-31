package com.example.calculadoradebitcoin

import android.os.AsyncTask
import java.net.URL

class TarefaAssincrona(private val callback: (String) -> Unit) : AsyncTask<Unit, Unit, String>() {

    override fun doInBackground(vararg p0: Unit): String {

        return try {
            val resposta = URL(API_URL).readText()
            resposta

        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    override fun onPostExecute(result: String) {
        super.onPostExecute(result)

        callback(result)
    }

    companion object {
        private const val API_URL = "https://www.mercadobitcoin.net/api/BTC/ticker/"
    }
}
