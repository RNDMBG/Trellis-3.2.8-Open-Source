            package com.crashlytics.android;

            import java.io.File;
            import java.io.FilenameFilter;
            
            
            
   final class C0603Q implements FilenameFilter {
       private C0603Q() {
                }

        C0603Q(byte b) {
           this();
                }

                @Override 
       public final boolean accept(File file, String fileName) {
                    return !C0744v.f493a.accept(file, fileName) && C0744v.m420i().matcher(fileName).matches();
                }
            }
