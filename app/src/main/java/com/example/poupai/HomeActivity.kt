package com.example.poupai

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.poupai.network.DashboardData
import com.example.poupai.network.RetrofitClient
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {

    private lateinit var donutChart: PieChart
    private lateinit var txtTotalValor: TextView
    private lateinit var txtNomeUsuario: TextView

    private var userId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Pega o ID que veio do Login
        userId = intent.getLongExtra("USER_ID", -1)

        donutChart = findViewById(R.id.graficoDonut)
        txtTotalValor = findViewById(R.id.txt_total_valor)
        txtNomeUsuario = findViewById(R.id.txt_nome_usuario)

        configurarGrafico()

        if (userId != -1L) {
            carregarDadosDoServidor()
        } else {
            Toast.makeText(this, "Erro: Usuário não identificado.", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun configurarGrafico() {
        donutChart.isDrawHoleEnabled = true
        donutChart.holeRadius = 70f
        donutChart.transparentCircleRadius = 75f
        donutChart.setHoleColor(Color.TRANSPARENT)

        donutChart.description.isEnabled = false
        donutChart.legend.isEnabled = false // Esconde a legenda para ficar clean
        donutChart.setDrawEntryLabels(false) // Esconde os textos das fatias

        donutChart.animateY(1200)
    }

    private fun carregarDadosDoServidor() {
        RetrofitClient.instance.getDashboard(userId)
            .enqueue(object : Callback<DashboardData> {

                override fun onResponse(
                    call: Call<DashboardData>,
                    response: Response<DashboardData>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val dados = response.body()!!
                        atualizarUI(dados)
                    } else {
                        Toast.makeText(this@HomeActivity, "Erro ao buscar dados", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<DashboardData>, t: Throwable) {
                    Toast.makeText(this@HomeActivity, "Falha de rede: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
    }

    private fun atualizarUI(dados: DashboardData) {
        // 🔹 Atualiza o Nome do usuário com o que veio do BD
        txtNomeUsuario.text = dados.userName

        // 🔹 Atualiza o Valor total com o que veio do BD
        txtTotalValor.text = "R$ %.2f".format(dados.totalBalance)

        val entradas = ArrayList<PieEntry>()
        val cores = ArrayList<Int>()

        // Cores personalizadas
        val coresMap = mapOf(
            "Alimentação" to Color.parseColor("#FF7043"),
            "Transporte" to Color.parseColor("#42A5F5"),
            "Lazer" to Color.parseColor("#66BB6A"),
            "Outros" to Color.parseColor("#AB47BC")
        )

        for ((categoria, valor) in dados.chartData) {
            if (valor > 0) {
                entradas.add(PieEntry(valor.toFloat()))
                cores.add(coresMap[categoria] ?: Color.GRAY)
            }
        }

        if (entradas.isEmpty()) {
            // Se não tiver dados, limpa o gráfico
            donutChart.clear()
            donutChart.invalidate()
            return
        }

        val dataSet = PieDataSet(entradas, "")
        dataSet.colors = cores
        dataSet.sliceSpace = 3f
        dataSet.valueTextSize = 0f // Remove os números de cima das fatias

        donutChart.data = PieData(dataSet)
        donutChart.invalidate() // Atualiza a tela
    }
}