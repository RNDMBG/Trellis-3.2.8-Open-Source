            package com.crashlytics.android;

            import android.content.DialogInterface;
            
            
            
   final class DialogInterfaceOnClickListenerC0741s implements DialogInterface.OnClickListener {

                
                private  RunnableC0738p f486a;

       DialogInterfaceOnClickListenerC0741s(RunnableC0738p runnableC0738p) {
           this.f486a = runnableC0738p;
                }

                @Override 
       public final void onClick(DialogInterface dialog, int i) {
           Crashlytics crashlytics = this.f486a.f480b;
                    Crashlytics.m12a(true);
           this.f486a.f479a.m375a(true);
           dialog.dismiss();
                }
            }
