## Repo

Single-module Android app (`:app`). Package: `net.trackgeek.mobile.android`. Kotlin + Jetpack Compose + Material 3. No Hilt/Dagger yet (plain Compose navigation). No DI framework wired. All screen content is currently mock data.

## Build

- **Gradle 9.4.1** via wrapper, AGP 9.2.0, Kotlin 2.2.10, Compose BOM 2024.09.00
- Android SDK 35 (compileSdk/targetSdk), minSdk 29, Java 11
- `./gradlew assembleRelease` → APK at `app/build/outputs/apk/release/`
- No lint/formatter/test scripts in Gradle config — use Android Studio or direct Gradle tasks:
    - `./gradlew lint` (no custom lint config)
    - `./gradlew test` (unit tests)
    - `./gradlew connectedCheck` (instrumented tests, needs device/emulator)
- `local.properties.example` referenced in README but file doesn't exist in repo — create manually if needed (`sdk.dir=...`)

## Architecture

- **Entrypoint**: `app/src/main/java/.../MainActivity.kt` — single-activity, sets up `NavHost` with 4 routes: `home`, `search`, `list`, `profile`
- **UI structure**:
    - `ui/screens/` — screen-level composables (HomeScreen, SearchScreen, ListScreen, ProfileScreen)
    - `ui/components/` — reusable (BottomBar, CategoryTabs, MediaCard, MediaCarousel)
    - `ui/theme/` — dark-only `TrackGeekTheme`, custom icons in `ui/icons/`
    - No ViewModels, no Repository layer, no API client yet
- **Navigation**: `androidx.navigation.compose` with `BottomBar` using `NavItem` sealed class
- **Theme**: forced dark color scheme, `dynamicColor` parameter exists but is never set to `true`

## Key Conventions

- **Commit style**: Conventional Commits (`feat|fix|style|refactor|perf|test|workflow|ci|chore|types|wip(scope): message`) — see `.github/COMMIT_CONVENTION.md`
- **No Kotlin code style plugins** (e.g. ktlint/detekt) in Gradle config — format manually
- **Tests**: `ExampleUnitTest` and `ExampleInstrumentedTest` exist as placeholders only (no real tests)

## Gotchas

- AGP 9.2.0 requires Gradle 8.9+. This repo uses 9.4.1, so that's fine.
- `res/xml/backup_rules.xml` and `data_extraction_rules.xml` referenced in manifest — verify they exist (likely android-generated defaults)
- No CI config found in `.github/` — lint/format checks are manual for now
- `.idea/` is checked in — don't modify project-level IDE config without understanding IntelliJ workspace conventions
- Custom vector icons in `ui/icons/` — if adding new ones, follow existing pattern