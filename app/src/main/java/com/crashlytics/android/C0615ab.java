            package com.crashlytics.android;

            import java.io.File;
            import java.io.FilenameFilter;
            
            
            
    final class C0615ab implements FilenameFilter {
        C0615ab() {
                }

                @Override 
        public final boolean accept(File file, String filename) {
                    return filename.endsWith(".cls") && !filename.contains("Session");
                }
            }
