            package com.googlecode.javacv;

            import com.googlecode.javacv.cpp.opencv_core;
            import java.nio.Buffer;
            
            
            public class Frame {
                public opencv_core.IplImage image;
                public boolean keyFrame;
                public Buffer[] samples;
            }
