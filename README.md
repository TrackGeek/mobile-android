<p align="center">
  <img src="https://github.com/TrackGeek.png" height="100px">
</p>

<h1 align="center">
  <samp>Mobile Android</samp>
</h1>

<h4 align="center">
  <samp>Android app for tracking all your media in one unified platform.</samp>
</h4>

<p align="center">
  <img src="https://img.shields.io/badge/Kotlin-000000?style=for-the-badge&logo=kotlin&logoColor=FFFFFF">
  <img src="https://img.shields.io/badge/Jetpack%20Compose-000000?style=for-the-badge&logo=jetpackcompose&logoColor=FFFFFF">
  <img src="https://img.shields.io/badge/Android-000000?style=for-the-badge&logo=android&logoColor=FFFFFF">
  <img src="https://img.shields.io/badge/Material%203-000000?style=for-the-badge&logo=materialdesign&logoColor=FFFFFF">
  <a href="https://translate.trackgeek.net"><img src="https://img.shields.io/badge/Crowdin-000000?style=for-the-badge&logo=crowdin&logoColor=FFFFFF"></a>
</p>

## <samp>Features</samp>

<samp>

- Native Android app with modern Material Design 3;
- Track games, anime, movies, TV shows, books, and manga with progress system;
- Custom lists management;
- User profiles with favorites;
- Full i18n support;
- Feed with real-time updates;
- Push notifications for upcoming releases;
- Image gallery and screenshots;
- Content relationship viewer;
- Smooth animations and transitions;
- Responsive layouts for tablets.

</samp>

## <samp>Tech Stack</samp>

<samp>

- Kotlin
- Jetpack Compose
- Material Design 3
- Android SDK 36
- Coroutines
- Flow
- Retrofit
- Hilt/Dagger
- Coil

</samp>

## <samp>Run Locally</samp>

<samp>

### Prerequisites

- Android Studio Ladybug or newer
- JDK 11 or higher
- Android SDK 36
- Physical device or emulator with Android 10 (API 29) or higher

### Steps

1. Clone the project

```bash
git clone https://github.com/TrackGeek/mobile-android.git
```

2. Open Android Studio

- Launch Android Studio
- Click on "Open" or "Open an Existing Project"
- Navigate to the cloned repository folder and select it
- Wait for Gradle sync to complete

3. Configure the project

- Copy `local.properties.example` to `local.properties` if needed
- Update API endpoints in the configuration files

4. Build the project

- Click on "Build" > "Make Project" (or press Ctrl+F9 / Cmd+F9)
- Wait for the build to complete successfully

5. Run the app

- Connect your Android device via USB with USB debugging enabled, or start an emulator
- Click on the "Run" button (green play icon) or press Shift+F10 / Ctrl+R
- Select your target device
- Wait for the app to install and launch

### Build APK

To generate a release APK:

```bash
./gradlew assembleRelease
```

The APK will be generated in `app/build/outputs/apk/release/`

### Build AAB (Android App Bundle)

For Play Store distribution:

```bash
./gradlew bundleRelease
```

The AAB will be generated in `app/build/outputs/bundle/release/`

</samp>

## <samp>Contributing</samp>

<samp>

Contributions are always welcome!

See `CONTRIBUTING.md` for ways to get started.

Please adhere to this project's `code of conduct`.

</samp>

