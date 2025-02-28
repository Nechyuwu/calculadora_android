package com.example.calculadorapro

import android.os.Bundle
import android.service.autofill.OnClickAction
import android.text.style.BackgroundColorSpan
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculadorapro.ui.theme.CalculadoraProTheme


data class BotonModelo(val id: String,var numero: String){

}

var hileras_de_botones_a_dibujar = arrayOf(
    arrayOf(
        BotonModelo("9","9"),
        BotonModelo("8","8"),
        BotonModelo("7","7")
    ),
    arrayOf(
        BotonModelo("6","6"),
        BotonModelo("5","5"),
        BotonModelo("4","4")
    ),
    arrayOf(
        BotonModelo("3","3"),
        BotonModelo("2","2"),
        BotonModelo("1","1")
    ),
    arrayOf(
        BotonModelo("punto","."),
        BotonModelo("0","0"),
        BotonModelo("boton operacion","op")
    )
)


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
    var pantalla_calculadora = remember { mutableSetOf("0") }
    var estado_de_la_calculadora = rember { mutableSetOf("Mostrar_numeros")}
    fun dibujar_pantalla(boton: BotonModelo){
     if(boton.id == "boton operacion"){
         estado_de_la_calculadora.value = "mostrar operacion"
     }
        if (estado_de_la_calculadora.value == "mostrar operaciones"){
            pantalla_calculadora.value = boton.numero
        }
        else{
            pantalla_calculadora.value = boton.id
        }
    }
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center) {
        Text(
            text = "${pantalla_calculadora.value}", modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .background(Color.Blue)
                .height(50.dp),
            textAlign = TextAlign.Right
        )

        // Deberia jugar mas con el estilo de aqui
        Column(modifier = Modifier.fillMaxSize().background(Color.DarkGray)) {
            for (Hilera_de_botones in hileras_de_botones_a_dibujar){
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    for(boton_a_dibujar in Hilera_de_botones){
                        Boton(boton_a_dibujar.numero, alPulsar = {
                            pulsar_boton()
                        })

                    }
                }
            }
        }
    }
}

@Composable
fun Boton(etiqueta: String, alPulsar: () -> Unit){
    Button(onClick = alPulsar) {
        Image(
            painter = painterResource(R.drawable.hard),
            contentDescription = "Una foto hard",
            modifier = Modifier.size(45.dp)
        )

        Text(etiqueta, modifier = Modifier
            //.background(Color.Green)
            .background(Color.Green),
            textAlign = TextAlign.Center,
            color = Color.Red
        )
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CalculadoraProTheme {
        Calculadora()
    }
}

@Preview(showBackground = true)
@Composable
fun mostrar_boton(){
    CalculadoraProTheme {
        Boton("4", alPulsar = {})
    }
}