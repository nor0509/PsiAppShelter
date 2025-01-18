import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter

@Composable
fun ImageLoader(profileImageUrl: String, modifier: Modifier = Modifier) {
    val painter = rememberAsyncImagePainter(
        model = coil.request.ImageRequest.Builder(LocalContext.current)
            .data(profileImageUrl)
            .crossfade(true)
            .build()
    )

    Image(
        painter = painter,
        contentDescription = null,
        modifier = modifier.fillMaxSize()
    )
}