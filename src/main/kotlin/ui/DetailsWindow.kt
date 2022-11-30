package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        Text(
            deal!!.title,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
                .padding(top = 12.dp),
            fontSize = 38.sp
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                load = { loadImageBitmap(deal.image) },
                painterFor = { remember { BitmapPainter(it) } },
                contentDescription = "image",
                modifier = Modifier
                    .padding(20.dp)
                    .clip(RoundedCornerShape(10)),
                contentScale = ContentScale.FillWidth,
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(start = 16.dp)
        ) {
            Text(
                "Platform: ${deal.platforms}",
            )
            Text(
                "Ends: ${if (deal.end_date == "N/A") "Unknown" else deal.end_date}",
            )
        }
        Box(
            Modifier.fillMaxWidth()
                .padding(16.dp)
                .weight(weight = 1f, fill = false)
        ) {
            val state = rememberScrollState(0)

            Text(
                deal.description,
                modifier = Modifier.fillMaxWidth()
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
                Desktop.getDesktop().browse(URI(deal.open_giveaway_url))
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