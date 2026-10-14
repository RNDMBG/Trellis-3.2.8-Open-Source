            package org.apache.commons.p005io.filefilter;

            import java.util.List;
            
            
            public interface ConditionalFileFilter {
                void addFileFilter(IOFileFilter iOFileFilter);

                List<IOFileFilter> getFileFilters();

                boolean removeFileFilter(IOFileFilter iOFileFilter);

                void setFileFilters(List<IOFileFilter> list);
            }
