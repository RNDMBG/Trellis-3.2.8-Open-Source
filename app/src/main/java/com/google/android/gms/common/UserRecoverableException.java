            package com.google.android.gms.common;

            import android.content.Intent;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class UserRecoverableException extends Exception {
                private final Intent mIntent;

                public UserRecoverableException(String msg, Intent intent) {
                    super(msg);
                    this.mIntent = intent;
                }

                public Intent getIntent() {
                    return new Intent(this.mIntent);
                }
            }
