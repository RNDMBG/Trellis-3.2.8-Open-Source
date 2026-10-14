# Trellis-3.2.8-Open-Source

Trellis is a maintainable Android Studio reconstruction of the Vine 3.2.8 app,
recovered from an existing APK via JADX and repaired for compilation.
It is **not** the official Vine source release. See `NOTICE-RECOVERY.md` and
`PROJECT-LICENSE.md` before redistributing: recovered code, artwork, native
libraries and third-party components keep their original ownership.

## Build

1. Open `jadx-android-studio-project` in Android Studio (uses the bundled JBR, JDK 17).
2. Let Gradle sync, then build:
   `.\gradlew.bat :app:assembleArm64Debug`
3. Release builds are signed with the project `key0` keystore, which is
   **private and never committed**. Pass it via temporary properties:
   `.\gradlew.bat :app:assembleArm64Release -PreleaseStoreFile=../key0 -PreleaseStorePassword=... -PreleaseKeyAlias=key0 -PreleaseKeyPassword=...`

## Versioning

`versionName` stays `3.2.8` forever. Each official build bumps
`trellisBuildNumber` in `gradle.properties` by the number of changes since
the last build (first official build is 10). `versionCode` is derived as
`1600241 + trellisBuildNumber` so every install is an upgrade.

## Updates

Settings → Updates checks `releases/latest` on this repo, shows
"A new Vine version has been released, would you like to update?" with
Yes (downloads the APK, opens the installer) / Later. Point
`UpdateConfig.GITHUB_OWNER/GITHUB_REPO` at this repo. Each release must be
tagged `build-<N>` (e.g. `build-30`) with the signed APK attached
(any name ending in `.apk`).

## Maintenance rule

`app/src/main/java/co/vine/android/C0257R.java` is a frozen constants copy.
**After adding ANY resource**, regenerate its values from the build's
`app/build/intermediates/runtime_symbol_list/<variant>/process<Variant>Resources/R.txt`
before shipping, or the app will resolve wrong resources and crash.
