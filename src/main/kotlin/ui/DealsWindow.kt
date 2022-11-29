package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.unit.dp
import model.DealData
import network.NetworkManager
import org.jetbrains.skia.impl.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
@Preview
fun DealsWindow(platform: String? = null, title : String, onBackClick: () -> Unit) {
    var type: String? = null
    var sortBy: String? = null

    val dealsList: MutableList<DealData> = mutableStateListOf()

    var newWindow by remember { mutableStateOf(false) }
    var dealData: DealData? by remember { mutableStateOf(null) }

    @Composable
    @Preview
    fun DealItem(deal: DealData) {
        Row(
            Modifier.clickable {
                newWindow = true
                dealData = deal
            }.fillMaxWidth()
        ) {
            AsyncImage(
                load = { loadImageBitmap(deal.thumbnail) },
                painterFor = { remember { BitmapPainter(it) } },
                contentDescription = "Thumbnail",
                modifier = Modifier.width(200.dp)
            )
            Column(
                Modifier.padding(start = 12.dp),
            ) {
                Text(
                    deal.title,
                    //fontSize =
                )
                Text(
                    deal.platforms
                )
            }
        }
    }

    fun displayDealsData(receivedDealsData: Array<DealData?>?) {
        for (deal in receivedDealsData!!) {
            dealsList.add(deal!!)
        }
    }

    fun loadDealData() {
        dealsList.clear()
        NetworkManager.getDeals(platform, sortBy, type)?.enqueue(object : Callback<Array<DealData?>?> {
            override fun onResponse(
                call: Call<Array<DealData?>?>,
                response: Response<Array<DealData?>?>
            ) {
                if (response.isSuccessful) {
                    displayDealsData(response.body())
                } else {
                    Log.error("Válasz jött, de valami nem jó!")
                }
            }

            override fun onFailure(
                call: Call<Array<DealData?>?>,
                throwable: Throwable
            ) {
                throwable.printStackTrace()
                type = null
                sortBy = null
                Log.error("Hibára futunk")
            }
        })
    }

    MaterialTheme {
        if (newWindow) {
            DetailsWindow(dealData) { newWindow = false }
        } else {
            loadDealData()
            Column {
                TopAppBar(
                    title = { Text(title) },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = null
                            )
                        }
                    }
                )
                Box(
                    Modifier.fillMaxSize()
                        .padding(8.dp)
                ) {

                    val state = rememberLazyListState()

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
}