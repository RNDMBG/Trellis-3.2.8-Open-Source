            package android.support.v4.app;

            import android.os.Parcel;
            import android.os.Parcelable;
            
            
            
   final class FragmentManagerState implements Parcelable {
                public static final Parcelable.Creator<FragmentManagerState> CREATOR = new C00141();
                FragmentState[] mActive;
                int[] mAdded;
                BackStackState[] mBackStack;

       public FragmentManagerState() {
                }

       public FragmentManagerState(Parcel in) {
           this.mActive = (FragmentState[]) in.createTypedArray(FragmentState.CREATOR);
           this.mAdded = in.createIntArray();
           this.mBackStack = (BackStackState[]) in.createTypedArray(BackStackState.CREATOR);
                }

                @Override 
       public int describeContents() {
           return 0;
                }

                @Override 
       public void writeToParcel(Parcel dest, int flags) {
           dest.writeTypedArray(this.mActive, flags);
           dest.writeIntArray(this.mAdded);
           dest.writeTypedArray(this.mBackStack, flags);
                }

                
                
       static class C00141 implements Parcelable.Creator<FragmentManagerState> {
           C00141() {
                    }

                    @Override 
           public   FragmentManagerState createFromParcel(Parcel x0) {
               return createFromParcel(x0);
                    }

                    @Override 
           public   FragmentManagerState[] newArray(int x0) {
               return newArray(x0);
                    }

                    
                    @Override 
           public FragmentManagerState createFromParcel(Parcel in) {
               return new FragmentManagerState(in);
                    }

                    
                    @Override 
           public FragmentManagerState[] newArray(int size) {
               return new FragmentManagerState[size];
                    }
                }
            }
