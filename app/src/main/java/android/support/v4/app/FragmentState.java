            package android.support.v4.app;

            import android.os.Bundle;
            import android.os.Parcel;
            import android.os.Parcelable;
            import android.util.Log;
            
            /* JADX INFO: compiled from: Fragment.java */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 61 */    final class FragmentState implements Parcelable {
                public static final Parcelable.Creator<FragmentState> CREATOR = new C00151();
                final Bundle mArguments;
                final String mClassName;
                final int mContainerId;
                final boolean mDetached;
                final int mFragmentId;
                final boolean mFromLayout;
                final int mIndex;
                Fragment mInstance;
                final boolean mRetainInstance;
                Bundle mSavedFragmentState;
                final String mTag;

/* 62 */        public FragmentState(Fragment frag) {
/* 64 */            this.mClassName = frag.getClass().getName();
/* 65 */            this.mIndex = frag.mIndex;
/* 66 */            this.mFromLayout = frag.mFromLayout;
/* 67 */            this.mFragmentId = frag.mFragmentId;
/* 68 */            this.mContainerId = frag.mContainerId;
/* 69 */            this.mTag = frag.mTag;
/* 70 */            this.mRetainInstance = frag.mRetainInstance;
/* 71 */            this.mDetached = frag.mDetached;
/* 72 */            this.mArguments = frag.mArguments;
                }

/* 74 */        public FragmentState(Parcel in) {
/* 76 */            this.mClassName = in.readString();
/* 77 */            this.mIndex = in.readInt();
/* 78 */            this.mFromLayout = in.readInt() != 0;
/* 79 */            this.mFragmentId = in.readInt();
/* 80 */            this.mContainerId = in.readInt();
/* 81 */            this.mTag = in.readString();
/* 82 */            this.mRetainInstance = in.readInt() != 0;
/* 83 */            this.mDetached = in.readInt() != 0;
/* 84 */            this.mArguments = in.readBundle();
/* 85 */            this.mSavedFragmentState = in.readBundle();
                }

/* 88 */        public Fragment instantiate(FragmentActivity activity, Fragment parent) {
/* 89 */            if (this.mInstance != null) {
/* 90 */                return this.mInstance;
                    }
/* 93 */            if (this.mArguments != null) {
/* 94 */                this.mArguments.setClassLoader(activity.getClassLoader());
                    }
/* 97 */            this.mInstance = Fragment.instantiate(activity, this.mClassName, this.mArguments);
/* 99 */            if (this.mSavedFragmentState != null) {
/* 100 */               this.mSavedFragmentState.setClassLoader(activity.getClassLoader());
/* 101 */               this.mInstance.mSavedFragmentState = this.mSavedFragmentState;
                    }
/* 103 */           this.mInstance.setIndex(this.mIndex, parent);
/* 104 */           this.mInstance.mFromLayout = this.mFromLayout;
/* 105 */           this.mInstance.mRestored = true;
/* 106 */           this.mInstance.mFragmentId = this.mFragmentId;
/* 107 */           this.mInstance.mContainerId = this.mContainerId;
/* 108 */           this.mInstance.mTag = this.mTag;
/* 109 */           this.mInstance.mRetainInstance = this.mRetainInstance;
/* 110 */           this.mInstance.mDetached = this.mDetached;
/* 111 */           this.mInstance.mFragmentManager = activity.mFragments;
/* 113 */           if (FragmentManagerImpl.DEBUG) {
                        Log.v("FragmentManager", "Instantiated fragment " + this.mInstance);
                    }
/* 116 */           return this.mInstance;
                }

                @Override // android.os.Parcelable
/* 119 */       public int describeContents() {
/* 120 */           return 0;
                }

                @Override // android.os.Parcelable
/* 123 */       public void writeToParcel(Parcel dest, int flags) {
/* 124 */           dest.writeString(this.mClassName);
/* 125 */           dest.writeInt(this.mIndex);
/* 126 */           dest.writeInt(this.mFromLayout ? 1 : 0);
/* 127 */           dest.writeInt(this.mFragmentId);
/* 128 */           dest.writeInt(this.mContainerId);
/* 129 */           dest.writeString(this.mTag);
/* 130 */           dest.writeInt(this.mRetainInstance ? 1 : 0);
/* 131 */           dest.writeInt(this.mDetached ? 1 : 0);
/* 132 */           dest.writeBundle(this.mArguments);
/* 133 */           dest.writeBundle(this.mSavedFragmentState);
                }

                /* JADX INFO: renamed from: android.support.v4.app.FragmentState$1 */
                /* JADX INFO: compiled from: Fragment.java */
/* 135 */       static class C00151 implements Parcelable.Creator<FragmentState> {
/* 136 */           C00151() {
                    }

                    @Override // android.os.Parcelable.Creator
/* 136 */           public /* bridge */ /* synthetic */ FragmentState createFromParcel(Parcel x0) {
/* 137 */               return createFromParcel(x0);
                    }

                    @Override // android.os.Parcelable.Creator
/* 136 */           public /* bridge */ /* synthetic */ FragmentState[] newArray(int x0) {
/* 137 */               return newArray(x0);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 138 */           public FragmentState createFromParcel(Parcel in) {
/* 139 */               return new FragmentState(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 142 */           public FragmentState[] newArray(int size) {
/* 143 */               return new FragmentState[size];
                    }
                }
            }
