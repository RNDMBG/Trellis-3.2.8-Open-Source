            package co.vine.android.mock.artists;

            import android.graphics.Canvas;
            import android.graphics.Paint;
            import android.media.AudioRecord;
            import java.text.SimpleDateFormat;
            import java.util.Date;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 32 */    public class ClockArtist implements MockPictureArtist, MockMusicArtist {
                private static final int BEEP_RANGE = 20;
                private short[] mBeepBuffer;
                private Canvas mCanvas;
                private SimpleDateFormat mFormatter;
                private int mHeight;
                private final MockMusicArtist.MusicArtistHelper mMusicArtistHelper = new MockMusicArtist.MusicArtistHelper();
                private Paint mPaint;
                private int mWidth;
                private short[] mZeroBuffer;
                private int mZeroSampleLimit;

                @Override // co.vine.android.mock.artists.MockPictureArtist
/* 33 */        public void setupVideo(Canvas canvas, int width, int height) {
/* 34 */            this.mCanvas = canvas;
/* 35 */            this.mPaint = new Paint();
/* 36 */            this.mPaint.setAntiAlias(true);
/* 37 */            this.mPaint.setColor(-1);
/* 38 */            this.mPaint.setTextSize(40.0f);
/* 39 */            this.mPaint.setTextAlign(Paint.Align.CENTER);
/* 40 */            this.mWidth = width / 2;
/* 41 */            this.mHeight = height / 2;
/* 43 */            this.mFormatter = new SimpleDateFormat("mm:ss:SSS");
                }

                @Override // co.vine.android.mock.artists.MockPictureArtist
/* 47 */        public void draw(long time) {
/* 48 */            this.mCanvas.drawColor(-16777216);
/* 49 */            this.mCanvas.drawText(this.mFormatter.format(new Date(time)), this.mWidth, this.mHeight, this.mPaint);
                }

                @Override // co.vine.android.mock.artists.MockMusicArtist
/* 53 */        public short[] setupAudio(AudioRecord audioRecord) {
/* 54 */            this.mZeroBuffer = this.mMusicArtistHelper.onSetupAudio(audioRecord);
/* 55 */            this.mZeroSampleLimit = this.mMusicArtistHelper.sampleRate * 20;
/* 56 */            this.mBeepBuffer = new short[this.mZeroSampleLimit * 2];
                    for (int i = 0; i < this.mBeepBuffer.length; i++) {
/* 58 */                this.mBeepBuffer[i] = (short) (Math.sin((6.283185307179586d * ((double) i)) / ((double) (this.mMusicArtistHelper.sampleRate / 1000))) * 5000.0d);
                    }
/* 61 */            return new short[this.mZeroBuffer.length];
                }

                @Override // co.vine.android.mock.artists.MockMusicArtist
/* 65 */        public int speak(short[] buffer, long time) {
/* 66 */            this.mMusicArtistHelper.onSpeak(buffer, time);
/* 67 */            System.arraycopy(this.mZeroBuffer, 0, buffer, 0, this.mZeroBuffer.length);
/* 68 */            long actualStart = time - ((long) ((this.mMusicArtistHelper.samplesToGive * 1000) / this.mMusicArtistHelper.sampleRate));
/* 69 */            long lastSecond = actualStart - ((long) ((int) (actualStart % 1000)));
/* 70 */            int lastStop = (int) (((20 + lastSecond) - actualStart) * ((long) this.mMusicArtistHelper.sampleRate));
/* 71 */            if (lastStop > 0) {
/* 73 */                System.arraycopy(this.mBeepBuffer, 0, buffer, 0, Math.min(lastStop, this.mMusicArtistHelper.samplesToGive));
                    }
/* 76 */            int nextStart = Math.max((int) ((((1000 + lastSecond) - 20) - actualStart) * ((long) this.mMusicArtistHelper.sampleRate)), 0);
/* 77 */            if (nextStart < this.mMusicArtistHelper.samplesToGive) {
/* 78 */                int nextEnd = (int) ((((1000 + lastSecond) + 20) - actualStart) * ((long) this.mMusicArtistHelper.sampleRate));
/* 80 */                System.arraycopy(this.mBeepBuffer, 0, buffer, nextStart, (Math.min(nextEnd, this.mMusicArtistHelper.samplesToGive) - nextStart) + 1);
                    }
/* 82 */            return this.mMusicArtistHelper.samplesToGive;
                }
            }
