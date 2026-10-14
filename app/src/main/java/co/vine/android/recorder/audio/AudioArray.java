            package co.vine.android.recorder.audio;

            import android.media.AudioRecord;
            import java.io.DataOutputStream;
            import java.io.IOException;
            import java.nio.Buffer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface AudioArray<T, K extends Buffer> {
                K asBuffer();

                T getData();

                void getFrom(K k);

                int getLength();

                void putInto(AudioArray<T, ?> audioArray, int i, int i2);

                void putInto(K k, int i, int i2);

                int readFrom(AudioRecord audioRecord, int i);

                void writeInto(DataOutputStream dataOutputStream, int i, int i2) throws IOException;
            }
