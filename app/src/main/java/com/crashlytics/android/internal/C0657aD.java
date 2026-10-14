            package com.crashlytics.android.internal;

            import java.io.IOException;
            
            
            
   public final class C0657aD extends RuntimeException {
                private static final long serialVersionUID = -1170466989781746231L;

                @Override 
       public final   Throwable getCause() {
           return (IOException) super.getCause();
                }

       protected C0657aD(IOException iOException) {
           super(iOException);
                }
            }
