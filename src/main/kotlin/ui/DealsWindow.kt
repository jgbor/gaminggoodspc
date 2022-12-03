package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    var showMenu by remember { mutableStateOf(false) }
    var showTypeMenu by remember { mutableStateOf(false) }
    var showSortbyMenu by remember { mutableStateOf(false) }
    var noData by remember { mutableStateOf(false)}

    @Composable
    @Preview
    fun DealItem(deal: DealData) {
        Row(
            Modifier.clickable {
                newWindow = true
                dealData = deal
            }.fillMaxWidth(1f)
        ) {
            AsyncImage(
                load = { loadImageBitmap(deal.thumbnail) },
                painterFor = { remember { BitmapPainter(it) } },
                contentDescription = "Thumbnail",
                modifier = Modifier.width(200.dp)
                    .clip(RoundedCornerShape(10))
            )
            Column(
                Modifier.padding(start = 12.dp),
            ) {
                Text(
                    deal.title,
                    fontSize = 24.sp
                )
                Text(
                    deal.platforms,
                    fontSize = 12.sp
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
            Column{
                TopAppBar(
                    title = {
                        Text(title)
                     },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = null
                            )
                        }
                    },
                    modifier = Modifier.width(1000.dp),
                    backgroundColor = mainColor,
                    actions = {
                        Box{
                            IconButton(onClick = { showMenu = !showMenu }) {
                                Icon(Icons.Default.ArrowDropDown, "")
                            }
                            DropdownMenu(
                                expanded = showMenu,
                                onDismissRequest = { showMenu = !showMenu },
                            ) {
                                DropdownMenu(
                                    expanded = showSortbyMenu,
                                    onDismissRequest = { showSortbyMenu = !showSortbyMenu },
                                    offset = DpOffset((-110).dp, (-150).dp),
                                ) {
                                    DropdownMenuItem(
                                        onClick = {
                                            sortBy = "value"
                                            showMenu = false
                                            showSortbyMenu = false
                                            loadDealData()
                                        }
                                    ) {
                                        Text(
                                            text = "Value"
                                        )
                                    }
                                    DropdownMenuItem(
                                        onClick = {
                                            sortBy = "date"
                                            showMenu = false
                                            showSortbyMenu = false
                                            loadDealData()
                                        }
                                    ) {
                                        Text(
                                            text = "Date"
                                        )
                                    }
                                    DropdownMenuItem(
                                        onClick = {
                                            sortBy = "popularity"
                                            showMenu = false
                                            showSortbyMenu = false
                                            loadDealData()
                                        }
                                    ) {
                                        Text(
                                            text = "Popularity"
                                        )
                                    }
                                }
                                DropdownMenu(
                                    expanded = showTypeMenu,
                                    onDismissRequest = { showTypeMenu = !showTypeMenu },
                                    offset = DpOffset((-110).dp, (-100).dp),
                                ) {
                                    DropdownMenuItem(
                                        onClick = {
                                            type = "game"
                                            showMenu = false
                                            showTypeMenu = false
                                            loadDealData()
                                        }
                                    ) {
                                        Text(
                                            text = "Game"
                                        )
                                    }
                                    DropdownMenuItem(
                                        onClick = {
                                            type = "loot"
                                            showMenu = false
                                            showTypeMenu = false
                                            loadDealData()
                                        }
                                    ) {
                                        Text(
                                            text = "Loot"
                                        )
                                    }
                                }
                                DropdownMenuItem(
                                    onClick = {
                                        showSortbyMenu = true
                                    }
                                ) {
                                    Text(
                                        text = "Sort by"
                                    )
                                }
                                DropdownMenuItem(
                                    onClick = {
                                        showTypeMenu = true
                                    }
                                ) {
                                    Text(
                                        text = "Type"
                                    )
                                }
                                DropdownMenuItem(
                                    onClick = {
                                        sortBy = null
                                        type = null
                                        showMenu = false
                                        loadDealData()
                                    }
                                ) {
                                    Text(
                                        text = "Reset"
                                    )
                                }
                            }
                        }
                    }
                )
                Box(
                    Modifier.width(1000.dp)
                        .height(600.dp)
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