            package co.vine.android.api;

            import android.os.Parcel;
            import android.os.Parcelable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 13 */    public class ComplaintMenuOption implements Parcelable {
                public static final Parcelable.Creator<ComplaintMenuOption> CREATOR = new C03141();
                public ComplaintChoice[] complaintChoices;
                public String complaintType;
                public String prompt;

/* 14 */        public ComplaintMenuOption() {
                }

/* 17 */        public ComplaintMenuOption(Parcel in) {
/* 19 */            this.complaintType = in.readString();
/* 20 */            this.prompt = in.readString();
/* 21 */            this.complaintChoices = (ComplaintChoice[]) in.createTypedArray(ComplaintChoice.CREATOR);
                }

                @Override // android.os.Parcelable
/* 25 */        public int describeContents() {
/* 26 */            return 0;
                }

                @Override // android.os.Parcelable
/* 31 */        public void writeToParcel(Parcel dest, int flags) {
/* 32 */            dest.writeString(this.complaintType);
/* 33 */            dest.writeString(this.prompt);
/* 34 */            dest.writeTypedArray(this.complaintChoices, 0);
                }

                /* JADX INFO: renamed from: co.vine.android.api.ComplaintMenuOption$1 */
/* 35 */        static class C03141 implements Parcelable.Creator<ComplaintMenuOption> {
/* 36 */            C03141() {
                    }



                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 39 */            public ComplaintMenuOption createFromParcel(Parcel source) {
/* 40 */                return new ComplaintMenuOption(source);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 44 */            public ComplaintMenuOption[] newArray(int size) {
/* 45 */                return new ComplaintMenuOption[size];
                    }
                }

/* 54 */        public static class ComplaintChoice implements Parcelable {
                    public static final Parcelable.Creator<ComplaintChoice> CREATOR = new C03151();
                    public String confirmation;
                    public boolean selected;
                    public String title;
                    public String value;

/* 55 */            public ComplaintChoice() {
/* 57 */                this.selected = false;
                    }

/* 59 */            public ComplaintChoice(Parcel in) {
/* 61 */                this.title = in.readString();
/* 62 */                this.value = in.readString();
/* 63 */                this.confirmation = in.readString();
/* 64 */                this.selected = in.readInt() != 0;
                    }

                    @Override // android.os.Parcelable
/* 68 */            public int describeContents() {
/* 69 */                return 0;
                    }

                    @Override // android.os.Parcelable
/* 73 */            public void writeToParcel(Parcel dest, int flags) {
/* 74 */                dest.writeString(this.title);
/* 75 */                dest.writeString(this.value);
/* 76 */                dest.writeString(this.confirmation);
/* 77 */                dest.writeInt(this.selected ? 1 : 0);
                    }

                    /* JADX INFO: renamed from: co.vine.android.api.ComplaintMenuOption$ComplaintChoice$1 */
/* 78 */            static class C03151 implements Parcelable.Creator<ComplaintChoice> {
/* 79 */                C03151() {
                        }



                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // android.os.Parcelable.Creator
/* 82 */                public ComplaintChoice createFromParcel(Parcel source) {
/* 83 */                    return new ComplaintChoice(source);
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // android.os.Parcelable.Creator
/* 87 */                public ComplaintChoice[] newArray(int size) {
/* 88 */                    return new ComplaintChoice[size];
                        }
                    }
                }
            }
