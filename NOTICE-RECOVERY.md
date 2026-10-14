# Recovery and provenance notice

This repository was reconstructed from an existing Vine 3.2.8 Android APK supplied by the project owner. It is not the official Vine source repository and no claim is made that the recovered code is an authorized open-source release.

The following are retained as separate third-party or proprietary material and are not relicensed by this project:

- recovered Vine application code and resources;
- ARM32 native libraries;
- Android support, ExoPlayer, JavaCPP/JavaCV, OkHttp/Okio, Twitter4J, Jackson, Flurry, and other third-party components;
- original artwork, trademarks, service endpoints, and platform integrations.

New compatibility bridges and recovery edits were added only to make the recovered project readable and buildable. Consult each dependency's original license before distribution. Remove proprietary assets and replace service integrations if creating an independently distributable open-source fork.

The native recording stack remains ARM32-only. A genuine ARM64 port requires compatible 64-bit native sources/binaries or a replacement implementation; renaming the existing libraries is unsafe and does not constitute a port.
