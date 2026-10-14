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
            
            
    public class SharedPreferencesTokenCachingStrategy extends TokenCachingStrategy {
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

        public SharedPreferencesTokenCachingStrategy(Context context) {
            this(context, null);
                }

       public SharedPreferencesTokenCachingStrategy(Context context, String cacheKey) {
           Validate.notNull(context, "context");
           cacheKey = Utility.isNullOrEmpty(cacheKey) ? DEFAULT_CACHE_KEY : cacheKey;
           Context applicationContext = context.getApplicationContext();
           this.cache = (applicationContext != null ? applicationContext : context).getSharedPreferences(cacheKey, 0);
                }

                @Override 
       public Bundle load() {
           Bundle settings = new Bundle();
           Map<String, ?> allCachedEntries = this.cache.getAll();
                    for (String key : allCachedEntries.keySet()) {
                        try {
                   deserializeKey(key, settings);
                        } catch (JSONException e) {
                   Logger.log(LoggingBehavior.CACHE, 5, TAG, "Error reading cached value for key: '" + key + "' -- " + e);
                   return null;
                        }
                    }
           return settings;
                }

                @Override 
       public void save(Bundle bundle) {
           Validate.notNull(bundle, "bundle");
           SharedPreferences.Editor editor = this.cache.edit();
                    for (String key : bundle.keySet()) {
                        try {
                   serializeKey(key, bundle, editor);
                        } catch (JSONException e) {
                   Logger.log(LoggingBehavior.CACHE, 5, TAG, "Error processing value for key: '" + key + "' -- " + e);
                   return;
                        }
                    }
           boolean successfulCommit = editor.commit();
           if (!successfulCommit) {
               Logger.log(LoggingBehavior.CACHE, 5, TAG, "SharedPreferences.Editor.commit() was not successful");
                    }
                }

                @Override 
       public void clear() {
           this.cache.edit().clear().apply();
                }

       private void serializeKey(String key, Bundle bundle, SharedPreferences.Editor editor) throws JSONException {
           Object value = bundle.get(key);
           if (value != null) {
               String supportedType = null;
               JSONArray jsonArray = null;
               JSONObject json = new JSONObject();
               if (value instanceof Byte) {
                   supportedType = TYPE_BYTE;
                   json.put("value", ((Byte) value).intValue());
                        } else if (value instanceof Short) {
                   supportedType = TYPE_SHORT;
                   json.put("value", ((Short) value).intValue());
                        } else if (value instanceof Integer) {
                   supportedType = TYPE_INTEGER;
                   json.put("value", ((Integer) value).intValue());
                        } else if (value instanceof Long) {
                   supportedType = TYPE_LONG;
                   json.put("value", ((Long) value).longValue());
                        } else if (value instanceof Float) {
                   supportedType = TYPE_FLOAT;
                   json.put("value", ((Float) value).doubleValue());
                        } else if (value instanceof Double) {
                   supportedType = TYPE_DOUBLE;
                   json.put("value", ((Double) value).doubleValue());
                        } else if (value instanceof Boolean) {
                   supportedType = TYPE_BOOLEAN;
                   json.put("value", ((Boolean) value).booleanValue());
                        } else if (value instanceof Character) {
                   supportedType = TYPE_CHAR;
                   json.put("value", value.toString());
                        } else if (value instanceof String) {
                   supportedType = TYPE_STRING;
                   json.put("value", value);
                        } else if (value instanceof Enum) {
                   supportedType = TYPE_ENUM;
                   json.put("value", value.toString());
                   json.put(JSON_VALUE_ENUM_TYPE, value.getClass().getName());
                        } else {
                   jsonArray = new JSONArray();
                   if (value instanceof byte[]) {
                       supportedType = TYPE_BYTE_ARRAY;
                       byte[] arr$ = (byte[]) value;
                                for (byte v : arr$) {
                           jsonArray.put((int) v);
                                }
                            } else if (value instanceof short[]) {
                       supportedType = TYPE_SHORT_ARRAY;
                       short[] arr$2 = (short[]) value;
                                for (short v2 : arr$2) {
                           jsonArray.put((int) v2);
                                }
                            } else if (value instanceof int[]) {
                       supportedType = TYPE_INTEGER_ARRAY;
                       int[] arr$3 = (int[]) value;
                                for (int v3 : arr$3) {
                           jsonArray.put(v3);
                                }
                            } else if (value instanceof long[]) {
                       supportedType = TYPE_LONG_ARRAY;
                       long[] arr$4 = (long[]) value;
                                for (long v4 : arr$4) {
                           jsonArray.put(v4);
                                }
                            } else if (value instanceof float[]) {
                       supportedType = TYPE_FLOAT_ARRAY;
                       float[] arr$5 = (float[]) value;
                                for (float v5 : arr$5) {
                           jsonArray.put(v5);
                                }
                            } else if (value instanceof double[]) {
                       supportedType = TYPE_DOUBLE_ARRAY;
                       double[] arr$6 = (double[]) value;
                                for (double v6 : arr$6) {
                           jsonArray.put(v6);
                                }
                            } else if (value instanceof boolean[]) {
                       supportedType = TYPE_BOOLEAN_ARRAY;
                       boolean[] arr$7 = (boolean[]) value;
                                for (boolean v7 : arr$7) {
                           jsonArray.put(v7);
                                }
                            } else if (value instanceof char[]) {
                       supportedType = TYPE_CHAR_ARRAY;
                       char[] arr$8 = (char[]) value;
                                for (char v8 : arr$8) {
                           jsonArray.put(String.valueOf(v8));
                                }
                            } else if (value instanceof List) {
                       supportedType = TYPE_STRING_LIST;
                       List<String> stringList = (List) value;
                                for (String str : stringList) {
                           jsonArray.put(str == null ? JSONObject.NULL : str);
                                }
                            } else {
                       jsonArray = null;
                            }
                        }
               if (supportedType != null) {
                   json.put(JSON_VALUE_TYPE, supportedType);
                   if (jsonArray != null) {
                       json.putOpt("value", jsonArray);
                            }
                   String jsonString = json.toString();
                   editor.putString(key, jsonString);
                        }
                    }
                }

       private void deserializeKey(String key, Bundle bundle) throws JSONException {
           String jsonString = this.cache.getString(key, "{}");
           JSONObject json = new JSONObject(jsonString);
           String valueType = json.getString(JSON_VALUE_TYPE);
           if (valueType.equals(TYPE_BOOLEAN)) {
               bundle.putBoolean(key, json.getBoolean("value"));
               return;
                    }
           if (valueType.equals(TYPE_BOOLEAN_ARRAY)) {
               JSONArray jsonArray = json.getJSONArray("value");
               boolean[] array = new boolean[jsonArray.length()];
                        for (int i = 0; i < array.length; i++) {
                   array[i] = jsonArray.getBoolean(i);
                        }
               bundle.putBooleanArray(key, array);
               return;
                    }
           if (valueType.equals(TYPE_BYTE)) {
               bundle.putByte(key, (byte) json.getInt("value"));
               return;
                    }
           if (valueType.equals(TYPE_BYTE_ARRAY)) {
               JSONArray jsonArray2 = json.getJSONArray("value");
               byte[] array2 = new byte[jsonArray2.length()];
                        for (int i2 = 0; i2 < array2.length; i2++) {
                   array2[i2] = (byte) jsonArray2.getInt(i2);
                        }
               bundle.putByteArray(key, array2);
               return;
                    }
           if (valueType.equals(TYPE_SHORT)) {
               bundle.putShort(key, (short) json.getInt("value"));
               return;
                    }
           if (valueType.equals(TYPE_SHORT_ARRAY)) {
               JSONArray jsonArray3 = json.getJSONArray("value");
               short[] array3 = new short[jsonArray3.length()];
                        for (int i3 = 0; i3 < array3.length; i3++) {
                   array3[i3] = (short) jsonArray3.getInt(i3);
                        }
               bundle.putShortArray(key, array3);
               return;
                    }
           if (valueType.equals(TYPE_INTEGER)) {
               bundle.putInt(key, json.getInt("value"));
               return;
                    }
           if (valueType.equals(TYPE_INTEGER_ARRAY)) {
               JSONArray jsonArray4 = json.getJSONArray("value");
               int[] array4 = new int[jsonArray4.length()];
                        for (int i4 = 0; i4 < array4.length; i4++) {
                   array4[i4] = jsonArray4.getInt(i4);
                        }
               bundle.putIntArray(key, array4);
               return;
                    }
           if (valueType.equals(TYPE_LONG)) {
               bundle.putLong(key, json.getLong("value"));
               return;
                    }
           if (valueType.equals(TYPE_LONG_ARRAY)) {
               JSONArray jsonArray5 = json.getJSONArray("value");
               long[] array5 = new long[jsonArray5.length()];
                        for (int i5 = 0; i5 < array5.length; i5++) {
                   array5[i5] = jsonArray5.getLong(i5);
                        }
               bundle.putLongArray(key, array5);
               return;
                    }
           if (valueType.equals(TYPE_FLOAT)) {
               bundle.putFloat(key, (float) json.getDouble("value"));
               return;
                    }
           if (valueType.equals(TYPE_FLOAT_ARRAY)) {
               JSONArray jsonArray6 = json.getJSONArray("value");
               float[] array6 = new float[jsonArray6.length()];
                        for (int i6 = 0; i6 < array6.length; i6++) {
                   array6[i6] = (float) jsonArray6.getDouble(i6);
                        }
               bundle.putFloatArray(key, array6);
               return;
                    }
           if (valueType.equals(TYPE_DOUBLE)) {
               bundle.putDouble(key, json.getDouble("value"));
               return;
                    }
           if (valueType.equals(TYPE_DOUBLE_ARRAY)) {
               JSONArray jsonArray7 = json.getJSONArray("value");
               double[] array7 = new double[jsonArray7.length()];
                        for (int i7 = 0; i7 < array7.length; i7++) {
                   array7[i7] = jsonArray7.getDouble(i7);
                        }
               bundle.putDoubleArray(key, array7);
               return;
                    }
           if (valueType.equals(TYPE_CHAR)) {
               String charString = json.getString("value");
               if (charString != null && charString.length() == 1) {
                   bundle.putChar(key, charString.charAt(0));
                   return;
                        }
               return;
                    }
           if (valueType.equals(TYPE_CHAR_ARRAY)) {
               JSONArray jsonArray8 = json.getJSONArray("value");
               char[] array8 = new char[jsonArray8.length()];
                        for (int i8 = 0; i8 < array8.length; i8++) {
                   String charString2 = jsonArray8.getString(i8);
                   if (charString2 != null && charString2.length() == 1) {
                       array8[i8] = charString2.charAt(0);
                            }
                        }
               bundle.putCharArray(key, array8);
               return;
                    }
           if (valueType.equals(TYPE_STRING)) {
               bundle.putString(key, json.getString("value"));
               return;
                    }
           if (valueType.equals(TYPE_STRING_LIST)) {
               JSONArray jsonArray9 = json.getJSONArray("value");
               int numStrings = jsonArray9.length();
               ArrayList<String> stringList = new ArrayList<>(numStrings);
                        for (int i9 = 0; i9 < numStrings; i9++) {
                   Object jsonStringValue = jsonArray9.get(i9);
                   stringList.add(i9, jsonStringValue == JSONObject.NULL ? null : (String) jsonStringValue);
                        }
               bundle.putStringArrayList(key, stringList);
               return;
                    }
           if (valueType.equals(TYPE_ENUM)) {
                        try {
                   String enumType = json.getString(JSON_VALUE_ENUM_TYPE);
                   Enum<?> enumValue = Enum.valueOf((Class) Class.forName(enumType), json.getString("value"));
                   bundle.putSerializable(key, enumValue);
                        } catch (ClassNotFoundException e) {
                        } catch (IllegalArgumentException e2) {
                        }
                    }
                }
            }
