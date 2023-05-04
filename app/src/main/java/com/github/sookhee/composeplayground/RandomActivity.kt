package com.github.sookhee.composeplayground

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.github.sookhee.composeplayground.ui.theme.ComposePlaygroundTheme
import kotlinx.coroutines.delay
import kotlin.random.Random
import androidx.compose.runtime.LaunchedEffect as LaunchedEffect1

class RandomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var count by remember { mutableStateOf(0) }

            ComposePlaygroundTheme {
                Surface {
                    Button(onClick = {
                        count++
                    }) {
                        Text(text = "click")
                    }

                    Box(modifier = Modifier.fillMaxSize()) {
                        repeat(count) {
                            ShakeEffect()
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ShakeEffect() {
    var isVisible by remember { mutableStateOf(true) }
    val randomX by remember { mutableStateOf(Random.nextInt(200)) }
    val randomY by remember { mutableStateOf(Random.nextInt(200)) }

    val fadeOutYPosition by animateDpAsState(
        targetValue = if (isVisible) randomY.dp else (randomY - 20).dp,
        animationSpec = tween(
            durationMillis = 500,
            easing = LinearOutSlowInEasing
        )
    )

    androidx.compose.runtime.LaunchedEffect(key1 = null) {
        delay(1)
        isVisible = false
    }


    Box(modifier = Modifier.offset(x = randomX.dp, y = fadeOutYPosition)) {
        AnimatedVisibility(
            visible = isVisible,
            exit = fadeOut(
                targetAlpha = 0.0f,
                animationSpec = tween(
                    durationMillis = 500
                )
            ),
        ) {
            Image(
                painterResource(id = R.drawable.img_fever_danggn),
                contentDescription = null,
                modifier = Modifier.size(100.dp),
            )
        }
    }
}