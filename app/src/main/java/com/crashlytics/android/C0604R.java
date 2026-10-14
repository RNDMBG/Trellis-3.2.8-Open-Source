            package com.crashlytics.android;

            import java.io.File;
            import java.io.FilenameFilter;
            
            
            
    final class C0604R implements FilenameFilter {

                
                private final String f166a;

        public C0604R(String str) {
            this.f166a = str;
                }

                @Override 
        public final boolean accept(File file, String filename) {
                    return filename.contains(this.f166a) && !filename.endsWith(".cls_temp");
                }
            }
