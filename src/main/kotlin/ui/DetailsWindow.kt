package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import model.DealData


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
        Text(
            deal.description,
            modifier = Modifier.fillMaxWidth()
                .padding(32.dp)
        )
    }
}