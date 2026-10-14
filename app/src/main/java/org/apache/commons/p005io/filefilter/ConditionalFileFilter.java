            package org.apache.commons.p005io.filefilter;

            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface ConditionalFileFilter {
                void addFileFilter(IOFileFilter iOFileFilter);

                List<IOFileFilter> getFileFilters();

                boolean removeFileFilter(IOFileFilter iOFileFilter);

                void setFileFilters(List<IOFileFilter> list);
            }
