            package org.apache.commons.p005io.comparator;

            import java.io.File;
            import java.util.Arrays;
            import java.util.Collections;
            import java.util.Comparator;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 29 */    abstract class AbstractFileComparator implements Comparator<File> {
/* 30 */        AbstractFileComparator() {
                }

/* 43 */        public File[] sort(File... files) {
/* 44 */            if (files != null) {
/* 45 */                Arrays.sort(files, this);
                    }
/* 47 */            return files;
                }

/* 60 */        public List<File> sort(List<File> files) {
/* 61 */            if (files != null) {
/* 62 */                Collections.sort(files, this);
                    }
/* 64 */            return files;
                }

/* 73 */        public String toString() {
/* 74 */            return getClass().getSimpleName();
                }
            }
