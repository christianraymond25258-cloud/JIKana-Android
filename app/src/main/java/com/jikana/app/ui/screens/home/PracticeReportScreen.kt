package com.jikana.app.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jikana.app.ui.theme.BackgroundCard
import com.jikana.app.ui.theme.BackgroundDark
import com.jikana.app.ui.theme.BackgroundElevated
import com.jikana.app.ui.theme.BorderSubtle
import com.jikana.app.ui.theme.ErrorRed
import com.jikana.app.ui.theme.SuccessGreen
import com.jikana.app.ui.theme.SkyBlue
import com.jikana.app.ui.theme.SkyBlueDark
import com.jikana.app.ui.theme.TextMuted
import com.jikana.app.ui.theme.TextOnBlue
import com.jikana.app.ui.theme.TextPrimary
import com.jikana.app.ui.theme.TextSecondary

@Composable
fun PracticeReportScreen(
    correctCount: Int,
    wrongCount: Int,
    onPracticeAgain: () -> Unit,
    onBackHome: () -> Unit
) {
    val total = correctCount + wrongCount
    val accuracy = if (total > 0) (correctCount * 100) / total else 0

    val emoji = when {
        accuracy == 100 -> "🌟"
        accuracy >= 90 -> "🎉"
        accuracy >= 80 -> "😊"
        accuracy >= 70 -> "🙂"
        accuracy >= 50 -> "📚"
        else -> "💪"
    }

    val comment = when {
        accuracy == 100 -> "Flawless! You got every single one right.\nYou're a kana master!"
        accuracy >= 90 -> "Almost perfect! Just a tiny slip.\nYou clearly know your stuff!"
        accuracy >= 80 -> "Great session! Your memory is sharp.\nKeep building on this momentum!"
        accuracy >= 70 -> "Good work! You're getting there.\nA little more practice and you'll nail it!"
        accuracy >= 50 -> "Not bad for a solid effort!\nReview the tricky ones and try again."
        accuracy > 0 -> "Every master was once a beginner.\nDon't give up — keep going!"
        else -> "No answers recorded.\nSelect some rows and give it a go!"
    }

    val grade = when {
        accuracy == 100 -> "S"
        accuracy >= 90 -> "A"
        accuracy >= 80 -> "B"
        accuracy >= 70 -> "C"
        accuracy >= 50 -> "D"
        else -> "F"
    }

    val gradeColor = when (grade) {
        "S" -> Color(0xFFFFD700)
        "A" -> SuccessGreen
        "B" -> SkyBlue
        "C" -> Color(0xFFFBBF24)
        "D" -> Color(0xFFFF9966)
        else -> ErrorRed
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        // Top glow
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            SkyBlueDark.copy(alpha = 0.15f),
                            Color.Transparent
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
                .padding(top = 60.dp, bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Emoji
            Text(text = emoji, fontSize = 72.sp, textAlign = TextAlign.Center)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Session Report",
                fontSize = 13.sp,
                color = TextMuted,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Practice Complete",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Grade + Accuracy card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(22.dp))
                    .background(BackgroundCard)
                    .border(1.dp, BorderSubtle, RoundedCornerShape(22.dp))
                    .padding(24.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    // Grade circle
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(40.dp))
                            .background(gradeColor.copy(alpha = 0.15f))
                            .border(2.dp, gradeColor, RoundedCornerShape(40.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = grade,
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold,
                            color = gradeColor
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "$accuracy%",
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )

                    Text(
                        text = "Accuracy",
                        fontSize = 12.sp,
                        color = TextMuted,
                        modifier = Modifier.padding(top = 2.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Stats row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        StatBox(
                            value = "$correctCount",
                            label = "Correct",
                            color = SuccessGreen
                        )
                        StatBox(
                            value = "$wrongCount",
                            label = "Wrong",
                            color = ErrorRed
                        )
                        StatBox(
                            value = "$total",
                            label = "Total",
                            color = SkyBlue
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Accuracy bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(BackgroundCard)
                    .border(1.dp, BorderSubtle, RoundedCornerShape(16.dp))
                    .padding(20.dp)
            ) {
                Column {
                    Text(
                        text = "Performance Breakdown",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimary,
                        modifier = Modifier.padding(bottom = 14.dp)
                    )

                    // Correct bar
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Correct",
                            fontSize = 12.sp,
                            color = TextSecondary,
                            modifier = Modifier.padding(end = 10.dp)
                        )
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(BackgroundElevated)
                        ) {
                            val correctFraction = if (total > 0)
                                correctCount.toFloat() / total else 0f
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(correctFraction)
                                    .height(8.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(
                                        brush = Brush.horizontalGradient(
                                            colors = listOf(SuccessGreen, SuccessGreen.copy(0.6f))
                                        )
                                    )
                            )
                        }
                        Text(
                            text = "$correctCount",
                            fontSize = 12.sp,
                            color = SuccessGreen,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Wrong bar
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Wrong  ",
                            fontSize = 12.sp,
                            color = TextSecondary,
                            modifier = Modifier.padding(end = 10.dp)
                        )
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(BackgroundElevated)
                        ) {
                            val wrongFraction = if (total > 0)
                                wrongCount.toFloat() / total else 0f
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(wrongFraction)
                                    .height(8.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(
                                        brush = Brush.horizontalGradient(
                                            colors = listOf(ErrorRed, ErrorRed.copy(0.6f))
                                        )
                                    )
                            )
                        }
                        Text(
                            text = "$wrongCount",
                            fontSize = 12.sp,
                            color = ErrorRed,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Comment card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                SkyBlueDark.copy(alpha = 0.25f),
                                BackgroundCard
                            )
                        )
                    )
                    .border(
                        1.dp,
                        SkyBlue.copy(alpha = 0.25f),
                        RoundedCornerShape(16.dp)
                    )
                    .padding(20.dp)
            ) {
                Column {
                    Text(
                        text = "✦  Sensei says",
                        fontSize = 11.sp,
                        color = SkyBlue,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = comment,
                        fontSize = 15.sp,
                        color = TextPrimary,
                        lineHeight = 22.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Buttons
            Button(
                onClick = onPracticeAgain,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SkyBlue,
                    contentColor = TextOnBlue
                )
            ) {
                Text(
                    text = "Practice Again",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = onBackHome,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = TextSecondary
                )
            ) {
                Text(
                    text = "Back to Home",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun StatBox(value: String, label: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            text = label,
            fontSize = 11.sp,
            color = TextMuted,
            modifier = Modifier.padding(top = 2.dp)
        )
    }
}
