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
            
            
    public class MultipartEntity extends AbstractHttpEntity {
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

        public MultipartEntity(Context context, ProgressListener listener) {
            this.mListener = listener;
            this.contentType = new BasicHeader("Content-Type", "multipart/form-data; boundary=vineaADFASDFADSFAFD");
            this.mEntities = new ArrayList<>();
            this.mContext = context.getApplicationContext();
                }

                @Override 
        public boolean isRepeatable() {
            return true;
                }

                @Override 
        public long getContentLength() {
            return this.mLength;
                }

                @Override 
        public InputStream getContent() throws IOException {
            return new MultipartInputStream(this.mEntities);
                }

                @Override 
        public boolean isStreaming() {
            return false;
                }

                @Override 
        public void writeTo(OutputStream outstream) throws IOException {
            OutputStream out = new CountingOutputStream(outstream, this.mApproxStreamLength, this.mListener);
                    for (Entity e : this.mEntities) {
                e.writeTo(out);
                    }
                }

        public void addFile(String name, String filename, Uri uri) throws IOException {
            byte[] fileHeader = (BOUNDARY + "\r\nContent-Disposition: form-data; name=\"" + name + "\"; filename=\"" + filename + "\"\r\nContent-Type: image/jpeg\r\nContent-Transfer-Encoding: binary\r\n\r\n").getBytes("UTF-8");
            byte[] fileFooter = "\r\n".getBytes("UTF-8");
            ArrayList<Entity> entities = this.mEntities;
            entities.add(new ByteArrayEntity(fileHeader));
            entities.add(new UriEntity(this.mContext, uri));
            entities.add(new ByteArrayEntity(fileFooter));
            this.mApproxStreamLength = (int) (this.mApproxStreamLength + (Util.getFileSize(this.mContext, uri) * 1024.0f) + fileHeader.length + fileFooter.length);
                }

        public void addString(String name, String value) {
           byte[] entity = (BOUNDARY + "\r\nContent-Disposition: form-data; name=\"" + name + "\"\r\n\r\n" + value + "\r\n").getBytes();
           this.mApproxStreamLength += entity.length;
           this.mLength += entity.length;
           this.mEntities.add(new ByteArrayEntity(entity));
                }

       public void end() throws IOException {
           this.mEntities.add(new ByteArrayEntity("--vineaADFASDFADSFAFD--\r\n".getBytes("UTF-8")));
                }

       public static class ByteArrayEntity implements Entity {
                    private final byte[] mValue;

           public ByteArrayEntity(byte[] value) {
               this.mValue = value;
                    }

                    @Override 
           public void writeTo(OutputStream outstream) throws IOException {
               outstream.write(this.mValue);
                    }

                    @Override 
           public InputStream getInputStream() throws IOException {
               return new ByteArrayInputStream(this.mValue);
                    }
                }

       public static class UriEntity implements Entity {
                    private final Context mContext;
                    private final Uri mUri;

           public UriEntity(Context context, Uri uri) {
               this.mUri = uri;
               this.mContext = context;
                    }

                    @Override 
           public void writeTo(OutputStream outstream) throws IOException {
               InputStream fileStream = this.mContext.getContentResolver().openInputStream(this.mUri);
               byte[] buf = new byte[4096];
                        while (true) {
                   int read = fileStream.read(buf);
                            if (read > 0) {
                       outstream.write(buf, 0, read);
                            } else {
                       return;
                            }
                        }
                    }

                    @Override 
           public InputStream getInputStream() throws IOException {
               return this.mContext.getContentResolver().openInputStream(this.mUri);
                    }
                }
            }
