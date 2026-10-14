            package com.crashlytics.android;

            import java.io.File;
            import java.io.FilenameFilter;
            import java.util.Arrays;
            import java.util.Comparator;
            
            
            
    class C0619b {

                
                public final String f186a;

                
                public final String f187b;

                
                public final String f188c;

                
                public final String f189d;

                
                public final String f190e;

                
                public final String f191f;

                
                public final int f192g;

                
                public final String f193h;

                
                public final String f194i;

                
                public final C0611Y f195j;

                
        public static void m60a(File file, FilenameFilter filenameFilter, int i, Comparator<File> comparator) {
            File[] fileArrListFiles = file.listFiles(filenameFilter);
            if (fileArrListFiles != null && fileArrListFiles.length > i) {
                Arrays.sort(fileArrListFiles, comparator);
                int length = fileArrListFiles.length;
                        for (File file2 : fileArrListFiles) {
                    if (length > i) {
                        file2.delete();
                        length--;
                            } else {
                        return;
                            }
                        }
                    }
                }

        public C0619b(String str, String str2, String str3, String str4, String str5, String str6, int i, String str7, String str8, C0611Y c0611y) {
            this.f186a = str;
            this.f187b = str2;
            this.f188c = str3;
            this.f189d = str4;
            this.f190e = str5;
            this.f191f = str6;
            this.f192g = i;
            this.f193h = str7;
            this.f194i = str8;
            this.f195j = c0611y;
                }
            }
