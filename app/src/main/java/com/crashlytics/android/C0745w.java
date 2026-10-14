            package com.crashlytics.android;

            import java.io.File;
            import java.io.FilenameFilter;
            
            
            
   final class C0745w implements FilenameFilter {
       C0745w() {
                }

                @Override 
       public final boolean accept(File file, String filename) {
                    return filename.length() == 39 && filename.endsWith(".cls");
                }
            }
