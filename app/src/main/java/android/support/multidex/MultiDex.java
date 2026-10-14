            package android.support.multidex;

            import android.content.Context;
            import android.content.pm.ApplicationInfo;
            import android.content.pm.PackageManager;
            import android.os.Build;
            import android.util.Log;
            import co.vine.android.util.CommonUtil;
            import dalvik.system.DexFile;
            import java.io.File;
            import java.io.IOException;
            import java.lang.reflect.Array;
            import java.lang.reflect.Field;
            import java.lang.reflect.InvocationTargetException;
            import java.lang.reflect.Method;
            import java.util.ArrayList;
            import java.util.Arrays;
            import java.util.HashSet;
            import java.util.List;
            import java.util.ListIterator;
            import java.util.Set;
            import java.util.regex.Matcher;
            import java.util.regex.Pattern;
            import java.util.zip.ZipFile;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 55 */    public final class MultiDex {
                private static final int MAX_SUPPORTED_SDK_VERSION = 20;
                private static final int MIN_SDK_VERSION = 4;
                static final String TAG = "MultiDex";
                private static final int VM_WITH_MULTIDEX_VERSION_MAJOR = 2;
                private static final int VM_WITH_MULTIDEX_VERSION_MINOR = 1;
                private static final String OLD_SECONDARY_FOLDER_NAME = "secondary-dexes";
                private static final String SECONDARY_FOLDER_NAME = "code_cache" + File.separator + OLD_SECONDARY_FOLDER_NAME;
                private static final Set<String> installedApk = new HashSet();
                private static final boolean IS_VM_MULTIDEX_CAPABLE = isVMMultidexCapable(System.getProperty("java.vm.version"));

/* 56 */        static /* synthetic */ Field access$300(Object x0, String x1) throws NoSuchFieldException {
/* 57 */            return findField(x0, x1);
                }

/* 56 */        static /* synthetic */ void access$400(Object x0, String x1, Object[] x2) throws IllegalAccessException, NoSuchFieldException, IllegalArgumentException {
/* 57 */            expandFieldArray(x0, x1, x2);
                }

/* 56 */        static /* synthetic */ Method access$500(Object x0, String x1, Class[] x2) throws NoSuchMethodException {
/* 57 */            return findMethod(x0, x1, x2);
                }

/* 78 */        private MultiDex() {
                }

/* 91 */        public static void install(Context context) {
/* 92 */            Log.i(TAG, "install");
/* 93 */            if (IS_VM_MULTIDEX_CAPABLE) {
/* 94 */                Log.i(TAG, "VM has multidex support, MultiDex support library is disabled.");
/* 181 */               return;
                    }
/* 98 */            if (Build.VERSION.SDK_INT < 4) {
/* 99 */                throw new RuntimeException("Multi dex installation failed. SDK " + Build.VERSION.SDK_INT + " is unsupported. Min SDK version is 4.");
                    }
                    try {
/* 104 */               ApplicationInfo applicationInfo = getApplicationInfo(context);
/* 105 */               if (applicationInfo != null) {
                            synchronized (installedApk) {
                                try {
/* 111 */                           String apkPath = applicationInfo.sourceDir;
/* 112 */                           if (!installedApk.contains(apkPath)) {
/* 115 */                               installedApk.add(apkPath);
/* 117 */                               if (Build.VERSION.SDK_INT > 20) {
/* 118 */                                   Log.w(TAG, "MultiDex is not guaranteed to work in SDK version " + Build.VERSION.SDK_INT + ": SDK version higher than 20 should be backed by runtime with built-in multidex capabilty but it's not the case here: java.vm.version=\"" + System.getProperty("java.vm.version") + "\"");
                                        }
                                        try {
/* 133 */                                   ClassLoader loader = context.getClassLoader();
/* 143 */                                   if (loader == null) {
/* 145 */                                       Log.e(TAG, "Context class loader is null. Must be running in test mode. Skip patching.");
/* 181 */                                       return;
                                            }
                                            try {
/* 152 */                                       clearOldDexDir(context);
                                            } catch (Throwable t) {
/* 154 */                                       Log.w(TAG, "Something went wrong when trying to clear old MultiDex extraction, continuing without cleaning.", t);
                                            }
/* 158 */                                   File dexDir = new File(applicationInfo.dataDir, SECONDARY_FOLDER_NAME);
/* 159 */                                   List<File> files = MultiDexExtractor.load(context, applicationInfo, dexDir, false);
/* 160 */                                   if (checkValidZipFiles(files)) {
/* 161 */                                       installSecondaryDexes(loader, dexDir, files);
                                            } else {
/* 163 */                                       Log.w(TAG, "Files were not valid zip files.  Forcing a reload.");
/* 165 */                                       List<File> files2 = MultiDexExtractor.load(context, applicationInfo, dexDir, true);
/* 167 */                                       if (checkValidZipFiles(files2)) {
/* 168 */                                           installSecondaryDexes(loader, dexDir, files2);
                                                } else {
/* 171 */                                           throw new RuntimeException("Zip files were not valid.");
                                                }
                                            }
/* 180 */                                   Log.i(TAG, "install done");
                                        } catch (RuntimeException e) {
/* 139 */                                   Log.w(TAG, "Failure while trying to obtain Context class loader. Must be running in test mode. Skip patching.", e);
                                        }
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        }
                    } catch (Exception e2) {
/* 177 */               Log.e(TAG, "Multidex installation failure", e2);
/* 178 */               throw new RuntimeException("Multi dex installation failed (" + e2.getMessage() + ").");
                    }
                }

/* 187 */       private static ApplicationInfo getApplicationInfo(Context context) throws PackageManager.NameNotFoundException {
                    try {
/* 188 */               PackageManager pm = context.getPackageManager();
/* 189 */               String packageName = context.getPackageName();
/* 199 */               if (pm == null || packageName == null) {
                            return null;
                        }
/* 203 */               return pm.getApplicationInfo(packageName, 128);
                    } catch (RuntimeException e) {
/* 195 */               Log.w(TAG, "Failure while trying to obtain ApplicationInfo from Context. Must be running in test mode. Skip patching.", e);
                        return null;
                    }
                }

/* 214 */       static boolean isVMMultidexCapable(String versionString) {
/* 215 */           boolean isMultidexCapable = false;
/* 216 */           if (versionString != null) {
/* 217 */               Matcher matcher = Pattern.compile("(\\d+)\\.(\\d+)(\\.\\d+)?").matcher(versionString);
/* 218 */               if (matcher.matches()) {
                            try {
/* 220 */                       int major = Integer.parseInt(matcher.group(1));
/* 221 */                       int minor = Integer.parseInt(matcher.group(2));
/* 222 */                       isMultidexCapable = major > 2 || (major == 2 && minor >= 1);
                            } catch (NumberFormatException e) {
                            }
                        }
                    }
/* 230 */           Log.i(TAG, "VM with version " + versionString + (isMultidexCapable ? " has multidex support" : " does not have multidex support"));
/* 234 */           return isMultidexCapable;
                }

/* 239 */       private static void installSecondaryDexes(ClassLoader loader, File dexDir, List<File> files) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, IOException, IllegalArgumentException, InvocationTargetException {
/* 240 */           if (!files.isEmpty()) {
/* 241 */               if (Build.VERSION.SDK_INT >= 19) {
/* 242 */                   V19.access$000(loader, files, dexDir);
                        } else if (Build.VERSION.SDK_INT >= 14) {
/* 244 */                   V14.access$100(loader, files, dexDir);
                        } else {
/* 246 */                   C0000V4.access$200(loader, files);
                        }
                    }
                }

/* 255 */       private static boolean checkValidZipFiles(List<File> files) {
                    for (File file : files) {
/* 257 */               if (!MultiDexExtractor.verifyZipFile(file)) {
/* 258 */                   return false;
                        }
                    }
                    return true;
                }

/* 272 */       private static Field findField(Object instance, String name) throws NoSuchFieldException {
                    for (Class<?> clazz = instance.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
                        try {
/* 275 */                   Field field = clazz.getDeclaredField(name);
/* 278 */                   if (!field.isAccessible()) {
/* 279 */                       field.setAccessible(true);
                            }
/* 282 */                   return field;
                        } catch (NoSuchFieldException e) {
                        }
                    }
/* 288 */           throw new NoSuchFieldException("Field " + name + " not found in " + instance.getClass());
                }

/* 301 */       private static Method findMethod(Object instance, String name, Class<?>... parameterTypes) throws NoSuchMethodException {
                    for (Class<?> clazz = instance.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
                        try {
/* 304 */                   Method method = clazz.getDeclaredMethod(name, parameterTypes);
/* 307 */                   if (!method.isAccessible()) {
/* 308 */                       method.setAccessible(true);
                            }
/* 311 */                   return method;
                        } catch (NoSuchMethodException e) {
                        }
                    }
/* 317 */           throw new NoSuchMethodException("Method " + name + " with parameters " + Arrays.asList(parameterTypes) + " not found in " + instance.getClass());
                }

/* 330 */       private static void expandFieldArray(Object instance, String fieldName, Object[] extraElements) throws IllegalAccessException, NoSuchFieldException, IllegalArgumentException {
/* 331 */           Field jlrField = findField(instance, fieldName);
/* 332 */           Object[] original = (Object[]) jlrField.get(instance);
/* 333 */           Object[] combined = (Object[]) Array.newInstance(original.getClass().getComponentType(), original.length + extraElements.length);
/* 335 */           System.arraycopy(original, 0, combined, 0, original.length);
/* 336 */           System.arraycopy(extraElements, 0, combined, original.length, extraElements.length);
/* 337 */           jlrField.set(instance, combined);
                }

/* 340 */       private static void clearOldDexDir(Context context) throws Exception {
/* 341 */           File dexDir = new File(context.getFilesDir(), OLD_SECONDARY_FOLDER_NAME);
/* 342 */           if (dexDir.isDirectory()) {
/* 343 */               Log.i(TAG, "Clearing old secondary dex dir (" + dexDir.getPath() + ").");
/* 344 */               File[] files = dexDir.listFiles();
/* 345 */               if (files == null) {
/* 346 */                   Log.w(TAG, "Failed to list secondary dex dir content (" + dexDir.getPath() + ").");
/* 364 */                   return;
                        }
                        for (File oldFile : files) {
/* 350 */                   Log.i(TAG, "Trying to delete old file " + oldFile.getPath() + " of size " + oldFile.length());
/* 352 */                   if (!oldFile.delete()) {
/* 353 */                       Log.w(TAG, "Failed to delete old file " + oldFile.getPath());
                            } else {
/* 355 */                       Log.i(TAG, "Deleted old file " + oldFile.getPath());
                            }
                        }
/* 358 */               if (!dexDir.delete()) {
/* 359 */                   Log.w(TAG, "Failed to delete secondary dex dir " + dexDir.getPath());
                        } else {
/* 361 */                   Log.i(TAG, "Deleted old secondary dex dir " + dexDir.getPath());
                        }
                    }
                }

/* 367 */       private static final class V19 {
/* 368 */           private V19() {
                    }

/* 368 */           static /* synthetic */ void access$000(ClassLoader x0, List x1, File x2) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
/* 369 */               install(x0, x1, x2);
                    }

/* 379 */           private static void install(ClassLoader loader, List<File> additionalClassPathEntries, File optimizedDirectory) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
                        IOException[] dexElementsSuppressedExceptions;
/* 380 */               Field pathListField = MultiDex.access$300(loader, "pathList");
/* 381 */               Object dexPathList = pathListField.get(loader);
/* 382 */               ArrayList<IOException> suppressedExceptions = new ArrayList<>();
/* 383 */               MultiDex.access$400(dexPathList, "dexElements", makeDexElements(dexPathList, new ArrayList(additionalClassPathEntries), optimizedDirectory, suppressedExceptions));
/* 386 */               if (suppressedExceptions.size() > 0) {
                            for (IOException e : suppressedExceptions) {
/* 388 */                       Log.w(MultiDex.TAG, "Exception in makeDexElement", e);
                            }
/* 390 */                   Field suppressedExceptionsField = MultiDex.access$300(loader, "dexElementsSuppressedExceptions");
/* 392 */                   IOException[] dexElementsSuppressedExceptions2 = (IOException[]) suppressedExceptionsField.get(loader);
/* 395 */                   if (dexElementsSuppressedExceptions2 == null) {
/* 396 */                       dexElementsSuppressedExceptions = (IOException[]) suppressedExceptions.toArray(new IOException[suppressedExceptions.size()]);
                            } else {
/* 400 */                       IOException[] combined = new IOException[suppressedExceptions.size() + dexElementsSuppressedExceptions2.length];
/* 403 */                       suppressedExceptions.toArray(combined);
/* 404 */                       System.arraycopy(dexElementsSuppressedExceptions2, 0, combined, suppressedExceptions.size(), dexElementsSuppressedExceptions2.length);
/* 406 */                       dexElementsSuppressedExceptions = combined;
                            }
/* 409 */                   suppressedExceptionsField.set(loader, dexElementsSuppressedExceptions);
                        }
                    }

/* 421 */           private static Object[] makeDexElements(Object dexPathList, ArrayList<File> files, File optimizedDirectory, ArrayList<IOException> suppressedExceptions) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
/* 422 */               Method makeDexElements = MultiDex.access$500(dexPathList, "makeDexElements", new Class[]{ArrayList.class, File.class, ArrayList.class});
                        return (Object[]) makeDexElements.invoke(dexPathList, files, optimizedDirectory, suppressedExceptions);
                    }
                }

/* 432 */       private static final class V14 {
/* 433 */           private V14() {
                    }

/* 433 */           static /* synthetic */ void access$100(ClassLoader x0, List x1, File x2) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
/* 434 */               install(x0, x1, x2);
                    }

/* 444 */           private static void install(ClassLoader loader, List<File> additionalClassPathEntries, File optimizedDirectory) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
/* 445 */               Field pathListField = MultiDex.access$300(loader, "pathList");
/* 446 */               Object dexPathList = pathListField.get(loader);
/* 447 */               MultiDex.access$400(dexPathList, "dexElements", makeDexElements(dexPathList, new ArrayList(additionalClassPathEntries), optimizedDirectory));
                    }

/* 458 */           private static Object[] makeDexElements(Object dexPathList, ArrayList<File> files, File optimizedDirectory) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
/* 459 */               Method makeDexElements = MultiDex.access$500(dexPathList, "makeDexElements", new Class[]{ArrayList.class, File.class});
                        return (Object[]) makeDexElements.invoke(dexPathList, files, optimizedDirectory);
                    }
                }

                /* JADX INFO: renamed from: android.support.multidex.MultiDex$V4 */
/* 467 */       private static final class C0000V4 {
/* 468 */           private C0000V4() {
                    }

/* 468 */           static /* synthetic */ void access$200(ClassLoader x0, List x1) throws IllegalAccessException, NoSuchFieldException, IOException, IllegalArgumentException {
/* 469 */               install(x0, x1);
                    }

/* 477 */           private static void install(ClassLoader loader, List<File> additionalClassPathEntries) throws IllegalAccessException, NoSuchFieldException, IOException, IllegalArgumentException {
/* 478 */               int extraSize = additionalClassPathEntries.size();
/* 480 */               Field pathField = MultiDex.access$300(loader, "path");
/* 482 */               StringBuilder path = new StringBuilder((String) pathField.get(loader));
/* 483 */               String[] extraPaths = new String[extraSize];
/* 484 */               File[] extraFiles = new File[extraSize];
/* 485 */               ZipFile[] extraZips = new ZipFile[extraSize];
/* 486 */               DexFile[] extraDexs = new DexFile[extraSize];
/* 487 */               ListIterator<File> iterator = additionalClassPathEntries.listIterator();
/* 488 */               while (iterator.hasNext()) {
/* 489 */                   File additionalEntry = iterator.next();
/* 490 */                   String entryPath = additionalEntry.getAbsolutePath();
/* 491 */                   path.append(CommonUtil.MARKER_COLON).append(entryPath);
/* 492 */                   int index = iterator.previousIndex();
/* 493 */                   extraPaths[index] = entryPath;
/* 494 */                   extraFiles[index] = additionalEntry;
/* 495 */                   extraZips[index] = new ZipFile(additionalEntry);
/* 496 */                   extraDexs[index] = DexFile.loadDex(entryPath, entryPath + ".dex", 0);
                        }
/* 499 */               pathField.set(loader, path.toString());
/* 500 */               MultiDex.access$400(loader, "mPaths", extraPaths);
/* 501 */               MultiDex.access$400(loader, "mFiles", extraFiles);
/* 502 */               MultiDex.access$400(loader, "mZips", extraZips);
/* 503 */               MultiDex.access$400(loader, "mDexs", extraDexs);
                    }
                }
            }
