package com.jikana.app.ui.screens.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jikana.app.model.JapaneseWord
import com.jikana.app.model.WordData
import com.jikana.app.model.WordDifficulty
import com.jikana.app.ui.components.HapticFeedback
import com.jikana.app.ui.theme.BackgroundCard
import com.jikana.app.ui.theme.BackgroundDark
import com.jikana.app.ui.theme.BackgroundElevated
import com.jikana.app.ui.theme.BorderSubtle
import com.jikana.app.ui.theme.ErrorRed
import com.jikana.app.ui.theme.SuccessGreen
import com.jikana.app.ui.theme.SkyBlue
import com.jikana.app.ui.theme.SkyBlueDark
import com.jikana.app.ui.theme.SkyBlueLight
import com.jikana.app.ui.theme.TextMuted
import com.jikana.app.ui.theme.TextOnBlue
import com.jikana.app.ui.theme.TextPrimary
import com.jikana.app.ui.theme.TextSecondary

enum class WordAnswerState { IDLE, CORRECT, WRONG }

@Composable
fun WordModeScreen() {
    var selectedDifficulty by remember { mutableStateOf<WordDifficulty?>(null) }
    var wordList by remember { mutableStateOf<List<JapaneseWord>>(emptyList()) }
    var currentIndex by remember { mutableIntStateOf(0) }
    var input by remember { mutableStateOf("") }
    var answerState by remember { mutableStateOf(WordAnswerState.IDLE) }
    var correctCount by remember { mutableIntStateOf(0) }
    var wrongCount by remember { mutableIntStateOf(0) }
    var isFinished by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val currentWord = if (wordList.isNotEmpty() && currentIndex < wordList.size)
        wordList[currentIndex] else null

    fun startSession(difficulty: WordDifficulty) {
        wordList = WordData.getByDifficulty(difficulty).shuffled()
        currentIndex = 0
        input = ""
        answerState = WordAnswerState.IDLE
        correctCount = 0
        wrongCount = 0
        isFinished = false
        selectedDifficulty = difficulty
    }

    fun submitAnswer() {
        val word = currentWord ?: return
        val isCorrect = input.trim().lowercase() == word.romaji.lowercase()
        answerState = if (isCorrect) WordAnswerState.CORRECT else WordAnswerState.WRONG
        if (isCorrect) {
            correctCount++
            HapticFeedback.correct(context)
        } else {
            wrongCount++
            HapticFeedback.wrong(context)
        }
    }

    fun nextWord() {
        if (currentIndex + 1 >= wordList.size) {
            isFinished = true
        } else {
            currentIndex++
            input = ""
            answerState = WordAnswerState.IDLE
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .imePadding()
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            SkyBlueDark.copy(alpha = 0.2f),
                            Color.Transparent
                        )
                    )
                )
                .padding(top = 52.dp, start = 20.dp, end = 20.dp, bottom = 16.dp)
        ) {
            Column {
                Text(
                    text = "Word Mode",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = "Translate kana words to romaji",
                    fontSize = 13.sp,
                    color = TextMuted,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        if (selectedDifficulty == null) {
            // DIFFICULTY SELECTION
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Choose Difficulty",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                DifficultyCard(
                    title = "Beginner",
                    subtitle = "Simple common words",
                    emoji = "🌱",
                    accentColor = SuccessGreen,
                    examples = "ねこ、いぬ、みず",
                    onClick = { startSession(WordDifficulty.BEGINNER) }
                )

                Spacer(modifier = Modifier.height(14.dp))

                DifficultyCard(
                    title = "Intermediate",
                    subtitle = "Everyday vocabulary",
                    emoji = "⚡",
                    accentColor = SkyBlue,
                    examples = "がっこう、でんしゃ、りょこう",
                    onClick = { startSession(WordDifficulty.INTERMEDIATE) }
                )

                Spacer(modifier = Modifier.height(14.dp))

                DifficultyCard(
                    title = "Advanced",
                    subtitle = "Complex vocabulary",
                    emoji = "🔥",
                    accentColor = ErrorRed,
                    examples = "しんかんせん、けいざい、へいわ",
                    onClick = { startSession(WordDifficulty.ADVANCED) }
                )
            }

        } else if (isFinished) {
            // RESULTS
            val total = correctCount + wrongCount
            val percentage = if (total > 0) (correctCount * 100) / total else 0
            val emoji = when {
                percentage == 100 -> "🎉"
                percentage >= 80 -> "😊"
                percentage >= 60 -> "🙂"
                else -> "📚"
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = emoji, fontSize = 64.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = when {
                        percentage == 100 -> "Perfect!"
                        percentage >= 80 -> "Great job!"
                        percentage >= 60 -> "Good effort!"
                        else -> "Keep practicing!"
                    },
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Word Mode Complete",
                    fontSize = 13.sp,
                    color = TextMuted
                )
                Spacer(modifier = Modifier.height(32.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(BackgroundCard)
                        .border(1.dp, BorderSubtle, RoundedCornerShape(20.dp))
                        .padding(24.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "$percentage%",
                            fontSize = 52.sp,
                            fontWeight = FontWeight.Bold,
                            color = SkyBlue
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("$correctCount", fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold, color = SuccessGreen)
                                Text("Correct", fontSize = 11.sp, color = TextMuted)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("$wrongCount", fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold, color = ErrorRed)
                                Text("Wrong", fontSize = 11.sp, color = TextMuted)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("$total", fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold, color = TextPrimary)
                                Text("Total", fontSize = 11.sp, color = TextMuted)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { startSession(selectedDifficulty!!) },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SkyBlue, contentColor = TextOnBlue)
                ) {
                    Text("Try Again", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { selectedDifficulty = null },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BackgroundCard, contentColor = TextSecondary)
                ) {
                    Text("Change Difficulty", fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                }
            }

        } else {
            // PRACTICE
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Progress
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(BackgroundElevated)
                ) {
                    val progress = if (wordList.isNotEmpty())
                        currentIndex.toFloat() / wordList.size else 0f
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(progress)
                            .height(4.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(SkyBlue)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${currentIndex + 1} / ${wordList.size}",
                        fontSize = 12.sp, color = TextMuted
                    )
                    Row {
                        Text("✓ $correctCount", fontSize = 12.sp,
                            color = SuccessGreen, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text("✗ $wrongCount", fontSize = 12.sp,
                            color = ErrorRed, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Difficulty badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            when (selectedDifficulty) {
                                WordDifficulty.BEGINNER -> SuccessGreen.copy(alpha = 0.15f)
                                WordDifficulty.INTERMEDIATE -> SkyBlue.copy(alpha = 0.15f)
                                WordDifficulty.ADVANCED -> ErrorRed.copy(alpha = 0.15f)
                                null -> BackgroundCard
                            }
                        )
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = selectedDifficulty?.name?.lowercase()
                            ?.replaceFirstChar { it.uppercase() } ?: "",
                        fontSize = 11.sp,
                        color = when (selectedDifficulty) {
                            WordDifficulty.BEGINNER -> SuccessGreen
                            WordDifficulty.INTERMEDIATE -> SkyBlue
                            WordDifficulty.ADVANCED -> ErrorRed
                            null -> TextMuted
                        },
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Word card
                AnimatedContent(
                    targetState = currentWord,
                    transitionSpec = {
                        (slideInHorizontally(tween(300)) { it } +
                                fadeIn(tween(300))).togetherWith(
                            slideOutHorizontally(tween(300)) { -it } +
                                    fadeOut(tween(300))
                        )
                    },
                    label = "word_card"
                ) { word: JapaneseWord? ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(24.dp))
                            .background(
                                when (answerState) {
                                    WordAnswerState.CORRECT -> SuccessGreen.copy(alpha = 0.12f)
                                    WordAnswerState.WRONG -> ErrorRed.copy(alpha = 0.12f)
                                    WordAnswerState.IDLE -> BackgroundCard
                                }
                            )
                            .border(
                                2.dp,
                                when (answerState) {
                                    WordAnswerState.CORRECT -> SuccessGreen
                                    WordAnswerState.WRONG -> ErrorRed
                                    WordAnswerState.IDLE -> BorderSubtle
                                },
                                RoundedCornerShape(24.dp)
                            )
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = word?.kana ?: "",
                                fontSize = 56.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "What is the romaji?",
                                fontSize = 13.sp,
                                color = TextMuted,
                                textAlign = TextAlign.Center
                            )
                            if (answerState != WordAnswerState.IDLE) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "meaning: ${word?.meaning}",
                                    fontSize = 12.sp,
                                    color = SkyBlue,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Feedback
                val feedbackText = when (answerState) {
                    WordAnswerState.CORRECT -> "✓ Correct!"
                    WordAnswerState.WRONG -> "✗ It's \"${currentWord?.romaji}\""
                    WordAnswerState.IDLE -> ""
                }
                Text(
                    text = feedbackText,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = when (answerState) {
                        WordAnswerState.CORRECT -> SuccessGreen
                        WordAnswerState.WRONG -> ErrorRed
                        WordAnswerState.IDLE -> Color.Transparent
                    },
                    modifier = Modifier.height(22.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Input
                OutlinedTextField(
                    value = input,
                    onValueChange = { if (answerState == WordAnswerState.IDLE) input = it },
                    label = { Text("Type romaji...", color = TextMuted) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SkyBlue,
                        unfocusedBorderColor = BorderSubtle,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary,
                        cursorColor = SkyBlue,
                        focusedContainerColor = BackgroundCard,
                        unfocusedContainerColor = BackgroundCard
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Ascii,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (answerState == WordAnswerState.IDLE) submitAnswer()
                            else nextWord()
                        }
                    ),
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = TextPrimary
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        if (answerState == WordAnswerState.IDLE) submitAnswer()
                        else nextWord()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .navigationBarsPadding(),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = when (answerState) {
                            WordAnswerState.CORRECT -> SuccessGreen
                            WordAnswerState.WRONG -> ErrorRed
                            WordAnswerState.IDLE -> SkyBlue
                        },
                        contentColor = TextOnBlue
                    )
                ) {
                    Text(
                        text = if (answerState == WordAnswerState.IDLE) "Check" else "Next →",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
fun DifficultyCard(
    title: String,
    subtitle: String,
    emoji: String,
    accentColor: Color,
    examples: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(BackgroundCard)
            .border(1.dp, accentColor.copy(alpha = 0.3f), RoundedCornerShape(18.dp))
            .clickable { onClick() }
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = emoji, fontSize = 32.sp)
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = TextMuted,
                    modifier = Modifier.padding(top = 2.dp)
                )
                Text(
                    text = examples,
                    fontSize = 13.sp,
                    color = accentColor.copy(alpha = 0.8f),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(accentColor)
            )
        }
    }
}
