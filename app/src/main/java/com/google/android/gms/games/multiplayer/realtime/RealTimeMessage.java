            package com.google.android.gms.games.multiplayer.realtime;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.internal.C1028dm;
            
            
            public final class RealTimeMessage implements Parcelable {
                public static final Parcelable.Creator<RealTimeMessage> CREATOR = new C09271();
                public static final int RELIABLE = 1;
                public static final int UNRELIABLE = 0;

                
                private final String f1101oa;

                
                private final byte[] f1102ob;

                
                private final int f1103oc;

                
                static class C09271 implements Parcelable.Creator<RealTimeMessage> {
                    C09271() {
                    }

                    
                    public RealTimeMessage[] m1219U(int i) {
                        return new RealTimeMessage[i];
                    }

                    @Override 
                    public  RealTimeMessage createFromParcel(Parcel x0) {
                        return m1220x(x0);
                    }

                    @Override 
                    public  RealTimeMessage[] newArray(int x0) {
                        return m1219U(x0);
                    }

                    
                    public RealTimeMessage m1220x(Parcel parcel) {
                        return new RealTimeMessage(parcel, null);
                    }
                }

                private RealTimeMessage(Parcel parcel) {
                    this(parcel.readString(), parcel.createByteArray(), parcel.readInt());
                }

                 RealTimeMessage(Parcel x0, C09271 x1) {
                    this(x0);
                }

                public RealTimeMessage(String senderParticipantId, byte[] messageData, int isReliable) {
                    this.f1101oa = (String) C1028dm.m1605e(senderParticipantId);
                    this.f1102ob = (byte[]) ((byte[]) C1028dm.m1605e(messageData)).clone();
                    this.f1103oc = isReliable;
                }

                @Override 
                public int describeContents() {
                    return 0;
                }

                public byte[] getMessageData() {
                    return this.f1102ob;
                }

                public String getSenderParticipantId() {
                    return this.f1101oa;
                }

                public boolean isReliable() {
                    return this.f1103oc == 1;
                }

                @Override 
                public void writeToParcel(Parcel parcel, int flag) {
                    parcel.writeString(this.f1101oa);
                    parcel.writeByteArray(this.f1102ob);
                    parcel.writeInt(this.f1103oc);
                }
            }
