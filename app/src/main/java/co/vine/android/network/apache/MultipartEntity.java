            package co.vine.android.network.apache;

            import android.content.Context;
            import android.net.Uri;
            import co.vine.android.network.CountingOutputStream;
            import co.vine.android.network.ProgressListener;
            import co.vine.android.util.Util;
            import java.io.ByteArrayInputStream;
            import java.io.IOException;
            import java.io.InputStream;
            import java.io.OutputStream;
            import java.util.ArrayList;
            import org.apache.http.entity.AbstractHttpEntity;
            import org.apache.http.message.BasicHeader;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 40 */    public class MultipartEntity extends AbstractHttpEntity {
                public static final String BOUNDARY = "--vineaADFASDFADSFAFD";
                public static final String BOUNDARY_NAME = "vineaADFASDFADSFAFD";
                private static final int BUFF_SIZE = 4096;
                public static final String CRLF = "\r\n";
                public static final String DASH_DASH = "--";
                private static final String UTF8 = "UTF-8";
                private int mApproxStreamLength;
                private final Context mContext;
                private final ArrayList<Entity> mEntities;
                private int mLength;
                private final ProgressListener mListener;

                public interface Entity {
                    InputStream getInputStream() throws IOException;

                    void writeTo(OutputStream outputStream) throws IOException;
                }

/* 41 */        public MultipartEntity(Context context, ProgressListener listener) {
/* 43 */            this.mListener = listener;
/* 44 */            this.contentType = new BasicHeader("Content-Type", "multipart/form-data; boundary=vineaADFASDFADSFAFD");
/* 46 */            this.mEntities = new ArrayList<>();
/* 47 */            this.mContext = context.getApplicationContext();
                }

                @Override // org.apache.http.HttpEntity
/* 50 */        public boolean isRepeatable() {
/* 51 */            return true;
                }

                @Override // org.apache.http.HttpEntity
/* 60 */        public long getContentLength() {
/* 61 */            return this.mLength;
                }

                @Override // org.apache.http.HttpEntity
/* 64 */        public InputStream getContent() throws IOException {
/* 65 */            return new MultipartInputStream(this.mEntities);
                }

                @Override // org.apache.http.HttpEntity
/* 68 */        public boolean isStreaming() {
/* 69 */            return false;
                }

                @Override // org.apache.http.HttpEntity
/* 72 */        public void writeTo(OutputStream outstream) throws IOException {
/* 73 */            OutputStream out = new CountingOutputStream(outstream, this.mApproxStreamLength, this.mListener);
                    for (Entity e : this.mEntities) {
/* 77 */                e.writeTo(out);
                    }
                }

/* 81 */        public void addFile(String name, String filename, Uri uri) throws IOException {
/* 82 */            byte[] fileHeader = (BOUNDARY + "\r\nContent-Disposition: form-data; name=\"" + name + "\"; filename=\"" + filename + "\"\r\nContent-Type: image/jpeg\r\nContent-Transfer-Encoding: binary\r\n\r\n").getBytes("UTF-8");
/* 88 */            byte[] fileFooter = "\r\n".getBytes("UTF-8");
/* 90 */            ArrayList<Entity> entities = this.mEntities;
/* 91 */            entities.add(new ByteArrayEntity(fileHeader));
/* 92 */            entities.add(new UriEntity(this.mContext, uri));
/* 93 */            entities.add(new ByteArrayEntity(fileFooter));
/* 95 */            this.mApproxStreamLength = (int) (this.mApproxStreamLength + (Util.getFileSize(this.mContext, uri) * 1024.0f) + fileHeader.length + fileFooter.length);
                }

/* 99 */        public void addString(String name, String value) {
/* 100 */           byte[] entity = (BOUNDARY + "\r\nContent-Disposition: form-data; name=\"" + name + "\"\r\n\r\n" + value + "\r\n").getBytes();
/* 104 */           this.mApproxStreamLength += entity.length;
/* 105 */           this.mLength += entity.length;
/* 106 */           this.mEntities.add(new ByteArrayEntity(entity));
                }

/* 109 */       public void end() throws IOException {
/* 110 */           this.mEntities.add(new ByteArrayEntity("--vineaADFASDFADSFAFD--\r\n".getBytes("UTF-8")));
                }

/* 120 */       public static class ByteArrayEntity implements Entity {
                    private final byte[] mValue;

/* 121 */           public ByteArrayEntity(byte[] value) {
/* 123 */               this.mValue = value;
                    }

                    @Override // co.vine.android.network.apache.MultipartEntity.Entity
/* 127 */           public void writeTo(OutputStream outstream) throws IOException {
/* 128 */               outstream.write(this.mValue);
                    }

                    @Override // co.vine.android.network.apache.MultipartEntity.Entity
/* 132 */           public InputStream getInputStream() throws IOException {
/* 133 */               return new ByteArrayInputStream(this.mValue);
                    }
                }

/* 139 */       public static class UriEntity implements Entity {
                    private final Context mContext;
                    private final Uri mUri;

/* 140 */           public UriEntity(Context context, Uri uri) {
/* 142 */               this.mUri = uri;
/* 143 */               this.mContext = context;
                    }

                    @Override // co.vine.android.network.apache.MultipartEntity.Entity
/* 147 */           public void writeTo(OutputStream outstream) throws IOException {
/* 148 */               InputStream fileStream = this.mContext.getContentResolver().openInputStream(this.mUri);
/* 150 */               byte[] buf = new byte[4096];
                        while (true) {
/* 152 */                   int read = fileStream.read(buf);
                            if (read > 0) {
/* 153 */                       outstream.write(buf, 0, read);
                            } else {
/* 155 */                       return;
                            }
                        }
                    }

                    @Override // co.vine.android.network.apache.MultipartEntity.Entity
/* 158 */           public InputStream getInputStream() throws IOException {
/* 159 */               return this.mContext.getContentResolver().openInputStream(this.mUri);
                    }
                }
            }
