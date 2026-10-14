            package android.support.multidex;

            import android.content.Context;
            import android.content.SharedPreferences;
            import android.content.pm.ApplicationInfo;
            import android.os.Build;
            import android.util.Log;
            import co.vine.android.service.VineUploadService;
            import java.io.BufferedOutputStream;
            import java.io.Closeable;
            import java.io.File;
            import java.io.FileFilter;
            import java.io.FileOutputStream;
            import java.io.IOException;
            import java.io.InputStream;
            import java.lang.reflect.InvocationTargetException;
            import java.lang.reflect.Method;
            import java.util.ArrayList;
            import java.util.List;
            import java.util.zip.ZipEntry;
            import java.util.zip.ZipException;
            import java.util.zip.ZipFile;
            import java.util.zip.ZipOutputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 44 */    final class MultiDexExtractor {
                private static final int BUFFER_SIZE = 16384;
                private static final String DEX_PREFIX = "classes";
                private static final String DEX_SUFFIX = ".dex";
                private static final String EXTRACTED_NAME_EXT = ".classes";
                private static final String EXTRACTED_SUFFIX = ".zip";
                private static final String KEY_CRC = "crc";
                private static final String KEY_DEX_NUMBER = "dex.number";
                private static final String KEY_TIME_STAMP = "timestamp";
                private static final int MAX_EXTRACT_ATTEMPTS = 3;
                private static final long NO_VALUE = -1;
                private static final String PREFS_FILE = "multidex.version";
                private static final String TAG = "MultiDex";
                private static Method sApplyMethod;

/* 45 */        MultiDexExtractor() {
                }

/* 83 */        static List<File> load(Context context, ApplicationInfo applicationInfo, File dexDir, boolean forceReload) throws IOException {
                    List<File> files;
/* 84 */            Log.i(TAG, "MultiDexExtractor.load(" + applicationInfo.sourceDir + ", " + forceReload + ")");
/* 85 */            File sourceApk = new File(applicationInfo.sourceDir);
/* 87 */            long currentCrc = getZipCrc(sourceApk);
/* 90 */            if (!forceReload && !isModified(context, sourceApk, currentCrc)) {
                        try {
/* 92 */                    files = loadExistingExtractions(context, sourceApk, dexDir);
                        } catch (IOException ioe) {
/* 94 */                    Log.w(TAG, "Failed to reload existing extracted secondary dex files, falling back to fresh extraction", ioe);
/* 96 */                    files = performExtractions(sourceApk, dexDir);
/* 97 */                    putStoredApkInfo(context, getTimeStamp(sourceApk), currentCrc, files.size() + 1);
                        }
                    } else {
/* 101 */               Log.i(TAG, "Detected that extraction must be performed.");
/* 102 */               files = performExtractions(sourceApk, dexDir);
/* 103 */               putStoredApkInfo(context, getTimeStamp(sourceApk), currentCrc, files.size() + 1);
                    }
/* 106 */           Log.i(TAG, "load found " + files.size() + " secondary dex files");
/* 107 */           return files;
                }

/* 111 */       private static List<File> loadExistingExtractions(Context context, File sourceApk, File dexDir) throws IOException {
/* 112 */           Log.i(TAG, "loading existing secondary dex files");
/* 114 */           String extractedFilePrefix = sourceApk.getName() + EXTRACTED_NAME_EXT;
/* 115 */           int totalDexNumber = getMultiDexPreferences(context).getInt(KEY_DEX_NUMBER, 1);
/* 116 */           List<File> files = new ArrayList<>(totalDexNumber);
                    for (int secondaryNumber = 2; secondaryNumber <= totalDexNumber; secondaryNumber++) {
/* 119 */               String fileName = extractedFilePrefix + secondaryNumber + EXTRACTED_SUFFIX;
/* 120 */               File extractedFile = new File(dexDir, fileName);
/* 121 */               if (extractedFile.isFile()) {
/* 122 */                   files.add(extractedFile);
/* 123 */                   if (!verifyZipFile(extractedFile)) {
/* 124 */                       Log.i(TAG, "Invalid zip file: " + extractedFile);
/* 125 */                       throw new IOException("Invalid ZIP file.");
                            }
                        } else {
/* 128 */                   throw new IOException("Missing extracted secondary dex file '" + extractedFile.getPath() + "'");
                        }
                    }
/* 133 */           return files;
                }

/* 136 */       private static boolean isModified(Context context, File archive, long currentCrc) {
/* 137 */           SharedPreferences prefs = getMultiDexPreferences(context);
                    return (prefs.getLong("timestamp", -1L) == getTimeStamp(archive) && prefs.getLong(KEY_CRC, -1L) == currentCrc) ? false : true;
                }

/* 142 */       private static long getTimeStamp(File archive) {
/* 143 */           long timeStamp = archive.lastModified();
                    if (timeStamp == -1) {
/* 146 */               return timeStamp - 1;
                    }
/* 148 */           return timeStamp;
                }

/* 152 */       private static long getZipCrc(File archive) throws IOException {
/* 153 */           long computedValue = ZipUtil.getZipCrc(archive);
                    if (computedValue == -1) {
/* 156 */               return computedValue - 1;
                    }
/* 158 */           return computedValue;
                }

/* 163 */       private static List<File> performExtractions(File sourceApk, File dexDir) throws IOException {
/* 164 */           String extractedFilePrefix = sourceApk.getName() + EXTRACTED_NAME_EXT;
/* 170 */           prepareDexDir(dexDir, extractedFilePrefix);
/* 172 */           List<File> files = new ArrayList<>();
/* 174 */           ZipFile apk = new ZipFile(sourceApk);
/* 177 */           int secondaryNumber = 2;
                    try {
/* 177 */               ZipEntry dexFile = apk.getEntry(DEX_PREFIX + 2 + DEX_SUFFIX);
/* 180 */               while (dexFile != null) {
/* 181 */                   String fileName = extractedFilePrefix + secondaryNumber + EXTRACTED_SUFFIX;
/* 182 */                   File extractedFile = new File(dexDir, fileName);
/* 183 */                   files.add(extractedFile);
/* 185 */                   Log.i(TAG, "Extraction is needed for file " + extractedFile);
/* 186 */                   int numAttempts = 0;
/* 187 */                   boolean isExtractionSuccessful = false;
/* 188 */                   while (numAttempts < 3 && !isExtractionSuccessful) {
/* 189 */                       numAttempts++;
/* 193 */                       extract(apk, dexFile, extractedFile, extractedFilePrefix);
/* 196 */                       isExtractionSuccessful = verifyZipFile(extractedFile);
/* 199 */                       Log.i(TAG, "Extraction " + (isExtractionSuccessful ? VineUploadService.EXTRA_SUCCESS : "failed") + " - length " + extractedFile.getAbsolutePath() + ": " + extractedFile.length());
/* 202 */                       if (!isExtractionSuccessful) {
/* 204 */                           extractedFile.delete();
/* 205 */                           if (extractedFile.exists()) {
/* 206 */                               Log.w(TAG, "Failed to delete corrupted secondary dex '" + extractedFile.getPath() + "'");
                                    }
                                }
                            }
/* 211 */                   if (!isExtractionSuccessful) {
/* 212 */                       throw new IOException("Could not create zip file " + extractedFile.getAbsolutePath() + " for secondary dex (" + secondaryNumber + ")");
                            }
/* 216 */                   secondaryNumber++;
/* 217 */                   dexFile = apk.getEntry(DEX_PREFIX + secondaryNumber + DEX_SUFFIX);
                        }
                        try {
/* 221 */                   apk.close();
                        } catch (IOException e) {
/* 223 */                   Log.w(TAG, "Failed to close resource", e);
                        }
/* 227 */               return files;
                    } catch (IOException th) {
                        try {
/* 221 */                   apk.close();
                        } catch (IOException e2) {
/* 223 */                   Log.w(TAG, "Failed to close resource", e2);
                        }
/* 224 */               throw th;
                    }
                }

/* 231 */       private static void putStoredApkInfo(Context context, long timeStamp, long crc, int totalDexNumber) {
/* 232 */           SharedPreferences prefs = getMultiDexPreferences(context);
/* 233 */           SharedPreferences.Editor edit = prefs.edit();
/* 234 */           edit.putLong("timestamp", timeStamp);
/* 235 */           edit.putLong(KEY_CRC, crc);
/* 240 */           edit.putInt(KEY_DEX_NUMBER, totalDexNumber);
/* 241 */           apply(edit);
                }

/* 244 */       private static SharedPreferences getMultiDexPreferences(Context context) {
/* 245 */           return context.getSharedPreferences(PREFS_FILE, Build.VERSION.SDK_INT < 11 ? 0 : 4);
                }

/* 255 */       private static void prepareDexDir(File dexDir, String extractedFilePrefix) throws IOException {
/* 256 */           dexDir.mkdirs();
/* 257 */           if (!dexDir.isDirectory()) {
/* 258 */               throw new IOException("Failed to create dex directory " + dexDir.getPath());
                    }
/* 262 */           FileFilter filter = new C00011(extractedFilePrefix);
/* 269 */           File[] files = dexDir.listFiles(filter);
/* 270 */           if (files == null) {
/* 271 */               Log.w(TAG, "Failed to list secondary dex dir content (" + dexDir.getPath() + ").");
/* 283 */               return;
                    }
                    for (File oldFile : files) {
/* 275 */               Log.i(TAG, "Trying to delete old file " + oldFile.getPath() + " of size " + oldFile.length());
/* 277 */               if (!oldFile.delete()) {
/* 278 */                   Log.w(TAG, "Failed to delete old file " + oldFile.getPath());
                        } else {
/* 280 */                   Log.i(TAG, "Deleted old file " + oldFile.getPath());
                        }
                    }
                }

                /* JADX INFO: renamed from: android.support.multidex.MultiDexExtractor$1 */
/* 260 */       static class C00011 implements FileFilter {
                    final /* synthetic */ String val$extractedFilePrefix;

/* 261 */           C00011(String str) {
/* 262 */               this.val$extractedFilePrefix = str;
                    }

                    @Override // java.io.FileFilter
/* 265 */           public boolean accept(File pathname) {
                        return !pathname.getName().startsWith(this.val$extractedFilePrefix);
                    }
                }

/* 287 */       private static void extract(ZipFile apk, ZipEntry dexFile, File extractTo, String extractedFilePrefix) throws IOException {
                    InputStream in = apk.getInputStream(dexFile);
                    File tmp = File.createTempFile(extractedFilePrefix, EXTRACTED_SUFFIX, extractTo.getParentFile());
                    Log.i(TAG, "Extracting " + tmp.getPath());
                    ZipOutputStream out = null;
                    try {
                        out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(tmp)));
                        ZipEntry classesDex = new ZipEntry("classes.dex");
                        classesDex.setTime(dexFile.getTime());
                        out.putNextEntry(classesDex);
                        byte[] buffer = new byte[16384];
                        for (int length = in.read(buffer); length != -1; length = in.read(buffer)) {
                            out.write(buffer, 0, length);
                        }
                        out.closeEntry();
                        out.close();
                        out = null;
                        Log.i(TAG, "Renaming to " + extractTo.getPath());
                        if (!tmp.renameTo(extractTo)) {
                            throw new IOException("Failed to rename \"" + tmp.getAbsolutePath() + "\" to \"" + extractTo.getAbsolutePath() + "\"");
                        }
                    } finally {
                        closeQuietly(out);
                        closeQuietly(in);
                        tmp.delete();
                    }
                }

/* 326 */       static boolean verifyZipFile(File file) {
                    try {
                        try {
/* 327 */                   ZipFile zipFile = new ZipFile(file);
                            try {
/* 329 */                       zipFile.close();
/* 330 */                       return true;
                            } catch (IOException e) {
/* 332 */                       Log.w(TAG, "Failed to close zip file: " + file.getAbsolutePath());
/* 339 */                       return false;
                            }
                        } catch (ZipException ex) {
/* 335 */                   Log.w(TAG, "File " + file.getAbsolutePath() + " is not a valid zip file.", ex);
                        }
                    } catch (IOException ex2) {
/* 337 */               Log.w(TAG, "Got an IOException trying to open zip file: " + file.getAbsolutePath(), ex2);
                    }
                    return false;
                }

/* 346 */       private static void closeQuietly(Closeable closeable) {
                    try {
/* 347 */               closeable.close();
                    } catch (IOException e) {
/* 349 */               Log.w(TAG, "Failed to close resource", e);
                    }
                }

                static {
                    try {
/* 358 */               sApplyMethod = SharedPreferences.Editor.class.getMethod("apply", new Class[0]);
                    } catch (NoSuchMethodException e) {
/* 361 */               sApplyMethod = null;
                    }
                }

/* 365 */       private static void apply(SharedPreferences.Editor editor) {
/* 366 */           if (sApplyMethod != null) {
                        try {
/* 368 */                   sApplyMethod.invoke(editor, new Object[0]);
/* 377 */                   return;
                        } catch (IllegalAccessException e) {
                        } catch (InvocationTargetException e2) {
                        }
                    }
/* 376 */           editor.commit();
                }
            }
