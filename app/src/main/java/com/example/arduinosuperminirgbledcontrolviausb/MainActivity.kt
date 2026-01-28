package com.example.arduinosuperminirgbledcontrolviausb

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.arduinosuperminirgbledcontrolviausb.ui.theme.ArduinoSuperMiniRGBLedControlViaUSBTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Slider
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.material3.Button
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.ExperimentalFoundationApi
import com.example.arduinosuperminirgbledcontrolviausb.usb.DummyUsbSerialManager
import com.example.arduinosuperminirgbledcontrolviausb.usb.UsbSerialManager
import com.example.arduinosuperminirgbledcontrolviausb.usb.UsbSerial
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.UsbManager
import android.os.Build
import android.util.Log
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import com.example.arduinosuperminirgbledcontrolviausb.debug.AppLogger
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt


class MainActivity : ComponentActivity() {

    private lateinit var usbManager: UsbSerialManager

    private val usbPermissionReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {

                UsbSerialManager.ACTION_USB_PERMISSION -> {
                    val granted = intent.getBooleanExtra(
                        UsbManager.EXTRA_PERMISSION_GRANTED, false
                    )
                    AppLogger.log("USB_RX", "Permission granted = $granted")
                    if (granted) {
                        Log.d("USB", "Permission granted")
                        usbManager.tryRequestPermission()
                    }
                }

                UsbManager.ACTION_USB_DEVICE_ATTACHED -> {
                    Log.d("USB", "USB device attached")
                    AppLogger.log("USB_RX", "USB device attached")
                    usbManager.tryRequestPermission()
                }

                UsbManager.ACTION_USB_DEVICE_DETACHED -> {
                    AppLogger.log("USB_RX", "USB device detached")
                    usbManager.disconnect()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        usbManager = UsbSerialManager(this)

        enableEdgeToEdge()
        setContent {
            ArduinoSuperMiniRGBLedControlViaUSBTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RgbController(
                        modifier = Modifier.padding(innerPadding),
                        usbManager = usbManager
                    )
                }
            }
        }
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    override fun onResume() {
        super.onResume()

        val filter = IntentFilter().apply {
            addAction(UsbSerialManager.ACTION_USB_PERMISSION)
            addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED)
            addAction(UsbManager.ACTION_USB_DEVICE_DETACHED)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(
                usbPermissionReceiver,
                filter,
                RECEIVER_NOT_EXPORTED
            )
        } else {
            @Suppress("DEPRECATION")
            registerReceiver(
                usbPermissionReceiver,
                filter
            )
        }

        usbManager.tryRequestPermission()
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(usbPermissionReceiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        usbManager.close()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RgbController(
    modifier: Modifier = Modifier,
    usbManager: UsbSerial
) {
    val isConnected by (usbManager as UsbSerialManager).connected
    var soloLedIndex by remember { mutableStateOf<Int?>(null) }
    var ledEnabled = remember {
        mutableStateListOf(true, true, true)
    }

    var red by remember { mutableFloatStateOf(0f) }
    var green by remember { mutableFloatStateOf(150f) }
    var blue by remember { mutableFloatStateOf(100f) }

    var debugVisible by remember { mutableStateOf(false) } // Debug / ▼ Debug

    var brightness by remember { mutableFloatStateOf(255f) }  // nový slider jas

    val color = Color(
        red = red / 255f,
        green = green / 255f,
        blue = blue / 255f
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        // === LED DLAŽDICE ===
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            for (i in 0..2) {
                val animatedColor by animateColorAsState(
                    targetValue = if (ledEnabled[i]) color else color.copy(alpha = 0.2f),
                    label = "LED_$i"
                )

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(12.dp))
                        .background(animatedColor)
                        .combinedClickable(
                            onClick = { ledEnabled[i] = !ledEnabled[i] },
                            onLongClick = {
                                soloLedIndex = if (soloLedIndex == i) null else i
                                for (j in ledEnabled.indices) {
                                    ledEnabled[j] = (soloLedIndex == null || j == soloLedIndex)
                                }
                            }
                        )
                ) {
                    Text(
                        text = "LED ${i + 1}",
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(6.dp),
                        color = Color.White
                    )
                }
            }
        }

        // === SLIDERY RGB ===
        Text("R: ${red.toInt()}  G: ${green.toInt()}  B: ${blue.toInt()}",
            fontSize = 15.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)) // Material barva pozadí
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            Text("Red", fontSize = 14.sp)
            Slider(
                value = red,
                onValueChange = { red = it },
                valueRange = 0f..255f
            )

            Text("Green", fontSize = 14.sp)
            Slider(
                value = green,
                onValueChange = { green = it },
                valueRange = 0f..255f
            )

            Text("Blue", fontSize = 14.sp)
            Slider(
                value = blue,
                onValueChange = { blue = it },
                valueRange = 0f..255f
            )

            // === BRIGHTNESS SLIDER ===
            val brightnessPercent = ((brightness / 255f) * 100).roundToInt()
            Text("Brightness: $brightnessPercent%", fontSize = 14.sp)

            Slider(
                value = brightness,
                onValueChange = { brightness = it },
                valueRange = 0f..255f
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // === USB STAV ===
        Text(
            text = if (isConnected) "USB: Connected" else "USB: Disconnected",
            color = if (isConnected) Color.Green else Color.Red,
            fontSize = 15.sp
        )

        //Spacer(modifier = Modifier.height(4.dp))

        // === SEND BUTTON ===
        Button(
            onClick = {
                val r = red.toInt().coerceIn(0, 255)
                val g = green.toInt().coerceIn(0, 255)
                val b = blue.toInt().coerceIn(0, 255)
                val bright = brightness.toInt().coerceIn(0, 255)

                AppLogger.log("BTN_SEND", "R=$r G=$g B=$b Brightness=$bright")

                val allOff = ledEnabled.all { !it }
                if (bright == 0 || allOff) {
                    // Odeslat vypnutí všech LED, pokud jas 0 nebo všechny vypnuté
                    for (i in 0..2) {
                        usbManager.sendLed(i, 0, 0, 0)
                    }
                } else {
                    // Počítá upravené RGB podle jasu
                    val adjR = (r * bright / 255f).toInt()
                    val adjG = (g * bright / 255f).toInt()
                    val adjB = (b * bright / 255f).toInt()

                    for (i in 0..2) {
                        if (ledEnabled[i]) {
                            usbManager.sendLed(i, adjR, adjG, adjB)
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 14.dp)
        ) {
            Text("SEND")
        }

        Spacer(modifier = Modifier.height(12.dp))

        // === DEBUG KONZOLE POD TLAČÍTEM SEND ===
        Text(
            text = if (debugVisible) "▼ Debug Console" else "▶ Debug Console",
            color = Color.Gray,
            modifier = Modifier
                .padding(vertical = 4.dp)
                .combinedClickable(
                    onClick = { debugVisible = !debugVisible }
                )
        )

        if (debugVisible) {
            DebugConsole(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(6.dp))
            )
        }
    }
}

@Composable
fun DebugConsole(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(Color.Black)
            .padding(6.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "DEBUG CONSOLE:",
            color = Color.Green,
            fontSize = 12.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        AppLogger.logs.forEach { logLine ->
            Text(
                text = logLine,
                color = Color.White,
                fontSize = 10.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RgbControllerPreview() {
    ArduinoSuperMiniRGBLedControlViaUSBTheme {
        RgbController(
            usbManager = DummyUsbSerialManager()
        )
    }
}





