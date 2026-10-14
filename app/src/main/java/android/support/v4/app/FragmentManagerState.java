            package android.support.v4.app;

            import android.os.Parcel;
            import android.os.Parcelable;
            
            /* JADX INFO: compiled from: FragmentManager.java */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 358 */   final class FragmentManagerState implements Parcelable {
                public static final Parcelable.Creator<FragmentManagerState> CREATOR = new C00141();
                FragmentState[] mActive;
                int[] mAdded;
                BackStackState[] mBackStack;

/* 359 */       public FragmentManagerState() {
                }

/* 362 */       public FragmentManagerState(Parcel in) {
/* 364 */           this.mActive = (FragmentState[]) in.createTypedArray(FragmentState.CREATOR);
/* 365 */           this.mAdded = in.createIntArray();
/* 366 */           this.mBackStack = (BackStackState[]) in.createTypedArray(BackStackState.CREATOR);
                }

                @Override // android.os.Parcelable
/* 369 */       public int describeContents() {
/* 370 */           return 0;
                }

                @Override // android.os.Parcelable
/* 373 */       public void writeToParcel(Parcel dest, int flags) {
/* 374 */           dest.writeTypedArray(this.mActive, flags);
/* 375 */           dest.writeIntArray(this.mAdded);
/* 376 */           dest.writeTypedArray(this.mBackStack, flags);
                }

                /* JADX INFO: renamed from: android.support.v4.app.FragmentManagerState$1 */
                /* JADX INFO: compiled from: FragmentManager.java */
/* 378 */       static class C00141 implements Parcelable.Creator<FragmentManagerState> {
/* 379 */           C00141() {
                    }

                    @Override // android.os.Parcelable.Creator
/* 379 */           public /* bridge */ /* synthetic */ FragmentManagerState createFromParcel(Parcel x0) {
/* 380 */               return createFromParcel(x0);
                    }

                    @Override // android.os.Parcelable.Creator
/* 379 */           public /* bridge */ /* synthetic */ FragmentManagerState[] newArray(int x0) {
/* 380 */               return newArray(x0);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 381 */           public FragmentManagerState createFromParcel(Parcel in) {
/* 382 */               return new FragmentManagerState(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 385 */           public FragmentManagerState[] newArray(int size) {
/* 386 */               return new FragmentManagerState[size];
                    }
                }
            }
