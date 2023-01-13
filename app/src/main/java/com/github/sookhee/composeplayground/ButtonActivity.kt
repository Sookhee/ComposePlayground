package com.github.sookhee.composeplayground

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.sookhee.composeplayground.ui.ButtonStyle
import com.github.sookhee.composeplayground.ui.MashUpButton

class ButtonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var showLoading by remember { mutableStateOf(true) }

                Column {
                    MashUpButton(
                        modifier = Modifier.padding(16.dp),
                        buttonStyle = ButtonStyle.PRIMARY,
                        text = "다음",
                        onClick = { showLoading = !showLoading },
                        showLoading = showLoading
                    )

                    MashUpButton(
                        modifier = Modifier.padding(16.dp),
                        buttonStyle = ButtonStyle.PRIMARY,
                        text = "다음",
                        onClick = {},
                    )

                    MashUpButton(
                        modifier = Modifier.padding(16.dp),
                        buttonStyle = ButtonStyle.DEFAULT,
                        text = "다음",
                        onClick = {},
                    )

                    MashUpButton(
                        modifier = Modifier.padding(16.dp),
                        buttonStyle = ButtonStyle.PRIMARY,
                        text = "다음",
                        onClick = {},
                        isEnabled = false
                    )
                }
            }
        }
    }
}

@Composable
fun ButtonCircularProgressbar(
    size: Dp = 20.dp,
    indicatorThickness: Dp = 3.dp,
) {
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 800
            }
        )
    )

    Box(
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.size(size = size)
        ) {

            // Background circle
            drawCircle(
                color = Color(0x80FFFFFF),
                radius = size.toPx() / 2,
                style = Stroke(width = indicatorThickness.toPx())
            )

            // Foreground circle(arc)
            drawArc(
                color = Color(0xFFFFFFFF),
                startAngle = angle,
                sweepAngle = 90f,
                useCenter = false,
                style = Stroke(indicatorThickness.toPx(), cap = StrokeCap.Round)
            )
        }
    }
}

@Preview
@Composable
fun prevProgress() {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ButtonCircularProgressbar()
        }
    }
}
