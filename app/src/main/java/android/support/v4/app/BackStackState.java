            package android.support.v4.app;

            import android.os.Parcel;
            import android.os.Parcelable;
            import android.text.TextUtils;
            import android.util.Log;
            import java.util.ArrayList;
            
            /* JADX INFO: compiled from: BackStackRecord.java */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 38 */    final class BackStackState implements Parcelable {
                public static final Parcelable.Creator<BackStackState> CREATOR = new C00041();
                final int mBreadCrumbShortTitleRes;
                final CharSequence mBreadCrumbShortTitleText;
                final int mBreadCrumbTitleRes;
                final CharSequence mBreadCrumbTitleText;
                final int mIndex;
                final String mName;
                final int[] mOps;
                final int mTransition;
                final int mTransitionStyle;

/* 39 */        public BackStackState(FragmentManagerImpl fm, BackStackRecord bse) {
                    int pos;
/* 41 */            int numRemoved = 0;
/* 43 */            for (BackStackRecord.C0003Op op = bse.mHead; op != null; op = op.next) {
/* 44 */                if (op.removed != null) {
                            numRemoved += op.removed.size();
                        }
                    }
/* 47 */            this.mOps = new int[(bse.mNumOp * 7) + numRemoved];
/* 49 */            if (!bse.mAddToBackStack) {
/* 50 */                throw new IllegalStateException("Not on back stack");
                    }
/* 53 */            BackStackRecord.C0003Op op2 = bse.mHead;
/* 54 */            int pos2 = 0;
/* 55 */            while (op2 != null) {
                        int pos3 = pos2 + 1;
/* 56 */                this.mOps[pos2] = op2.cmd;
                        int pos4 = pos3 + 1;
/* 57 */                this.mOps[pos3] = op2.fragment != null ? op2.fragment.mIndex : -1;
                        int pos5 = pos4 + 1;
/* 58 */                this.mOps[pos4] = op2.enterAnim;
                        int pos6 = pos5 + 1;
/* 59 */                this.mOps[pos5] = op2.exitAnim;
                        int pos7 = pos6 + 1;
/* 60 */                this.mOps[pos6] = op2.popEnterAnim;
                        int pos8 = pos7 + 1;
/* 61 */                this.mOps[pos7] = op2.popExitAnim;
/* 62 */                if (op2.removed != null) {
/* 63 */                    int N = op2.removed.size();
/* 64 */                    this.mOps[pos8] = N;
/* 65 */                    int i = 0;
                            int pos9 = pos8 + 1;
                            while (i < N) {
/* 66 */                        this.mOps[pos9] = op2.removed.get(i).mIndex;
/* 65 */                        i++;
                                pos9++;
                            }
                            pos = pos9;
                        } else {
                            pos = pos8 + 1;
/* 69 */                    this.mOps[pos8] = 0;
                        }
/* 71 */                op2 = op2.next;
                        pos2 = pos;
                    }
/* 73 */            this.mTransition = bse.mTransition;
/* 74 */            this.mTransitionStyle = bse.mTransitionStyle;
/* 75 */            this.mName = bse.mName;
/* 76 */            this.mIndex = bse.mIndex;
/* 77 */            this.mBreadCrumbTitleRes = bse.mBreadCrumbTitleRes;
/* 78 */            this.mBreadCrumbTitleText = bse.mBreadCrumbTitleText;
/* 79 */            this.mBreadCrumbShortTitleRes = bse.mBreadCrumbShortTitleRes;
/* 80 */            this.mBreadCrumbShortTitleText = bse.mBreadCrumbShortTitleText;
                }

/* 82 */        public BackStackState(Parcel in) {
/* 84 */            this.mOps = in.createIntArray();
/* 85 */            this.mTransition = in.readInt();
/* 86 */            this.mTransitionStyle = in.readInt();
/* 87 */            this.mName = in.readString();
/* 88 */            this.mIndex = in.readInt();
/* 89 */            this.mBreadCrumbTitleRes = in.readInt();
/* 90 */            this.mBreadCrumbTitleText = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
/* 91 */            this.mBreadCrumbShortTitleRes = in.readInt();
/* 92 */            this.mBreadCrumbShortTitleText = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
                }

/* 95 */        public BackStackRecord instantiate(FragmentManagerImpl fm) {
/* 96 */            BackStackRecord bse = new BackStackRecord(fm);
/* 97 */            int pos = 0;
/* 98 */            int num = 0;
/* 99 */            while (pos < this.mOps.length) {
/* 100 */               BackStackRecord.C0003Op op = new BackStackRecord.C0003Op();
                        int pos2 = pos + 1;
/* 101 */               op.cmd = this.mOps[pos];
/* 102 */               if (FragmentManagerImpl.DEBUG) {
                            Log.v("FragmentManager", "Instantiate " + bse + " op #" + num + " base fragment #" + this.mOps[pos2]);
                        }
                        int pos3 = pos2 + 1;
/* 104 */               int findex = this.mOps[pos2];
/* 105 */               if (findex >= 0) {
/* 106 */                   Fragment f = fm.mActive.get(findex);
/* 107 */                   op.fragment = f;
                        } else {
/* 109 */                   op.fragment = null;
                        }
                        int pos4 = pos3 + 1;
/* 111 */               op.enterAnim = this.mOps[pos3];
                        int pos5 = pos4 + 1;
/* 112 */               op.exitAnim = this.mOps[pos4];
                        int pos6 = pos5 + 1;
/* 113 */               op.popEnterAnim = this.mOps[pos5];
                        int pos7 = pos6 + 1;
/* 114 */               op.popExitAnim = this.mOps[pos6];
                        int pos8 = pos7 + 1;
/* 115 */               int N = this.mOps[pos7];
/* 116 */               if (N > 0) {
/* 117 */                   op.removed = new ArrayList<>(N);
/* 118 */                   int i = 0;
                            while (i < N) {
/* 119 */                       if (FragmentManagerImpl.DEBUG) {
                                    Log.v("FragmentManager", "Instantiate " + bse + " set remove fragment #" + this.mOps[pos8]);
                                }
/* 121 */                       Fragment r = fm.mActive.get(this.mOps[pos8]);
/* 122 */                       op.removed.add(r);
/* 118 */                       i++;
                                pos8++;
                            }
                        }
                        pos = pos8;
/* 125 */               bse.addOp(op);
/* 126 */               num++;
                    }
/* 128 */           bse.mTransition = this.mTransition;
/* 129 */           bse.mTransitionStyle = this.mTransitionStyle;
/* 130 */           bse.mName = this.mName;
/* 131 */           bse.mIndex = this.mIndex;
/* 132 */           bse.mAddToBackStack = true;
/* 133 */           bse.mBreadCrumbTitleRes = this.mBreadCrumbTitleRes;
/* 134 */           bse.mBreadCrumbTitleText = this.mBreadCrumbTitleText;
/* 135 */           bse.mBreadCrumbShortTitleRes = this.mBreadCrumbShortTitleRes;
/* 136 */           bse.mBreadCrumbShortTitleText = this.mBreadCrumbShortTitleText;
/* 137 */           bse.bumpBackStackNesting(1);
/* 138 */           return bse;
                }

                @Override // android.os.Parcelable
/* 141 */       public int describeContents() {
/* 142 */           return 0;
                }

                @Override // android.os.Parcelable
/* 145 */       public void writeToParcel(Parcel dest, int flags) {
/* 146 */           dest.writeIntArray(this.mOps);
/* 147 */           dest.writeInt(this.mTransition);
/* 148 */           dest.writeInt(this.mTransitionStyle);
/* 149 */           dest.writeString(this.mName);
/* 150 */           dest.writeInt(this.mIndex);
/* 151 */           dest.writeInt(this.mBreadCrumbTitleRes);
/* 152 */           TextUtils.writeToParcel(this.mBreadCrumbTitleText, dest, 0);
/* 153 */           dest.writeInt(this.mBreadCrumbShortTitleRes);
/* 154 */           TextUtils.writeToParcel(this.mBreadCrumbShortTitleText, dest, 0);
                }

                /* JADX INFO: renamed from: android.support.v4.app.BackStackState$1 */
                /* JADX INFO: compiled from: BackStackRecord.java */
/* 156 */       static class C00041 implements Parcelable.Creator<BackStackState> {
/* 157 */           C00041() {
                    }

                    @Override // android.os.Parcelable.Creator
/* 157 */           public /* bridge */ /* synthetic */ BackStackState createFromParcel(Parcel x0) {
/* 158 */               return createFromParcel(x0);
                    }

                    @Override // android.os.Parcelable.Creator
/* 157 */           public /* bridge */ /* synthetic */ BackStackState[] newArray(int x0) {
/* 158 */               return newArray(x0);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 159 */           public BackStackState createFromParcel(Parcel in) {
/* 160 */               return new BackStackState(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 163 */           public BackStackState[] newArray(int size) {
/* 164 */               return new BackStackState[size];
                    }
                }
            }
