            package org.scribe.utils;

            import com.vandalsoftware.p004io.IoUtils;
            import java.io.UnsupportedEncodingException;
            import java.net.URLDecoder;
            import java.net.URLEncoder;
            import java.util.Collections;
            import java.util.HashMap;
            import java.util.Map;
            import java.util.regex.Pattern;
            import org.scribe.exceptions.OAuthException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 12 */    public class OAuthEncoder {
                private static String CHARSET = IoUtils.UTF_8;
                private static final Map<String, String> ENCODING_RULES;

                static {
/* 19 */            Map<String, String> rules = new HashMap<>();
/* 20 */            rules.put("*", "%2A");
/* 21 */            rules.put("+", "%20");
/* 22 */            rules.put("%7E", "~");
/* 23 */            ENCODING_RULES = Collections.unmodifiableMap(rules);
                }

/* 25 */        private OAuthEncoder() {
                }

/* 29 */        public static String encode(String plain) {
/* 30 */            Preconditions.checkNotNull(plain, "Cannot encode null object");
                    try {
/* 34 */                String encoded = URLEncoder.encode(plain, CHARSET);
                        for (Map.Entry<String, String> rule : ENCODING_RULES.entrySet()) {
/* 42 */                    encoded = applyRule(encoded, rule.getKey(), rule.getValue());
                        }
/* 44 */                return encoded;
                    } catch (UnsupportedEncodingException uee) {
/* 38 */                throw new OAuthException("Charset not found while encoding string: " + CHARSET, uee);
                    }
                }

/* 48 */        private static String applyRule(String encoded, String toReplace, String replacement) {
/* 49 */            return encoded.replaceAll(Pattern.quote(toReplace), replacement);
                }

/* 53 */        public static String decode(String encoded) {
/* 54 */            Preconditions.checkNotNull(encoded, "Cannot decode null object");
                    try {
/* 57 */                return URLDecoder.decode(encoded, CHARSET);
                    } catch (UnsupportedEncodingException uee) {
/* 61 */                throw new OAuthException("Charset not found while decoding string: " + CHARSET, uee);
                    }
                }
            }
