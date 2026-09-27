package com.gillopa.newyear

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gillopa.newyear.ui.theme.NewYearTheme
import com.gillopa.newyear.widget.NewYearWidgetUpdater
import kotlin.math.sin
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        NewYearWidgetUpdater.updateAll(this)
        setContent {
            NewYearTheme {
                CountdownScreen()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        NewYearWidgetUpdater.updateAll(this)
    }
}

@Composable
fun CountdownScreen() {
    var days by remember { mutableStateOf(CountdownCalculator.daysUntilNewYear()) }
    var year by remember { mutableStateOf(CountdownCalculator.targetYear()) }
    var isToday by remember { mutableStateOf(CountdownCalculator.isNewYearsDay()) }

    LaunchedEffect(Unit) {
        days = CountdownCalculator.daysUntilNewYear()
        year = CountdownCalculator.targetYear()
        isToday = CountdownCalculator.isNewYearsDay()
    }

    val vibe = remember(days, isToday) {
        if (isToday) "С Новым годом!"
        else CountdownCalculator.vibeLine(days)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0B1220),
                        Color(0xFF152238),
                        Color(0xFF1A2F1F),
                        Color(0xFF2A1A12)
                    )
                )
            )
    ) {
        Snowfall(modifier = Modifier.fillMaxSize())
        WarmGlow(modifier = Modifier.fillMaxSize())

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 28.dp, vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "До Нового года",
                style = TextStyle(
                    color = Color(0xFFE8D5A3),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 2.sp,
                    fontFamily = FontFamily.Serif,
                    shadow = Shadow(
                        color = Color(0x66FFD27A),
                        blurRadius = 18f
                    )
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.weight(1f))

            if (isToday) {
                Text(
                    text = "С Новым\nгодом!",
                    style = TextStyle(
                        color = Color(0xFFFFF1C9),
                        fontSize = 56.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        lineHeight = 62.sp,
                        textAlign = TextAlign.Center,
                        shadow = Shadow(color = Color(0x88FFB347), blurRadius = 28f)
                    ),
                    textAlign = TextAlign.Center
                )
            } else {
                Text(
                    text = "осталось",
                    style = TextStyle(
                        color = Color(0xFFB8C4D8),
                        fontSize = 18.sp,
                        letterSpacing = 4.sp,
                        fontWeight = FontWeight.Light
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = days.toString(),
                    style = TextStyle(
                        color = Color(0xFFFFF6DE),
                        fontSize = 112.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        shadow = Shadow(color = Color(0x66FFC857), blurRadius = 36f)
                    )
                )
                Text(
                    text = CountdownCalculator.daysWord(days),
                    style = TextStyle(
                        color = Color(0xFFE8D5A3),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = FontFamily.Serif,
                        letterSpacing = 1.sp
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "до $year",
                    style = TextStyle(
                        color = Color(0xFF8FA0B8),
                        fontSize = 16.sp,
                        letterSpacing = 3.sp
                    )
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = vibe,
                style = TextStyle(
                    color = Color(0xFFD6C4A0),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily.Serif,
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 12.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "добавьте виджет на рабочий стол",
                style = TextStyle(
                    color = Color(0x668FA0B8),
                    fontSize = 12.sp,
                    letterSpacing = 1.sp
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun WarmGlow(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color(0x33FFB347), Color.Transparent),
                center = Offset(size.width * 0.5f, size.height * 0.38f),
                radius = size.minDimension * 0.55f
            ),
            radius = size.minDimension * 0.55f,
            center = Offset(size.width * 0.5f, size.height * 0.38f)
        )
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color(0x22C84B31), Color.Transparent),
                center = Offset(size.width * 0.2f, size.height * 0.85f),
                radius = size.minDimension * 0.45f
            ),
            radius = size.minDimension * 0.45f,
            center = Offset(size.width * 0.2f, size.height * 0.85f)
        )
    }
}

@Composable
private fun Snowfall(modifier: Modifier = Modifier) {
    val flakes = remember {
        List(46) {
            SnowFlake(
                x = Random.nextFloat(),
                y = Random.nextFloat(),
                size = Random.nextFloat() * 3.5f + 1.2f,
                speed = Random.nextFloat() * 0.35f + 0.15f,
                drift = Random.nextFloat() * 0.4f + 0.1f,
                phase = Random.nextFloat() * 6.28f
            )
        }
    }
    val transition = rememberInfiniteTransition(label = "snow")
    val t by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 12000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "snowProgress"
    )

    Canvas(modifier = modifier) {
        flakes.forEach { flake ->
            val y = (flake.y + t * flake.speed) % 1.05f
            val x = (flake.x + sin((t * 6.28f) + flake.phase) * 0.03f * flake.drift + 1f) % 1f
            drawCircle(
                color = Color.White.copy(alpha = 0.18f + flake.size / 20f),
                radius = flake.size,
                center = Offset(x * size.width, y * size.height)
            )
        }
    }
}

private data class SnowFlake(
    val x: Float,
    val y: Float,
    val size: Float,
    val speed: Float,
    val drift: Float,
    val phase: Float
)
