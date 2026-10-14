package co.vine.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
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
    private static final Map<Long, Integer> PENDING_BUILDS = new HashMap<>();
    private static BroadcastReceiver sDownloadReceiver;
    private static boolean sStartupChecked;

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

    
    public static int currentBuild(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            int build = info.versionCode - UpdateConfig.VERSION_CODE_BASE;
            return Math.max(build, 0);
        } catch (Exception e) {
            return 0;
        }
    }

    
    public static void checkOnStartup(final Activity activity) {
        cleanupFinishedDownload(activity.getApplicationContext());
        ensureDownloadReceiver(activity.getApplicationContext());
        if (sStartupChecked || !isAutoEnabled(activity)) {
            return;
        }
        sStartupChecked = true;
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
                                handleNewerFound(activity, info);
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

    
    static void handleNewerFound(Activity activity, UpdateInfo info) {
        if (isDownloadReady(activity) && readyBuild(activity) == info.build) {
            showInstallDialog(activity);
        } else {
            clearReadyState(activity);
            deleteStaleApks(activity);
            showUpdateDialog(activity, info);
        }
    }

    public static void showUpdateDialog(final Activity activity, final UpdateInfo info) {
        try {
            new AlertDialog.Builder(activity)
                    .setTitle(C0257R.string.updates_available_title)
                    .setMessage(activity.getString(C0257R.string.updates_available_msg) + " (build " + info.build + ")")
                    .setPositiveButton(C0257R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            downloadAndInstall(activity, info.apkUrl, info.build);
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

    public static void showInstallDialog(final Activity activity) {
        try {
            new AlertDialog.Builder(activity)
                    .setTitle(C0257R.string.updates_install_title)
                    .setMessage(activity.getString(C0257R.string.updates_install_msg))
                    .setPositiveButton(C0257R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            tryInstallThenDownloads(activity);
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

    
    static void tryInstallThenDownloads(Activity activity) {
        try {
            installApk(activity, UpdateConfig.APK_FILE_NAME);
        } catch (Exception e) {
            CrashUtil.logException(e);
            openSystemDownloads(activity);
        }
    }

    
    public static void openReleasesPage(Activity activity) {
        try {
            String url = "https://github.com/" + UpdateConfig.GITHUB_OWNER + "/" + UpdateConfig.GITHUB_REPO + "/releases/latest";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            activity.startActivity(intent);
        } catch (Exception e) {
            CrashUtil.logException(e);
        }
    }

    
    static void openSystemDownloads(Context context) {
        try {
            Intent intent = new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
            if (!(context instanceof Activity)) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
        } catch (Exception e) {
            CrashUtil.logException(e);
            try {
                installApk(context, UpdateConfig.APK_FILE_NAME);
            } catch (Exception ignored) {
            }
        }
    }

    static File downloadedApkFile(Context context) {
        try {
            return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), UpdateConfig.APK_FILE_NAME);
        } catch (Exception e) {
            return null;
        }
    }

    
    static boolean isDownloadReady(Context context) {
        try {
            if (!Util.getDefaultSharedPrefs(context).getBoolean(UpdateConfig.PREF_UPDATE_READY, false)) {
                return false;
            }
            File file = downloadedApkFile(context);
            return file != null && file.exists() && file.length() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    static int readyBuild(Context context) {
        try {
            return Util.getDefaultSharedPrefs(context).getInt(UpdateConfig.PREF_READY_BUILD, 0);
        } catch (Exception e) {
            return 0;
        }
    }

    static void clearReadyState(Context context) {
        try {
            Util.getDefaultSharedPrefs(context).edit().putBoolean(UpdateConfig.PREF_UPDATE_READY, false).apply();
        } catch (Exception ignored) {
        }
    }

    
    static void deleteStaleApks(Context context) {
        try {
            File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File[] files = dir.listFiles();
            if (files == null) {
                return;
            }
            String base = UpdateConfig.APK_FILE_NAME;
            int dot = base.lastIndexOf('.');
            final String prefix = dot > 0 ? base.substring(0, dot) : base;
            for (File f : files) {
                String name = f.getName();
                if (name.equals(base) || (name.startsWith(prefix) && name.endsWith(UpdateConfig.APK_ASSET_SUFFIX))) {
                    try {
                        f.delete();
                    } catch (Exception ignored) {
                    }
                }
            }
        } catch (Exception ignored) {
        }
    }
    static void markDownloadReady(Context context, int build) {
        try {
            Util.getDefaultSharedPrefs(context).edit()
                    .putBoolean(UpdateConfig.PREF_UPDATE_READY, true)
                    .putInt(UpdateConfig.PREF_READY_BUILD, build)
                    .apply();
        } catch (Exception ignored) {
        }
    }

    



    static void cleanupFinishedDownload(Context context) {
        try {
            SharedPreferences prefs = Util.getDefaultSharedPrefs(context);
            if (!prefs.getBoolean(UpdateConfig.PREF_UPDATE_READY, false)) {
                return;
            }
            int readyBuild = prefs.getInt(UpdateConfig.PREF_READY_BUILD, 0);
            if (currentBuild(context) >= readyBuild && readyBuild > 0) {
                deleteStaleApks(context);
                prefs.edit().putBoolean(UpdateConfig.PREF_UPDATE_READY, false).apply();
            }
        } catch (Exception ignored) {
        }
    }
    public static void downloadAndInstall(Context context, String apkUrl, int build) {
        long id = enqueueDownload(context.getApplicationContext(), apkUrl);
        if (id < 0) {
            try {
                Toast.makeText(context.getApplicationContext(), C0257R.string.updates_error, Toast.LENGTH_LONG).show();
            } catch (Exception ignored) {
            }
        } else {
            synchronized (PENDING_BUILDS) {
                PENDING_BUILDS.put(Long.valueOf(id), Integer.valueOf(build));
            }
        }
    }

    
    public static void downloadAndInstall(final Activity activity, String apkUrl, final int build) {
        final Context app = activity.getApplicationContext();
        final long id = enqueueDownload(app, apkUrl);
        synchronized (PENDING_BUILDS) {
            PENDING_BUILDS.put(Long.valueOf(id), Integer.valueOf(build));
        }
        final long[] trackId = new long[]{id};
        final boolean[] cancelled = new boolean[1];
        final String downloadUrl = apkUrl;
        if (id < 0) {
            try {
                Toast.makeText(app, C0257R.string.updates_error, Toast.LENGTH_LONG).show();
            } catch (Exception ignored) {
            }
            return;
        }
        try {
            final android.widget.ProgressBar bar = new android.widget.ProgressBar(activity, null, android.R.attr.progressBarStyleHorizontal);
            bar.setMax(100);
            bar.setProgress(0);
            int pad = (int) (24.0f * activity.getResources().getDisplayMetrics().density + 0.5f);
            bar.setPadding(pad, pad / 2, pad, 0);
            final AlertDialog dialog = new AlertDialog.Builder(activity)
                    .setTitle(C0257R.string.updates_downloading_title)
                    .setMessage(app.getString(C0257R.string.updates_downloading))
                    .setView(bar)
                    .setCancelable(false)
                    .setNegativeButton(C0257R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface d, int which) {
                            try {
                                DownloadManager dm = (DownloadManager) app.getSystemService(Context.DOWNLOAD_SERVICE);
                                if (dm != null) {
                                    dm.remove(id);
                                }
                            } catch (Exception ignored) {
                            }
                            cancelled[0] = true;
                            synchronized (PENDING_NAMES) {
                                PENDING_NAMES.remove(Long.valueOf(id));
                            }
                            synchronized (PENDING_BUILDS) {
                                PENDING_BUILDS.remove(Long.valueOf(id));
                            }
                            try {
                                d.dismiss();
                            } catch (Exception ignored) {
                            }
                        }
                    })
                    .show();
            final DownloadManager dm = (DownloadManager) app.getSystemService(Context.DOWNLOAD_SERVICE);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    pollDownloadProgress(app, activity, dm, trackId, build, downloadUrl, cancelled, bar, dialog);
                }
            }).start();
        } catch (Exception e) {
            CrashUtil.logException(e);
        }
    }

    private static void pollDownloadProgress(Context app, final Activity activity, DownloadManager dm, final long[] trackId, final int build, final String apkUrl, final boolean[] cancelled, final android.widget.ProgressBar bar, final AlertDialog dialog) {
        final Context appCtx = app.getApplicationContext();
        try {
            boolean done = false;
            final long[] startedAt = new long[]{System.currentTimeMillis()};
            long lastProgressAt = startedAt[0];
            int lastProgressBytes = 0;
            int restarts = 0;
            while (!done && !cancelled[0]) {
                long id = trackId[0];
                int status = -1;
                int soFar = 0;
                int total = -1;
                if (dm != null) {
                    DownloadManager.Query query = new DownloadManager.Query();
                    query.setFilterById(id);
                    Cursor cursor = null;
                    try {
                        cursor = dm.query(query);
                        if (cursor != null && cursor.moveToFirst()) {
                            status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                            soFar = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                            total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                        }
                    } catch (Exception ignored) {
                    } finally {
                        if (cursor != null) {
                            try {
                                cursor.close();
                            } catch (Exception ignored) {
                            }
                        }
                    }
                }
                final int fStatus = status;
                final int fSoFar = soFar;
                final int fTotal = total;
                long now = System.currentTimeMillis();
                
                
                final double fMbps = Math.max((soFar / 1048576.0) / Math.max((now - startedAt[0]) / 1000.0, 0.001), 0.0);
                MAIN.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!activity.isFinishing()) {
                                if (fTotal > 0) {
                                    int pct = (int) ((fSoFar * 100L) / fTotal);
                                    bar.setProgress(pct);
                                    dialog.setMessage(String.format(java.util.Locale.US, "%.1f/%.1f MB (%d%%)\n%.1f MB/s",
                                            fSoFar / 1048576.0, fTotal / 1048576.0, pct, fMbps));
                                } else {
                                    bar.setIndeterminate(true);
                                    dialog.setMessage(String.format(java.util.Locale.US, "%.1f MB\n%.1f MB/s",
                                            fSoFar / 1048576.0, fMbps));
                                }
                            }
                        } catch (Exception ignored) {
                        }
                    }
                });
                if (fStatus == DownloadManager.STATUS_SUCCESSFUL || fStatus == DownloadManager.STATUS_FAILED) {
                    done = true;
                    MAIN.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                dialog.dismiss();
                            } catch (Exception ignored) {
                            }
                        }
                    });
                    if (fStatus == DownloadManager.STATUS_SUCCESSFUL) {
                        markDownloadReady(appCtx, build);
                        String name;
                        synchronized (PENDING_NAMES) {
                            name = PENDING_NAMES.remove(Long.valueOf(id));
                        }
                        synchronized (PENDING_BUILDS) {
                            PENDING_BUILDS.remove(Long.valueOf(id));
                        }
                        if (name != null) {
                            MAIN.post(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        if (!activity.isFinishing()) {
                                            showInstallDialog(activity);
                                        }
                                    } catch (Exception ignored) {
                                    }
                                }
                            });
                        }
                    } else {
                        forgetDownload(id);
                        MAIN.post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Toast.makeText(appCtx, C0257R.string.updates_error, Toast.LENGTH_LONG).show();
                                } catch (Exception ignored) {
                                }
                            }
                        });
                    }
                } else if (cancelled[0]) {
                    done = true;
                } else if (soFar > 0 && soFar == lastProgressBytes && now - lastProgressAt > 45000) {
                    
                    
                    
                    if (restarts < 1) {
                        restarts++;
                        long freshId = restartStalledDownload(appCtx, dm, trackId, build, apkUrl);
                        if (freshId >= 0) {
                            startedAt[0] = System.currentTimeMillis();
                            lastProgressAt = startedAt[0];
                        } else {
                            done = true;
                            forgetDownload(trackId[0]);
                            MAIN.post(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        dialog.dismiss();
                                    } catch (Exception ignored) {
                                    }
                                    try {
                                        Toast.makeText(appCtx, C0257R.string.updates_error, Toast.LENGTH_LONG).show();
                                    } catch (Exception ignored) {
                                    }
                                }
                            });
                        }
                    } else {
                        done = true;
                        forgetDownload(trackId[0]);
                        MAIN.post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    dialog.dismiss();
                                } catch (Exception ignored) {
                                }
                                try {
                                    Toast.makeText(appCtx, C0257R.string.updates_error, Toast.LENGTH_LONG).show();
                                } catch (Exception ignored) {
                                }
                            }
                        });
                    }
                } else {
                    if (soFar != lastProgressBytes) {
                        lastProgressBytes = soFar;
                        lastProgressAt = now;
                    }
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException ignored) {
                        done = true;
                    }
                }
            }
        } catch (Exception e) {
            CrashUtil.logException(e);
        }
    }

    private static void forgetDownload(long id) {
        synchronized (PENDING_NAMES) {
            PENDING_NAMES.remove(Long.valueOf(id));
        }
        synchronized (PENDING_BUILDS) {
            PENDING_BUILDS.remove(Long.valueOf(id));
        }
    }

    



    private static long restartStalledDownload(Context appCtx, DownloadManager dm, long[] trackId, int build, String apkUrl) {
        try {
            long oldId = trackId[0];
            if (dm != null) {
                try {
                    dm.remove(oldId);
                } catch (Exception ignored) {
                }
            }
            forgetDownload(oldId);
            long id = enqueueDownload(appCtx, apkUrl);
            if (id < 0) {
                return -1L;
            }
            trackId[0] = id;
            synchronized (PENDING_BUILDS) {
                PENDING_BUILDS.put(Long.valueOf(id), Integer.valueOf(build));
            }
            return id;
        } catch (Exception e) {
            CrashUtil.logException(e);
            return -1L;
        }
    }

    private static long enqueueDownload(Context app, String apkUrl) {
        try {
            ensureDownloadReceiver(app);
            try {
                File stale = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), UpdateConfig.APK_FILE_NAME);
                if (stale.exists()) {
                    stale.delete();
                }
            } catch (Exception ignored) {
            }
            DownloadManager dm = (DownloadManager) app.getSystemService(Context.DOWNLOAD_SERVICE);
            if (dm == null) {
                return -1L;
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
            return id;
        } catch (Exception e) {
            CrashUtil.logException(e);
            return -1L;
        }
    }

    static void ensureDownloadReceiver(final Context app) {
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
                        int readyBuild = 0;
                        synchronized (PENDING_BUILDS) {
                            Integer b = PENDING_BUILDS.remove(Long.valueOf(id));
                            if (b != null) {
                                readyBuild = b.intValue();
                            }
                        }
                        markDownloadReady(app, readyBuild);
                    } else {
                        synchronized (PENDING_BUILDS) {
                            PENDING_BUILDS.remove(Long.valueOf(id));
                        }
                    }
                } catch (Exception e) {
                    CrashUtil.logException(e);
                }
            }
        };
        app.registerReceiver(sDownloadReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    private static void installApk(Context context, String fileName) throws Exception {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
        if (file == null || !file.exists()) {
            throw new java.io.FileNotFoundException(fileName);
        }
        Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(intent);
    }

}
