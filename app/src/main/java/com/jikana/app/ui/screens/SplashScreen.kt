package com.jikana.app.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOutBack
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.jikana.app.navigation.NavRoutes
import com.jikana.app.ui.theme.BackgroundDark
import com.jikana.app.ui.theme.SkyBlue
import com.jikana.app.ui.theme.SkyBlueGlow
import com.jikana.app.ui.theme.TextMuted
import com.jikana.app.ui.theme.TextPrimary
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(navController: NavController) {
    val scale = remember { Animatable(0f) }
    val alpha = remember { Animatable(0f) }
    val glowScale = remember { Animatable(0.5f) }
    val subtitleAlpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        launch {
            scale.animateTo(1f, tween(700, easing = EaseOutBack))
        }
        launch {
            alpha.animateTo(1f, tween(500))
        }
        launch {
            glowScale.animateTo(1.4f, tween(1200, easing = EaseOutCubic))
        }

        delay(600)
        subtitleAlpha.animateTo(1f, tween(500))
        delay(1200)

        // Check auth state AFTER animation
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            navController.navigate(NavRoutes.HOME) {
                popUpTo(NavRoutes.SPLASH) { inclusive = true }
            }
        } else {
            navController.navigate(NavRoutes.LOGIN) {
                popUpTo(NavRoutes.SPLASH) { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(280.dp)
                .scale(glowScale.value)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(SkyBlueGlow.copy(alpha = 0.5f), Color.Transparent)
                    )
                )
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .scale(scale.value)
                .alpha(alpha.value)
        ) {
            Text(
                text = "流",
                fontSize = 96.sp,
                fontWeight = FontWeight.Bold,
                color = SkyBlue
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "JIKana",
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                letterSpacing = 3.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Master Japanese Characters",
                fontSize = 14.sp,
                color = TextMuted,
                letterSpacing = 1.sp,
                modifier = Modifier.alpha(subtitleAlpha.value)
            )
        }
    }
}
