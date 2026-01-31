<p align="center" width="100%">
    <img width="20%" src="images/top.jpeg">
    <img width="20%" src="images/bottom.jpeg">
    <img width="25%" src="images/pinout.jpeg">
</p>


# <img width="4%" src="images/app_icon_lower.png"> Arduino USB RGB LED Controller

Android application + Arduino firmware for controlling 3× built-in **RGB LEDs** on a 3rd party **Arduino Super Mini board** via **OTG USB**.

The Android app communicates with the Arduino via USB and controls the onboard RGB LEDs connected to pin D2, sending RGB and brightness values in real time.

<br>

---

## Project structure

- Releases (Only for Android) — Final Android APK packages to install on your phone, link here: [Releases](https://github.com/Urobotos/Arduino-USB-RGB-LED-Controller/releases)
- android-app/ — Android application (Jetpack Compose)
- arduino/ — Arduino firmware (.ino) for SuperMini / Nano-compatible board
- images/ — photos and screenshots <br>

<br>

---

### Jump Navigation:

- ⚙️ [Setup for Arduino Firmware](#%EF%B8%8F-setup-for-arduino-firmware)
- ⚙️ [Setup_for_Android_App](#%EF%B8%8F-setup-for-android-app)
- 🔌 [USB Connection of Android to Arduino board](#-usb-connection-of-android-to-arduino-board)
- 📱 [App Screenshots](#-app-screenshots)
- 🚀 [Usage](#-usage)
- 🐞 [Debug Console](#-debug-console)
- 🛠 [Development](#-development)
- 📄 [Distribution, License and Acknowledgements](#-distribution)

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
* **USB OTG adapter or OTG cable (REQUIRED)**
* Android 8.0 (API 26) or newer recommended
* Arduino connected via USB
* Arduino SuperMini board with **3 built-in RGB LEDs**
> ⚠️ Note: The board name "Super Mini" is commonly used by 3rd party vendors and does not refer to an official Arduino product.

<br>

---

## ⚙️ Setup for Arduino Firmware:

📂 Download <a href="/arduino/Arduino-USB-RGB-LED-Controller.ino">Arduino-USB-RGB-LED-Controller.ino</a> file from `arduino/` directory <br>

📂 Open downloaded `Arduino-USB-RGB-LED-Controller.ino` file in Arduino IDE <br><br>

🔌 Connect your Arduino SuperMini board to the PC via USB cable <br><br>

**In Arduino IDE, select:**

- **Board:** `Arduino Nano`
- **Processor:** `ATmega328P (Old Bootloader)`
- **Port:** (the COM port where the board appears)<br><br>


📦 **Install required library:** <br>
  &nbsp; &nbsp; &nbsp; (This project uses WS2812 / NeoPixel RGB LEDs)
  
- Open Library Manager (📓 icon on the left bar)
- Search for: `Adafruit NeoPixel`
- Install `Adafruit NeoPixel (by Adafruit)` <br><br>


🚀 **Upload firmware:**
- Now you can click the **Upload button** &nbsp;<img width="25px" src="images/UNO_Upload.png">&nbsp; in the IDE <br><br>

✔️ After successful upload, the Arduino is ready to communicate with the Android app via USB. <br>
ℹ️ If upload fails, double-check that `Old Bootloader` is selected.


<br>

---

## ⚙️ Setup for Android App:
You can install the prepared release APK directly, build the APK manually, or open and build the project in Android Studio. <br><br>

📂 **Project structure:** <br>
The Android project is located in: <br> 

```
android-app/
```

<br>

### Option A): Install prepared release APK (recommended):

1️⃣ Download the latest release APK from the [GitHub Releases page](https://github.com/Urobotos/Arduino-USB-RGB-LED-Controller/releases) <br>
Look for the asset named similar to `arduino-usb-rgb-led-controller-v1.0.0.apk`.<br><br>

2️⃣ Transfer the APK file to your Android phone <br>
You can use `Google Drive`, `email`, `USB cable`, or any file transfer method.<br><br>

3️⃣ On your Android phone, open the APK file using a `File manager` app <br>
You may be prompted to allow installation of apps from unknown sources — accept and confirm.<br><br>

4️⃣ Install the app <br>
Once installed, you can launch it from your app drawer.

<br>


### Option B): Build APK using command line (Gradle):

This option is intended for developers who want to build the APK themselves
from source code using Gradle. <br>

*(To run `Gradle commands`, only the `JDK (Java Development Kit)` must be installed on your system, Gradle will download the other required dependencies itself and build the application).*

<br>

#### 1️⃣ Download the source code (which includes the Gradle wrapper):

*(You can either download the repository as a ZIP file from GitHub,
or clone it using Git).* <br>

<br>

**Using Git (recommended):**

```
git clone https://github.com/Urobotos/Arduino-USB-RGB-LED-Controller.git
```

<br>

*On Linux and macOS, `Git` is usually installed by default. <br>
On Windows, you may need to install `Git` first: https://git-scm.com/* <br>

<br>

#### 2️⃣ Navigate to the Android project directory:

After cloning the repository, move into the `android-app` directory:

```
cd Arduino-USB-RGB-LED-Controller/android-app
```

<br>

*⚠️ All Gradle commands must be executed inside the `android-app` folder!*

<br>

#### 3️⃣ Build debug APK:

Run the Gradle wrapper to build a debug APK:

```
./gradlew assembleDebug
```

On Windows (PowerShell or Command Prompt), use:

```
.\gradlew assembleDebug
```

<br>

#### 4️⃣ Locate the generated APK:

After a successful build, the APK will be created at:

```
android-app/app/build/outputs/apk/debug/app-debug.apk
```

<br>

#### 5️⃣ Install the APK on your Android phone:
1. Transfer `app-debug.apk` to your Android phone (Google Drive, USB cable, email, etc.)

2. Open the file using a `File manager` app

3. Allow installation from unknown sources if prompted

4. Install the application


*(Permission dialogs and wording may vary depending on Android version and device manufacturer)*

<br>


### Option C): Open project in Android Studio:

*(Direct installation of the application using a USB cable from Android Studio to the phone)*

1️⃣ Open Android Studio <br>
2️⃣ Select Open and choose the `android-app` folder <br>
3️⃣ Wait for Gradle sync to finish <br>
4️⃣ Connect your Android phone via USB (with USB debugging enabled) <br>
5️⃣ Check in `LogCat` (cat icon 🐱 in the left panel) that your phone is connected to Android Studio <br>
6️⃣ Click Run ▶️ <br>

Android Studio will build and install the app automatically.


<br>

---

## 🔌 USB Connection of Android to Arduino board:

1. Connect the Arduino board to your Android device using a **USB OTG adapter/cable**
2. Launch the app
3. Grant USB permission when prompted
4. The status text in the app should show **USB: Connected**

If the device is disconnected or permission is revoked, the app will update the status automatically.

<br>

---

## 📱 App Screenshots:

<br>
<p align="center" width="100%" text="strong">
    &nbsp; USB Disconnected: &nbsp; &nbsp;&nbsp;
    &nbsp; USB Permission: &nbsp; &nbsp; &nbsp; 
    &nbsp;&nbsp; USB Connected: &nbsp; &nbsp; 
    &nbsp; &nbsp; &nbsp; One LED control: &nbsp;
    &nbsp; &nbsp; &nbsp; Debug Console: &nbsp; &nbsp;
</p>
<p align="center" width="100%">
    <img width="19%" src="images/screenshot_disconnected.jpg">
    <img width="19%" src="images/screenshot_permission.jpg">
    <img width="19%" src="images/screenshot_connected.jpg">
    <img width="19%" src="images/screenshot_long_press.jpg">
    <img width="19%" src="images/screenshot_debug_console.jpg">
</p>

<br><br>

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

<br>

---

## 📦 Distribution

* **GitHub Releases**
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

