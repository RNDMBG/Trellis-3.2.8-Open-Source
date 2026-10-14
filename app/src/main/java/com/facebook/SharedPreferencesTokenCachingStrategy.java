            package com.facebook;

            import android.content.Context;
            import android.content.SharedPreferences;
            import android.os.Bundle;
            import com.facebook.internal.Logger;
            import com.facebook.internal.Utility;
            import com.facebook.internal.Validate;
            import java.util.ArrayList;
            import java.util.List;
            import java.util.Map;
            import org.json.JSONArray;
            import org.json.JSONException;
            import org.json.JSONObject;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 85 */    public class SharedPreferencesTokenCachingStrategy extends TokenCachingStrategy {
                private static final String DEFAULT_CACHE_KEY = "com.facebook.SharedPreferencesTokenCachingStrategy.DEFAULT_KEY";
                private static final String JSON_VALUE = "value";
                private static final String JSON_VALUE_ENUM_TYPE = "enumType";
                private static final String JSON_VALUE_TYPE = "valueType";
                private static final String TAG = SharedPreferencesTokenCachingStrategy.class.getSimpleName();
                private static final String TYPE_BOOLEAN = "bool";
                private static final String TYPE_BOOLEAN_ARRAY = "bool[]";
                private static final String TYPE_BYTE = "byte";
                private static final String TYPE_BYTE_ARRAY = "byte[]";
                private static final String TYPE_CHAR = "char";
                private static final String TYPE_CHAR_ARRAY = "char[]";
                private static final String TYPE_DOUBLE = "double";
                private static final String TYPE_DOUBLE_ARRAY = "double[]";
                private static final String TYPE_ENUM = "enum";
                private static final String TYPE_FLOAT = "float";
                private static final String TYPE_FLOAT_ARRAY = "float[]";
                private static final String TYPE_INTEGER = "int";
                private static final String TYPE_INTEGER_ARRAY = "int[]";
                private static final String TYPE_LONG = "long";
                private static final String TYPE_LONG_ARRAY = "long[]";
                private static final String TYPE_SHORT = "short";
                private static final String TYPE_SHORT_ARRAY = "short[]";
                private static final String TYPE_STRING = "string";
                private static final String TYPE_STRING_LIST = "stringList";
                private SharedPreferences cache;

/* 86 */        public SharedPreferencesTokenCachingStrategy(Context context) {
/* 87 */            this(context, null);
                }

/* 101 */       public SharedPreferencesTokenCachingStrategy(Context context, String cacheKey) {
/* 103 */           Validate.notNull(context, "context");
/* 105 */           cacheKey = Utility.isNullOrEmpty(cacheKey) ? DEFAULT_CACHE_KEY : cacheKey;
/* 110 */           Context applicationContext = context.getApplicationContext();
/* 113 */           this.cache = (applicationContext != null ? applicationContext : context).getSharedPreferences(cacheKey, 0);
                }

                @Override // com.facebook.TokenCachingStrategy
/* 123 */       public Bundle load() {
/* 124 */           Bundle settings = new Bundle();
/* 126 */           Map<String, ?> allCachedEntries = this.cache.getAll();
                    for (String key : allCachedEntries.keySet()) {
                        try {
/* 130 */                   deserializeKey(key, settings);
                        } catch (JSONException e) {
/* 133 */                   Logger.log(LoggingBehavior.CACHE, 5, TAG, "Error reading cached value for key: '" + key + "' -- " + e);
/* 135 */                   return null;
                        }
                    }
/* 139 */           return settings;
                }

                @Override // com.facebook.TokenCachingStrategy
/* 149 */       public void save(Bundle bundle) {
/* 150 */           Validate.notNull(bundle, "bundle");
/* 152 */           SharedPreferences.Editor editor = this.cache.edit();
                    for (String key : bundle.keySet()) {
                        try {
/* 156 */                   serializeKey(key, bundle, editor);
                        } catch (JSONException e) {
/* 159 */                   Logger.log(LoggingBehavior.CACHE, 5, TAG, "Error processing value for key: '" + key + "' -- " + e);
/* 170 */                   return;
                        }
                    }
/* 166 */           boolean successfulCommit = editor.commit();
/* 167 */           if (!successfulCommit) {
/* 168 */               Logger.log(LoggingBehavior.CACHE, 5, TAG, "SharedPreferences.Editor.commit() was not successful");
                    }
                }

                @Override // com.facebook.TokenCachingStrategy
/* 175 */       public void clear() {
/* 176 */           this.cache.edit().clear().apply();
                }

/* 180 */       private void serializeKey(String key, Bundle bundle, SharedPreferences.Editor editor) throws JSONException {
/* 181 */           Object value = bundle.get(key);
/* 182 */           if (value != null) {
/* 187 */               String supportedType = null;
/* 188 */               JSONArray jsonArray = null;
/* 189 */               JSONObject json = new JSONObject();
/* 191 */               if (value instanceof Byte) {
/* 192 */                   supportedType = TYPE_BYTE;
/* 193 */                   json.put("value", ((Byte) value).intValue());
                        } else if (value instanceof Short) {
/* 195 */                   supportedType = TYPE_SHORT;
/* 196 */                   json.put("value", ((Short) value).intValue());
                        } else if (value instanceof Integer) {
/* 198 */                   supportedType = TYPE_INTEGER;
/* 199 */                   json.put("value", ((Integer) value).intValue());
                        } else if (value instanceof Long) {
/* 201 */                   supportedType = TYPE_LONG;
/* 202 */                   json.put("value", ((Long) value).longValue());
                        } else if (value instanceof Float) {
/* 204 */                   supportedType = TYPE_FLOAT;
/* 205 */                   json.put("value", ((Float) value).doubleValue());
                        } else if (value instanceof Double) {
/* 207 */                   supportedType = TYPE_DOUBLE;
/* 208 */                   json.put("value", ((Double) value).doubleValue());
                        } else if (value instanceof Boolean) {
/* 210 */                   supportedType = TYPE_BOOLEAN;
/* 211 */                   json.put("value", ((Boolean) value).booleanValue());
                        } else if (value instanceof Character) {
/* 213 */                   supportedType = TYPE_CHAR;
/* 214 */                   json.put("value", value.toString());
                        } else if (value instanceof String) {
/* 216 */                   supportedType = TYPE_STRING;
/* 217 */                   json.put("value", value);
                        } else if (value instanceof Enum) {
/* 219 */                   supportedType = TYPE_ENUM;
/* 220 */                   json.put("value", value.toString());
/* 221 */                   json.put(JSON_VALUE_ENUM_TYPE, value.getClass().getName());
                        } else {
/* 225 */                   jsonArray = new JSONArray();
/* 226 */                   if (value instanceof byte[]) {
/* 227 */                       supportedType = TYPE_BYTE_ARRAY;
/* 228 */                       byte[] arr$ = (byte[]) value;
                                for (byte v : arr$) {
/* 229 */                           jsonArray.put((int) v);
                                }
                            } else if (value instanceof short[]) {
/* 232 */                       supportedType = TYPE_SHORT_ARRAY;
/* 233 */                       short[] arr$2 = (short[]) value;
                                for (short v2 : arr$2) {
/* 234 */                           jsonArray.put((int) v2);
                                }
                            } else if (value instanceof int[]) {
/* 237 */                       supportedType = TYPE_INTEGER_ARRAY;
/* 238 */                       int[] arr$3 = (int[]) value;
                                for (int v3 : arr$3) {
/* 239 */                           jsonArray.put(v3);
                                }
                            } else if (value instanceof long[]) {
/* 242 */                       supportedType = TYPE_LONG_ARRAY;
/* 243 */                       long[] arr$4 = (long[]) value;
                                for (long v4 : arr$4) {
/* 244 */                           jsonArray.put(v4);
                                }
                            } else if (value instanceof float[]) {
/* 247 */                       supportedType = TYPE_FLOAT_ARRAY;
/* 248 */                       float[] arr$5 = (float[]) value;
                                for (float v5 : arr$5) {
/* 249 */                           jsonArray.put(v5);
                                }
                            } else if (value instanceof double[]) {
/* 252 */                       supportedType = TYPE_DOUBLE_ARRAY;
/* 253 */                       double[] arr$6 = (double[]) value;
                                for (double v6 : arr$6) {
/* 254 */                           jsonArray.put(v6);
                                }
                            } else if (value instanceof boolean[]) {
/* 257 */                       supportedType = TYPE_BOOLEAN_ARRAY;
/* 258 */                       boolean[] arr$7 = (boolean[]) value;
                                for (boolean v7 : arr$7) {
/* 259 */                           jsonArray.put(v7);
                                }
                            } else if (value instanceof char[]) {
/* 262 */                       supportedType = TYPE_CHAR_ARRAY;
/* 263 */                       char[] arr$8 = (char[]) value;
                                for (char v8 : arr$8) {
/* 264 */                           jsonArray.put(String.valueOf(v8));
                                }
                            } else if (value instanceof List) {
/* 267 */                       supportedType = TYPE_STRING_LIST;
/* 269 */                       List<String> stringList = (List) value;
                                for (String str : stringList) {
/* 271 */                           jsonArray.put(str == null ? JSONObject.NULL : str);
                                }
                            } else {
/* 276 */                       jsonArray = null;
                            }
                        }
/* 280 */               if (supportedType != null) {
/* 281 */                   json.put(JSON_VALUE_TYPE, supportedType);
/* 282 */                   if (jsonArray != null) {
/* 285 */                       json.putOpt("value", jsonArray);
                            }
/* 288 */                   String jsonString = json.toString();
/* 289 */                   editor.putString(key, jsonString);
                        }
                    }
                }

/* 294 */       private void deserializeKey(String key, Bundle bundle) throws JSONException {
/* 295 */           String jsonString = this.cache.getString(key, "{}");
/* 296 */           JSONObject json = new JSONObject(jsonString);
/* 298 */           String valueType = json.getString(JSON_VALUE_TYPE);
/* 300 */           if (valueType.equals(TYPE_BOOLEAN)) {
/* 301 */               bundle.putBoolean(key, json.getBoolean("value"));
/* 401 */               return;
                    }
/* 302 */           if (valueType.equals(TYPE_BOOLEAN_ARRAY)) {
/* 303 */               JSONArray jsonArray = json.getJSONArray("value");
/* 304 */               boolean[] array = new boolean[jsonArray.length()];
                        for (int i = 0; i < array.length; i++) {
/* 306 */                   array[i] = jsonArray.getBoolean(i);
                        }
/* 308 */               bundle.putBooleanArray(key, array);
/* 401 */               return;
                    }
/* 309 */           if (valueType.equals(TYPE_BYTE)) {
/* 310 */               bundle.putByte(key, (byte) json.getInt("value"));
/* 401 */               return;
                    }
/* 311 */           if (valueType.equals(TYPE_BYTE_ARRAY)) {
/* 312 */               JSONArray jsonArray2 = json.getJSONArray("value");
/* 313 */               byte[] array2 = new byte[jsonArray2.length()];
                        for (int i2 = 0; i2 < array2.length; i2++) {
/* 315 */                   array2[i2] = (byte) jsonArray2.getInt(i2);
                        }
/* 317 */               bundle.putByteArray(key, array2);
/* 401 */               return;
                    }
/* 318 */           if (valueType.equals(TYPE_SHORT)) {
/* 319 */               bundle.putShort(key, (short) json.getInt("value"));
/* 401 */               return;
                    }
/* 320 */           if (valueType.equals(TYPE_SHORT_ARRAY)) {
/* 321 */               JSONArray jsonArray3 = json.getJSONArray("value");
/* 322 */               short[] array3 = new short[jsonArray3.length()];
                        for (int i3 = 0; i3 < array3.length; i3++) {
/* 324 */                   array3[i3] = (short) jsonArray3.getInt(i3);
                        }
/* 326 */               bundle.putShortArray(key, array3);
/* 401 */               return;
                    }
/* 327 */           if (valueType.equals(TYPE_INTEGER)) {
/* 328 */               bundle.putInt(key, json.getInt("value"));
/* 401 */               return;
                    }
/* 329 */           if (valueType.equals(TYPE_INTEGER_ARRAY)) {
/* 330 */               JSONArray jsonArray4 = json.getJSONArray("value");
/* 331 */               int[] array4 = new int[jsonArray4.length()];
                        for (int i4 = 0; i4 < array4.length; i4++) {
/* 333 */                   array4[i4] = jsonArray4.getInt(i4);
                        }
/* 335 */               bundle.putIntArray(key, array4);
/* 401 */               return;
                    }
/* 336 */           if (valueType.equals(TYPE_LONG)) {
/* 337 */               bundle.putLong(key, json.getLong("value"));
/* 401 */               return;
                    }
/* 338 */           if (valueType.equals(TYPE_LONG_ARRAY)) {
/* 339 */               JSONArray jsonArray5 = json.getJSONArray("value");
/* 340 */               long[] array5 = new long[jsonArray5.length()];
                        for (int i5 = 0; i5 < array5.length; i5++) {
/* 342 */                   array5[i5] = jsonArray5.getLong(i5);
                        }
/* 344 */               bundle.putLongArray(key, array5);
/* 401 */               return;
                    }
/* 345 */           if (valueType.equals(TYPE_FLOAT)) {
/* 346 */               bundle.putFloat(key, (float) json.getDouble("value"));
/* 401 */               return;
                    }
/* 347 */           if (valueType.equals(TYPE_FLOAT_ARRAY)) {
/* 348 */               JSONArray jsonArray6 = json.getJSONArray("value");
/* 349 */               float[] array6 = new float[jsonArray6.length()];
                        for (int i6 = 0; i6 < array6.length; i6++) {
/* 351 */                   array6[i6] = (float) jsonArray6.getDouble(i6);
                        }
/* 353 */               bundle.putFloatArray(key, array6);
/* 401 */               return;
                    }
/* 354 */           if (valueType.equals(TYPE_DOUBLE)) {
/* 355 */               bundle.putDouble(key, json.getDouble("value"));
/* 401 */               return;
                    }
/* 356 */           if (valueType.equals(TYPE_DOUBLE_ARRAY)) {
/* 357 */               JSONArray jsonArray7 = json.getJSONArray("value");
/* 358 */               double[] array7 = new double[jsonArray7.length()];
                        for (int i7 = 0; i7 < array7.length; i7++) {
/* 360 */                   array7[i7] = jsonArray7.getDouble(i7);
                        }
/* 362 */               bundle.putDoubleArray(key, array7);
/* 401 */               return;
                    }
/* 363 */           if (valueType.equals(TYPE_CHAR)) {
/* 364 */               String charString = json.getString("value");
/* 365 */               if (charString != null && charString.length() == 1) {
/* 366 */                   bundle.putChar(key, charString.charAt(0));
/* 401 */                   return;
                        }
/* 401 */               return;
                    }
/* 368 */           if (valueType.equals(TYPE_CHAR_ARRAY)) {
/* 369 */               JSONArray jsonArray8 = json.getJSONArray("value");
/* 370 */               char[] array8 = new char[jsonArray8.length()];
                        for (int i8 = 0; i8 < array8.length; i8++) {
/* 372 */                   String charString2 = jsonArray8.getString(i8);
/* 373 */                   if (charString2 != null && charString2.length() == 1) {
/* 374 */                       array8[i8] = charString2.charAt(0);
                            }
                        }
/* 377 */               bundle.putCharArray(key, array8);
/* 401 */               return;
                    }
/* 378 */           if (valueType.equals(TYPE_STRING)) {
/* 379 */               bundle.putString(key, json.getString("value"));
/* 401 */               return;
                    }
/* 380 */           if (valueType.equals(TYPE_STRING_LIST)) {
/* 381 */               JSONArray jsonArray9 = json.getJSONArray("value");
/* 382 */               int numStrings = jsonArray9.length();
/* 383 */               ArrayList<String> stringList = new ArrayList<>(numStrings);
                        for (int i9 = 0; i9 < numStrings; i9++) {
/* 385 */                   Object jsonStringValue = jsonArray9.get(i9);
/* 386 */                   stringList.add(i9, jsonStringValue == JSONObject.NULL ? null : (String) jsonStringValue);
                        }
/* 388 */               bundle.putStringArrayList(key, stringList);
/* 401 */               return;
                    }
/* 389 */           if (valueType.equals(TYPE_ENUM)) {
                        try {
/* 391 */                   String enumType = json.getString(JSON_VALUE_ENUM_TYPE);
/* 395 */                   Enum<?> enumValue = Enum.valueOf((Class) Class.forName(enumType), json.getString("value"));
/* 396 */                   bundle.putSerializable(key, enumValue);
                        } catch (ClassNotFoundException e) {
                        } catch (IllegalArgumentException e2) {
                        }
                    }
                }
            }
