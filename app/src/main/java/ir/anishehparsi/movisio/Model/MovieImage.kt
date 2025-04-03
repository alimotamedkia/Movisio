package ir.anishehparsi.movisio.Model

import kotlinx.serialization.Serializable

@Serializable
data class MovieImage(
    val change_keys: List<String>,
    val images: Images,
)