<p align="center" width="100%">
    <img width="20%" src="images/top.jpeg">
    <img width="20%" src="images/bottom.jpeg">
    <img width="25%" src="images/pinout.jpeg">
</p>

# <img width="4%" src="images/app_icon_lower.png"> Arduino USB RGB LED Controller

Android application + Arduino firmware for controlling **3× built-in RGB LEDs** on a 3rd party **Arduino Super Mini board** via OTG USB.

The Android app communicates with the Arduino onboard RGB LEDs on the pin `D2` via USB and sends RGB + brightness values in real time.

<br>

---

## Project structure

- android-app/ — Android application (Jetpack Compose)
- arduino/ — Arduino firmware for SuperMini / Nano-compatible board
- images/ — photos and screenshots

<br>

---

## ✨ Features

* 🎨 RGB color control (Red / Green / Blue sliders)
* 🔆 Global brightness control (0–100%)
* 💡 Enable / disable individual LEDs
* 🔁 Solo LED mode (long-press)
* 🔌 USB connection status
* 🐞 Debug console (expandable)
* 🚫 No ads, no tracking, no data collection

<br>

---

## 📱 Requirements

* Android device with **USB OTG support**
* **USB OTG adapter or OTG cable** (required)
* Android 8.0 (API 26) or newer recommended
* Arduino connected via USB
* Arduino SuperMini board with **3 built-in RGB LEDs**
> ⚠️ Note: The board name "Super Mini" is commonly used by 3rd party vendors and does not refer to an official Arduino product.

<br>

---

## ⚙️ Setup for Arduino Firmware:

📂 Download `.ino` file from path: `arduino/Arduino-USB-RGB-LED-Controller.ino` <br>
📂 Open downloaded `Arduino-USB-RGB-LED-Controller.ino` file in Arduino IDE <br><br>

Connect your Arduino SuperMini board to the PC via USB <br><br>

In **Arduino IDE**, select:

- **Board:** `Arduino Nano`
- **Processor:** `ATmega328P (Old Bootloader)`
- **Port:** (the COM port where the board appears)<br><br>


- **Install Adafruit Neopixel Library to the IDE:**
  - In the left column, click on the 📓 library icon,
  - Type `neopixel` in the search field and search for: `Adafruit NeoPixel (from Adafruit)` ➜ Click the install button <br><br>


- **Clic on Upload button <img width="2%" src="images/UNO_Upload.png">**




<br>

---

## ⚙️ Setup for Android App:
Lorem ipsum ...

<br>

---

## 🖼 Images

Board photos and app screenshots can be found in:

<img width="25%" src="images/app_screenshot">

<br>

---

## 🔌 USB Connection

1. Connect the Arduino board to your Android device using a **USB OTG adapter/cable**
2. Launch the app
3. Grant USB permission when prompted
4. The status text should show **USB: Connected**

If the device is disconnected or permission is revoked, the app will update the status automatically.

<br>

---

## 🚀 Usage

1. Adjust **Red, Green, Blue** sliders to set the base color
2. Adjust **Brightness** slider to control overall intensity
3. Tap LED tiles to enable/disable individual LEDs
4. Long-press an LED tile to enter **solo mode**
5. Press **SEND** to transmit values to the Arduino

If brightness is set to 0% or all color tiles are disabled, the app will turn all LEDs off.

<br>

---

## 🐞 Debug Console

* Expandable debug console is available at the bottom of the UI
* Shows USB events, permissions, and sent values
* Useful for development and troubleshooting

<br>

---

## 🛠 Development

* Written in **Kotlin**
* UI built with **Jetpack Compose**
* USB communication handled via custom `UsbSerialManager`
* No third-party analytics or advertising libraries

### Build

You can build the app using **Android Studio**:

```bash
./gradlew assembleDebug
```

Or generate a release APK/AAB for distribution.

<br>

---

## 📦 Distribution

* Planned for **GitHub Releases**
* Planned for **Google Play Store** (free, no ads)

<br>

---

## 📄 License

<a href="/LICENSE">MIT License</a>

<br>

---

## 🙌 Acknowledgements

Inspired by hardware-level RGB control and simple USB-based tools for makers.<br>

Feel free to open issues or pull requests if you want to improve or extend the project.

<br>

