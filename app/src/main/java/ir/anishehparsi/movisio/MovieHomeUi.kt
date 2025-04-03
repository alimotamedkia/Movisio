package ir.anishehparsi.movisio

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import ir.anishehparsi.movisio.Logic.LoadingAnimation
import ir.anishehparsi.movisio.Model.Images
import ir.anishehparsi.movisio.Model.Result
import ir.anishehparsi.movisio.Service.getMovieData
import ir.anishehparsi.movisio.Service.getMovieImage
import ir.anishehparsi.movisio.destinations.MovieDetailUiDestination

@Destination(start = true)
@Composable
fun MovieHomeUi(modifier: Modifier = Modifier, navigator: DestinationsNavigator) {
    var movieListState by remember { mutableStateOf<List<Result>?>(null) }
    var movieImageState by remember { mutableStateOf<Images?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        try {
            movieListState = getMovieData()
            movieImageState = getMovieImage()
            isLoading = false
        } catch (e: Exception) {
            isLoading = false
            isError = true
        }
    }

    if (isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoadingAnimation()
        }
    } else if (isError) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(128.dp),
                painter = painterResource(R.drawable.signal_wifi_connected_no_internet),
                contentDescription = ""
            )
            Text(text = "Internet not connected")
        }
    } else {
        Column(modifier = Modifier.fillMaxSize()) {
            OutlinedTextField(
                label = { Text("Search...") },
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp)
            )

            val filteredMovies = movieListState?.filter {
                it.title.contains(searchText, ignoreCase = true)
            } ?: emptyList()

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top
            ) {
                items(filteredMovies) { movie ->
                    movieImageState?.let { MovieItemUi(item = movie, images = it, navigator = navigator) }
                }
            }
        }
    }
}

@Composable
fun MovieItemUi(modifier: Modifier = Modifier, item: Result, images: Images, navigator: DestinationsNavigator) {
    Card(modifier = Modifier.clickable { navigator.navigate(MovieDetailUiDestination(item, images)) }) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 6.dp),
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(128.dp, 96.dp),
                model = images.secure_base_url + images.poster_sizes[0] + item.poster_path,
                contentDescription = "Poster of the movie ${item.title}",
                placeholder = painterResource(R.drawable.movisio_nbg),
                error = painterResource(R.drawable.movisio_nbg)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Column(verticalArrangement = Arrangement.Center) {
                Text(text = item.title)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = item.original_language)
                Spacer(modifier = Modifier.weight(1f))

                Row {
                    Image(
                        painter = painterResource(R.drawable.star),
                        contentDescription = "Star icon representing movie rating"
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(text = item.vote_average.toString())
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(6.dp))
}