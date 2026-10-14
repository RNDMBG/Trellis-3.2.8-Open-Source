            package co.vine.android.recorder.audio;

            import java.io.Externalizable;
            import java.io.IOException;
            import java.io.ObjectInput;
            import java.io.ObjectOutput;
            
            
    public class AudioData implements Externalizable {
                static final long serialVersionUID = -210670612808274402L;
                public int size;
                public int start;

        public AudioData() {
                }

        public AudioData(int start, int size) {
            this.start = start;
            this.size = size;
                }

        public AudioData(AudioData source) {
            this.start = source.start;
            this.size = source.size;
                }

                @Override 
        public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
            this.start = input.readInt();
            this.size = input.readInt();
                }

                @Override 
        public void writeExternal(ObjectOutput output) throws IOException {
            output.writeInt(this.start);
            output.writeInt(this.size);
                }
            }
