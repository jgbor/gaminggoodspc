package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.DealData
import network.NetworkManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DealsWindow {
    private var platform : String? = null
    private var type : String? = null
    private var sortBy : String? = null

    private var dealsList : MutableList<DealData> = mutableStateListOf()

    @Composable
    @Preview
    fun show() {
        loadDealData()
        MaterialTheme {
            Column(Modifier.fillMaxSize(), Arrangement.spacedBy(5.dp)) {
                Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                    }) {
                    Text(if (dealsList.size>0) "${dealsList[0].description}!" else "nincs adat")
                }
                Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {

                    }) {
                    Text("${dealsList.size}")
                }
            }
        }
    }

    private fun loadDealData() {
        NetworkManager.getDeals(platform,sortBy,type)?.enqueue(object : Callback<Array<DealData?>?> {
            override fun onResponse(
                call: Call<Array<DealData?>?>,
                response: Response<Array<DealData?>?>
            ) {
                if (response.isSuccessful) {
                    displayDealsData(response.body())
                } else {
                    println("Válasz jött, de valami nem jó!")
                }
            }

            override fun onFailure(
                call: Call<Array<DealData?>?>,
                throwable: Throwable
            ) {
                throwable.printStackTrace()
                type = null
                sortBy = null
                println("Hibára futunk")
            }
        })
    }

    private fun displayDealsData(receivedDealsData: Array<DealData?>?){
        for (dealData in receivedDealsData!!) {
            dealsList.add(dealData!!)
        }
    }
}