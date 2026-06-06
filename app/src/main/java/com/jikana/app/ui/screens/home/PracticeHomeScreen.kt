package com.jikana.app.ui.screens.home

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jikana.app.model.HiraganaData
import com.jikana.app.model.KanaChar
import com.jikana.app.model.KanaRow
import com.jikana.app.model.KatakanaData
import com.jikana.app.ui.components.HapticFeedback
import com.jikana.app.ui.theme.BackgroundCard
import com.jikana.app.ui.theme.BackgroundDark
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
import androidx.navigation.NavController
import com.jikana.app.navigation.NavRoutes

enum class InlineAnswerState { IDLE, WRONG }

@Composable
fun PracticeHomeScreen(navController: NavController) {
    val context = LocalContext.current
    val focusRequester = remember { FocusRequester() }

    val selectedHiragana = remember { mutableStateListOf<Int>() }
    val selectedKatakana = remember { mutableStateListOf<Int>() }

    var practicePool by remember { mutableStateOf<List<KanaChar>>(emptyList()) }
    var currentChar by remember { mutableStateOf<KanaChar?>(null) }
    var input by remember { mutableStateOf("") }
    var answerState by remember { mutableStateOf(InlineAnswerState.IDLE) }
    var correctCount by remember { mutableStateOf(0) }
    var wrongCount by remember { mutableStateOf(0) }
    var isPracticing by remember { mutableStateOf(false) }
    var showReport by remember { mutableStateOf(false) }

    fun buildPool(): List<KanaChar> {
        val chars = mutableListOf<KanaChar>()
        selectedHiragana.sorted().forEach { i -> chars.addAll(HiraganaData.rows[i].characters) }
        selectedKatakana.sorted().forEach { i -> chars.addAll(KatakanaData.rows[i].characters) }
        return chars.shuffled()
    }

    fun nextChar() {
        if (practicePool.isEmpty()) return
        currentChar = practicePool.random()
        input = ""
        answerState = InlineAnswerState.IDLE
    }

    fun startPractice() {
        practicePool = buildPool()
        correctCount = 0
        wrongCount = 0
        nextChar()
        isPracticing = true
        showReport = false
    }

    fun submitAnswer() {
        val char = currentChar ?: return
        val isCorrect = input.trim().lowercase() == char.romaji.lowercase()
        if (isCorrect) {
            correctCount++
            HapticFeedback.correct(context)
            nextChar()
        } else {
            wrongCount++
            HapticFeedback.wrong(context)
            answerState = InlineAnswerState.WRONG
        }
    }

    LaunchedEffect(selectedHiragana.size, selectedKatakana.size) {
        if (isPracticing && (selectedHiragana.isNotEmpty() || selectedKatakana.isNotEmpty())) {
            practicePool = buildPool()
            if (currentChar == null) nextChar()
        } else if (isPracticing && selectedHiragana.isEmpty() && selectedKatakana.isEmpty()) {
            isPracticing = false
            currentChar = null
        }
    }

    LaunchedEffect(isPracticing, answerState) {
        if (isPracticing && answerState == InlineAnswerState.IDLE) {
            try { focusRequester.requestFocus() } catch (e: Exception) { }
        }
    }

    // Show report screen
    if (showReport) {
        PracticeReportScreen(
            correctCount = correctCount,
            wrongCount = wrongCount,
            onPracticeAgain = {
                showReport = false
                startPractice()
            },
            onBackHome = {
                showReport = false
                isPracticing = false
                currentChar = null
                selectedHiragana.clear()
                selectedKatakana.clear()
                correctCount = 0
                wrongCount = 0
            }
        )
        return
    }

    val cardBg = when (answerState) {
        InlineAnswerState.WRONG -> ErrorRed.copy(alpha = 0.12f)
        InlineAnswerState.IDLE -> BackgroundCard
    }
    val cardBorder = when (answerState) {
        InlineAnswerState.WRONG -> ErrorRed
        InlineAnswerState.IDLE -> BorderSubtle
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
                        colors = listOf(SkyBlueDark.copy(alpha = 0.2f), Color.Transparent)
                    )
                )
                .padding(top = 52.dp, start = 20.dp, end = 20.dp, bottom = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Practice", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                    Text(
                        text = if (isPracticing) "Tap rows to change selection anytime"
                        else "Select rows below to begin",
                        fontSize = 12.sp,
                        color = TextMuted,
                        modifier = Modifier.padding(top = 3.dp)
                    )
                }
                if (isPracticing) {
                    Row {
                        Text("✓ $correctCount", fontSize = 13.sp, color = SuccessGreen, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text("✗ $wrongCount", fontSize = 13.sp, color = ErrorRed, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .padding(bottom = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // PRACTICE AREA
            if (isPracticing && currentChar != null) {
                Spacer(modifier = Modifier.height(8.dp))

                // Character card + Finish button side by side
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Character card
                    Box(
                        modifier = Modifier
                            .size(160.dp)
                            .clip(RoundedCornerShape(28.dp))
                            .background(cardBg)
                            .border(2.dp, cardBorder, RoundedCornerShape(28.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(120.dp)
                                .background(
                                    brush = Brush.radialGradient(
                                        colors = listOf(SkyBlue.copy(alpha = 0.08f), Color.Transparent)
                                    )
                                )
                        )
                        Text(
                            text = currentChar?.character ?: "",
                            fontSize = 80.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary,
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    // Finish button
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        SkyBlueDark.copy(alpha = 0.6f),
                                        SkyBlue.copy(alpha = 0.3f)
                                    )
                                )
                            )
                            .border(1.dp, SkyBlue.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
                            .clickable { showReport = true }
                            .padding(horizontal = 18.dp, vertical = 22.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("🏁", fontSize = 26.sp)
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Finish",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Only show feedback when WRONG
                if (answerState == InlineAnswerState.WRONG) {
                    Text(
                        text = "✗  The answer is  \"${currentChar?.romaji}\"",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = ErrorRed,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                } else {
                    Spacer(modifier = Modifier.height(28.dp))
                }

                // Input field
                if (answerState == InlineAnswerState.IDLE) {
                    OutlinedTextField(
                        value = input,
                        onValueChange = { newVal ->
                            input = newVal
                            val char = currentChar
                            if (char != null &&
                                newVal.trim().lowercase() == char.romaji.lowercase()) {
                                correctCount++
                                HapticFeedback.correct(context)
                                nextChar()
                            }
                        },
                        label = { Text("Type romaji...", color = TextMuted) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester),
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
                            onDone = { submitAnswer() }
                        ),
                        textStyle = LocalTextStyle.current.copy(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = TextPrimary
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = { submitAnswer() },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SkyBlue,
                            contentColor = TextOnBlue
                        )
                    ) {
                        Text("Check", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    }
                } else {
                    Button(
                        onClick = { nextChar() },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ErrorRed,
                            contentColor = Color.White
                        )
                    ) {
                        Text("Next →", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

            } else if (!isPracticing) {
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(BackgroundCard)
                        .border(1.dp, BorderSubtle, RoundedCornerShape(20.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("あ", fontSize = 48.sp, color = TextMuted.copy(alpha = 0.3f), fontWeight = FontWeight.Bold)
                        Text("Select rows below to start", fontSize = 12.sp, color = TextMuted, modifier = Modifier.padding(top = 4.dp))
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            // HIRAGANA ROWS
            SectionHeader(
                title = "Hiragana", subtitle = "ひらがな", accentColor = SkyBlue,
                onSelectAll = {
                    selectedHiragana.clear()
                    selectedHiragana.addAll(HiraganaData.rows.indices)
                    if (!isPracticing) startPractice() else practicePool = buildPool()
                },
                onClear = {
                    selectedHiragana.clear()
                    if (selectedKatakana.isEmpty()) { isPracticing = false; currentChar = null }
                    else practicePool = buildPool()
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            HiraganaData.rows.forEachIndexed { index, row ->
                val isSelected = selectedHiragana.contains(index)
                RowItem(
                    row = row, isSelected = isSelected, accentColor = SkyBlue,
                    onClick = {
                        if (isSelected) {
                            selectedHiragana.remove(index)
                            if (selectedHiragana.isEmpty() && selectedKatakana.isEmpty()) {
                                isPracticing = false; currentChar = null
                            } else if (isPracticing) practicePool = buildPool()
                        } else {
                            selectedHiragana.add(index)
                            if (!isPracticing) startPractice() else practicePool = buildPool()
                        }
                    }
                )
                Spacer(modifier = Modifier.height(4.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // KATAKANA ROWS
            SectionHeader(
                title = "Katakana", subtitle = "カタカナ", accentColor = SkyBlueLight,
                onSelectAll = {
                    selectedKatakana.clear()
                    selectedKatakana.addAll(KatakanaData.rows.indices)
                    if (!isPracticing) startPractice() else practicePool = buildPool()
                },
                onClear = {
                    selectedKatakana.clear()
                    if (selectedHiragana.isEmpty()) { isPracticing = false; currentChar = null }
                    else practicePool = buildPool()
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            KatakanaData.rows.forEachIndexed { index, row ->
                val isSelected = selectedKatakana.contains(index)
                RowItem(
                    row = row, isSelected = isSelected, accentColor = SkyBlueLight,
                    onClick = {
                        if (isSelected) {
                            selectedKatakana.remove(index)
                            if (selectedHiragana.isEmpty() && selectedKatakana.isEmpty()) {
                                isPracticing = false; currentChar = null
                            } else if (isPracticing) practicePool = buildPool()
                        } else {
                            selectedKatakana.add(index)
                            if (!isPracticing) startPractice() else practicePool = buildPool()
                        }
                    }
                )
                Spacer(modifier = Modifier.height(4.dp))
            }

            Spacer(modifier = Modifier.height(20.dp))

            // KANJI BUTTON
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(SkyBlueDark.copy(alpha = 0.3f), BackgroundCard)
                        )
                    )
                    .border(1.dp, SkyBlueDark.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                    .clickable { navController.navigate(NavRoutes.KANJI) }
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Kanji", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                        Text("Multiple choice SRS practice", fontSize = 12.sp, color = TextMuted, modifier = Modifier.padding(top = 2.dp))
                        Text("Tap to begin →", fontSize = 11.sp, color = SkyBlueDark, modifier = Modifier.padding(top = 4.dp))
                    }
                    Text("漢字", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = SkyBlueDark)
                }
            }
        }
    }
}

@Composable
fun SectionHeader(
    title: String, subtitle: String, accentColor: Color,
    onSelectAll: () -> Unit, onClear: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.width(4.dp).height(20.dp).clip(RoundedCornerShape(2.dp)).background(accentColor))
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                Text(subtitle, fontSize = 11.sp, color = accentColor)
            }
        }
        Row {
            Text("All", fontSize = 12.sp, color = accentColor, fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable { onSelectAll() }.padding(horizontal = 8.dp, vertical = 4.dp))
            Text("Clear", fontSize = 12.sp, color = TextMuted,
                modifier = Modifier.clickable { onClear() }.padding(horizontal = 8.dp, vertical = 4.dp))
        }
    }
}

@Composable
fun RowItem(row: KanaRow, isSelected: Boolean, accentColor: Color, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(if (isSelected) accentColor.copy(alpha = 0.1f) else BackgroundCard)
            .border(
                width = if (isSelected) 1.5.dp else 1.dp,
                color = if (isSelected) accentColor else BorderSubtle,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(row.rowName, fontSize = 13.sp, fontWeight = FontWeight.SemiBold,
                    color = if (isSelected) accentColor else TextPrimary)
                Text(
                    text = row.characters.joinToString("  ") { it.character },
                    fontSize = 16.sp,
                    color = if (isSelected) accentColor.copy(0.85f) else TextSecondary,
                    modifier = Modifier.padding(top = 3.dp)
                )
            }
            Checkbox(
                checked = isSelected,
                onCheckedChange = { onClick() },
                colors = CheckboxDefaults.colors(
                    checkedColor = accentColor,
                    uncheckedColor = BorderSubtle,
                    checkmarkColor = BackgroundDark
                )
            )
        }
    }
}
