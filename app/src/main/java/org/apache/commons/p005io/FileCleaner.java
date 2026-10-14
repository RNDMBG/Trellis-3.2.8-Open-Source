            package org.apache.commons.p005io;

            import java.io.File;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            @Deprecated
/* 56 */    public class FileCleaner {
                static final FileCleaningTracker theInstance = new FileCleaningTracker();

                @Deprecated
/* 57 */        public static void track(File file, Object marker) {
/* 58 */            theInstance.track(file, marker);
                }

                @Deprecated
/* 73 */        public static void track(File file, Object marker, FileDeleteStrategy deleteStrategy) {
/* 74 */            theInstance.track(file, marker, deleteStrategy);
                }

                @Deprecated
/* 88 */        public static void track(String path, Object marker) {
/* 89 */            theInstance.track(path, marker);
                }

                @Deprecated
/* 104 */       public static void track(String path, Object marker, FileDeleteStrategy deleteStrategy) {
/* 105 */           theInstance.track(path, marker, deleteStrategy);
                }

                @Deprecated
/* 117 */       public static int getTrackCount() {
/* 118 */           return theInstance.getTrackCount();
                }

                @Deprecated
/* 144 */       public static synchronized void exitWhenFinished() {
                    try {
                        theInstance.exitWhenFinished();
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 156 */       public static FileCleaningTracker getInstance() {
/* 157 */           return theInstance;
                }
            }
