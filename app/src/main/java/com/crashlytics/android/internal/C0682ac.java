            package com.crashlytics.android.internal;

            import java.io.File;
            import java.util.Comparator;
            
            
            
   final class C0682ac implements Comparator<File> {
       C0682ac() {
                }

                @Override 
       public final  int compare(File file, File file2) {
           return (int) (file.lastModified() - file2.lastModified());
                }
            }
