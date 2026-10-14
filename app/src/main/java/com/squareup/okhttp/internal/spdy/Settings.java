            package com.squareup.okhttp.internal.spdy;

            import java.util.Arrays;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 83 */    public final class Settings {
                static final int CLIENT_CERTIFICATE_VECTOR_SIZE = 8;
                static final int COUNT = 10;
                static final int CURRENT_CWND = 5;
                static final int DEFAULT_INITIAL_WINDOW_SIZE = 65536;
                static final int DOWNLOAD_BANDWIDTH = 2;
                static final int DOWNLOAD_RETRANS_RATE = 6;
                static final int ENABLE_PUSH = 2;
                static final int FLAG_CLEAR_PREVIOUSLY_PERSISTED_SETTINGS = 1;
                static final int FLOW_CONTROL_OPTIONS = 10;
                static final int FLOW_CONTROL_OPTIONS_DISABLED = 1;
                static final int HEADER_TABLE_SIZE = 1;
                static final int INITIAL_WINDOW_SIZE = 7;
                static final int MAX_CONCURRENT_STREAMS = 4;
                static final int MAX_FRAME_SIZE = 5;
                static final int MAX_HEADER_LIST_SIZE = 6;
                static final int PERSISTED = 2;
                static final int PERSIST_VALUE = 1;
                static final int ROUND_TRIP_TIME = 3;
                static final int UPLOAD_BANDWIDTH = 1;
                private int persistValue;
                private int persisted;
                private int set;
                private final int[] values = new int[10];

/* 84 */        void clear() {
/* 85 */            this.persisted = 0;
                    this.persistValue = 0;
                    this.set = 0;
/* 86 */            Arrays.fill(this.values, 0);
                }

/* 89 */        Settings set(int id, int idFlags, int value) {
/* 90 */            if (id < this.values.length) {
/* 94 */                int bit = 1 << id;
/* 95 */                this.set |= bit;
/* 96 */                if ((idFlags & 1) != 0) {
/* 97 */                    this.persistValue |= bit;
                        } else {
/* 99 */                    this.persistValue &= bit ^ (-1);
                        }
/* 101 */               if ((idFlags & 2) != 0) {
/* 102 */                   this.persisted |= bit;
                        } else {
/* 104 */                   this.persisted &= bit ^ (-1);
                        }
/* 107 */               this.values[id] = value;
                    }
/* 108 */           return this;
                }

/* 112 */       boolean isSet(int id) {
/* 113 */           int bit = 1 << id;
                    return (this.set & bit) != 0;
                }

/* 118 */       int get(int id) {
/* 119 */           return this.values[id];
                }

/* 123 */       int flags(int id) {
/* 124 */           int result = isPersisted(id) ? 0 | 2 : 0;
                    return persistValue(id) ? result | 1 : result;
                }

/* 131 */       int size() {
/* 132 */           return Integer.bitCount(this.set);
                }

/* 136 */       int getUploadBandwidth(int defaultValue) {
/* 137 */           if ((this.set & 2) == 0) {
                        return defaultValue;
                    }
                    int defaultValue2 = this.values[1];
                    return defaultValue2;
                }

/* 142 */       int getHeaderTableSize() {
/* 143 */           if ((this.set & 2) != 0) {
                        return this.values[1];
                    }
                    return -1;
                }

/* 148 */       int getDownloadBandwidth(int defaultValue) {
/* 149 */           if ((this.set & 4) == 0) {
                        return defaultValue;
                    }
                    int defaultValue2 = this.values[2];
                    return defaultValue2;
                }

/* 155 */       boolean getEnablePush(boolean defaultValue) {
                    int i;
/* 156 */           if ((this.set & 4) != 0) {
                        i = this.values[2];
                    } else {
                        i = defaultValue ? 1 : 0;
                    }
                    return i == 1;
                }

/* 161 */       int getRoundTripTime(int defaultValue) {
/* 162 */           if ((this.set & 8) == 0) {
                        return defaultValue;
                    }
                    int defaultValue2 = this.values[3];
                    return defaultValue2;
                }

/* 167 */       int getMaxConcurrentStreams(int defaultValue) {
/* 168 */           if ((this.set & 16) == 0) {
                        return defaultValue;
                    }
                    int defaultValue2 = this.values[4];
                    return defaultValue2;
                }

/* 173 */       int getCurrentCwnd(int defaultValue) {
/* 174 */           if ((this.set & 32) == 0) {
                        return defaultValue;
                    }
                    int defaultValue2 = this.values[5];
                    return defaultValue2;
                }

/* 179 */       int getMaxFrameSize(int defaultValue) {
/* 180 */           if ((this.set & 32) == 0) {
                        return defaultValue;
                    }
                    int defaultValue2 = this.values[5];
                    return defaultValue2;
                }

/* 185 */       int getDownloadRetransRate(int defaultValue) {
/* 186 */           if ((this.set & 64) == 0) {
                        return defaultValue;
                    }
                    int defaultValue2 = this.values[6];
                    return defaultValue2;
                }

/* 191 */       int getMaxHeaderListSize(int defaultValue) {
/* 192 */           if ((this.set & 64) == 0) {
                        return defaultValue;
                    }
                    int defaultValue2 = this.values[6];
                    return defaultValue2;
                }

/* 196 */       int getInitialWindowSize(int defaultValue) {
/* 197 */           if ((this.set & 128) == 0) {
                        return defaultValue;
                    }
                    int defaultValue2 = this.values[7];
                    return defaultValue2;
                }

/* 202 */       int getClientCertificateVectorSize(int defaultValue) {
/* 203 */           if ((this.set & 256) == 0) {
                        return defaultValue;
                    }
                    int defaultValue2 = this.values[8];
                    return defaultValue2;
                }

/* 208 */       boolean isFlowControlDisabled() {
/* 209 */           int value = (this.set & 1024) != 0 ? this.values[10] : 0;
                    return (value & 1) != 0;
                }

/* 218 */       boolean persistValue(int id) {
/* 219 */           int bit = 1 << id;
                    return (this.persistValue & bit) != 0;
                }

/* 224 */       boolean isPersisted(int id) {
/* 225 */           int bit = 1 << id;
                    return (this.persisted & bit) != 0;
                }

/* 233 */       void merge(Settings other) {
                    for (int i = 0; i < 10; i++) {
/* 235 */               if (other.isSet(i)) {
/* 236 */                   set(i, other.flags(i), other.get(i));
                        }
                    }
                }
            }
