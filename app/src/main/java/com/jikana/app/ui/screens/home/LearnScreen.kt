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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.jikana.app.ui.theme.SkyBlue
import com.jikana.app.ui.theme.SkyBlueDark
import com.jikana.app.ui.theme.SkyBlueLight
import com.jikana.app.ui.theme.TextMuted
import com.jikana.app.ui.theme.TextPrimary
import com.jikana.app.ui.theme.TextSecondary

data class KanaCell(val kana: String, val romaji: String)

@Composable
fun LearnScreen() {
    val hiraganaRows = listOf(
        listOf(KanaCell("あ","a"), KanaCell("い","i"), KanaCell("う","u"), KanaCell("え","e"), KanaCell("お","o")),
        listOf(KanaCell("か","ka"), KanaCell("き","ki"), KanaCell("く","ku"), KanaCell("け","ke"), KanaCell("こ","ko")),
        listOf(KanaCell("さ","sa"), KanaCell("し","shi"), KanaCell("す","su"), KanaCell("せ","se"), KanaCell("そ","so")),
        listOf(KanaCell("た","ta"), KanaCell("ち","chi"), KanaCell("つ","tsu"), KanaCell("て","te"), KanaCell("と","to")),
        listOf(KanaCell("な","na"), KanaCell("に","ni"), KanaCell("ぬ","nu"), KanaCell("ね","ne"), KanaCell("の","no")),
        listOf(KanaCell("は","ha"), KanaCell("ひ","hi"), KanaCell("ふ","fu"), KanaCell("へ","he"), KanaCell("ほ","ho")),
        listOf(KanaCell("ま","ma"), KanaCell("み","mi"), KanaCell("む","mu"), KanaCell("め","me"), KanaCell("も","mo")),
        listOf(KanaCell("や","ya"), KanaCell("",""), KanaCell("ゆ","yu"), KanaCell("",""), KanaCell("よ","yo")),
        listOf(KanaCell("ら","ra"), KanaCell("り","ri"), KanaCell("る","ru"), KanaCell("れ","re"), KanaCell("ろ","ro")),
        listOf(KanaCell("わ","wa"), KanaCell("",""), KanaCell("",""), KanaCell("",""), KanaCell("を","wo")),
        listOf(KanaCell("ん","n"), KanaCell("",""), KanaCell("",""), KanaCell("",""), KanaCell("",""))
    )

    val katakanaRows = listOf(
        listOf(KanaCell("ア","a"), KanaCell("イ","i"), KanaCell("ウ","u"), KanaCell("エ","e"), KanaCell("オ","o")),
        listOf(KanaCell("カ","ka"), KanaCell("キ","ki"), KanaCell("ク","ku"), KanaCell("ケ","ke"), KanaCell("コ","ko")),
        listOf(KanaCell("サ","sa"), KanaCell("シ","shi"), KanaCell("ス","su"), KanaCell("セ","se"), KanaCell("ソ","so")),
        listOf(KanaCell("タ","ta"), KanaCell("チ","chi"), KanaCell("ツ","tsu"), KanaCell("テ","te"), KanaCell("ト","to")),
        listOf(KanaCell("ナ","na"), KanaCell("ニ","ni"), KanaCell("ヌ","nu"), KanaCell("ネ","ne"), KanaCell("ノ","no")),
        listOf(KanaCell("ハ","ha"), KanaCell("ヒ","hi"), KanaCell("フ","fu"), KanaCell("ヘ","he"), KanaCell("ホ","ho")),
        listOf(KanaCell("マ","ma"), KanaCell("ミ","mi"), KanaCell("ム","mu"), KanaCell("メ","me"), KanaCell("モ","mo")),
        listOf(KanaCell("ヤ","ya"), KanaCell("",""), KanaCell("ユ","yu"), KanaCell("",""), KanaCell("ヨ","yo")),
        listOf(KanaCell("ラ","ra"), KanaCell("リ","ri"), KanaCell("ル","ru"), KanaCell("レ","re"), KanaCell("ロ","ro")),
        listOf(KanaCell("ワ","wa"), KanaCell("",""), KanaCell("",""), KanaCell("",""), KanaCell("ヲ","wo")),
        listOf(KanaCell("ン","n"), KanaCell("",""), KanaCell("",""), KanaCell("",""), KanaCell("",""))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 100.dp)
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
                .padding(top = 52.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
        ) {
            Column {
                Text(
                    text = "Reference Chart",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = "Study hiragana & katakana characters",
                    fontSize = 13.sp,
                    color = TextMuted,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        // Hiragana Section
        ChartSection(
            title = "Hiragana",
            subtitle = "ひらがな",
            accentColor = SkyBlue,
            rows = hiraganaRows
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Katakana Section
        ChartSection(
            title = "Katakana",
            subtitle = "カタカナ",
            accentColor = SkyBlueLight,
            rows = katakanaRows
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun ChartSection(
    title: String,
    subtitle: String,
    accentColor: Color,
    rows: List<List<KanaCell>>
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        // Section header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 12.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(24.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(accentColor)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = subtitle,
                    fontSize = 13.sp,
                    color = accentColor
                )
            }
        }

        // Header row (vowels)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                .background(accentColor.copy(alpha = 0.15f))
                .border(
                    1.dp,
                    accentColor.copy(alpha = 0.3f),
                    RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                )
                .padding(vertical = 8.dp, horizontal = 4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                listOf("a", "i", "u", "e", "o").forEach { vowel ->
                    Text(
                        text = vowel,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = accentColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Character rows
        rows.forEachIndexed { rowIndex, row ->
            val isLast = rowIndex == rows.size - 1
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        if (isLast) RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
                        else RoundedCornerShape(0.dp)
                    )
                    .background(
                        if (rowIndex % 2 == 0) BackgroundCard
                        else BackgroundElevated
                    )
                    .border(
                        width = if (isLast) 1.dp else 0.dp,
                        color = if (isLast) BorderSubtle else Color.Transparent,
                        shape = if (isLast) RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
                        else RoundedCornerShape(0.dp)
                    )
                    .padding(vertical = 6.dp, horizontal = 4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    row.forEach { cell ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.weight(1f)
                        ) {
                            if (cell.kana.isNotEmpty()) {
                                Text(
                                    text = cell.kana,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = cell.romaji,
                                    fontSize = 9.sp,
                                    color = TextSecondary,
                                    textAlign = TextAlign.Center
                                )
                            } else {
                                Text(
                                    text = "—",
                                    fontSize = 16.sp,
                                    color = TextMuted.copy(alpha = 0.3f),
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = "",
                                    fontSize = 9.sp,
                                    color = Color.Transparent
                                )
                            }
                        }
                    }
                }
            }

            // Row divider
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(BorderSubtle.copy(alpha = 0.5f))
                )
            }
        }
    }
}
