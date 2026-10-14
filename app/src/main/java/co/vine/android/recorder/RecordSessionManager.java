            package co.vine.android.recorder;

            import android.content.Context;
            import android.support.annotation.NonNull;
            import android.support.annotation.Nullable;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.SystemUtil;
            import com.edisonwang.android.slog.SLog;
            import com.google.android.exoplayer.util.MimeTypes;
            import java.io.BufferedInputStream;
            import java.io.BufferedOutputStream;
            import java.io.EOFException;
            import java.io.File;
            import java.io.FileInputStream;
            import java.io.FileNotFoundException;
            import java.io.FileOutputStream;
            import java.io.IOException;
            import java.io.InputStream;
            import java.io.ObjectInput;
            import java.io.ObjectInputStream;
            import java.io.ObjectOutput;
            import java.io.ObjectOutputStream;
            import java.io.OutputStream;
            import java.util.ArrayList;
            import java.util.Collections;
            import java.util.HashMap;
            import java.util.HashSet;
            import org.apache.commons.io.FileUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 39 */    public class RecordSessionManager {
                public static RecordSessionVersion DEFAULT_VERSION = RecordSessionVersion.HW;
                private static final int REQUIRED_SPACE_LIMIT = 20971520;
                private static final HashSet<String> sDeletedSessions;
                private final File mDir;
                private final RecordSessionVersion mVersion;

                static {
/* 44 */            if (DEFAULT_VERSION == RecordSessionVersion.HW && !RecordConfigUtils.HW_ENABLED) {
/* 45 */                throw new IllegalStateException("Invalidate version type, HW Encoding is off.");
                    }
/* 47 */            if (DEFAULT_VERSION != RecordSessionVersion.HW && !RecordConfigUtils.SW_ENABLED) {
/* 48 */                throw new IllegalStateException("Invalidate version type, HW Encoding is on.");
                    }
/* 56 */            sDeletedSessions = new HashSet<>();
                }

/* 60 */        public static RecordSessionVersion getCurrentVersion(@Nullable Context context) {
/* 61 */            return DEFAULT_VERSION;
                }

/* 63 */        public RecordSessionManager(Context context, RecordSessionVersion version) throws IOException {
/* 65 */            this.mVersion = version;
/* 66 */            File root = null;
                    try {
/* 68 */                root = context.getExternalFilesDir(null);
                    } catch (RuntimeException e) {
                    }
/* 72 */            if (root == null) {
/* 73 */                SLog.m455e("Failed to create drafts in external folder.");
/* 74 */                root = context.getFilesDir();
                    }
/* 76 */            this.mDir = new File(root, version.folder);
/* 77 */            if (this.mDir.exists() && !this.mDir.isDirectory()) {
/* 79 */                FileUtils.forceDelete(this.mDir);
                    }
/* 82 */            FileUtils.forceMkdir(this.mDir);
                    try {
/* 84 */                long freeBytes = this.mDir.getFreeSpace();
                        if (freeBytes > 0 && freeBytes < 20971520) {
/* 86 */                    throw new NotEnoughSpaceException();
                        }
/* 88 */                SLog.m447d("free space left: {}.", Long.valueOf(freeBytes));
                    } catch (SecurityException e2) {
                    }
                }

/* 95 */        public File getFolderFromName(String name) {
/* 96 */            if (name != null) {
/* 97 */                return new File(this.mDir, name);
                    }
                    return null;
                }

/* 103 */       public File createFolderForSession() throws IOException {
/* 104 */           File folder = new File(this.mDir, String.valueOf(System.currentTimeMillis()));
/* 105 */           FileUtils.forceMkdir(folder);
/* 106 */           return folder;
                }

/* 112 */       public ArrayList<File> getFolders() throws IOException {
                    File[] files;
/* 113 */           ArrayList<File> folders = new ArrayList<>();
/* 114 */           File[] list = this.mDir.listFiles();
/* 115 */           if (list == null) {
/* 116 */               throw new IOException("This should never happen.");
                    }
                    for (File folder : list) {
/* 119 */               if (folder.isDirectory() && (files = folder.listFiles()) != null) {
/* 122 */                   boolean hasAdded = false;
                            for (File file : files) {
/* 124 */                       String name = file.getName();
/* 125 */                       if (name.endsWith(this.mVersion.videoOutputExtension)) {
/* 126 */                           folders.add(folder);
/* 127 */                           hasAdded = true;
                                    break;
                                }
                            }
/* 131 */                   if (!hasAdded) {
/* 132 */                       folders.add(folder);
                            }
                        }
                    }
/* 137 */           return folders;
                }

/* 140 */       public static boolean isSessionSaved(File folder) {
                    return getMetaFile(folder).exists() && getDataFile(folder, true).exists();
                }

/* 145 */       public void cleanUnusedFolders() {
                    File[] listFiles;
/* 146 */           int i = 0;
/* 147 */           File[] list = this.mDir.listFiles();
/* 148 */           if (list != null) {
                        for (File folder : list) {
/* 150 */                   if (folder.isDirectory() && (listFiles = folder.listFiles()) != null) {
/* 153 */                       if (listFiles.length == 0) {
/* 154 */                           FileUtils.deleteQuietly(folder);
/* 155 */                           i++;
                                } else {
/* 157 */                           int j = listFiles.length;
                                    for (File f : listFiles) {
/* 159 */                               if (f.getName().endsWith(".mp4") || f.getName().endsWith(".mkv")) {
/* 160 */                                   j--;
                                        }
                                    }
/* 163 */                           if (j == 0) {
/* 164 */                               FileUtils.deleteQuietly(folder);
                                    }
/* 166 */                           i++;
                                }
                            }
                        }
                    }
/* 172 */           SLog.m465i("{} empty folders deleted.", Integer.valueOf(i));
                }

/* 173 */       public static class RecordSessionInfo implements Comparable<RecordSessionInfo> {
                    public final File folder;
                    public final RecordSessionMeta meta;
                    public final File thumb;
                    public final File video;


/* 180 */           public RecordSessionInfo(File folder, File thumb, File video, RecordSessionMeta meta) {
/* 182 */               this.folder = folder;
/* 183 */               this.thumb = thumb;
/* 184 */               this.video = video;
/* 185 */               this.meta = meta;
                    }

                    /* JADX INFO: renamed from: compareTo, reason: avoid collision after fix types in other method */
/* 189 */           public int compareTo(@NonNull RecordSessionInfo another) {
/* 190 */               return Long.valueOf(this.folder.getName()).compareTo(Long.valueOf(another.folder.getName()));
                    }
                }

/* 194 */       public static ArrayList<RecordSessionInfo> getValidSessions(Context context, RecordSessionVersion version) throws IOException {
/* 195 */           return version.getManager(context).getValidSessions();
                }

/* 198 */       public static int getNumberOfValidSessions(BasicVineRecorder recorder) throws IOException {
/* 199 */           return getNumberOfValidSessions(recorder.getActivity(), recorder.getVersion());
                }

/* 202 */       public static int getNumberOfValidSessions(Context context, RecordSessionVersion version) throws IOException {
/* 203 */           ArrayList<RecordSessionInfo> info = getValidSessions(context, version);
/* 204 */           if (info != null) {
/* 205 */               return info.size();
                    }
                    return 0;
                }

/* 211 */       public ArrayList<RecordSessionInfo> getValidSessions() throws IOException {
/* 212 */           ArrayList<File> folders = getFolders();
/* 213 */           ArrayList<RecordSessionInfo> sessions = new ArrayList<>(folders.size());
                    for (File folder : folders) {
                        try {
/* 216 */                   if (getMetaFile(folder).exists()) {
/* 217 */                       File thumb = new File(getThumbnailPath(folder));
/* 218 */                       File video = new File(getVideoPath(this.mVersion, folder));
/* 219 */                       File data = getDataFile(folder, true);
/* 220 */                       File data2 = getDataFile(folder, false);
/* 221 */                       RecordSessionMeta meta = readMetaObject(folder);
/* 222 */                       if (thumb.exists() && video.exists() && (data.exists() || data2.exists())) {
/* 224 */                           sessions.add(new RecordSessionInfo(folder, thumb, video, meta));
                                } else {
/* 226 */                           SLog.m456e("Invalid session found: {}.", folder.getAbsolutePath());
                                }
                            }
                        } catch (Exception e) {
/* 230 */                   CrashUtil.logException(e);
                        }
                    }
/* 233 */           Collections.sort(sessions);
/* 234 */           return sessions;
                }

/* 237 */       public HashMap<RecordSession, File> getCrashedSession() throws IOException {
/* 238 */           ArrayList<File> folders = getFolders();
                    for (File folder : folders) {
/* 240 */               File dataFile = getDataFile(folder, false);
/* 241 */               if (dataFile.exists()) {
                            try {
/* 243 */                       HashMap<RecordSession, File> map = new HashMap<>();
/* 244 */                       map.put((RecordSession) readObject(dataFile), folder);
/* 251 */                       return map;
                            } catch (IOException e) {
/* 247 */                       SLog.m459e("Failed to read session object.", (Throwable) e);
                            }
                        }
                    }
/* 251 */           return null;
                }

/* 254 */       public static File getFrameInfoFile(File folder) {
/* 255 */           return new File(folder, "frame.info");
                }

/* 258 */       public static File getDataFile(File folder, boolean isFinal) {
/* 259 */           return new File(folder, isFinal ? "data.bin" : "data.temp");
                }

/* 262 */       public static File getMetaFile(File folder) {
/* 263 */           return new File(folder, "meta.bin");
                }

/* 266 */       public static String getVideoPath(RecordSessionVersion version, File folder) {
/* 267 */           return new File(folder, MimeTypes.BASE_TYPE_VIDEO + version.videoOutputExtension).getAbsolutePath();
                }

/* 270 */       public static String getThumbnailPath(File folder) {
/* 271 */           return new File(folder, "thumbnail.jpg").getAbsolutePath();
                }

/* 274 */       public static String getSegmentVideoPath(RecordSessionVersion version, File folder) {
/* 275 */           return new File(folder, "segment_" + System.currentTimeMillis() + version.videoOutputExtension).getAbsolutePath();
                }

/* 278 */       public static String getSegmentThumbnailPath(File folder) {
/* 279 */           return new File(folder, "segment.jpg").getAbsolutePath();
                }

/* 282 */       public static String getPreviewVideoPath(RecordSessionVersion version, File folder) {
/* 283 */           return new File(folder, "preview" + version.videoOutputExtension).getAbsolutePath();
                }

/* 286 */       public static String getPreviewThumbnailPath(File folder) {
/* 287 */           return new File(folder, "preview.jpg").getAbsolutePath();
                }

/* 290 */       public static void deleteSession(File folder, String reason) throws IOException {
/* 291 */           CrashUtil.log("Session deleted: {}, {}.", reason, folder);
/* 292 */           if (sDeletedSessions.size() > 10) {
/* 293 */               sDeletedSessions.clear();
                    }
/* 295 */           sDeletedSessions.add(folder.getAbsolutePath());
/* 296 */           if (folder.exists()) {
/* 297 */               FileUtils.deleteDirectory(folder);
                    }
                }

/* 301 */       public static boolean wasSessionJustDeleted(File folder) {
/* 302 */           return sDeletedSessions.contains(folder.getAbsolutePath());
                }

/* 305 */       private static void writeObject(File f, Object obj) throws IOException {
/* 306 */           CrashUtil.log("Writing: {}.", f);
/* 307 */           SystemUtil.quietlyEnsureParentExists(f);
/* 308 */           OutputStream file = new FileOutputStream(f);
/* 309 */           OutputStream buffer = new BufferedOutputStream(file);
/* 310 */           ObjectOutput output = new ObjectOutputStream(buffer);
                    try {
/* 312 */               output.writeObject(obj);
/* 314 */               output.close();
/* 315 */               CrashUtil.log("Closed: {}.", f);
                    } catch (Throwable th) {
                        output.close();
/* 315 */               CrashUtil.log("Closed: {}.", f);
                        throw th;
                    }
                }

/* 319 */       public static void writeRecordingFile(File folder, RecordSession session, boolean isFinal) throws IOException {
/* 320 */           writeData(folder, session, isFinal);
/* 321 */           if (isFinal) {
/* 322 */               writeMeta(folder, new RecordSessionMeta(session.getDurationMs()));
/* 323 */               FileUtils.deleteQuietly(getDataFile(folder, false));
                    }
/* 325 */           cleanUpSegmentData(folder, session);
                }

/* 328 */       public static void cleanUpSegmentData(File folder, RecordSession session) {
/* 329 */           File segmentDataFolder = RecordSegment.getSegmentDataFolder(folder);
/* 330 */           if (segmentDataFolder.exists()) {
/* 331 */               File[] dataFiles = segmentDataFolder.listFiles();
/* 332 */               ArrayList<RecordSegment> segments = session.getSegments();
/* 333 */               HashSet<String> validSet = new HashSet<>();
                        for (RecordSegment segment : segments) {
/* 335 */                   String fn = segment.getDataFileName();
/* 336 */                   if (fn != null) {
/* 337 */                       validSet.add(fn);
                            }
                        }
                        for (File dataFile : dataFiles) {
/* 341 */                   String fn2 = dataFile.getName();
/* 342 */                   if (!validSet.contains(dataFile.getName())) {
/* 343 */                       boolean delete = dataFile.delete();
/* 344 */                       SLog.m448d("Detected removed segment: {}, deleted: {}.", fn2, Boolean.valueOf(delete));
                            }
                        }
                    }
                }

/* 350 */       public static void cleanUpSegmentData(File folder, HashSet<RecordSegment> deletes) {
/* 351 */           File segmentDataFolder = RecordSegment.getSegmentDataFolder(folder);
/* 352 */           if (segmentDataFolder.exists()) {
/* 353 */               File[] dataFiles = segmentDataFolder.listFiles();
/* 354 */               HashSet<String> invalidSet = new HashSet<>();
                        for (RecordSegment segment : deletes) {
/* 356 */                   String fn = segment.getDataFileName();
/* 357 */                   if (fn != null) {
/* 358 */                       invalidSet.add(fn);
                            }
                        }
                        for (File dataFile : dataFiles) {
/* 362 */                   String fn2 = dataFile.getName();
/* 363 */                   if (invalidSet.contains(dataFile.getName())) {
/* 364 */                       boolean delete = dataFile.delete();
/* 365 */                       SLog.m448d("Detected removed segment: {}, deleted: {}.", fn2, Boolean.valueOf(delete));
                            }
                        }
                    }
                }

/* 372 */       public static void writeRecordingFile(RecordingFile file, boolean isFinal) throws IOException {
/* 373 */           writeRecordingFile(file.folder, file.getSession(), isFinal);
                }

/* 376 */       private static void writeData(File folder, RecordSession session, boolean isFinal) throws IOException {
/* 377 */           writeObject(getDataFile(folder, isFinal), session);
/* 378 */           if (isFinal) {
/* 379 */               FileUtils.deleteQuietly(getDataFile(folder, false));
                    }
                }

/* 383 */       private static void writeMeta(File folder, RecordSessionMeta meta) throws IOException {
/* 384 */           writeObject(getMetaFile(folder), meta);
                }

/* 388 */       private static Object readObject(File f) throws IOException {
                    try {
/* 389 */               if (!f.exists()) {
/* 390 */                   throw new FileNotFoundException("File not found: " + f.getPath());
                        }
/* 392 */               InputStream file = new FileInputStream(f);
/* 393 */               InputStream buffer = new BufferedInputStream(file);
                        try {
/* 395 */                   ObjectInput input = new ObjectInputStream(buffer);
                            try {
/* 397 */                       Object object = input.readObject();
/* 399 */                       input.close();
                                return object;
                            } catch (Throwable th) {
                                input.close();
                                throw th;
                            }
                        } catch (EOFException e) {
/* 402 */                   SLog.m455e("Failed to read a corrupted file.");
/* 404 */                   throw e;
                        }
                    } catch (ClassNotFoundException ex) {
/* 407 */               throw new IOException("Invalid file found.", ex);
                    }
                }

/* 411 */       public static RecordSession readDataObject(File folder) throws IOException {
/* 412 */           return (RecordSession) readObject(getDataFile(folder, true));
                }

/* 415 */       public static RecordSessionMeta readMetaObject(File folder) throws IOException {
/* 416 */           return (RecordSessionMeta) readObject(getMetaFile(folder));
                }
            }
