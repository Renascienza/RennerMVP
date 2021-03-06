package com.renascienza.rennermvp.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.renascienza.rennermvp.R

private val fonts = FontFamily(
    Font(R.font.roboto_flex_var)
)

val Typography = typographyFromDefaults(
    h1 = TextStyle(
        fontSize = 96.sp,
        fontFamily = fonts,
        fontWeight = FontWeight.Light
    ),
    h2 = TextStyle(
        fontSize = 60.sp,
        fontFamily = fonts,
        fontWeight = FontWeight.Light
    ),
    h3 = TextStyle(
        fontSize = 48.sp,
        fontFamily = fonts,
        fontWeight = FontWeight.Normal
    ),
    h4 = TextStyle(
        fontSize = 34.sp,
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        lineHeight = 40.sp
    ),
    h5 = TextStyle(
        fontSize = 24.sp,
        fontFamily = fonts,
        fontWeight = FontWeight.Normal
    ),
    h6 = TextStyle(
        fontSize = 20.sp,
        fontFamily = fonts,
        fontWeight = FontWeight.W500,
        lineHeight = 28.sp
    ),
    subtitle1 = TextStyle(
        fontSize = 16.sp,
        fontFamily = fonts,
        fontWeight = FontWeight.W500,
        lineHeight = 22.sp
    ),
    subtitle2 = TextStyle(
        fontSize = 14.sp,
        fontFamily = fonts,
        fontWeight = FontWeight.W500
    ),
    body1 = TextStyle(
        fontSize = 16.sp,
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        lineHeight = 28.sp
    ),
    body2 = TextStyle(
        fontSize = 14.sp,
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        lineHeight = 16.sp
    ),
    button = TextStyle(
        fontSize = 12.sp,
        fontFamily = fonts,
        fontWeight = FontWeight.Medium
    ),
    caption = TextStyle(
        fontSize = 12.sp,
        fontFamily = fonts
    ),
    overline = TextStyle(
        fontSize = 10.sp,
        fontWeight = FontWeight.Light,
        fontFamily = fonts,
        letterSpacing = 0.08.em
    )
)

fun typographyFromDefaults(
    h1: TextStyle?,
    h2: TextStyle?,
    h3: TextStyle?,
    h4: TextStyle?,
    h5: TextStyle?,
    h6: TextStyle?,
    subtitle1: TextStyle?,
    subtitle2: TextStyle?,
    body1: TextStyle?,
    body2: TextStyle?,
    button: TextStyle?,
    caption: TextStyle?,
    overline: TextStyle?
): Typography {
    val defaults = Typography()
    return Typography(
        h1 = defaults.h1.merge(h1),
        h2 = defaults.h2.merge(h2),
        h3 = defaults.h3.merge(h3),
        h4 = defaults.h4.merge(h4),
        h5 = defaults.h5.merge(h5),
        h6 = defaults.h6.merge(h6),
        subtitle1 = defaults.subtitle1.merge(subtitle1),
        subtitle2 = defaults.subtitle2.merge(subtitle2),
        body1 = defaults.body1.merge(body1),
        body2 = defaults.body2.merge(body2),
        button = defaults.button.merge(button),
        caption = defaults.caption.merge(caption),
        overline = defaults.overline.merge(overline)
    )
}