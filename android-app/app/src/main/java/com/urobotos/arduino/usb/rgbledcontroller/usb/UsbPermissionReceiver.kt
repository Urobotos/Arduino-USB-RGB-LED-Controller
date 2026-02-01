package com.urobotos.arduino.usb.rgbledcontroller.usb

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.hardware.usb.UsbManager
import android.util.Log

class UsbPermissionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != UsbSerialManager.ACTION_USB_PERMISSION) return

        val granted = intent.getBooleanExtra(
            UsbManager.EXTRA_PERMISSION_GRANTED, false
        )

        if (granted) {
            Log.d("USB", "Permission granted – reconnecting")
            UsbSerialManager(context).connect()
        } else {
            Log.e("USB", "USB permission denied")
        }
    }

}