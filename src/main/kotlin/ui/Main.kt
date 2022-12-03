package ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState


var backgroundColor = Color(220, 200, 220)
var mainColor = Color(204,21,14)

fun main() = application {
    val title = "Gaming Goods"
    val icon = painterResource("ic_launcher_round.png")
    val windowState = rememberWindowState(size = DpSize.Unspecified, position = WindowPosition(Alignment.Center))
    var toNextPage by remember { mutableStateOf(false) }

    var platform : String? = null

    val height = 500.dp

    Window(
        onCloseRequest = ::exitApplication,
        title = title,
        icon = icon,
        state = windowState,
        resizable = false,
    ) {
        Box(
            Modifier.wrapContentSize()
                .background(color = backgroundColor)
                .padding(8.dp)
        ) {
            if (toNextPage) {
                windowState.size = DpSize.Unspecified
                windowState.position = WindowPosition(Alignment.Center)
                DealsWindow(platform = platform, title = "All deals") {
                    toNextPage = false
                }
            } else {
                windowState.size = DpSize.Unspecified
                windowState.position = WindowPosition(Alignment.Center)

                val state = rememberLazyListState()

                Box(
                    Modifier.width(600.dp)
                        .height(height)
                        .padding(8.dp)
                ) {

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        state = state
                    ) {
                        item {
                            Box(
                                Modifier.clickable {
                                    toNextPage = true
                                    platform = null
                                }
                            ) {
                                Image(
                                    painter = painterResource("all.9.png"),
                                    contentDescription = "All",
                                    modifier = Modifier.fillMaxWidth().align(Alignment.Center),
                                    contentScale = ContentScale.FillWidth
                                )
                                Text(
                                    text = "All",
                                    color = Color.Black,
                                    modifier = Modifier.fillMaxWidth().align(Alignment.Center),
                                    fontSize = 42.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }

                        item {
                            Box(
                                Modifier.clickable {
                                    toNextPage = true
                                    platform = "pc"
                                }
                            ) {
                                Image(
                                    painter = painterResource("pc.9.png"),
                                    contentDescription = "PC",
                                    modifier = Modifier.fillMaxWidth().align(Alignment.Center),
                                    contentScale = ContentScale.FillWidth
                                )
                                Text(
                                    text = "PC",
                                    color = Color.White,
                                    modifier = Modifier.fillMaxWidth().align(Alignment.Center),
                                    fontSize = 42.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }

                        item {
                            Box(
                                Modifier.clickable {
                                    toNextPage = true
                                    platform = "ps4"
                                }
                            ) {
                                Image(
                                    painter = painterResource("ps4.9.png"),
                                    contentDescription = "PS4",
                                    modifier = Modifier.fillMaxWidth().align(Alignment.Center),
                                    contentScale = ContentScale.FillWidth
                                )
                                Text(
                                    text = "PS4",
                                    color = Color.White,
                                    modifier = Modifier.fillMaxWidth().align(Alignment.Center),
                                    fontSize = 42.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }

                        item {
                            Box(
                                Modifier.clickable {
                                    toNextPage = true
                                    platform = "ps5"
                                }
                            ) {
                                Image(
                                    painter = painterResource("ps5.9.png"),
                                    contentDescription = "PS5",
                                    modifier = Modifier.fillMaxWidth().align(Alignment.Center),
                                    contentScale = ContentScale.FillWidth
                                )
                                Text(
                                    text = "PS5",
                                    color = Color.Black,
                                    modifier = Modifier.fillMaxWidth().align(Alignment.Center),
                                    fontSize = 42.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }

                        item {
                            Box(
                                Modifier.clickable {
                                    toNextPage = true
                                    platform = "xbox-one"
                                }
                            ) {
                                Image(
                                    painter = painterResource("xboxone.9.png"),
                                    contentDescription = "Xbox1",
                                    modifier = Modifier.fillMaxWidth().align(Alignment.Center),
                                    contentScale = ContentScale.FillWidth
                                )
                                Text(
                                    text = "Xbox One",
                                    color = Color.Black,
                                    modifier = Modifier.fillMaxWidth().align(Alignment.Center),
                                    fontSize = 42.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }

                        item {
                            Box(
                                Modifier.clickable {
                                    toNextPage = true
                                    platform = "xbox-series-xs"
                                }
                            ) {
                                Image(
                                    painter = painterResource("xboxseriesx.9.png"),
                                    contentDescription = "XboxSeriesX",
                                    modifier = Modifier.fillMaxWidth().align(Alignment.Center),
                                    contentScale = ContentScale.FillWidth
                                )
                                Text(
                                    text = "Xbox Series X",
                                    color = Color.White,
                                    modifier = Modifier.fillMaxWidth().align(Alignment.Center),
                                    fontSize = 42.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }

                        item {
                            Box(
                                Modifier.clickable {
                                    toNextPage = true
                                    platform = "switch"
                                }
                            ) {
                                Image(
                                    painter = painterResource("nintendoswitch.9.png"),
                                    contentDescription = "Switch",
                                    modifier = Modifier.fillMaxWidth().align(Alignment.Center),
                                    contentScale = ContentScale.FillWidth
                                )
                                Text(
                                    text = "Switch",
                                    color = Color.White,
                                    modifier = Modifier.fillMaxWidth().align(Alignment.Center),
                                    fontSize = 42.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }

                        item {
                            Box(
                                Modifier.clickable {
                                    toNextPage = true
                                    platform = "vr"
                                }
                            ) {
                                Image(
                                    painter = painterResource("vr.9.png"),
                                    contentDescription = "VR",
                                    modifier = Modifier.fillMaxWidth().align(Alignment.Center),
                                    contentScale = ContentScale.FillWidth
                                )
                                Text(
                                    text = "VR",
                                    color = Color.White,
                                    modifier = Modifier.fillMaxWidth().align(Alignment.Center),
                                    fontSize = 42.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }

                        item {
                            Box(
                                Modifier.clickable {
                                    toNextPage = true
                                    platform = "android"
                                }
                            ) {
                                Image(
                                    painter = painterResource("android.9.png"),
                                    contentDescription = "Android",
                                    modifier = Modifier.fillMaxWidth().align(Alignment.Center),
                                    contentScale = ContentScale.FillWidth
                                )
                                Text(
                                    text = "Android",
                                    color = Color.Black,
                                    modifier = Modifier.fillMaxWidth().align(Alignment.Center),
                                    fontSize = 42.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }

                        item {
                            Box(
                                Modifier.clickable {
                                    toNextPage = true
                                    platform = "ios"
                                }
                            ) {
                                Image(
                                    painter = painterResource("ios.9.png"),
                                    contentDescription = "iOS",
                                    modifier = Modifier.fillMaxWidth().align(Alignment.Center),
                                    contentScale = ContentScale.FillWidth
                                )
                                Text(
                                    text = "iOS",
                                    color = Color.Black,
                                    modifier = Modifier.fillMaxWidth().align(Alignment.Center),
                                    fontSize = 42.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
                VerticalScrollbar(
                    modifier = Modifier.align(Alignment.CenterEnd).height(height),
                    adapter = rememberScrollbarAdapter(
                        scrollState = state
                    )
                )
            }
        }
    }
}
