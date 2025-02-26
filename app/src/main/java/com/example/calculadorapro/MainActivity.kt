package com.example.calculadorapro

import android.os.Bundle
import android.text.style.BackgroundColorSpan
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.calculadorapro.ui.theme.CalculadoraProTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraProTheme {

                    Calculadora()

            }
        }
    }
}

@Composable
fun Calculadora() {
    Column (modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center){
        Text(text = "Aqui van numero")

        Column (modifier = Modifier.fillMaxSize().background(Color.Red) ){
            Row (horizontalArrangement = Arrangement){

            }
        }
    }



    Text(
        text = "Hello !",

    )
}

@Composable
fun Boton(etiqueta: String){
    Text(
        etiqueta, modifier = Modifier.background(Color.Green)
            .padding(10.dp)
    )
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CalculadoraProTheme {
        Calculadora("Android")
    }
}