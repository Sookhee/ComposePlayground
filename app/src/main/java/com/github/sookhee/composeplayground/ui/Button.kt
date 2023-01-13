package com.github.sookhee.composeplayground.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val Brand500 = Color(0xFF6A36FF)
val Brand300 = Color(0xFFCDBFF6)
val Gray600 = Color(0xFF686F7E)
val Gray100 = Color(0xFFEBEFF9)

enum class ButtonStyle(val backgroundColorRes: Color, val textColorRes: Color) {
    PRIMARY(backgroundColorRes = Brand500, textColorRes = Color.White),
    DISABLE(backgroundColorRes = Brand300, textColorRes = Color.White),
    DEFAULT(backgroundColorRes = Gray100, textColorRes = Gray600)
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MashUpButton(
    modifier: Modifier = Modifier,
    buttonStyle: ButtonStyle = ButtonStyle.PRIMARY,
    text: String,
    onClick: () -> Unit,
    isEnabled: Boolean = true,
    showLoading: Boolean = false,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .fillMaxWidth()
            .height(52.dp)
            .background(if (isEnabled) buttonStyle.backgroundColorRes else ButtonStyle.DISABLE.backgroundColorRes)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { if (isEnabled) onClick() },
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AnimatedVisibility(
                visible = showLoading,
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                ButtonCircularProgressbar(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(20.dp)
                )
            }

            Text(
                text = text,
                style = TextStyle(
                    color = if (isEnabled) buttonStyle.textColorRes else ButtonStyle.DISABLE.textColorRes
                )
            )
        }
    }
}

@Composable
fun ButtonCircularProgressbar(
    modifier: Modifier = Modifier,
    progressBarWidth: Dp = 3.dp,
    progressBarColor: Color = Color(0xFFFFFFFF),
    backgroundProgressBarColor: Color = Color(0x80FFFFFF),
) {
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 500
            }
        )
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        val canvasSize = size.minDimension
        val radius = canvasSize / 2 - progressBarWidth.toPx() / 2

        // ProgressBar Background(Circle)
        drawCircle(
            color = backgroundProgressBarColor,
            radius = radius,
            style = Stroke(width = progressBarWidth.toPx())
        )

        // ProgressBar(Arc)
        drawArc(
            color = progressBarColor,
            startAngle = angle,
            sweepAngle = 90f,
            useCenter = false,
            topLeft = size.center - Offset(radius, radius),
            size = Size(radius * 2, radius * 2),
            style = Stroke(
                width = progressBarWidth.toPx(),
                cap = StrokeCap.Round
            )
        )
    }
}

@Preview(widthDp = 20, heightDp = 20, backgroundColor = 0xFF6A36FF, showBackground = true)
@Composable
fun PrevButtonCircularProgressbar() {
    MaterialTheme {
        ButtonCircularProgressbar()
    }
}

@Preview(widthDp = 320, backgroundColor = 0xFFF8F7FC, showBackground = true)
@Composable
fun PrevMashUpButton() {
    MaterialTheme {
        Column {
            MashUpButton(
                modifier = Modifier.padding(16.dp),
                buttonStyle = ButtonStyle.PRIMARY,
                text = "다음",
                onClick = {},
                showLoading = true
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
