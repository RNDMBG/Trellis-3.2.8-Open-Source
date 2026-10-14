package co.vine.android;

/**
 * In-app updater configuration.
 *
 * Point GITHUB_OWNER / GITHUB_REPO at the repository that hosts Vine releases,
 * then publish one GitHub Release per version with the APK attached.
 * See the handoff notes for the exact release steps.
 */
public final class UpdateConfig {
    public static final String GITHUB_OWNER = "RNDMBG";
    public static final String GITHUB_REPO = "Trellis-3.2.8-Open-Source";
    public static final String APK_ASSET_SUFFIX = ".apk";
    public static final String PREF_AUTO_UPDATE = "auto_updates";
    public static final String APK_FILE_NAME = "vine-update.apk";
    // versionName stays 3.2.8 forever. The Trellis build number is
    // versionCode - VERSION_CODE_BASE (first official build is 10).
    // Release tags look like "build-30".
    public static final int VERSION_CODE_BASE = 1600241;

    private UpdateConfig() {
    }
}
