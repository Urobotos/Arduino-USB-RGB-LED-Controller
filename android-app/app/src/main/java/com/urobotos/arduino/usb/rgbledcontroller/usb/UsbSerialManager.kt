package com.urobotos.arduino.usb.rgbledcontroller.usb

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbDeviceConnection
import android.hardware.usb.UsbManager
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.hoho.android.usbserial.driver.UsbSerialPort
import com.hoho.android.usbserial.driver.UsbSerialProber
import com.urobotos.arduino.usb.rgbledcontroller.AppLogger

class UsbSerialManager(private val context: Context) : UsbSerial {

    companion object {
        const val ACTION_USB_PERMISSION =
            "com.urobotos.arduino.usb.rgbledcontroller.USB_PERMISSION"
    }

    private val _connected = mutableStateOf(false)
    val connected: State<Boolean> = _connected

    private var port: UsbSerialPort? = null
    private var connection: UsbDeviceConnection? = null

    fun tryRequestPermission() {
        val usbManager = context.getSystemService(Context.USB_SERVICE) as UsbManager
        val drivers = UsbSerialProber.getDefaultProber().findAllDrivers(usbManager)

        if (drivers.isEmpty()) return

        val device = drivers[0].device

        if (!usbManager.hasPermission(device)) {
            requestPermission(usbManager, device)
        } else {
            connectInternal()
        }
    }

    override fun connect(): Boolean {
        val usbManager = context.getSystemService(Context.USB_SERVICE) as UsbManager
        val drivers = UsbSerialProber.getDefaultProber().findAllDrivers(usbManager)
        AppLogger.log("USB_MGR", "Found ${drivers.size} drivers")
        if (drivers.isEmpty()) return false

        val device = drivers[0].device

        if (!usbManager.hasPermission(device)) {
            requestPermission(usbManager, device)
            return false
        }

        return connectInternal()
    }

    private fun connectInternal(): Boolean {
        val usbManager = context.getSystemService(Context.USB_SERVICE) as UsbManager
        val driver = UsbSerialProber.getDefaultProber()
            .findAllDrivers(usbManager)
            .firstOrNull() ?: return false

        connection = usbManager.openDevice(driver.device)
        port = driver.ports[0]

        port?.open(connection)
        port?.setParameters(
            115200,
            8,
            UsbSerialPort.STOPBITS_1,
            UsbSerialPort.PARITY_NONE
        )
            _connected.value = true
            AppLogger.log("USB_MGR", "Connected")
            return true
    }

    private fun requestPermission(usbManager: UsbManager, device: UsbDevice) {
        val intent = PendingIntent.getBroadcast(
            context,
            0,
            Intent(ACTION_USB_PERMISSION),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        usbManager.requestPermission(device, intent)
        Log.d("USB", "Requesting USB permission")
    }


    override fun sendLed(index: Int, r: Int, g: Int, b: Int) {
        if (!isConnected()) {
            AppLogger.log("USB_SEND", "Not connected, command skipped")
            return
        }

        val cmd = "L$index:$r,$g,$b"

        try {
            port?.write((cmd + "\n").toByteArray(), 100)
            AppLogger.log("USB_SEND", "Sent: $cmd")
        } catch (e: Exception) {
            AppLogger.log("USB_SEND", "Error: ${e.message}")
            disconnect()
        }
    }

    fun disconnect() {
        try {
            port?.close()
            connection?.close()
        } catch (_: Exception) {
        } finally {
            port = null
            connection = null
            _connected.value = false
            AppLogger.log("USB_MGR", "Disconnected")
        }
    }

    override fun close() {
        disconnect()
    }

    fun isConnected(): Boolean {
        return try {
            port != null && connection != null
        } catch (_: Exception) {
            false
        }
    }
}