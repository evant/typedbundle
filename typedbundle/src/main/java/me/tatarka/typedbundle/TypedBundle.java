package me.tatarka.typedbundle;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * A typesafe {@link android.os.Bundle}. This class enforces that values stored in the bundle are
 * the correct type defined by the {@link me.tatarka.typedbundle.Key}.
 */
public final class TypedBundle implements Parcelable {
    public static final TypedBundle EMPTY = new TypedBundle(Bundle.EMPTY);

    private Bundle bundle;

    /**
     * Constructs a new, empty {@code TypedBundle}.
     */
    public TypedBundle() {
        this.bundle = new Bundle();
    }

    /**
     * Constructs a new, empty {@code TypedBundle} that uses a specific ClassLoader for
     * instantiating Parcelable adn Serializable objects.
     *
     * @param loader the ClassLoader
     */
    public TypedBundle(ClassLoader loader) {
        this.bundle = new Bundle(loader);
    }

    /**
     * Constructs a new, empty {@code TypedBundle} sized to hold the given number of elements.
     *
     * @param capacity the capacity
     */
    public TypedBundle(int capacity) {
        this.bundle = new Bundle(capacity);
    }

    /**
     * Wraps a {@link android.os.Bundle} so that you can access it in a typesafe way.
     * <p/>
     * Note: This will not make a copy of the given bundle, changes to the typesafe bundle will be
     * reflected in the given bundle and vice-versa. If you do want a copy, use
     * {@link #TypedBundle(TypedBundle)} instead or do {@code new TypedBundle(new Bundle(bundle))}.
     *
     * @param bundle the bundle to wrap.
     */
    public TypedBundle(Bundle bundle) {
        if (bundle == null) {
            throw new NullPointerException("bundle cannot be null");
        }
        this.bundle = bundle;
    }

    /**
     * Constructs a {@code TypedBundle} containing a copy of the mappings from the given
     * {@code TypedBundle}.
     *
     * @param bundle the bundle to copy
     */
    public TypedBundle(TypedBundle bundle) {
        this.bundle = new Bundle(bundle.bundle);
    }

    /**
     * Returns the value associate with the given key, or null if no mapping of the desired type
     * exists for the given key or a null value is explicitly associated with the key.
     *
     * @param key the key
     * @param <T> the value's type
     * @return the value for the key
     */
    public <T> T get(Key<T> key) {
        Object value = bundle.get(key.name);
        return (T) value;
    }

    /**
     * Returns the value associated with the given key, or defauleValue if no mapping of the desired
     * type exists for the given key.
     *
     * @param key          the key
     * @param defaultValue the default value
     * @param <T>          the value's type
     * @return the value for the key or defaultValue if it doesn't exist
     */
    public <T> T get(Key<T> key, T defaultValue) {
        Object value = bundle.get(key.name);
        return value != null ? (T) value : defaultValue;
    }

    /**
     * Inserts a value into the mapping of this {@code TypedBundle}, replacing any existing value
     * for the given key. The value <em>must</em> be a type supported by {@link android.os.Bundle}
     * or an {@link java.lang.IllegalArgumentException} will be thrown.
     *
     * @param key   the key
     * @param value the value
     * @param <T>   the value's type
     * @return the {@code TypedBundle} for chaining
     */
    @TargetApi(21)
    public <T> TypedBundle put(Key<T> key, T value) {
        if (Build.VERSION.SDK_INT >= 18 && value instanceof IBinder) {
            bundle.putBinder(key.name, (IBinder) value);
        } else if (value instanceof Boolean) {
            bundle.putBoolean(key.name, (Boolean) value);
        } else if (value instanceof boolean[]) {
            bundle.putBooleanArray(key.name, (boolean[]) value);
        } else if (value instanceof Bundle) {
            bundle.putBundle(key.name, (Bundle) value);
        } else if (value instanceof Byte) {
            bundle.putByte(key.name, (Byte) value);
        } else if (value instanceof byte[]) {
            bundle.putByteArray(key.name, (byte[]) value);
        } else if (value instanceof Character) {
            bundle.putChar(key.name, (Character) value);
        } else if (value instanceof char[]) {
            bundle.putCharArray(key.name, (char[]) value);
        } else if (value instanceof String) {
            bundle.putString(key.name, (String) value);
        } else if (value instanceof String[]) {
            bundle.putStringArray(key.name, (String[]) value);
        } else if (value instanceof CharSequence) {
            bundle.putCharSequence(key.name, (CharSequence) value);
        } else if (value instanceof CharSequence[]) {
            bundle.putCharSequenceArray(key.name, (CharSequence[]) value);
        } else if (value instanceof Double) {
            bundle.putDouble(key.name, (Double) value);
        } else if (value instanceof double[]) {
            bundle.putDoubleArray(key.name, (double[]) value);
        } else if (value instanceof Float) {
            bundle.putFloat(key.name, (Float) value);
        } else if (value instanceof float[]) {
            bundle.putFloatArray(key.name, (float[]) value);
        } else if (value instanceof Integer) {
            bundle.putInt(key.name, (Integer) value);
        } else if (value instanceof int[]) {
            bundle.putIntArray(key.name, (int[]) value);
        } else if (value instanceof Long) {
            bundle.putLong(key.name, (Long) value);
        } else if (value instanceof long[]) {
            bundle.putLongArray(key.name, (long[]) value);
        } else if (value instanceof Parcelable) {
            bundle.putParcelable(key.name, (Parcelable) value);
        } else if (value instanceof Parcelable[]) {
            bundle.putParcelableArray(key.name, (Parcelable[]) value);
        } else if (value instanceof Short) {
            bundle.putShort(key.name, (Short) value);
        } else if (value instanceof short[]) {
            bundle.putShortArray(key.name, (short[]) value);
        } else if (Build.VERSION.SDK_INT >= 21 && value instanceof Size) {
            bundle.putSize(key.name, (Size) value);
        } else if (Build.VERSION.SDK_INT >= 21 && value instanceof SizeF) {
            bundle.putSizeF(key.name, (SizeF) value);
        } else if (value instanceof ArrayList) {
            // Need to get generic type of the list.
            Class<?> itemType = findArrayListType((ArrayList) value);
            if (itemType == null) {
                // This means the list has no items, it doesn't really matter what type it is since
                // type info gets lost in the underlying bundle anyway.
                // Therefore, we can just stick an empty ArrayList of an arbitrary type that bundle
                // can handle and it will be able to get it out for whatever type it was.
                bundle.putIntegerArrayList(key.name, new ArrayList<Integer>());
            } else if (String.class.isAssignableFrom(itemType)) {
                bundle.putStringArrayList(key.name, (ArrayList<String>) value);
            } else if (CharSequence.class.isAssignableFrom(itemType)) {
                bundle.putCharSequenceArrayList(key.name, (ArrayList<CharSequence>) value);
            } else if (Integer.class.isAssignableFrom(itemType)) {
                bundle.putIntegerArrayList(key.name, (ArrayList<Integer>) value);
            } else if (Parcelable.class.isAssignableFrom(itemType)) {
                bundle.putParcelableArrayList(key.name, (ArrayList<Parcelable>) value);
            } else {
                throw new IllegalArgumentException("Invalid bundle type for " + key + ": " + value);
            }
        } else if (value instanceof SparseArray) {
            // The only impl for SparseArray is for Parcelable items.
            bundle.putSparseParcelableArray(key.name, (SparseArray<Parcelable>) value);
        } else if (value instanceof Serializable) {
            bundle.putSerializable(key.name, (Serializable) value);
        } else {
            throw new IllegalArgumentException("Invalid bundle type for " + key + ": " + value);
        }
        return this;
    }

    private static Class<?> findArrayListType(ArrayList list) {
        if (list.isEmpty()) {
            return null; // Can't figure out type.
        } else {
            return list.get(0).getClass();
        }
    }

    /**
     * Inserts all mappings from the given {@link android.os.Bundle} into this {@code TypedBundle}.
     *
     * @param bundle the bundle to copy.
     * @return the {@code TypedBundle} for chaining
     */
    public TypedBundle putAll(Bundle bundle) {
        this.bundle.putAll(bundle);
        return this;
    }

    /**
     * Inserts all mappings from the given {@code TypedBundle} into this {@code TypedBundle}.
     *
     * @param bundle the the bundle to copy
     * @return the {@code TypedBundle} for chaining
     */
    public TypedBundle putAll(TypedBundle bundle) {
        this.bundle.putAll(bundle.bundle);
        return this;
    }

    /**
     * Returns the underlying {@link android.os.Bundle}. If {@link #TypedBundle(android.os.Bundle)}
     * was used, this will be the same instance passed into that constructor.
     *
     * @return the bundle
     */
    public Bundle getBundle() {
        return bundle;
    }

    /**
     * Removes all elements from teh mapping of this Bundle.
     */
    public void clear() {
        bundle.clear();
    }

    /**
     * Returns true if the given key is contained in the mapping of this Bundle.
     *
     * @param key the key to check
     * @return true if the bundle contains the key, false otherwise
     */
    public boolean containsKey(Key<?> key) {
        return bundle.containsKey(key.name);
    }

    /**
     * Returns true if the mapping of this Bundle is empty, false otherwise
     *
     * @return true if the bundle is empty, false otherwise
     */
    public boolean isEmpty() {
        return bundle.isEmpty();
    }

    /**
     * Removes any entry with the given key from the mapping fo this Bundle.
     *
     * @param key the key to remove
     */
    public void remove(Key<?> key) {
        bundle.remove(key.name);
    }

    @Override
    public Object clone() {
        return new TypedBundle(this);
    }

    @Override
    public String toString() {
        return "Typed" + bundle;
    }

    /**
     * Returns the Set containing the keys used in this {@code TypedBundle}. Note that due to type
     * erasure, the type is lost, so you will only get back keys of type {@code Key<Object>}.
     *
     * @return the key set
     */
    public Set<Key<Object>> keySet() {
        Set<Key<Object>> set = new HashSet<>();
        for (String key : bundle.keySet()) {
            set.add(new Key<>(key));
        }
        return set;
    }

    /**
     * Returns the number of mapping contained in this {@code TypedBundle}.
     *
     * @return the size
     */
    public int size() {
        return bundle.size();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBundle(bundle);
    }

    private TypedBundle(Parcel in) {
        this.bundle = in.readBundle(TypedBundle.class.getClassLoader());
    }

    public static final Creator<TypedBundle> CREATOR = new Creator<TypedBundle>() {
        @Override
        public TypedBundle createFromParcel(Parcel source) {
            return new TypedBundle(source);
        }

        @Override
        public TypedBundle[] newArray(int size) {
            return new TypedBundle[size];
        }
    };
}
