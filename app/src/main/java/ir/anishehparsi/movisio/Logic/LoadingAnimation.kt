package ir.anishehparsi.movisio.Logic

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dotlottie.dlplayer.Mode
import com.lottiefiles.dotlottie.core.compose.ui.DotLottieAnimation
import com.lottiefiles.dotlottie.core.util.DotLottieSource

@Composable
fun LoadingAnimation(modifier: Modifier = Modifier) {


    DotLottieAnimation(
        source = DotLottieSource.Url("https://lottie.host/b1ccd4c3-bd78-4a2c-8d45-ee101a662d50/brns7Negca.lottie"),
// source = DotLottieSource.Asset("file.json"),
        autoplay = true,
        loop = true,
        speed = 2f,
        useFrameInterpolation = false,
        playMode = Mode.FORWARD
    )
}