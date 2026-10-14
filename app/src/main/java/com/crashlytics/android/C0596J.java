            package com.crashlytics.android;

            import java.io.File;
            import java.util.Comparator;
            
            
            
   final class C0596J implements Comparator<File> {
       C0596J() {
                }

                @Override 
       public final  int compare(File file, File file2) {
           return file.getName().compareTo(file2.getName());
                }
            }
