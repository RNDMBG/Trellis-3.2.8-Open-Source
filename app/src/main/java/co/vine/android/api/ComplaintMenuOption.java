            package co.vine.android.api;

            import android.os.Parcel;
            import android.os.Parcelable;
            
            
    public class ComplaintMenuOption implements Parcelable {
                public static final Parcelable.Creator<ComplaintMenuOption> CREATOR = new C03141();
                public ComplaintChoice[] complaintChoices;
                public String complaintType;
                public String prompt;

        public ComplaintMenuOption() {
                }

        public ComplaintMenuOption(Parcel in) {
            this.complaintType = in.readString();
            this.prompt = in.readString();
            this.complaintChoices = (ComplaintChoice[]) in.createTypedArray(ComplaintChoice.CREATOR);
                }

                @Override 
        public int describeContents() {
            return 0;
                }

                @Override 
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.complaintType);
            dest.writeString(this.prompt);
            dest.writeTypedArray(this.complaintChoices, 0);
                }

                
        static class C03141 implements Parcelable.Creator<ComplaintMenuOption> {
            C03141() {
                    }



                    
                    @Override 
            public ComplaintMenuOption createFromParcel(Parcel source) {
                return new ComplaintMenuOption(source);
                    }

                    
                    @Override 
            public ComplaintMenuOption[] newArray(int size) {
                return new ComplaintMenuOption[size];
                    }
                }

        public static class ComplaintChoice implements Parcelable {
                    public static final Parcelable.Creator<ComplaintChoice> CREATOR = new C03151();
                    public String confirmation;
                    public boolean selected;
                    public String title;
                    public String value;

            public ComplaintChoice() {
                this.selected = false;
                    }

            public ComplaintChoice(Parcel in) {
                this.title = in.readString();
                this.value = in.readString();
                this.confirmation = in.readString();
                this.selected = in.readInt() != 0;
                    }

                    @Override 
            public int describeContents() {
                return 0;
                    }

                    @Override 
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.title);
                dest.writeString(this.value);
                dest.writeString(this.confirmation);
                dest.writeInt(this.selected ? 1 : 0);
                    }

                    
            static class C03151 implements Parcelable.Creator<ComplaintChoice> {
                C03151() {
                        }



                        
                        @Override 
                public ComplaintChoice createFromParcel(Parcel source) {
                    return new ComplaintChoice(source);
                        }

                        
                        @Override 
                public ComplaintChoice[] newArray(int size) {
                    return new ComplaintChoice[size];
                        }
                    }
                }
            }
