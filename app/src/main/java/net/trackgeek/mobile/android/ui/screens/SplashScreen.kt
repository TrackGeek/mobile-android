package net.trackgeek.mobile.android.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import net.trackgeek.mobile.android.R
import net.trackgeek.mobile.android.ui.theme.TrackGeekTheme
import kotlin.random.Random

@Composable
fun SplashScreen(onAnimationFinished: () -> Unit) {
    val logoDpSize = 150.dp
    val density = LocalDensity.current

    // Converter o tamanho do logo para pixéis diretamente
    val logoSizePx = with(density) { logoDpSize.toPx() }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val screenWidth = with(density) { maxWidth.toPx() }
        val screenHeight = with(density) { maxHeight.toPx() }

        var posX by remember { mutableFloatStateOf(0f) }
        var posY by remember { mutableFloatStateOf(0f) }
        var velX by remember { mutableFloatStateOf(8f) }
        var velY by remember { mutableFloatStateOf(8f) }

        LaunchedEffect(Unit) {
            delay(50)

            val maxX = (screenWidth - logoSizePx).coerceAtLeast(0f)
            val maxY = (screenHeight - logoSizePx).coerceAtLeast(0f)
            posX = Random.nextFloat() * maxX
            posY = Random.nextFloat() * maxY

            val finishTime = System.currentTimeMillis() + 4000

            while (System.currentTimeMillis() < finishTime) {
                posX += velX
                posY += velY

                if (posX <= 0f) {
                    velX = kotlin.math.abs(velX)
                    posX = 0f
                } else if (posX + logoSizePx >= screenWidth) {
                    velX = -kotlin.math.abs(velX)
                    posX = screenWidth - logoSizePx
                }

                if (posY <= 0f) {
                    velY = kotlin.math.abs(velY)
                    posY = 0f
                } else if (posY + logoSizePx >= screenHeight) {
                    velY = -kotlin.math.abs(velY)
                    posY = screenHeight - logoSizePx
                }

                delay(16) // ~60 FPS
            }

            onAnimationFinished()
        }

        Image(
            painter = painterResource(id = R.mipmap.ic_splash),
            contentDescription = "TrackGeek Logo",
            modifier = Modifier
                .size(logoDpSize)
                .offset { IntOffset(posX.toInt(), posY.toInt()) },
            contentScale = ContentScale.Fit
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    TrackGeekTheme {
        SplashScreen(onAnimationFinished = {})
    }
}