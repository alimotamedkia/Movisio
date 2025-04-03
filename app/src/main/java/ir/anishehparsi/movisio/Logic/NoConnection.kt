package ir.anishehparsi.movisio.Logic

import androidx.compose.runtime.Composable
import com.dotlottie.dlplayer.Mode
import com.lottiefiles.dotlottie.core.compose.ui.DotLottieAnimation
import com.lottiefiles.dotlottie.core.util.DotLottieSource

@Composable
fun NoConnection() {
    DotLottieAnimation(
        source = DotLottieSource.Url("https://lottie.host/83ee7aef-02de-4674-b2e0-21dec8105261/HpfveYVVRy.lottie"),
// source = DotLottieSource.Asset("file.json"),
        autoplay = true,
        loop = true,
        speed = 2f,
        useFrameInterpolation = false,
        playMode = Mode.FORWARD
    )

}