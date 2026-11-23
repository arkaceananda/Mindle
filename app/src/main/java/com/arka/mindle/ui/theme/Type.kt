package com.arka.mindle.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.arka.mindle.R

@OptIn(ExperimentalTextApi::class)
private fun variableFont(resId: Int, weight: Int, style: FontStyle = FontStyle.Normal) = Font(
    resId = resId,
    weight = FontWeight(weight),
    style = style,
    variationSettings = FontVariation.Settings(FontVariation.weight(weight)),
)

val SourceSerifFamily = FontFamily(
    variableFont(R.font.source_serif_4, 600),
    variableFont(R.font.source_serif_4, 700),
    variableFont(R.font.source_serif_4_italic, 400, FontStyle.Italic),
)

val InterFamily = FontFamily(
    variableFont(R.font.inter, 400),
    variableFont(R.font.inter, 500),
    variableFont(R.font.inter, 600),
)

val PlexMonoFamily = FontFamily(
    Font(R.font.ibm_plex_mono_medium, FontWeight.Medium),
    Font(R.font.ibm_plex_mono_semibold, FontWeight.SemiBold),
)

// §4.1 type scale
val DisplayL = TextStyle(
    fontFamily = SourceSerifFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 30.sp,
    lineHeight = 34.sp,
)
val DisplayM = TextStyle(
    fontFamily = SourceSerifFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 24.sp,
    lineHeight = 28.sp,
)
val TitleL = TextStyle(
    fontFamily = SourceSerifFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 19.sp,
    lineHeight = 24.sp,
)
val TitleM = TextStyle(
    fontFamily = InterFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 15.sp,
    lineHeight = 20.sp,
)
val BodyL = TextStyle(
    fontFamily = InterFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 15.sp,
    lineHeight = 22.sp,
)
val BodyM = TextStyle(
    fontFamily = InterFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 13.sp,
    lineHeight = 19.sp,
)
val BodyS = TextStyle(
    fontFamily = InterFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 12.sp,
    lineHeight = 16.sp,
)
val Label = TextStyle(
    fontFamily = InterFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 10.sp,
    lineHeight = 12.sp,
    letterSpacing = 0.6.sp,
)
val NumHeroSerif = TextStyle(
    fontFamily = SourceSerifFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 26.sp,
    lineHeight = 30.sp,
)
val NumHeroMono = TextStyle(
    fontFamily = PlexMonoFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 26.sp,
    lineHeight = 30.sp,
)
val NumM = TextStyle(
    fontFamily = PlexMonoFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 13.sp,
    lineHeight = 16.sp,
)
val Quote = TextStyle(
    fontFamily = SourceSerifFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 15.sp,
    lineHeight = 21.sp,
    fontStyle = FontStyle.Italic,
)

// Every M3 slot is filled so no stock type style can leak through (§12, rule 2).
val MindleTypography = Typography(
    displayLarge = DisplayL,
    displayMedium = DisplayM,
    displaySmall = DisplayM,
    headlineLarge = TitleL,
    headlineMedium = TitleL,
    headlineSmall = TitleM,
    titleLarge = TitleL,
    titleMedium = TitleM,
    titleSmall = BodyS,
    bodyLarge = BodyL,
    bodyMedium = BodyM,
    bodySmall = BodyS,
    labelLarge = TitleM,
    labelMedium = Label,
    labelSmall = Label,
)
