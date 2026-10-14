            package com.crashlytics.android;

            import java.io.File;
            import java.io.FilenameFilter;
            
            
            
    final class C0624g implements FilenameFilter {
        C0624g() {
                }

                @Override 
       public final boolean accept(File file, String filename) {
           return filename.endsWith(".cls_temp");
                }
            }
