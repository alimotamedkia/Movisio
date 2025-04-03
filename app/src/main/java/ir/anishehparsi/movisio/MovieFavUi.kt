package ir.anishehparsi.movisio

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.orhanobut.hawk.Hawk
import com.ramcosta.composedestinations.annotation.Destination
import ir.anishehparsi.movisio.Model.Result

@Destination
@Composable
fun MovieFavUi(modifier: Modifier = Modifier) {
    var movieFavList by remember { mutableStateOf(Hawk.get("movieFavList", emptyList<Result>())) }

    LaunchedEffect(key1 = Unit) {
        movieFavList = Hawk.get("movieFavList", emptyList())
    }
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        items(movieFavList ?: emptyList()) { item ->
            SaveMovieList(
                item = item,
                onDeletedCLicked = {
                    val updatedList = movieFavList.toMutableList()
                    updatedList.remove(item)
                    Hawk.put("movieFavList", updatedList)
                    movieFavList = updatedList
                })

        }
    }
}

@Composable
fun SaveMovieList(
    item: Result,
    onDeletedCLicked: () -> Unit,
) {
    val context = LocalContext.current

    OutlinedCard(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(24.dp)
        ) {
            Text(
                text = item.title,
            )
            Spacer(modifier=Modifier.weight(1f))
            Image(
                modifier = Modifier
                    .clickable {
                        onDeletedCLicked()
                        Toast.makeText(context, "Favorite Canceled ❌", Toast.LENGTH_SHORT).show()
                    },
                painter = painterResource(R.drawable.favorite),
                contentDescription = "Remove from favorites"
            )
        }

    }
    Spacer(modifier=Modifier.height(4.dp))
}