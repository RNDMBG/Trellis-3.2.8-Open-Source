            package com.crashlytics.android;

            import android.content.DialogInterface;
            
            
            
   final class DialogInterfaceOnClickListenerC0740r implements DialogInterface.OnClickListener {

                
                private  RunnableC0738p f485a;

       DialogInterfaceOnClickListenerC0740r(RunnableC0738p runnableC0738p) {
           this.f485a = runnableC0738p;
                }

                @Override 
       public final void onClick(DialogInterface dialog, int i) {
           this.f485a.f479a.m375a(false);
           dialog.dismiss();
                }
            }
