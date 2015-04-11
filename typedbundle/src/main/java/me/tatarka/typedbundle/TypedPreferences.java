package me.tatarka.typedbundle;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.SharedPreferences;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by evan on 4/11/15.
 */
public class TypedPreferences {
    private SharedPreferences prefs;

    public TypedPreferences(SharedPreferences prefs) {
        this.prefs = prefs;
    }

    public boolean contains(Key<?> key) {
        return prefs.contains(key.name);
    }

    public Editor edit() {
        return new Editor(prefs);
    }

    public Map<Key<Object>, Object> getAll() {
        Map<String, ?> map = prefs.getAll();
        Map<Key<Object>, Object> result = new HashMap<>(map.size());
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            result.put(new Key<>(entry.getKey()), entry.getValue());
        }
        return Collections.unmodifiableMap(result);
    }

    public boolean get(Key<Boolean> key, boolean defValue) {
        return prefs.getBoolean(key.name, defValue);
    }

    public float get(Key<Float> key, float defValue) {
        return prefs.getFloat(key.name, defValue);
    }

    public int get(Key<Integer> key, int defValue) {
        return prefs.getInt(key.name, defValue);
    }

    public long get(Key<Long> key, long defValue) {
        return prefs.getLong(key.name, defValue);
    }

    public String get(Key<String> key, String defValue) {
        return prefs.getString(key.name, defValue);
    }

    @TargetApi(11)
    public Set<String> get(Key<Set<String>> key, Set<String> defValue) {
        return prefs.getStringSet(key.name, defValue);
    }

    public SharedPreferences getSharedPreferences() {
        return prefs;
    }

    public static class Editor {
        private SharedPreferences.Editor editor;
        
        @SuppressLint("CommitPrefEdits")
        private Editor(SharedPreferences prefs) {
            editor = prefs.edit();
        }
        
        @TargetApi(9)
        public void apply() {
            editor.apply();
        }
        
        public Editor clear() {
            editor.clear();
            return this;
        }
        
        public boolean commit() {
            return editor.commit();
        }
        
        public Editor put(Key<Boolean> key, boolean value) {
            editor.putBoolean(key.name, value);
            return this;
        }
        
        public Editor put(Key<Float> key, float value) {
            editor.putFloat(key.name, value);
            return this;
        }
        
        public Editor put(Key<Integer> key, int value) {
            editor.putInt(key.name, value);
            return this;
        }
        
        public Editor put(Key<Long> key, long value) {
            editor.putLong(key.name, value);
            return this;
        }
        
        public Editor put(Key<String> key, String value) {
            editor.putString(key.name, value);
            return this;
        }
        
        @TargetApi(11)
        public Editor put(Key<Set<String>> key, Set<String> value) {
            editor.putStringSet(key.name, value);
            return this;
        }
        
        public Editor remove(Key<?> key) {
            editor.remove(key.name);
            return this;
        }
    }
}
