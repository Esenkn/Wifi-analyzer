# WiFi Analyzer

A lightweight WiFi network dashboard designed for both a browser demo and a native Android app.

## Project status

This repository now contains:

- a static web prototype at `index.html`
- an Android app project under `app/` using Jetpack Compose

## Web version

Open `index.html` directly in a browser or run:

```bash
cd Wifi-analyzer
python3 -m http.server 8000
```

Then open:

```text
http://localhost:8000
```

## Android app version

This project is structured as a standard Android app using Kotlin and Jetpack Compose.

### Prerequisites

- Android Studio
- Android SDK 34
- JDK 17

### Open in Android Studio

1. Open the repository in Android Studio.
2. Let Gradle sync.
3. Select the `app` module.
4. Run the app on an emulator or connected Android device.

### Build from the command line

```bash
./gradlew assembleDebug
```

If you are using a Windows machine:

```powershell
gradlew.bat assembleDebug
```

## Features

- connected network details
- signal strength and band information
- nearby network scanning simulation
- channel recommendation logic

## Future improvements

- real Android WiFi API integration
- RSSI trend charts
- channel overlap analysis
- export and save scan reports
- more advanced network diagnostics
