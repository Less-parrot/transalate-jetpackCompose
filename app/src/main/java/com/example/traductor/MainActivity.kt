package com.example.traductor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.traductor.ui.theme.TraductorTheme
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}


@Composable
fun MainScreen() {
    TraductorTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xF0171A4A)
        ) {
            //
        }
    }
}


fun translateText(inputText: String, onTranslationComplete: (String?) -> Unit) {
    val options = TranslatorOptions.Builder()
        .setSourceLanguage(TranslateLanguage.SPANISH)
        .setTargetLanguage(TranslateLanguage.ENGLISH)
        .build()

    val translator = Translation.getClient(options)

    val conditions = DownloadConditions.Builder()
        .requireWifi()
        .build()

    translator.downloadModelIfNeeded(conditions)
        .addOnSuccessListener {
            translator.translate(inputText)
                .addOnSuccessListener { translatedText ->
                    onTranslationComplete(translatedText)
                }
                .addOnFailureListener { exception ->
                    onTranslationComplete(null) // Maneja errores aquí
                }
        }
        .addOnFailureListener { exception ->
            onTranslationComplete(null) // Maneja errores aquí
        }
}



