package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import model.DealData
import network.NetworkManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
@Preview
fun DealsWindow(platform: String? = null) {
    var type: String? = null
    var sortBy: String? = null

    var dealsList: MutableList<DealData> = mutableStateListOf()

    var newWindow by remember { mutableStateOf(false) }
    var dealData: DealData? by remember { mutableStateOf(null) }

    @Composable
    @Preview
    fun DealItem(deal: DealData) {
        Row(
            Modifier.clickable {
                newWindow = true
                dealData = deal
            }
        ) {
            AsyncImage(
                load = { loadImageBitmap(deal.thumbnail) },
                painterFor = { remember { BitmapPainter(it) } },
                contentDescription = "Thumbnail",
                modifier = Modifier.width(200.dp)
            )
            Text(
                deal.title,
                Modifier.padding(start = 12.dp),
                textAlign = TextAlign.Center,
                //fontSize =
            )
        }
    }

    fun displayDealsData(receivedDealsData: Array<DealData?>?) {
        for (deal in receivedDealsData!!) {
            dealsList.add(deal!!)
        }
    }

    fun loadDealData() {
        NetworkManager.getDeals(platform, sortBy, type)?.enqueue(object : Callback<Array<DealData?>?> {
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

    loadDealData()
    MaterialTheme {
        Box(
            modifier = Modifier.fillMaxSize()
                .background(color = Color(180, 160, 180))
                .padding(8.dp)
        ) {

            val state = rememberLazyListState()
            if (newWindow) {
                DetailsWindow(dealData) { newWindow = false }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    state = state
                ) {
                    items(dealsList) { deal ->
                        DealItem(deal)
                    }
                }
                VerticalScrollbar(
                    modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
                    adapter = rememberScrollbarAdapter(
                        scrollState = state
                    )
                )
            }
        }
    }
}