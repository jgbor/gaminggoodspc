package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import model.DealData
import network.NetworkManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.net.URL

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
            Box(
                modifier = Modifier.fillMaxSize()
                    .background(color = Color(180, 180, 180))
                    .padding(8.dp)
            ) {

                val state = rememberLazyListState()
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
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

    @Composable
    @Preview
    private fun DealItem(dealData: DealData){
        Row{
            AsyncImage(
                load = { loadImageBitmap(dealData.thumbnail) },
                painterFor = { remember { BitmapPainter(it) } },
                contentDescription = "Sample",
                modifier = Modifier.width(200.dp)
            )
            Text(dealData.description)
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

    @Composable
    private fun <T> AsyncImage(
        load: suspend () -> T,
        painterFor: @Composable (T) -> Painter,
        contentDescription: String,
        modifier: Modifier = Modifier,
        contentScale: ContentScale = ContentScale.Fit,
    ) {
        val image: T? by produceState<T?>(null) {
            value = withContext(Dispatchers.IO) {
                try {
                    load()
                } catch (e: IOException) {
                    // instead of printing to console, you can also write this to log,
                    // or show some error placeholder
                    e.printStackTrace()
                    null
                }
            }
        }

        if (image != null) {
            Image(
                painter = painterFor(image!!),
                contentDescription = contentDescription,
                contentScale = contentScale,
                modifier = modifier
            )
        }
    }

    private fun loadImageBitmap(url: String): ImageBitmap =
        URL(url).openStream().buffered().use(::loadImageBitmap)
}