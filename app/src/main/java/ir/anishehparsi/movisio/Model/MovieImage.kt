package ir.anishehparsi.movisio.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class MovieImage(
    val change_keys: List<String>,
    val images: Images,
) : Parcelable