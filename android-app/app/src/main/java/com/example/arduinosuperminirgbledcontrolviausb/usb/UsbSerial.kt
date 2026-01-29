package com.example.arduinosuperminirgbledcontrolviausb.usb

interface UsbSerial {
    fun connect(): Boolean
    fun sendLed(index: Int, r: Int, g: Int, b: Int)
    fun close()
}

