// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.DealsWindow

var backgroundColor = Color(180, 160, 180)
fun main() = application {
    val title = "Gaming Goods"
    var toNextPage by remember { mutableStateOf(false) }
    Window(
        onCloseRequest = ::exitApplication,
        title = title,
        icon = MyAppIcon,
    ) {
        Box(
            Modifier.fillMaxSize()
                .background(color = backgroundColor)
                .padding(8.dp)
        ) {
            if (toNextPage) {
                DealsWindow(title = "All deals") {
                    toNextPage = false
                }
            } else {
                Column(
                    Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            toNextPage = true
                        },
                        Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "Next"
                        )
                    }
                }
            }
        }
    }
}

object MyAppIcon : Painter() {
    override val intrinsicSize = Size(256f, 256f)

    override fun DrawScope.onDraw() {
        drawOval(Color.Green, Offset(size.width / 4, 0f), Size(size.width / 2f, size.height))
        drawOval(Color.Blue, Offset(0f, size.height / 4), Size(size.width, size.height / 2f))
        drawOval(Color.Red, Offset(size.width / 4, size.height / 4), Size(size.width / 2f, size.height / 2f))
    }
}
