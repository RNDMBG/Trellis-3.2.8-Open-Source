            package org.apache.commons.p005io.filefilter;

            import java.io.File;
            import java.io.Serializable;
            import java.util.ArrayList;
            import java.util.Collections;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 46 */    public class OrFileFilter extends AbstractFileFilter implements ConditionalFileFilter, Serializable {
                private final List<IOFileFilter> fileFilters;

/* 47 */        public OrFileFilter() {
/* 49 */            this.fileFilters = new ArrayList();
                }

/* 58 */        public OrFileFilter(List<IOFileFilter> fileFilters) {
/* 60 */            if (fileFilters == null) {
/* 61 */                this.fileFilters = new ArrayList();
                    } else {
/* 63 */                this.fileFilters = new ArrayList(fileFilters);
                    }
                }

/* 73 */        public OrFileFilter(IOFileFilter filter1, IOFileFilter filter2) {
/* 75 */            if (filter1 == null || filter2 == null) {
/* 76 */                throw new IllegalArgumentException("The filters must not be null");
                    }
/* 78 */            this.fileFilters = new ArrayList(2);
/* 79 */            addFileFilter(filter1);
/* 80 */            addFileFilter(filter2);
                }

                @Override // org.apache.commons.p005io.filefilter.ConditionalFileFilter
/* 86 */        public void addFileFilter(IOFileFilter ioFileFilter) {
/* 87 */            this.fileFilters.add(ioFileFilter);
                }

                @Override // org.apache.commons.p005io.filefilter.ConditionalFileFilter
/* 93 */        public List<IOFileFilter> getFileFilters() {
/* 94 */            return Collections.unmodifiableList(this.fileFilters);
                }

                @Override // org.apache.commons.p005io.filefilter.ConditionalFileFilter
/* 100 */       public boolean removeFileFilter(IOFileFilter ioFileFilter) {
/* 101 */           return this.fileFilters.remove(ioFileFilter);
                }

                @Override // org.apache.commons.p005io.filefilter.ConditionalFileFilter
/* 107 */       public void setFileFilters(List<IOFileFilter> fileFilters) {
/* 108 */           this.fileFilters.clear();
/* 109 */           this.fileFilters.addAll(fileFilters);
                }

                @Override // org.apache.commons.p005io.filefilter.AbstractFileFilter, org.apache.commons.p005io.filefilter.IOFileFilter, java.io.FileFilter
/* 116 */       public boolean accept(File file) {
                    for (IOFileFilter fileFilter : this.fileFilters) {
/* 118 */               if (fileFilter.accept(file)) {
/* 119 */                   return true;
                        }
                    }
                    return false;
                }

                @Override // org.apache.commons.p005io.filefilter.AbstractFileFilter, org.apache.commons.p005io.filefilter.IOFileFilter, java.io.FilenameFilter
/* 129 */       public boolean accept(File file, String name) {
                    for (IOFileFilter fileFilter : this.fileFilters) {
/* 131 */               if (fileFilter.accept(file, name)) {
/* 132 */                   return true;
                        }
                    }
                    return false;
                }

                @Override // org.apache.commons.p005io.filefilter.AbstractFileFilter
/* 144 */       public String toString() {
/* 145 */           StringBuilder buffer = new StringBuilder();
/* 146 */           buffer.append(super.toString());
/* 147 */           buffer.append("(");
/* 148 */           if (this.fileFilters != null) {
                        for (int i = 0; i < this.fileFilters.size(); i++) {
/* 150 */                   if (i > 0) {
/* 151 */                       buffer.append(",");
                            }
/* 153 */                   Object filter = this.fileFilters.get(i);
/* 154 */                   buffer.append(filter == null ? "null" : filter.toString());
                        }
                    }
/* 157 */           buffer.append(")");
/* 158 */           return buffer.toString();
                }
            }
