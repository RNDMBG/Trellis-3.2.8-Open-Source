            package com.crashlytics.android;

            import android.content.DialogInterface;
            
            
            
   final class DialogInterfaceOnClickListenerC0739q implements DialogInterface.OnClickListener {

                
                private  RunnableC0738p f484a;

       DialogInterfaceOnClickListenerC0739q(RunnableC0738p runnableC0738p) {
           this.f484a = runnableC0738p;
                }

                @Override 
       public final void onClick(DialogInterface dialog, int i) {
           this.f484a.f479a.m375a(true);
           dialog.dismiss();
                }
            }
