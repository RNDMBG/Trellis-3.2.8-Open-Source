# Trellis-3.2.8-Open-Source

Trellis is Vine 3.2.8 for Android, brought back to life. The code was
recovered from the original app and fixed so it builds and runs today.
This is not the official Vine source. The old Vine code, art, and
libraries still belong to their owners. Read `NOTICE-DECOMP.md` and
`PROJECT-LICENSE.md` before you share anything.

## How to build

1. Open this folder in Android Studio (it uses the built-in Java).
2. Wait for Gradle to sync, then build:
   `.\gradlew.bat :app:assembleArm64Debug`
3. Release builds need the private `key0` key file. It is not in this
   repo. Build like this:
   `.\gradlew.bat :app:assembleArm64Release -PreleaseStoreFile=../key0 -PreleaseStorePassword=... -PreleaseKeyAlias=key0 -PreleaseKeyPassword=...`

## Versions

The version always stays `3.2.8`. Each official build gets a build
number instead. You find it in `gradle.properties` (`trellisBuildNumber`,
first one is 10). When you ship a new build, add the number of changes
since the last one (10 + 20 changes = build 30).

## Updates

Settings → Updates can check this repo for a new build. If there is one,
the app asks "A new Vine version has been released, would you like to
update?" Yes downloads it and opens the installer, Later asks again
next time you open the app. Each release here must be tagged
`build-<number>` (like `build-30`) and have the signed APK attached
(any file name ending in `.apk`).

## One rule

`app/src/main/java/co/vine/android/C0257R.java` is a frozen copy of the
resource numbers. **Every time you add a file to `res/`, you must refresh
the numbers in it from the build output** (`app/build/intermediates/
runtime_symbol_list/.../R.txt`) before you ship. If you skip this, the
app will open the wrong screens and crash.
