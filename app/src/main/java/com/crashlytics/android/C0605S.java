            package com.crashlytics.android;

            import java.io.File;
            import java.io.FilenameFilter;
            
            
            
    final class C0605S implements FilenameFilter {

                
                private final String f167a;

        public C0605S(String str) {
            this.f167a = str;
                }

                @Override 
        public final boolean accept(File file, String fileName) {
                    return (fileName.equals(new StringBuilder().append(this.f167a).append(".cls").toString()) || !fileName.contains(this.f167a) || fileName.endsWith(".cls_temp")) ? false : true;
                }
            }
