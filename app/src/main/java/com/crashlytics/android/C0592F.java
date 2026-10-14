            package com.crashlytics.android;

            import java.io.File;
            import java.io.FilenameFilter;
            
            
            
  final class C0592F implements FilenameFilter {

                
                private  String f152a;

      C0592F(C0744v c0744v, String str) {
          this.f152a = str;
                }

                @Override 
      public final boolean accept(File file, String name) {
          return name.startsWith(this.f152a);
                }
            }
