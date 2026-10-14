            package org.apache.commons.p005io.filefilter;

            import java.io.File;
            import java.io.Serializable;
            import java.util.List;
            import org.apache.commons.p005io.FilenameUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            @Deprecated
/* 62 */    public class WildcardFilter extends AbstractFileFilter implements Serializable {
                private final String[] wildcards;

/* 63 */        public WildcardFilter(String wildcard) {
/* 65 */            if (wildcard == null) {
/* 66 */                throw new IllegalArgumentException("The wildcard must not be null");
                    }
/* 68 */            this.wildcards = new String[]{wildcard};
                }

/* 76 */        public WildcardFilter(String[] wildcards) {
/* 78 */            if (wildcards == null) {
/* 79 */                throw new IllegalArgumentException("The wildcard array must not be null");
                    }
/* 81 */            this.wildcards = new String[wildcards.length];
/* 82 */            System.arraycopy(wildcards, 0, this.wildcards, 0, wildcards.length);
                }

/* 91 */        public WildcardFilter(List<String> wildcards) {
/* 93 */            if (wildcards == null) {
/* 94 */                throw new IllegalArgumentException("The wildcard list must not be null");
                    }
/* 96 */            this.wildcards = (String[]) wildcards.toArray(new String[wildcards.size()]);
                }

                @Override // org.apache.commons.p005io.filefilter.AbstractFileFilter, org.apache.commons.p005io.filefilter.IOFileFilter, java.io.FilenameFilter
/* 108 */       public boolean accept(File dir, String name) {
/* 109 */           if (dir != null && new File(dir, name).isDirectory()) {
                        return false;
                    }
/* 113 */           String[] arr$ = this.wildcards;
                    for (String wildcard : arr$) {
/* 114 */               if (FilenameUtils.wildcardMatch(name, wildcard)) {
/* 115 */                   return true;
                        }
                    }
                    return false;
                }

                @Override // org.apache.commons.p005io.filefilter.AbstractFileFilter, org.apache.commons.p005io.filefilter.IOFileFilter, java.io.FileFilter
/* 129 */       public boolean accept(File file) {
/* 130 */           if (file.isDirectory()) {
                        return false;
                    }
/* 134 */           String[] arr$ = this.wildcards;
                    for (String wildcard : arr$) {
/* 135 */               if (FilenameUtils.wildcardMatch(file.getName(), wildcard)) {
/* 136 */                   return true;
                        }
                    }
                    return false;
                }
            }
