package me.tatarka.typedbundle;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.test.AndroidTestCase;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class TypedPreferencesTest extends AndroidTestCase {
    public void testGetBoolean() {
        Key<Boolean> key = new Key<>("key");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        preferences.edit().putBoolean("key", true).commit();
        TypedPreferences typedPreferences = new TypedPreferences(preferences);

        assertThat(typedPreferences.get(key, false)).isTrue();
    }
    
    public void testGetFloat() {
        Key<Float> key = new Key<>("key");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        preferences.edit().putFloat("key", 1f).commit();
        TypedPreferences typedPreferences = new TypedPreferences(preferences);

        assertThat(typedPreferences.get(key, 0f)).isEqualTo(1f);
    }
    
    public void testGetInt() {
        Key<Integer> key = new Key<>("key");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        preferences.edit().putInt("key", 1).commit();
        TypedPreferences typedPreferences = new TypedPreferences(preferences);

        assertThat(typedPreferences.get(key, 0)).isEqualTo(1);
    }

    public void testGetLong() {
        Key<Long> key = new Key<>("key");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        preferences.edit().putLong("key", 1L).commit();
        TypedPreferences typedPreferences = new TypedPreferences(preferences);

        assertThat(typedPreferences.get(key, 0L)).isEqualTo(1L);
    }

    public void testGetString() {
        Key<String> key = new Key<>("key");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        preferences.edit().putString("key", "value").commit();
        TypedPreferences typedPreferences = new TypedPreferences(preferences);

        assertThat(typedPreferences.get(key, null)).isEqualTo("value");
    }
    
    @TargetApi(11)
    public void testGetStringSet() {
        Key<Set<String>> key = new Key<>("key");
        Set<String> values = new HashSet<>(Arrays.asList("value1", "value2"));
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        preferences.edit().putStringSet("key", values).commit();
        TypedPreferences typedPreferences = new TypedPreferences(preferences);

        assertThat(typedPreferences.get(key, null)).isEqualTo(values);
    }
    
    public void testContains() {
        Key<String> key = new Key<>("key");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        preferences.edit().putString("key", "value").commit();
        TypedPreferences typedPreferences = new TypedPreferences(preferences);

        assertThat(typedPreferences.contains(key)).isTrue();
    }
    
    public void testPutBoolean() {
        Key<Boolean> key = new Key<>("key");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        TypedPreferences typedPreferences = new TypedPreferences(preferences);
        typedPreferences.edit().put(key, true).commit();

        assertThat(preferences.getBoolean("key", false)).isTrue();
    }

    public void testPutFloat() {
        Key<Float> key = new Key<>("key");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        TypedPreferences typedPreferences = new TypedPreferences(preferences);
        typedPreferences.edit().put(key, 1f).commit();

        assertThat(preferences.getFloat("key", 0f)).isEqualTo(1f);
    }

    public void testPutInt() {
        Key<Integer> key = new Key<>("key");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        TypedPreferences typedPreferences = new TypedPreferences(preferences);
        typedPreferences.edit().put(key, 1).commit();

        assertThat(preferences.getInt("key", 0)).isEqualTo(1);
    }

    public void testPutLong() {
        Key<Long> key = new Key<>("key");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        TypedPreferences typedPreferences = new TypedPreferences(preferences);
        typedPreferences.edit().put(key, 1L).commit();

        assertThat(preferences.getLong("key", 0L)).isEqualTo(1L);
    }

    public void testPutString() {
        Key<String> key = new Key<>("key");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        TypedPreferences typedPreferences = new TypedPreferences(preferences);
        typedPreferences.edit().put(key, "value").commit();

        assertThat(preferences.getString("key", null)).isEqualTo("value");
    }

    @TargetApi(11)
    public void testPutStringSet() {
        Key<Set<String>> key = new Key<>("key");
        Set<String> values = new HashSet<>(Arrays.asList("value1", "value2"));
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        TypedPreferences typedPreferences = new TypedPreferences(preferences);
        typedPreferences.edit().put(key, values).commit();

        assertThat(preferences.getStringSet("key", null)).isEqualTo(values);
    }
}
