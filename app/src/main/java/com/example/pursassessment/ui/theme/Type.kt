package com.example.pursassessment.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.pursassessment.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val fontNameChivo = GoogleFont("Chivo")
val fontNameHind = GoogleFont("Hind Siliguri")
val fontNameFira = GoogleFont("Fira Sans")

val firaFontFamily = FontFamily(
    Font(
        googleFont = fontNameFira,
        fontProvider = provider
    )
)
val chivoFontFamily = FontFamily(
    Font(
        googleFont = fontNameChivo,
        fontProvider = provider
    )
)
val hindFontFamily = FontFamily(
    Font(
        googleFont = fontNameHind,
        fontProvider = provider
    )
)
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)
val restaurantNameTextStyle = TextStyle(
    color = Color.White,
    fontSize = 59.sp,
    fontWeight = FontWeight.ExtraBold,
    lineHeight = 65.sp,
    letterSpacing = 0.sp,
    fontFamily = firaFontFamily
)

val descriptionStyle = TextStyle(
    fontSize = 12.sp,
    fontWeight = FontWeight.W900,
    lineHeight = 14.sp,
    letterSpacing = 0.sp,
    color = Color.Gray,
    fontFamily = chivoFontFamily
)

val operationalHoursStyle = TextStyle(
    fontSize = 18.sp,
    fontWeight = FontWeight.W400,
    lineHeight = 29.sp,
    letterSpacing = 0.sp,
    color = Color.Black,
    fontFamily = hindFontFamily
)
val viewMenuStyle = TextStyle(
    fontSize = 18.sp,
    fontWeight = FontWeight.W400,
    color = Color.White,
    fontFamily = hindFontFamily
)

val operationDaysStyle = TextStyle(
    fontSize = 18.sp,
    fontWeight = FontWeight.W400,
    color = Color.Black,
    fontFamily = hindFontFamily,
    textAlign = TextAlign.Start
)

val operationDaysStyleBold = TextStyle(
    fontSize = 18.sp,
    fontWeight = FontWeight.W900,
    color = Color.Black,
    fontFamily = hindFontFamily,
    textAlign = TextAlign.Start
)

val operationHoursStyle = TextStyle(
    fontSize = 18.sp,
    fontWeight = FontWeight.W400,
    color = Color.Black,
    fontFamily = hindFontFamily,
    textAlign = TextAlign.End
)

val operationHoursStyleBold = TextStyle(
    fontSize = 18.sp,
    fontWeight = FontWeight.W900,
    color = Color.Black,
    fontFamily = hindFontFamily,
    textAlign = TextAlign.End
)