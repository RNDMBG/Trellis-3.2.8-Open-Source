            package com.facebook.model;

            import java.util.List;
            import org.json.JSONArray;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface GraphObjectList<T> extends List<T> {
                <U extends GraphObject> GraphObjectList<U> castToListOf(Class<U> cls);

                JSONArray getInnerJSONArray();
            }
