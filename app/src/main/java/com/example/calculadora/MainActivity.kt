

package com.example.calculadora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculadora.ui.theme.CalculadoraTheme
import java.text.NumberFormat

// ACTIVIDAD PRINCIPAL: inicia la aplicación Android.
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // Inicializa la Activity.
        enableEdgeToEdge() // Permite dibujar detrás de las barras del sistema.

        setContent { // Define la interfaz con Jetpack Compose.
            CalculadoraTheme { // Aplica el tema de la aplicación.
                Surface(
                    modifier = Modifier.fillMaxSize() // Ocupa toda la pantalla.
                ) {
                    TipTimeLayout() // Muestra la pantalla principal.
                }
            }
        }
    }
}

// PANTALLA PRINCIPAL: controla el estado y muestra la propina.
@Composable
fun TipTimeLayout() {

    var amountInput by remember { mutableStateOf("") } // Estado: conserva el texto entre recomposiciones.

    val amount = amountInput.toDoubleOrNull() ?: 0.0 // Convierte a Double; si falla, utiliza 0.0.
    val tip = calculateTip(amount) // Calcula la propina a partir del importe.

    Column(
        modifier = Modifier
            .statusBarsPadding() // Respeta la barra de estado.
            .padding(horizontal = 40.dp) // Añade márgenes laterales.
            .verticalScroll(rememberScrollState()) // Permite desplazamiento vertical.
            .safeDrawingPadding(), // Respeta las zonas seguras de la pantalla.
        horizontalAlignment = Alignment.CenterHorizontally, // Centra los elementos horizontalmente.
        verticalArrangement = Arrangement.Center // Centra verticalmente si existe espacio disponible.
    ) {

        Text(
            text = stringResource(R.string.calculate_tip), // Texto guardado en strings.xml.
            modifier = Modifier
                .padding(bottom = 16.dp, top = 40.dp) // Separación superior e inferior.
                .align(Alignment.Start) // Alinea el título a la izquierda.
        )

        EditNumberField(
            value = amountInput, // Envía el estado al campo de texto.
            onValueChange = { amountInput = it }, // Actualiza el estado con el texto introducido.
            modifier = Modifier
                .padding(bottom = 32.dp) // Separación inferior.
                .fillMaxWidth() // Ocupa todo el ancho disponible.
        )

        Text(
            text = stringResource(R.string.tip_amount, tip), // Muestra el resultado calculado.
            style = MaterialTheme.typography.displaySmall // Aplica un estilo de texto grande.
        )

        Spacer(modifier = Modifier.height(150.dp)) // Añade espacio vertical.
    }
}

// CAMPO DE TEXTO: recibe un valor y comunica sus modificaciones.
@Composable
fun EditNumberField(
    value: String, // Texto que debe mostrar el campo.
    onValueChange: (String) -> Unit, // Callback: recibe String y no devuelve nada (Unit).
    modifier: Modifier = Modifier // Modificador opcional con valor predeterminado.
) {
    TextField(
        value = value, // Muestra el valor recibido.
        onValueChange = onValueChange, // Notifica cada cambio de texto.
        modifier = modifier, // Aplica los modificadores externos.
        singleLine = true, // Limita el campo a una sola línea.
        label = {
            Text(stringResource(R.string.bill_amount)) // Etiqueta del campo.
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number // Solicita el teclado numérico.
        )
    )
}

// LÓGICA: calcula y formatea la propina.
private fun calculateTip(
    amount: Double, // Importe de la factura.
    tipPercent: Double = 15.0 // Parámetro opcional: 15 % por defecto.
): String {

    val tip = tipPercent / 100 * amount // Calcula el porcentaje.

    return NumberFormat.getCurrencyInstance().format(tip) // Devuelve texto con formato monetario.
}

// VISTA PREVIA: muestra la interfaz sin ejecutar el emulador.
@Preview(showBackground = true)
@Composable
fun TipTimeLayoutPreview() {
    CalculadoraTheme { // Mantiene el tema visual.
        TipTimeLayout() // Previsualiza la pantalla principal.
    }
}
