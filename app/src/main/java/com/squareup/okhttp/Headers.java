            package com.squareup.okhttp;

            import com.squareup.okhttp.internal.http.HttpDate;
            import java.util.ArrayList;
            import java.util.Arrays;
            import java.util.Collections;
            import java.util.Comparator;
            import java.util.Date;
            import java.util.List;
            import java.util.Set;
            import java.util.TreeSet;
            import org.apache.commons.io.IOUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 45 */    public final class Headers {
                private final String[] namesAndValues;

/* 46 */        /* synthetic */ Headers(Builder x0, Object x1) {
/* 47 */            this(x0);
                }

/* 49 */        private Headers(Builder builder) {
/* 51 */            this.namesAndValues = (String[]) Builder.access$000(builder).toArray(new String[Builder.access$000(builder).size()]);
                }

/* 53 */        private Headers(String[] namesAndValues) {
/* 55 */            this.namesAndValues = namesAndValues;
                }

/* 59 */        public String get(String name) {
/* 60 */            return get(this.namesAndValues, name);
                }

/* 68 */        public Date getDate(String name) {
/* 69 */            String value = get(name);
/* 70 */            if (value != null) {
                        return HttpDate.parse(value);
                    }
                    return null;
                }

/* 74 */        public int size() {
/* 75 */            return this.namesAndValues.length / 2;
                }

/* 79 */        public String name(int index) {
/* 80 */            int nameIndex = index * 2;
/* 81 */            if (nameIndex < 0 || nameIndex >= this.namesAndValues.length) {
/* 82 */                return null;
                    }
                    return this.namesAndValues[nameIndex];
                }

/* 88 */        public String value(int index) {
/* 89 */            int valueIndex = (index * 2) + 1;
/* 90 */            if (valueIndex < 0 || valueIndex >= this.namesAndValues.length) {
/* 91 */                return null;
                    }
                    return this.namesAndValues[valueIndex];
                }

/* 97 */        public Set<String> names() {
/* 98 */            TreeSet<String> result = new TreeSet<>((Comparator<? super String>) String.CASE_INSENSITIVE_ORDER);
                    for (int i = 0; i < size(); i++) {
/* 100 */               result.add(name(i));
                    }
/* 102 */           return Collections.unmodifiableSet(result);
                }

/* 106 */       public List<String> values(String name) {
/* 107 */           List<String> result = null;
                    for (int i = 0; i < size(); i++) {
/* 109 */               if (name.equalsIgnoreCase(name(i))) {
/* 110 */                   if (result == null) {
                                result = new ArrayList<>(2);
                            }
/* 111 */                   result.add(value(i));
                        }
                    }
                    return result != null ? Collections.unmodifiableList(result) : Collections.emptyList();
                }

/* 119 */       public Builder newBuilder() {
/* 120 */           Builder result = new Builder();
/* 121 */           Builder.access$000(result).addAll(Arrays.asList(this.namesAndValues));
/* 122 */           return result;
                }

/* 125 */       public String toString() {
/* 126 */           StringBuilder result = new StringBuilder();
                    for (int i = 0; i < size(); i++) {
/* 128 */               result.append(name(i)).append(": ").append(value(i)).append(IOUtils.LINE_SEPARATOR_UNIX);
                    }
/* 130 */           return result.toString();
                }

/* 133 */       private static String get(String[] namesAndValues, String name) {
                    for (int i = namesAndValues.length - 2; i >= 0; i -= 2) {
/* 135 */               if (name.equalsIgnoreCase(namesAndValues[i])) {
/* 136 */                   return namesAndValues[i + 1];
                        }
                    }
/* 139 */           return null;
                }

                /* JADX INFO: renamed from: of */
/* 147 */       public static Headers m2467of(String... namesAndValues) {
/* 148 */           if (namesAndValues == null || namesAndValues.length % 2 != 0) {
/* 149 */               throw new IllegalArgumentException("Expected alternating header names and values");
                    }
/* 153 */           String[] namesAndValues2 = (String[]) namesAndValues.clone();
                    for (int i = 0; i < namesAndValues2.length; i++) {
/* 155 */               if (namesAndValues2[i] == null) {
                            throw new IllegalArgumentException("Headers cannot be null");
                        }
/* 156 */               namesAndValues2[i] = namesAndValues2[i].trim();
                    }
                    for (int i2 = 0; i2 < namesAndValues2.length; i2 += 2) {
/* 161 */               String name = namesAndValues2[i2];
/* 162 */               String value = namesAndValues2[i2 + 1];
/* 163 */               if (name.length() == 0 || name.indexOf(0) != -1 || value.indexOf(0) != -1) {
/* 164 */                   throw new IllegalArgumentException("Unexpected header: " + name + ": " + value);
                        }
                    }
/* 168 */           return new Headers(namesAndValues2);
                }

/* 169 */       public static final class Builder {
                    private final List<String> namesAndValues = new ArrayList(20);

/* 170 */           static /* synthetic */ List access$000(Builder x0) {
/* 171 */               return x0.namesAndValues;
                    }

/* 175 */           Builder addLine(String line) {
/* 176 */               int index = line.indexOf(":", 1);
/* 177 */               if (index != -1) {
/* 178 */                   return addLenient(line.substring(0, index), line.substring(index + 1));
                        }
/* 179 */               if (line.startsWith(":")) {
/* 182 */                   return addLenient("", line.substring(1));
                        }
/* 184 */               return addLenient("", line);
                    }

/* 189 */           public Builder add(String name, String value) {
/* 190 */               if (name == null) {
                            throw new IllegalArgumentException("name == null");
                        }
/* 191 */               if (value == null) {
                            throw new IllegalArgumentException("value == null");
                        }
/* 192 */               if (name.length() == 0 || name.indexOf(0) != -1 || value.indexOf(0) != -1) {
/* 193 */                   throw new IllegalArgumentException("Unexpected header: " + name + ": " + value);
                        }
/* 195 */               return addLenient(name, value);
                    }

/* 202 */           private Builder addLenient(String name, String value) {
/* 203 */               this.namesAndValues.add(name);
/* 204 */               this.namesAndValues.add(value.trim());
/* 205 */               return this;
                    }

/* 208 */           public Builder removeAll(String name) {
/* 209 */               int i = 0;
                        while (i < this.namesAndValues.size()) {
/* 210 */                   if (name.equalsIgnoreCase(this.namesAndValues.get(i))) {
/* 211 */                       this.namesAndValues.remove(i);
/* 212 */                       this.namesAndValues.remove(i);
/* 213 */                       i -= 2;
                            }
/* 209 */                   i += 2;
                        }
/* 216 */               return this;
                    }

/* 223 */           public Builder set(String name, String value) {
/* 224 */               removeAll(name);
/* 225 */               add(name, value);
/* 226 */               return this;
                    }

/* 230 */           public String get(String name) {
                        for (int i = this.namesAndValues.size() - 2; i >= 0; i -= 2) {
/* 232 */                   if (name.equalsIgnoreCase(this.namesAndValues.get(i))) {
/* 233 */                       return this.namesAndValues.get(i + 1);
                            }
                        }
/* 236 */               return null;
                    }

/* 239 */           public Headers build() {
/* 240 */               return new Headers(this, null);
                    }
                }
            }
