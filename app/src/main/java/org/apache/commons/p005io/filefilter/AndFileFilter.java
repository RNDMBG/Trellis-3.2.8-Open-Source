            package org.apache.commons.p005io.filefilter;

            import java.io.File;
            import java.io.Serializable;
            import java.util.ArrayList;
            import java.util.Collections;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 47 */    public class AndFileFilter extends AbstractFileFilter implements ConditionalFileFilter, Serializable {
                private final List<IOFileFilter> fileFilters;

/* 48 */        public AndFileFilter() {
/* 50 */            this.fileFilters = new ArrayList();
                }

/* 59 */        public AndFileFilter(List<IOFileFilter> fileFilters) {
/* 61 */            if (fileFilters == null) {
/* 62 */                this.fileFilters = new ArrayList();
                    } else {
/* 64 */                this.fileFilters = new ArrayList(fileFilters);
                    }
                }

/* 74 */        public AndFileFilter(IOFileFilter filter1, IOFileFilter filter2) {
/* 76 */            if (filter1 == null || filter2 == null) {
/* 77 */                throw new IllegalArgumentException("The filters must not be null");
                    }
/* 79 */            this.fileFilters = new ArrayList(2);
/* 80 */            addFileFilter(filter1);
/* 81 */            addFileFilter(filter2);
                }

                @Override // org.apache.commons.p005io.filefilter.ConditionalFileFilter
/* 87 */        public void addFileFilter(IOFileFilter ioFileFilter) {
/* 88 */            this.fileFilters.add(ioFileFilter);
                }

                @Override // org.apache.commons.p005io.filefilter.ConditionalFileFilter
/* 94 */        public List<IOFileFilter> getFileFilters() {
/* 95 */            return Collections.unmodifiableList(this.fileFilters);
                }

                @Override // org.apache.commons.p005io.filefilter.ConditionalFileFilter
/* 101 */       public boolean removeFileFilter(IOFileFilter ioFileFilter) {
/* 102 */           return this.fileFilters.remove(ioFileFilter);
                }

                @Override // org.apache.commons.p005io.filefilter.ConditionalFileFilter
/* 108 */       public void setFileFilters(List<IOFileFilter> fileFilters) {
/* 109 */           this.fileFilters.clear();
/* 110 */           this.fileFilters.addAll(fileFilters);
                }

                @Override // org.apache.commons.p005io.filefilter.AbstractFileFilter, org.apache.commons.p005io.filefilter.IOFileFilter, java.io.FileFilter
/* 117 */       public boolean accept(File file) {
/* 118 */           if (this.fileFilters.isEmpty()) {
                        return false;
                    }
                    for (IOFileFilter fileFilter : this.fileFilters) {
/* 122 */               if (!fileFilter.accept(file)) {
                            return false;
                        }
                    }
/* 126 */           return true;
                }

                @Override // org.apache.commons.p005io.filefilter.AbstractFileFilter, org.apache.commons.p005io.filefilter.IOFileFilter, java.io.FilenameFilter
/* 133 */       public boolean accept(File file, String name) {
/* 134 */           if (this.fileFilters.isEmpty()) {
                        return false;
                    }
                    for (IOFileFilter fileFilter : this.fileFilters) {
/* 138 */               if (!fileFilter.accept(file, name)) {
                            return false;
                        }
                    }
/* 142 */           return true;
                }

                @Override // org.apache.commons.p005io.filefilter.AbstractFileFilter
/* 151 */       public String toString() {
/* 152 */           StringBuilder buffer = new StringBuilder();
/* 153 */           buffer.append(super.toString());
/* 154 */           buffer.append("(");
/* 155 */           if (this.fileFilters != null) {
                        for (int i = 0; i < this.fileFilters.size(); i++) {
/* 157 */                   if (i > 0) {
/* 158 */                       buffer.append(",");
                            }
/* 160 */                   Object filter = this.fileFilters.get(i);
/* 161 */                   buffer.append(filter == null ? "null" : filter.toString());
                        }
                    }
/* 164 */           buffer.append(")");
/* 165 */           return buffer.toString();
                }
            }
