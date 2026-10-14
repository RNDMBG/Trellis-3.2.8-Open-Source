            package org.apache.commons.p005io.monitor;

            import java.io.File;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 34 */    public class FileAlterationListenerAdaptor implements FileAlterationListener {
                @Override // org.apache.commons.p005io.monitor.FileAlterationListener
/* 35 */        public void onStart(FileAlterationObserver observer) {
                }

                @Override // org.apache.commons.p005io.monitor.FileAlterationListener
/* 43 */        public void onDirectoryCreate(File directory) {
                }

                @Override // org.apache.commons.p005io.monitor.FileAlterationListener
/* 51 */        public void onDirectoryChange(File directory) {
                }

                @Override // org.apache.commons.p005io.monitor.FileAlterationListener
/* 59 */        public void onDirectoryDelete(File directory) {
                }

                @Override // org.apache.commons.p005io.monitor.FileAlterationListener
/* 67 */        public void onFileCreate(File file) {
                }

                @Override // org.apache.commons.p005io.monitor.FileAlterationListener
/* 75 */        public void onFileChange(File file) {
                }

                @Override // org.apache.commons.p005io.monitor.FileAlterationListener
/* 83 */        public void onFileDelete(File file) {
                }

                @Override // org.apache.commons.p005io.monitor.FileAlterationListener
/* 91 */        public void onStop(FileAlterationObserver observer) {
                }
            }
