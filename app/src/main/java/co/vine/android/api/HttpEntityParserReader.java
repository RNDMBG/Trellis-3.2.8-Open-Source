package co.vine.android.api;

import android.util.Log;
import co.vine.android.network.HttpResult;
import co.vine.android.network.NetworkOperationReader;
import com.vandalsoftware.p004io.IoUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeaderValueParser;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.message.ParserCursor;
import org.apache.http.util.CharArrayBuffer;


public class HttpEntityParserReader implements NetworkOperationReader {
    public static final String TAG = "VineParserReader";
    public static final boolean LOGGABLE = Log.isLoggable(TAG, 3);
    private static final char[] DELIM = {'&'};
    private List<BasicNameValuePair> mParsedObject;

    @Override
    public void readInput(int statusCode, long contentLength, InputStream in) {
        if (statusCode != 200) {
            return;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[2048];
        try {
            long remaining = contentLength;
            while (remaining != 0) {
                int requested = remaining < 0 ? buffer.length : (int) Math.min(remaining, buffer.length);
                int count = in.read(buffer, 0, requested);
                if (count < 0) {
                    if (remaining > 0) {
                        throw new IOException("Invalid content length: " + remaining);
                    }
                    break;
                }
                if (count > 0) {
                    out.write(buffer, 0, count);
                    if (remaining > 0) {
                        remaining -= count;
                    }
                }
            }
            CharArrayBuffer chars = new CharArrayBuffer(out.size());
            chars.append(out.toByteArray(), 0, out.size());
            ParserCursor cursor = new ParserCursor(0, chars.length());
            List<BasicNameValuePair> parsed = new ArrayList<>();
            while (!cursor.atEnd()) {
                NameValuePair pair = BasicHeaderValueParser.DEFAULT.parseNameValuePair(chars, cursor, DELIM);
                if (pair.getName().length() > 0) {
                    parsed.add(new BasicNameValuePair(
                            URLDecoder.decode(pair.getName(), IoUtils.UTF_8),
                            URLDecoder.decode(pair.getValue(), IoUtils.UTF_8)));
                }
            }
            mParsedObject = parsed;
        } catch (Exception e) {
            if (LOGGABLE) {
                Log.e(TAG, "Error while reading response body", e);
            }
            mParsedObject = null;
        } finally {
            try {
                out.close();
            } catch (IOException ignored) {
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getParsedObject() {
        return (T) mParsedObject;
    }

    @Override
    public void onHandleError(HttpResult result) {
    }
}
