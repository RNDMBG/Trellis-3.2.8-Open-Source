            package org.scribe.model;

            import java.util.ArrayList;
            import java.util.Collections;
            import java.util.List;
            import java.util.Map;
            import org.scribe.utils.OAuthEncoder;
            import org.scribe.utils.Preconditions;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 21 */    public class ParameterList {
                private static final String EMPTY_STRING = "";
                private static final String PAIR_SEPARATOR = "=";
                private static final String PARAM_SEPARATOR = "&";
                private static final char QUERY_STRING_SEPARATOR = '?';
                private final List<Parameter> params;

/* 22 */        public ParameterList() {
/* 24 */            this.params = new ArrayList();
                }

/* 27 */        ParameterList(List<Parameter> params) {
/* 29 */            this.params = new ArrayList(params);
                }

/* 33 */        public ParameterList(Map<String, String> map) {
/* 34 */            this();
                    for (Map.Entry<String, String> entry : map.entrySet()) {
/* 37 */                this.params.add(new Parameter(entry.getKey(), entry.getValue()));
                    }
                }

/* 42 */        public void add(String key, String value) {
/* 43 */            this.params.add(new Parameter(key, value));
                }

/* 47 */        public String appendTo(String url) {
/* 48 */            Preconditions.checkNotNull(url, "Cannot append to null URL");
/* 49 */            String queryString = asFormUrlEncodedString();
/* 50 */            if (queryString.equals("")) {
                        return url;
                    }
/* 57 */            return (url + (url.indexOf(63) != -1 ? PARAM_SEPARATOR : Character.valueOf(QUERY_STRING_SEPARATOR))) + queryString;
                }

/* 63 */        public String asOauthBaseString() {
/* 64 */            return OAuthEncoder.encode(asFormUrlEncodedString());
                }

/* 68 */        public String asFormUrlEncodedString() {
/* 69 */            if (this.params.size() == 0) {
                        return "";
                    }
/* 71 */            StringBuilder builder = new StringBuilder();
                    for (Parameter p : this.params) {
/* 74 */                builder.append('&').append(p.asUrlEncodedPair());
                    }
/* 76 */            return builder.toString().substring(1);
                }

/* 80 */        public void addAll(ParameterList other) {
/* 81 */            this.params.addAll(other.params);
                }

/* 85 */        public void addQuerystring(String queryString) {
/* 86 */            if (queryString != null && queryString.length() > 0) {
/* 88 */                String[] arr$ = queryString.split(PARAM_SEPARATOR);
                        for (String param : arr$) {
/* 90 */                    String[] pair = param.split(PAIR_SEPARATOR);
/* 91 */                    String key = OAuthEncoder.decode(pair[0]);
/* 92 */                    String value = pair.length > 1 ? OAuthEncoder.decode(pair[1]) : "";
/* 93 */                    this.params.add(new Parameter(key, value));
                        }
                    }
                }

/* 99 */        public boolean contains(Parameter param) {
/* 100 */           return this.params.contains(param);
                }

/* 104 */       public int size() {
/* 105 */           return this.params.size();
                }

/* 109 */       public ParameterList sort() {
/* 110 */           ParameterList sorted = new ParameterList(this.params);
/* 111 */           Collections.sort(sorted.params);
/* 112 */           return sorted;
                }
            }
