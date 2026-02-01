package com.urobotos.arduino.usb.rgbledcontroller.usb

import android.util.Log
import com.urobotos.arduino.usb.rgbledcontroller.usb.UsbSerial

class DummyUsbSerialManager : UsbSerial {

    override fun connect(): Boolean {
        Log.d("USB_DUMMY", "connect()")
        return true
    }

    override fun sendLed(index: Int, r: Int, g: Int, b: Int) {
        Log.d("USB_DUMMY", "LED $index -> R:$r G:$g B:$b")
    }

    override fun close() {
        Log.d("USB_DUMMY", "close()")
    }
}