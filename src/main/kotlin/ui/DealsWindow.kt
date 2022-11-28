package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
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
            DealItem()
            DealItem()
        }
    }

    @Composable
    @Preview
    fun DealItem(){
        Scaffold {
            Text(if (dealsList.size>0) "${dealsList[0].description}!" else "nincs adat")
            var sz = remember{"Button"}
            Button(onClick = {
                sz = "Pressed!"
            }){
                Text(sz)
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