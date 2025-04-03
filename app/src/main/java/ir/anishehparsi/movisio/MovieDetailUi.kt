package ir.anishehparsi.movisio

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.orhanobut.hawk.Hawk
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import ir.anishehparsi.movisio.Model.Images
import ir.anishehparsi.movisio.Model.Result


@Destination
@Composable
fun MovieDetailUi(
    modifier: Modifier = Modifier,
    item: Result,
    images: Images
) {
val context = LocalContext.current
    var isFave by rememberSaveable { mutableStateOf(isMovieFavorite(item)) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = images.secure_base_url + images.poster_sizes[3] + item.poster_path,
            contentDescription = "Poster of the movie ${item.title}",
            placeholder = painterResource(R.drawable.movisio_nbg),
            error = painterResource(R.drawable.movisio_nbg)
        )

        Text(
            text = item.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier.height(16.dp))
        Row(
            modifier = Modifier
                .padding(horizontal = 32.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.star),
                contentDescription = "Star icon representing movie rating"
            )
            Spacer(Modifier.width(4.dp))
            Text(text = item.vote_average.toString())
            Spacer(modifier.weight(1f))
            Text(text = item.original_language)
            Spacer(modifier.weight(1f))
            Image(
                modifier = Modifier
                    .clickable {
                        val dbList: List<Result>? = Hawk.get("movieFavList", emptyList())
                        val finalList = dbList?.toMutableList()

                        if (isFave) {
                            finalList?.remove(item)
                            Toast.makeText(context, "Movie removed ❌", Toast.LENGTH_SHORT).show()
                        } else {
                            finalList?.add(item)
                            Toast.makeText(context, "Movie saved 👍", Toast.LENGTH_SHORT).show()
                        }

                        Hawk.put("movieFavList", finalList)
                        isFave = !isFave
                    },
                painter = painterResource(if (isFave) R.drawable.favorite else R.drawable.favorite_border),
                contentDescription = "Favorite Icon"
            )
        }
        Spacer(modifier.height(32.dp))
        Text(text = item.overview)
        Spacer(modifier.weight(1f))
    }
}

fun isMovieFavorite(item: Result): Boolean {
    val dbList: List<Result> = Hawk.get("movieFavList", emptyList())
    return dbList.contains(item)
}