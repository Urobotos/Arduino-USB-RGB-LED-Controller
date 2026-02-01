package com.urobotos.arduino.usb.rgbledcontroller.usb

interface UsbSerial {
    fun connect(): Boolean
    fun sendLed(index: Int, r: Int, g: Int, b: Int)
    fun close()
}