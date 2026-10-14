            package org.apache.commons.p005io.filefilter;

            import java.io.File;
            import java.io.Serializable;
            import java.util.regex.Pattern;
            import org.apache.commons.p005io.IOCase;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 54 */    public class RegexFileFilter extends AbstractFileFilter implements Serializable {
                private final Pattern pattern;

/* 55 */        public RegexFileFilter(String pattern) {
/* 57 */            if (pattern == null) {
/* 58 */                throw new IllegalArgumentException("Pattern is missing");
                    }
/* 61 */            this.pattern = Pattern.compile(pattern);
                }

/* 70 */        public RegexFileFilter(String pattern, IOCase caseSensitivity) {
/* 72 */            if (pattern == null) {
/* 73 */                throw new IllegalArgumentException("Pattern is missing");
                    }
/* 75 */            int flags = 0;
/* 76 */            if (caseSensitivity != null && !caseSensitivity.isCaseSensitive()) {
/* 77 */                flags = 2;
                    }
/* 79 */            this.pattern = Pattern.compile(pattern, flags);
                }

/* 88 */        public RegexFileFilter(String pattern, int flags) {
/* 90 */            if (pattern == null) {
/* 91 */                throw new IllegalArgumentException("Pattern is missing");
                    }
/* 93 */            this.pattern = Pattern.compile(pattern, flags);
                }

/* 101 */       public RegexFileFilter(Pattern pattern) {
/* 103 */           if (pattern == null) {
/* 104 */               throw new IllegalArgumentException("Pattern is missing");
                    }
/* 107 */           this.pattern = pattern;
                }

                @Override // org.apache.commons.p005io.filefilter.AbstractFileFilter, org.apache.commons.p005io.filefilter.IOFileFilter, java.io.FilenameFilter
/* 118 */       public boolean accept(File dir, String name) {
/* 119 */           return this.pattern.matcher(name).matches();
                }
            }
