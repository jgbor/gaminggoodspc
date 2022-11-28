// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import ui.DealsWindow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState


fun main() = application {
    val title = "Gaming Goods"
    Window(onCloseRequest = ::exitApplication,
            title = title,
        state = rememberWindowState(width = 300.dp, height = 300.dp)
    ) {
        DealsWindow().show()
    }
}
