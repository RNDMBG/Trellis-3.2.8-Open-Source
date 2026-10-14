# Vine 3.2.8 recovered Android project

This is a maintainable Android Studio recovery of the existing Vine 3.2.8 ARM32 APK. The Java sources were recovered from that APK and repaired only where needed for compilation and runtime-safe control flow.

## Build

1. Open this folder in Android Studio.
2. Use the bundled Android Studio JDK (JDK 17 language level is configured).
3. Let Gradle sync, then run `app` on a compatible device or emulator.
4. Local debug and release builds can be produced with `gradlew.bat :app:assembleDebug` and `gradlew.bat :app:assembleRelease`.

The verified outputs are `app/build/outputs/apk/debug/app-debug.apk` and `app/build/outputs/apk/release/app-release.apk`. Release lint is kept report-only because the recovered legacy AppCompat resource variants trigger current-AGP false-positive compatibility findings.

The build uses the existing cached dependencies and does not require downloading the original APK again. The compatibility facades in `src/main/compatbridge` replace unavailable optional Flurry/Google Play service entry points so the project can compile without those proprietary SDKs. Debug builds omit the original ARM32 native payload so they install on standard x86_64 Android Studio emulators; release builds retain the ARM32 payload for compatible devices. Recording/transcoding still requires a compatible native backend.

The target SDK is API 24, the minimum accepted by current Android 16 emulator images. This is a compatibility setting only; the existing UI resources and layouts were not redesigned.

## Important runtime limitation

The original native recording libraries are `armeabi-v7a` only. They are packaged as ARM32 libraries and must not be copied or renamed into `arm64-v8a`. The app can run on devices that support 32-bit ARM applications; ARM64-only devices still require a new recorder backend based on Android MediaCodec/MediaMuxer or newly sourced 64-bit native code.

## Source and licensing status

This is a reverse-engineered recovery, not an official Vine open-source release. See `NOTICE-RECOVERY.md` and `PROJECT-LICENSE.md` before redistributing it. The project additions and compatibility code may be licensed separately, but recovered Vine code, artwork, native libraries, and third-party libraries retain their original ownership and license obligations.
