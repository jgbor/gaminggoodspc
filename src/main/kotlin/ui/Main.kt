package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
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
                DealsWindow(title = "All deals") {
                    toNextPage = false
                }
                windowState.size = DpSize.Unspecified
                windowState.position = WindowPosition(Alignment.Center)
            } else {
                Column(
                    Modifier.wrapContentSize()
                ) {
                    Button(
                        onClick = {
                            toNextPage = true
                        },
                        Modifier.width(100.dp).height(50.dp)
                    ) {
                        Text(
                            text = "Next"
                        )
                    }
                }
                windowState.size = DpSize.Unspecified
                windowState.position = WindowPosition(Alignment.Center)
            }
        }
    }
}
