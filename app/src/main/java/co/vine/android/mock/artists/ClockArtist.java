            package co.vine.android.mock.artists;

            import android.graphics.Canvas;
            import android.graphics.Paint;
            import android.media.AudioRecord;
            import java.text.SimpleDateFormat;
            import java.util.Date;
            
            
    public class ClockArtist implements MockPictureArtist, MockMusicArtist {
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

                @Override 
        public void setupVideo(Canvas canvas, int width, int height) {
            this.mCanvas = canvas;
            this.mPaint = new Paint();
            this.mPaint.setAntiAlias(true);
            this.mPaint.setColor(-1);
            this.mPaint.setTextSize(40.0f);
            this.mPaint.setTextAlign(Paint.Align.CENTER);
            this.mWidth = width / 2;
            this.mHeight = height / 2;
            this.mFormatter = new SimpleDateFormat("mm:ss:SSS");
                }

                @Override 
        public void draw(long time) {
            this.mCanvas.drawColor(-16777216);
            this.mCanvas.drawText(this.mFormatter.format(new Date(time)), this.mWidth, this.mHeight, this.mPaint);
                }

                @Override 
        public short[] setupAudio(AudioRecord audioRecord) {
            this.mZeroBuffer = this.mMusicArtistHelper.onSetupAudio(audioRecord);
            this.mZeroSampleLimit = this.mMusicArtistHelper.sampleRate * 20;
            this.mBeepBuffer = new short[this.mZeroSampleLimit * 2];
                    for (int i = 0; i < this.mBeepBuffer.length; i++) {
                this.mBeepBuffer[i] = (short) (Math.sin((6.283185307179586d * ((double) i)) / ((double) (this.mMusicArtistHelper.sampleRate / 1000))) * 5000.0d);
                    }
            return new short[this.mZeroBuffer.length];
                }

                @Override 
        public int speak(short[] buffer, long time) {
            this.mMusicArtistHelper.onSpeak(buffer, time);
            System.arraycopy(this.mZeroBuffer, 0, buffer, 0, this.mZeroBuffer.length);
            long actualStart = time - ((long) ((this.mMusicArtistHelper.samplesToGive * 1000) / this.mMusicArtistHelper.sampleRate));
            long lastSecond = actualStart - ((long) ((int) (actualStart % 1000)));
            int lastStop = (int) (((20 + lastSecond) - actualStart) * ((long) this.mMusicArtistHelper.sampleRate));
            if (lastStop > 0) {
                System.arraycopy(this.mBeepBuffer, 0, buffer, 0, Math.min(lastStop, this.mMusicArtistHelper.samplesToGive));
                    }
            int nextStart = Math.max((int) ((((1000 + lastSecond) - 20) - actualStart) * ((long) this.mMusicArtistHelper.sampleRate)), 0);
            if (nextStart < this.mMusicArtistHelper.samplesToGive) {
                int nextEnd = (int) ((((1000 + lastSecond) + 20) - actualStart) * ((long) this.mMusicArtistHelper.sampleRate));
                System.arraycopy(this.mBeepBuffer, 0, buffer, nextStart, (Math.min(nextEnd, this.mMusicArtistHelper.samplesToGive) - nextStart) + 1);
                    }
            return this.mMusicArtistHelper.samplesToGive;
                }
            }
