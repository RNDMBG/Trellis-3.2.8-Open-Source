            package org.apache.commons.p005io.monitor;

            import java.io.File;
            
            
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
