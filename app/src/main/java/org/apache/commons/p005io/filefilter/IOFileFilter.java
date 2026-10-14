            package org.apache.commons.p005io.filefilter;

            import java.io.File;
            import java.io.FileFilter;
            import java.io.FilenameFilter;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface IOFileFilter extends FileFilter, FilenameFilter {
                @Override // java.io.FileFilter
                boolean accept(File file);

                boolean accept(File file, String str);
            }
