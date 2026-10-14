            package org.apache.commons.p005io.monitor;

            import java.io.File;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface FileAlterationListener {
                void onDirectoryChange(File file);

                void onDirectoryCreate(File file);

                void onDirectoryDelete(File file);

                void onFileChange(File file);

                void onFileCreate(File file);

                void onFileDelete(File file);

                void onStart(FileAlterationObserver fileAlterationObserver);

                void onStop(FileAlterationObserver fileAlterationObserver);
            }
