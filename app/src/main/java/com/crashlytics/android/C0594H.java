            package com.crashlytics.android;

            import java.io.File;
            import java.util.Comparator;
            
            
            
   final class C0594H implements Comparator<File> {
       C0594H() {
                }

                @Override 
       public final  int compare(File file, File file2) {
           return file2.getName().compareTo(file.getName());
                }
            }
