package com.example.calculadorapro

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadorapro.ui.theme.CalculadoraProTheme


data class BotonModelo(
    val id: String,
    var numero: String,
    var Operacion_Aritmetica:OperacionesAritmeticas = OperacionesAritmeticas.ninguna,
    var operacion_a_mostrar:String =""){}

enum class EstadosCalculadora{
    CuandoEstaEnCero,
    AgregandoNumeros,
    SeleccionandoOperacion,
    MostrandoResultado
}

enum class OperacionesAritmeticas{
    ninguna,
    suma,
    resta,
    multiplicacion,
    divicion,
    resultado
}

var hileras_de_botones_a_dibujar = arrayOf(
    arrayOf(
        BotonModelo("boton_9", "9", OperacionesAritmeticas.multiplicacion,"*"),
        BotonModelo("boton_8", "8"),
        BotonModelo("boton_7", "7", OperacionesAritmeticas.divicion,"/"),
    ),
    arrayOf(
        BotonModelo("boton_6", "6"),
        BotonModelo("boton_5", "5",OperacionesAritmeticas.resultado,"="),
        BotonModelo("boton_4", "4"),
    ),
    arrayOf(
        BotonModelo("boton_3", "3",OperacionesAritmeticas.suma,"+"),
        BotonModelo("boton_2", "2"),
        BotonModelo("boton_1", "1",OperacionesAritmeticas.resta,"-"),
    ),
    arrayOf(
        BotonModelo("boton_punto", "."),
        BotonModelo("boton_0", "0"),
        BotonModelo("boton_operacion", "OP"),
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
    var pantalla_calculadora = remember { mutableStateOf("0") }
    var numero_anterior = remember { mutableStateOf("0") }
    var estado_de_la_calculadora = remember { mutableStateOf(EstadosCalculadora.CuandoEstaEnCero) }
    var operacion_seleccionada = remember{ mutableStateOf(OperacionesAritmeticas.ninguna)}
    fun pulsar_boton(boton: BotonModelo){
        Log.v("BOTONES_INTERFAZ", "Se ha pulsado el boton ${boton.id} de la interfaz")

        when(estado_de_la_calculadora.value){
            EstadosCalculadora.CuandoEstaEnCero -> {
                if(boton.id == "boton_0"){
                    return
                }
                else if(boton.id == "boton_punto"){
                    pantalla_calculadora.value =  pantalla_calculadora.value + boton.numero
                    return
                }
                pantalla_calculadora.value = boton.numero
                estado_de_la_calculadora.value = EstadosCalculadora.AgregandoNumeros
            }

            EstadosCalculadora.AgregandoNumeros -> {
                if(boton.id == "boton_operacion"){
                    estado_de_la_calculadora.value = EstadosCalculadora.SeleccionandoOperacion
                    return
                }
                pantalla_calculadora.value =  pantalla_calculadora.value + boton.numero
            }

            EstadosCalculadora.SeleccionandoOperacion -> {
                if(boton.Operacion_Aritmetica != OperacionesAritmeticas.ninguna ||
                    boton.Operacion_Aritmetica != OperacionesAritmeticas.resultado
                    ){
                    operacion_seleccionada.value = boton.Operacion_Aritmetica
                    estado_de_la_calculadora.value = EstadosCalculadora.AgregandoNumeros

                    numero_anterior.value = pantalla_calculadora.value
                    pantalla_calculadora.value ="0"
                    return
                }
                else if (boton.Operacion_Aritmetica == OperacionesAritmeticas.resultado && operacion_seleccionada.value != OperacionesAritmeticas.ninguna){
                    pantalla_calculadora.value = "que pro"

                    estado_de_la_calculadora.value = EstadosCalculadora.MostrandoResultado

                    return

                }
                estado_de_la_calculadora.value = EstadosCalculadora.AgregandoNumeros
            }

            EstadosCalculadora.MostrandoResultado -> TODO()
        }

    }
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "${pantalla_calculadora.value}", modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .fillMaxHeight(0.33f)
            .background(Color.Blue)
            .height(50.dp),
            textAlign = TextAlign.Right,
            color = Color.White,
            fontSize = 56.sp
        )

        // Deberia jugar mas con el estilo de aqui
        Column(modifier = Modifier.fillMaxSize().background(Color.DarkGray)) {
            for(fila_de_botones in hileras_de_botones_a_dibujar){
                Row(horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxWidth()) {
                    for(boton_a_dibujar in fila_de_botones){
                        when(estado_de_la_calculadora.value){
                            EstadosCalculadora.SeleccionandoOperacion ->{
                                Boton(boton_a_dibujar.operacion_a_mostrar, alPulsar = {
                                    pulsar_boton(boton_a_dibujar)
                                })

                            }
                            else ->{
                                Boton(boton_a_dibujar.numero, alPulsar = {
                                    pulsar_boton(boton_a_dibujar)
                                })

                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Boton(etiqueta: String, alPulsar: () -> Unit ={}){
    Button(onClick = alPulsar, modifier = Modifier.fillMaxHeight( 0.2f)) {
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