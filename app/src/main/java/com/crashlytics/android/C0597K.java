            package com.crashlytics.android;

            import java.io.File;
            import java.io.FilenameFilter;
            
            
            
   final class C0597K implements FilenameFilter {
       C0597K() {
                }

                @Override 
       public final boolean accept(File file, String filename) {
           return C0744v.m420i().matcher(filename).matches();
                }
            }
