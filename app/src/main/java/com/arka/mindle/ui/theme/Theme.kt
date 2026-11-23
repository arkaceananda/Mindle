package com.arka.mindle.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// §3.3 metric contract
data class MetricStyle(
    val tint: Color,
    val accent: Color,
)

data class MetricColors(
    val tc: MetricStyle,
    val cp: MetricStyle,
    val reputation: MetricStyle,
)

// Tokens Material 3 cannot express on its own (§3.2, §3.3).
data class AppColors(
    val line: Color,
    val lineStrong: Color,
    val surfaceSunken: Color,
    val textTertiary: Color,
    val success: Color,
    val successSoft: Color,
    val warning: Color,
    val warningSoft: Color,
    val info: Color,
    val infoSoft: Color,
    val dangerSoft: Color,
    val metrics: MetricColors,
)

private val LightAppColors = AppColors(
    line = Line,
    lineStrong = LineStrong,
    surfaceSunken = SurfaceSunken,
    textTertiary = TextTertiary,
    success = Success,
    successSoft = SuccessSoft,
    warning = Warning,
    warningSoft = WarningSoft,
    info = Info,
    infoSoft = InfoSoft,
    dangerSoft = DangerSoft,
    metrics = MetricColors(
        tc = MetricStyle(TcTint, TcAccent),
        cp = MetricStyle(CpTint, CpAccent),
        reputation = MetricStyle(RepTint, RepAccent),
    ),
)

private val DarkAppColors = AppColors(
    line = LineDark,
    lineStrong = LineStrongDark,
    surfaceSunken = SurfaceSunkenDark,
    textTertiary = TextTertiaryDark,
    success = SuccessDark,
    successSoft = SuccessSoftDark,
    warning = WarningDark,
    warningSoft = WarningSoftDark,
    info = InfoDark,
    infoSoft = InfoSoftDark,
    dangerSoft = DangerSoftDark,
    metrics = MetricColors(
        tc = MetricStyle(TcTintDark, TcAccentDark),
        cp = MetricStyle(CpTintDark, CpAccentDark),
        reputation = MetricStyle(RepTintDark, RepAccentDark),
    ),
)

val LocalAppColors = staticCompositionLocalOf { LightAppColors }

// §12: every M3 role maps to a token, so themed components never show stock defaults.
private val LightColorScheme = lightColorScheme(
    primary = TextPrimary,
    onPrimary = OnPrimary,
    primaryContainer = SurfaceAlt,
    onPrimaryContainer = TextPrimary,
    secondary = TextSecondary,
    onSecondary = OnPrimary,
    secondaryContainer = SurfaceAlt,
    onSecondaryContainer = TextPrimary,
    tertiary = TextTertiary,
    onTertiary = OnPrimary,
    tertiaryContainer = SurfaceSunken,
    onTertiaryContainer = TextPrimary,
    background = Canvas,
    onBackground = TextPrimary,
    surface = Surface,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceAlt,
    onSurfaceVariant = TextSecondary,
    surfaceTint = TextPrimary,
    surfaceBright = Surface,
    surfaceDim = Canvas,
    surfaceContainerLowest = Surface,
    surfaceContainerLow = Surface,
    surfaceContainer = Surface,
    surfaceContainerHigh = SurfaceAlt,
    surfaceContainerHighest = SurfaceSunken,
    outline = LineStrong,
    outlineVariant = Line,
    error = Danger,
    onError = OnPrimary,
    errorContainer = DangerSoft,
    onErrorContainer = Danger,
    inverseSurface = TextPrimary,
    inverseOnSurface = Canvas,
    inversePrimary = OnPrimary,
    scrim = Color.Black,
)

private val DarkColorScheme = darkColorScheme(
    primary = TextPrimaryDark,
    onPrimary = OnPrimaryDark,
    primaryContainer = SurfaceAltDark,
    onPrimaryContainer = TextPrimaryDark,
    secondary = TextSecondaryDark,
    onSecondary = OnPrimaryDark,
    secondaryContainer = SurfaceAltDark,
    onSecondaryContainer = TextPrimaryDark,
    tertiary = TextTertiaryDark,
    onTertiary = OnPrimaryDark,
    tertiaryContainer = SurfaceSunkenDark,
    onTertiaryContainer = TextPrimaryDark,
    background = CanvasDark,
    onBackground = TextPrimaryDark,
    surface = SurfaceDark,
    onSurface = TextPrimaryDark,
    surfaceVariant = SurfaceAltDark,
    onSurfaceVariant = TextSecondaryDark,
    surfaceTint = TextPrimaryDark,
    surfaceBright = SurfaceAltDark,
    surfaceDim = CanvasDark,
    surfaceContainerLowest = SurfaceDark,
    surfaceContainerLow = SurfaceDark,
    surfaceContainer = SurfaceDark,
    surfaceContainerHigh = SurfaceAltDark,
    surfaceContainerHighest = SurfaceSunkenDark,
    outline = LineStrongDark,
    outlineVariant = LineDark,
    error = DangerDark,
    onError = OnPrimaryDark,
    errorContainer = DangerSoftDark,
    onErrorContainer = DangerDark,
    inverseSurface = TextPrimaryDark,
    inverseOnSurface = CanvasDark,
    inversePrimary = OnPrimaryDark,
    scrim = Color.Black,
)

@Composable
fun MindleTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(LocalAppColors provides if (darkTheme) DarkAppColors else LightAppColors) {
        MaterialTheme(
            colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
            typography = MindleTypography,
            shapes = MindleShapes,
            content = content,
        )
    }
}
