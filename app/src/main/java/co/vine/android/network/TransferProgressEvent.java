            package co.vine.android.network;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 8 */     public class TransferProgressEvent {
                private long bytesTransfered;

/* 9 */         public TransferProgressEvent(int bytesTransfered) {
/* 11 */            this.bytesTransfered = bytesTransfered;
                }

/* 19 */        public void setBytesTransfered(long bytesTransfered) {
/* 20 */            this.bytesTransfered = bytesTransfered;
                }

/* 28 */        public long getBytesTransfered() {
/* 29 */            return this.bytesTransfered;
                }
            }
