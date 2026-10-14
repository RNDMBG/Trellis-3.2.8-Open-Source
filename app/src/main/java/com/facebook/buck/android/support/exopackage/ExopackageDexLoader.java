            package com.facebook.buck.android.support.exopackage;

            import android.content.Context;
            import android.util.Log;
            import java.io.File;
            import java.util.ArrayList;
            import java.util.HashSet;
            import java.util.List;
            import java.util.Set;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 38 */    public class ExopackageDexLoader {
                private static final String TAG = "ExopackageDexLoader";

/* 39 */        private ExopackageDexLoader() {
                }

/* 49 */        public static void loadExopackageJars(Context context) {
/* 50 */            File containingDirectory = new File("/data/local/tmp/exopackage/" + context.getPackageName() + "/secondary-dex");
/* 53 */            List<File> dexJars = new ArrayList<>();
/* 54 */            Set<String> expectedOdexSet = new HashSet<>();
/* 56 */            File[] files = containingDirectory.listFiles();
/* 57 */            if (files != null) {
                        for (File file : files) {
/* 59 */                    if (!file.getName().equals("metadata.txt")) {
/* 62 */                        if (!file.getName().endsWith(".dex.jar")) {
/* 63 */                            Log.w(TAG, "Skipping unexpected file in exopackage directory: " + file.getName());
                                } else {
/* 66 */                            dexJars.add(file);
/* 67 */                            expectedOdexSet.add(file.getName().replaceFirst("\\.jar$", ".dex"));
                                }
                            }
                        }
                    }
/* 71 */            File dexOptDir = context.getDir("exopackage_dex_opt", 0);
/* 72 */            SystemClassLoaderAdder.installDexJars(context.getClassLoader(), dexOptDir, dexJars);
/* 74 */            File[] odexes = dexOptDir.listFiles();
/* 75 */            if (odexes != null) {
                        for (File odex : odexes) {
/* 77 */                    if (!expectedOdexSet.contains(odex.getName()) && !odex.delete()) {
/* 79 */                        Log.w(TAG, "Failed to delete stale odex: " + odex.getAbsolutePath());
                            }
                        }
                    }
                }
            }
