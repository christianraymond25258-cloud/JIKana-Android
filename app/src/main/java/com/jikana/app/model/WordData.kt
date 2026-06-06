package com.jikana.app.model

data class JapaneseWord(
    val kana: String,
    val romaji: String,
    val meaning: String,
    val difficulty: WordDifficulty
)

enum class WordDifficulty {
    BEGINNER,
    INTERMEDIATE,
    ADVANCED
}

object WordData {

    val beginner = listOf(
        JapaneseWord("ねこ", "neko", "cat", WordDifficulty.BEGINNER),
        JapaneseWord("いぬ", "inu", "dog", WordDifficulty.BEGINNER),
        JapaneseWord("さかな", "sakana", "fish", WordDifficulty.BEGINNER),
        JapaneseWord("みず", "mizu", "water", WordDifficulty.BEGINNER),
        JapaneseWord("ほん", "hon", "book", WordDifficulty.BEGINNER),
        JapaneseWord("き", "ki", "tree", WordDifficulty.BEGINNER),
        JapaneseWord("やま", "yama", "mountain", WordDifficulty.BEGINNER),
        JapaneseWord("かわ", "kawa", "river", WordDifficulty.BEGINNER),
        JapaneseWord("ひ", "hi", "fire / sun", WordDifficulty.BEGINNER),
        JapaneseWord("つき", "tsuki", "moon", WordDifficulty.BEGINNER),
        JapaneseWord("はな", "hana", "flower", WordDifficulty.BEGINNER),
        JapaneseWord("あめ", "ame", "rain", WordDifficulty.BEGINNER),
        JapaneseWord("くも", "kumo", "cloud", WordDifficulty.BEGINNER),
        JapaneseWord("そら", "sora", "sky", WordDifficulty.BEGINNER),
        JapaneseWord("うみ", "umi", "sea", WordDifficulty.BEGINNER),
        JapaneseWord("しろ", "shiro", "white", WordDifficulty.BEGINNER),
        JapaneseWord("くろ", "kuro", "black", WordDifficulty.BEGINNER),
        JapaneseWord("あか", "aka", "red", WordDifficulty.BEGINNER),
        JapaneseWord("あお", "ao", "blue", WordDifficulty.BEGINNER),
        JapaneseWord("て", "te", "hand", WordDifficulty.BEGINNER),
        JapaneseWord("め", "me", "eye", WordDifficulty.BEGINNER),
        JapaneseWord("くち", "kuchi", "mouth", WordDifficulty.BEGINNER),
        JapaneseWord("みみ", "mimi", "ear", WordDifficulty.BEGINNER),
        JapaneseWord("はな", "hana", "nose", WordDifficulty.BEGINNER),
        JapaneseWord("あし", "ashi", "leg / foot", WordDifficulty.BEGINNER)
    )

    val intermediate = listOf(
        JapaneseWord("がっこう", "gakkou", "school", WordDifficulty.INTERMEDIATE),
        JapaneseWord("せんせい", "sensei", "teacher", WordDifficulty.INTERMEDIATE),
        JapaneseWord("がくせい", "gakusei", "student", WordDifficulty.INTERMEDIATE),
        JapaneseWord("ともだち", "tomodachi", "friend", WordDifficulty.INTERMEDIATE),
        JapaneseWord("かぞく", "kazoku", "family", WordDifficulty.INTERMEDIATE),
        JapaneseWord("しごと", "shigoto", "work / job", WordDifficulty.INTERMEDIATE),
        JapaneseWord("でんしゃ", "densha", "train", WordDifficulty.INTERMEDIATE),
        JapaneseWord("じてんしゃ", "jitensha", "bicycle", WordDifficulty.INTERMEDIATE),
        JapaneseWord("たべもの", "tabemono", "food", WordDifficulty.INTERMEDIATE),
        JapaneseWord("のみもの", "nomimono", "drink", WordDifficulty.INTERMEDIATE),
        JapaneseWord("やさい", "yasai", "vegetables", WordDifficulty.INTERMEDIATE),
        JapaneseWord("くだもの", "kudamono", "fruit", WordDifficulty.INTERMEDIATE),
        JapaneseWord("としょかん", "toshokan", "library", WordDifficulty.INTERMEDIATE),
        JapaneseWord("びょういん", "byouin", "hospital", WordDifficulty.INTERMEDIATE),
        JapaneseWord("えいが", "eiga", "movie", WordDifficulty.INTERMEDIATE),
        JapaneseWord("おんがく", "ongaku", "music", WordDifficulty.INTERMEDIATE),
        JapaneseWord("りょこう", "ryokou", "travel", WordDifficulty.INTERMEDIATE),
        JapaneseWord("しんぶん", "shinbun", "newspaper", WordDifficulty.INTERMEDIATE),
        JapaneseWord("でんわ", "denwa", "phone", WordDifficulty.INTERMEDIATE),
        JapaneseWord("かいしゃ", "kaisha", "company", WordDifficulty.INTERMEDIATE),
        JapaneseWord("にほんご", "nihongo", "Japanese language", WordDifficulty.INTERMEDIATE),
        JapaneseWord("えいご", "eigo", "English language", WordDifficulty.INTERMEDIATE),
        JapaneseWord("じかん", "jikan", "time", WordDifficulty.INTERMEDIATE),
        JapaneseWord("まいにち", "mainichi", "every day", WordDifficulty.INTERMEDIATE),
        JapaneseWord("こんにちは", "konnichiwa", "hello", WordDifficulty.INTERMEDIATE)
    )

    val advanced = listOf(
        JapaneseWord("しんかんせん", "shinkansen", "bullet train", WordDifficulty.ADVANCED),
        JapaneseWord("かんきょう", "kankyou", "environment", WordDifficulty.ADVANCED),
        JapaneseWord("けいざい", "keizai", "economy", WordDifficulty.ADVANCED),
        JapaneseWord("せいじ", "seiji", "politics", WordDifficulty.ADVANCED),
        JapaneseWord("ぶんかさい", "bunkasai", "cultural festival", WordDifficulty.ADVANCED),
        JapaneseWord("にんげんかんけい", "ningenkanke", "human relations", WordDifficulty.ADVANCED),
        JapaneseWord("こうつうじこ", "koutsuujiko", "traffic accident", WordDifficulty.ADVANCED),
        JapaneseWord("じんこうちのう", "jinkouchinou", "artificial intelligence", WordDifficulty.ADVANCED),
        JapaneseWord("かがくぎじゅつ", "kagakugijutsu", "science and technology", WordDifficulty.ADVANCED),
        JapaneseWord("こくさいかんけい", "kokusaikanke", "international relations", WordDifficulty.ADVANCED),
        JapaneseWord("しゅうしょく", "shuushoku", "getting a job", WordDifficulty.ADVANCED),
        JapaneseWord("そつぎょうしき", "sotsugyoushiki", "graduation ceremony", WordDifficulty.ADVANCED),
        JapaneseWord("けんきゅうしつ", "kenkyuushitsu", "research lab", WordDifficulty.ADVANCED),
        JapaneseWord("たいいくかん", "taiikukan", "gymnasium", WordDifficulty.ADVANCED),
        JapaneseWord("ほうりつ", "houritsu", "law", WordDifficulty.ADVANCED),
        JapaneseWord("せいふ", "seifu", "government", WordDifficulty.ADVANCED),
        JapaneseWord("かんこうち", "kankouchi", "tourist spot", WordDifficulty.ADVANCED),
        JapaneseWord("でんとうてき", "dentouteki", "traditional", WordDifficulty.ADVANCED),
        JapaneseWord("かんしゃ", "kansha", "gratitude", WordDifficulty.ADVANCED),
        JapaneseWord("しんらい", "shinrai", "trust", WordDifficulty.ADVANCED),
        JapaneseWord("ゆうじょう", "yuujou", "friendship", WordDifficulty.ADVANCED),
        JapaneseWord("あいじょう", "aijou", "affection / love", WordDifficulty.ADVANCED),
        JapaneseWord("じゆう", "jiyuu", "freedom", WordDifficulty.ADVANCED),
        JapaneseWord("へいわ", "heiwa", "peace", WordDifficulty.ADVANCED),
        JapaneseWord("しあわせ", "shiawase", "happiness", WordDifficulty.ADVANCED)
    )

    fun getByDifficulty(difficulty: WordDifficulty) = when (difficulty) {
        WordDifficulty.BEGINNER -> beginner
        WordDifficulty.INTERMEDIATE -> intermediate
        WordDifficulty.ADVANCED -> advanced
    }
}
