package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import model.DealData
import java.awt.Desktop
import java.net.URI


@Composable
@Preview
fun DetailsWindow(deal : DealData?, onBackClick: () -> Unit){
    Column {
        TopAppBar(
            title = { Text("Deal #${deal!!.id}") },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        )
        AsyncImage(
            load = { loadImageBitmap(deal!!.image) },
            painterFor = { remember { BitmapPainter(it) } },
            contentDescription = "image",
            modifier = Modifier.fillMaxWidth().padding(24.dp)
        )
        Text(
            deal!!.title,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            deal.platforms,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Box(
            Modifier.fillMaxWidth()
                .padding(8.dp)
                .weight(1f, fill = false)
        ) {
            val state = rememberScrollState(0)

            Text(
                deal.description,
                modifier = Modifier.fillMaxWidth()
                    .padding(32.dp)
                    .verticalScroll(state)
            )
            VerticalScrollbar(
                modifier = Modifier.align(Alignment.CenterEnd),
                adapter = rememberScrollbarAdapter(
                    scrollState = state
                )
            )
        }
        Button(
            onClick = {
                Desktop.getDesktop().browse(URI(deal.open_giveaway_url));
            },
            Modifier.align(Alignment.CenterHorizontally)
                .fillMaxWidth()
        ){
            Text(
                text = "Get content!"
            )
        }
    }
}