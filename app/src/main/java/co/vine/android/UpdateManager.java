package co.vine.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import co.vine.android.util.CrashUtil;
import co.vine.android.util.Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Checks a GitHub releases feed for a newer Vine APK, prompts, downloads and
 * hands the file to the system package installer. Best-effort only: every
 * failure degrades to a status line, never a crash.
 */
public final class UpdateManager {

    public interface UpdateCallback {
        void onResult(UpdateInfo info);

        void onError();
    }

    public static final class UpdateInfo {
        public final String version;
        public final int build;
        public final String apkUrl;
        public final boolean newer;

        public UpdateInfo(String version, int build, String apkUrl, boolean newer) {
            this.version = version;
            this.build = build;
            this.apkUrl = apkUrl;
            this.newer = newer;
        }
    }

    private static final String LATEST_URL =
            "https://api.github.com/repos/" + UpdateConfig.GITHUB_OWNER + "/" + UpdateConfig.GITHUB_REPO + "/releases/latest";

    private static final Handler MAIN = new Handler(Looper.getMainLooper());
    private static final Map<Long, String> PENDING_NAMES = new HashMap<>();
    private static BroadcastReceiver sDownloadReceiver;

    private UpdateManager() {
    }

    public static boolean isAutoEnabled(Context context) {
        return Util.getDefaultSharedPrefs(context).getBoolean(UpdateConfig.PREF_AUTO_UPDATE, false);
    }

    public static void setAutoEnabled(Context context, boolean enabled) {
        Util.getDefaultSharedPrefs(context).edit().putBoolean(UpdateConfig.PREF_AUTO_UPDATE, enabled).apply();
    }

    public static String currentVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionName != null ? info.versionName : "";
        } catch (Exception e) {
            return "";
        }
    }

    /** Trellis build number: versionCode - base (3.2.8 stays frozen). */
    public static int currentBuild(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            int build = info.versionCode - UpdateConfig.VERSION_CODE_BASE;
            return Math.max(build, 0);
        } catch (Exception e) {
            return 0;
        }
    }

    /** Silent on failure; shows the Yes/Later dialog only when a newer APK exists. */
    public static void checkOnStartup(final Activity activity) {
        if (!isAutoEnabled(activity)) {
            return;
        }
        checkForUpdate(activity.getApplicationContext(), new UpdateCallback() {
            @Override
            public void onResult(final UpdateInfo info) {
                if (info == null || !info.newer || info.apkUrl == null) {
                    return;
                }
                MAIN.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!activity.isFinishing()) {
                                showUpdateDialog(activity, info);
                            }
                        } catch (Exception e) {
                            CrashUtil.logException(e);
                        }
                    }
                });
            }

            @Override
            public void onError() {
            }
        });
    }

    public static void checkForUpdate(final Context context, final UpdateCallback callback) {
        final Handler caller = new Handler(Looper.getMainLooper());
        new Thread(new Runnable() {
            @Override
            public void run() {
                final UpdateInfo info = fetchLatest(context);
                caller.post(new Runnable() {
                    @Override
                    public void run() {
                        if (info != null) {
                            callback.onResult(info);
                        } else {
                            callback.onError();
                        }
                    }
                });
            }
        }).start();
    }

    private static UpdateInfo fetchLatest(Context context) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(LATEST_URL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(15000);
            conn.setRequestProperty("User-Agent", "Vine-Android-Updater");
            conn.setRequestProperty("Accept", "application/vnd.github+json");
            if (conn.getResponseCode() != 200) {
                return null;
            }
            InputStream in = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            JSONObject release = new JSONObject(sb.toString());
            String tag = release.optString("tag_name", "");
            String apkUrl = null;
            JSONArray assets = release.optJSONArray("assets");
            if (assets != null) {
                for (int i = 0; i < assets.length(); i++) {
                    JSONObject asset = assets.optJSONObject(i);
                    if (asset == null) {
                        continue;
                    }
                    String name = asset.optString("name", "");
                    if (name.endsWith(UpdateConfig.APK_ASSET_SUFFIX)) {
                        apkUrl = asset.optString("browser_download_url", null);
                        break;
                    }
                }
            }
            if (tag.length() == 0 || apkUrl == null) {
                return null;
            }
            int tagBuild = parseBuildNumber(tag);
            boolean newer;
            if (tagBuild >= 0) {
                newer = tagBuild > currentBuild(context);
            } else {
                // Old-style tag without a build number: compare dotted versions.
                newer = isNewer(currentVersion(context), tag);
                tagBuild = 0;
            }
            return new UpdateInfo(tag, tagBuild, apkUrl, newer);
        } catch (Exception e) {
            return null;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    /** Reads the Trellis build number from tags like "build-30". -1 if none. */
    static int parseBuildNumber(String tag) {
        if (tag == null) {
            return -1;
        }
        java.util.regex.Matcher m = java.util.regex.Pattern.compile("(?i)build[-_ ]?(\\d+)").matcher(tag.trim());
        if (m.find()) {
            try {
                return Integer.parseInt(m.group(1));
            } catch (Exception e) {
                return -1;
            }
        }
        return -1;
    }

    /** Compares dotted versions, tolerating a leading "v" (v3.2.9 > 3.2.8). */
    static boolean isNewer(String current, String latest) {
        int[] c = splitVersion(current);
        int[] l = splitVersion(latest);
        int n = Math.max(c.length, l.length);
        for (int i = 0; i < n; i++) {
            int cv = i < c.length ? c[i] : 0;
            int lv = i < l.length ? l[i] : 0;
            if (lv != cv) {
                return lv > cv;
            }
        }
        return false;
    }

    private static int[] splitVersion(String version) {
        if (version == null) {
            return new int[0];
        }
        String clean = version.trim();
        while (clean.startsWith("v") || clean.startsWith("V")) {
            clean = clean.substring(1);
        }
        String[] parts = clean.split("[.\\-_]");
        int[] out = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            try {
                out[i] = Integer.parseInt(parts[i].replaceAll("[^0-9]", ""));
            } catch (Exception e) {
                out[i] = 0;
            }
        }
        return out;
    }

    public static void showUpdateDialog(final Activity activity, final UpdateInfo info) {
        try {
            new AlertDialog.Builder(activity)
                    .setTitle(C0257R.string.updates_available_title)
                    .setMessage(activity.getString(C0257R.string.updates_available_msg) + " (build " + info.build + ")")
                    .setPositiveButton(C0257R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            downloadAndInstall(activity, info.apkUrl);
                        }
                    })
                    .setNegativeButton(C0257R.string.later, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setCancelable(true)
                    .show();
        } catch (Exception e) {
            CrashUtil.logException(e);
        }
    }

    public static void downloadAndInstall(Context context, String apkUrl) {
        try {
            final Context app = context.getApplicationContext();
            ensureDownloadReceiver(app);
            DownloadManager dm = (DownloadManager) app.getSystemService(Context.DOWNLOAD_SERVICE);
            if (dm == null) {
                Toast.makeText(app, C0257R.string.updates_error, Toast.LENGTH_LONG).show();
                return;
            }
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(apkUrl));
            request.setTitle(app.getString(C0257R.string.updates_title));
            request.setMimeType("application/vnd.android.package-archive");
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, UpdateConfig.APK_FILE_NAME);
            long id = dm.enqueue(request);
            synchronized (PENDING_NAMES) {
                PENDING_NAMES.put(Long.valueOf(id), UpdateConfig.APK_FILE_NAME);
            }
            Toast.makeText(app, C0257R.string.updates_downloading, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            CrashUtil.logException(e);
        }
    }

    private static void ensureDownloadReceiver(final Context app) {
        if (sDownloadReceiver != null) {
            return;
        }
        sDownloadReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                try {
                    long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1L);
                    String name;
                    synchronized (PENDING_NAMES) {
                        name = PENDING_NAMES.remove(Long.valueOf(id));
                    }
                    if (name == null) {
                        return;
                    }
                    DownloadManager dm = (DownloadManager) app.getSystemService(Context.DOWNLOAD_SERVICE);
                    if (dm == null) {
                        return;
                    }
                    DownloadManager.Query query = new DownloadManager.Query();
                    query.setFilterById(id);
                    Cursor cursor = dm.query(query);
                    boolean ok = false;
                    if (cursor != null) {
                        try {
                            if (cursor.moveToFirst()) {
                                int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                                ok = status == DownloadManager.STATUS_SUCCESSFUL;
                            }
                        } finally {
                            cursor.close();
                        }
                    }
                    if (ok) {
                        installApk(app, name);
                    }
                } catch (Exception e) {
                    CrashUtil.logException(e);
                }
            }
        };
        app.registerReceiver(sDownloadReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    private static void installApk(Context context, String fileName) {
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
            if (!file.exists()) {
                return;
            }
            Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(intent);
        } catch (Exception e) {
            CrashUtil.logException(e);
            try {
                Toast.makeText(context, C0257R.string.updates_error, Toast.LENGTH_LONG).show();
            } catch (Exception ignored) {
            }
        }
    }

}
