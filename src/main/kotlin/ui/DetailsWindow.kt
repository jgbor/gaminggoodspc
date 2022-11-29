package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import model.DealData


@Composable
@Preview
fun DetailsWindow(deal : DealData?, onBackClick: () -> Unit){
    TopAppBar(
        title = { Text("Item details") },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null
                )
            }
        }
    )
    /*AsyncImage(
        load = { loadImageBitmap(deal.image) },
        painterFor = { remember { BitmapPainter(it) } },
        contentDescription = "image",
        modifier = Modifier.width(400.dp)
    )*/
}