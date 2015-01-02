package me.tatarka.typedbundle;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
    public TypedBundle(@NonNull TypedBundle bundle) {
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
    @SuppressWarnings("unchecked")
    @Nullable
    public <T> T get(@NonNull Key<T> key) {
        Object value = bundle.get(key.name);
        return (T) value;
    }

    /**
     * Returns the value associated with the given key, or defaultValue if no mapping of the desired
     * type exists for the given key.
     *
     * @param key          the key
     * @param defaultValue the default value
     * @param <T>          the value's type
     * @return the value for the key or defaultValue if it doesn't exist
     */
    @NonNull
    public <T> T get(@NonNull Key<T> key, @NonNull T defaultValue) {
        T value = get(key);
        return value != null ? value : defaultValue;
    }

    /**
     * Inserts a value into the mapping of this {@code TypedBundle}, replacing any existing value
     * for the given key.
     *
     * @param key   the key
     * @param value the value
     * @param <T>   the value's type
     * @return the {@code TypedBundle} for chaining
     */
    @TargetApi(18)
    public <T extends IBinder> TypedBundle put(@NonNull Key<T> key, T value) {
        bundle.putBinder(key.name, value);
        return this;
    }

    /**
     * Inserts a value into the mapping of this {@code TypedBundle}, replacing any existing value
     * for the given key.
     *
     * @param key   the key
     * @param value the value
     * @return the {@code TypedBundle} for chaining
     */
    public TypedBundle put(@NonNull Key<Boolean> key, boolean value) {
        bundle.putBoolean(key.name, value);
        return this;
    }

    /**
     * Inserts a value into the mapping of this {@code TypedBundle}, replacing any existing value
     * for the given key.
     *
     * @param key   the key
     * @param value the value
     * @return the {@code TypedBundle} for chaining
     */
    public TypedBundle put(@NonNull Key<boolean[]> key, boolean[] value) {
        bundle.putBooleanArray(key.name, value);
        return this;
    }

    /**
     * Inserts a value into the mapping of this {@code TypedBundle}, replacing any existing value
     * for the given key.
     *
     * @param key   the key
     * @param value the value
     * @return the {@code TypedBundle} for chaining
     */
    public TypedBundle put(@NonNull Key<Bundle> key, Bundle value) {
        bundle.putBundle(key.name, value);
        return this;
    }

    /**
     * Inserts a value into the mapping of this {@code TypedBundle}, replacing any existing value
     * for the given key.
     *
     * @param key   the key
     * @param value the value
     * @return the {@code TypedBundle} for chaining
     */
    public TypedBundle put(@NonNull Key<Byte> key, byte value) {
        bundle.putByte(key.name, value);
        return this;
    }

    /**
     * Inserts a value into the mapping of this {@code TypedBundle}, replacing any existing value
     * for the given key.
     *
     * @param key   the key
     * @param value the value
     * @return the {@code TypedBundle} for chaining
     */
    public TypedBundle put(@NonNull Key<byte[]> key, byte[] value) {
        bundle.putByteArray(key.name, value);
        return this;
    }

    /**
     * Inserts a value into the mapping of this {@code TypedBundle}, replacing any existing value
     * for the given key.
     *
     * @param key   the key
     * @param value the value
     * @return the {@code TypedBundle} for chaining
     */
    public TypedBundle put(@NonNull Key<Character> key, char value) {
        bundle.putChar(key.name, value);
        return this;
    }

    /**
     * Inserts a value into the mapping of this {@code TypedBundle}, replacing any existing value
     * for the given key.
     *
     * @param key   the key
     * @param value the value
     * @return the {@code TypedBundle} for chaining
     */
    public TypedBundle put(@NonNull Key<char[]> key, char[] value) {
        bundle.putCharArray(key.name, value);
        return this;
    }

    /**
     * Inserts a value into the mapping of this {@code TypedBundle}, replacing any existing value
     * for the given key.
     *
     * @param key   the key
     * @param value the value
     * @return the {@code TypedBundle} for chaining
     */
    public TypedBundle put(@NonNull Key<String> key, String value) {
        bundle.putString(key.name, value);
        return this;
    }

    /**
     * Inserts a value into the mapping of this {@code TypedBundle}, replacing any existing value
     * for the given key.
     *
     * @param key   the key
     * @param value the value
     * @return the {@code TypedBundle} for chaining
     */
    public TypedBundle put(@NonNull Key<String[]> key, String[] value) {
        bundle.putStringArray(key.name, value);
        return this;
    }

    /**
     * Inserts a value into the mapping of this {@code TypedBundle}, replacing any existing value
     * for the given key.
     *
     * @param key   the key
     * @param value the value
     * @return the {@code TypedBundle} for chaining
     */
    public TypedBundle put(@NonNull Key<CharSequence> key, CharSequence value) {
        bundle.putCharSequence(key.name, value);
        return this;
    }

    /**
     * Inserts a value into the mapping of this {@code TypedBundle}, replacing any existing value
     * for the given key.
     *
     * @param key   the key
     * @param value the value
     * @return the {@code TypedBundle} for chaining
     */
    public TypedBundle put(@NonNull Key<CharSequence[]> key, CharSequence[] value) {
        bundle.putCharSequenceArray(key.name, value);
        return this;
    }

    /**
     * Inserts a value into the mapping of this {@code TypedBundle}, replacing any existing value
     * for the given key.
     *
     * @param key   the key
     * @param value the value
     * @return the {@code TypedBundle} for chaining
     */
    public TypedBundle put(@NonNull Key<Double> key, double value) {
        bundle.putDouble(key.name, value);
        return this;
    }

    /**
     * Inserts a value into the mapping of this {@code TypedBundle}, replacing any existing value
     * for the given key.
     *
     * @param key   the key
     * @param value the value
     * @return the {@code TypedBundle} for chaining
     */
    public TypedBundle put(@NonNull Key<double[]> key, double[] value) {
        bundle.putDoubleArray(key.name, value);
        return this;
    }

    /**
     * Inserts a value into the mapping of this {@code TypedBundle}, replacing any existing value
     * for the given key.
     *
     * @param key   the key
     * @param value the value
     * @return the {@code TypedBundle} for chaining
     */
    public TypedBundle put(@NonNull Key<Float> key, float value) {
        bundle.putFloat(key.name, value);
        return this;
    }

    /**
     * Inserts a value into the mapping of this {@code TypedBundle}, replacing any existing value
     * for the given key.
     *
     * @param key   the key
     * @param value the value
     * @return the {@code TypedBundle} for chaining
     */
    public TypedBundle put(@NonNull Key<float[]> key, float[] value) {
        bundle.putFloatArray(key.name, value);
        return this;
    }

    /**
     * Inserts a value into the mapping of this {@code TypedBundle}, replacing any existing value
     * for the given key.
     *
     * @param key   the key
     * @param value the value
     * @return the {@code TypedBundle} for chaining
     */
    public TypedBundle put(@NonNull Key<Integer> key, int value) {
        bundle.putInt(key.name, value);
        return this;
    }

    /**
     * Inserts a value into the mapping of this {@code TypedBundle}, replacing any existing value
     * for the given key.
     *
     * @param key   the key
     * @param value the value
     * @return the {@code TypedBundle} for chaining
     */
    public TypedBundle put(@NonNull Key<int[]> key, int[] value) {
        bundle.putIntArray(key.name, value);
        return this;
    }

    /**
     * Inserts a value into the mapping of this {@code TypedBundle}, replacing any existing value
     * for the given key.
     *
     * @param key   the key
     * @param value the value
     * @return the {@code TypedBundle} for chaining
     */
    public TypedBundle put(@NonNull Key<Long> key, long value) {
        bundle.putLong(key.name, value);
        return this;
    }

    /**
     * Inserts a value into the mapping of this {@code TypedBundle}, replacing any existing value
     * for the given key.
     *
     * @param key   the key
     * @param value the value
     * @return the {@code TypedBundle} for chaining
     */
    public TypedBundle put(@NonNull Key<long[]> key, long[] value) {
        bundle.putLongArray(key.name, value);
        return this;
    }

    /**
     * Inserts a value into the mapping of this {@code TypedBundle}, replacing any existing value
     * for the given key.
     *
     * @param key   the key
     * @param value the value
     * @return the {@code TypedBundle} for chaining
     */
    public <T extends Parcelable> TypedBundle put(@NonNull Key<T> key, T value) {
        bundle.putParcelable(key.name, value);
        return this;
    }

    /**
     * Inserts a value into the mapping of this {@code TypedBundle}, replacing any existing value
     * for the given key.
     *
     * @param key   the key
     * @param value the value
     * @return the {@code TypedBundle} for chaining
     */
    public <T extends Parcelable> TypedBundle put(@NonNull Key<T[]> key, T[] value) {
        bundle.putParcelableArray(key.name, value);
        return this;
    }

    /**
     * Inserts a value into the mapping of this {@code TypedBundle}, replacing any existing value
     * for the given key.
     *
     * @param key   the key
     * @param value the value
     * @return the {@code TypedBundle} for chaining
     */
    public TypedBundle put(@NonNull Key<Short> key, short value) {
        bundle.putShort(key.name, value);
        return this;
    }

    /**
     * Inserts a value into the mapping of this {@code TypedBundle}, replacing any existing value
     * for the given key.
     *
     * @param key   the key
     * @param value the value
     * @return the {@code TypedBundle} for chaining
     */
    public TypedBundle put(@NonNull Key<short[]> key, short[] value) {
        bundle.putShortArray(key.name, value);
        return this;
    }

    /**
     * Inserts a value into the mapping of this {@code TypedBundle}, replacing any existing value
     * for the given key.
     *
     * @param key   the key
     * @param value the value
     * @return the {@code TypedBundle} for chaining
     */
    @TargetApi(21)
    public TypedBundle put(@NonNull Key<Size> key, Size value) {
        bundle.putSize(key.name, value);
        return this;
    }

    /**
     * Inserts a value into the mapping of this {@code TypedBundle}, replacing any existing value
     * for the given key.
     *
     * @param key   the key
     * @param value the value
     * @return the {@code TypedBundle} for chaining
     */
    @TargetApi(21)
    public TypedBundle put(@NonNull Key<SizeF> key, SizeF value) {
        bundle.putSizeF(key.name, value);
        return this;
    }

    /**
     * Inserts a value into the mapping of this {@code TypedBundle}, replacing any existing value
     * for the given key.
     *
     * @param key   the key
     * @param value the value
     * @return the {@code TypedBundle} for chaining
     */
    public <T extends Parcelable> TypedBundle put(@NonNull Key<SparseArray<T>> key, SparseArray<T> value) {
        bundle.putSparseParcelableArray(key.name, value);
        return this;
    }

    /**
     * Inserts a value into the mapping of this {@code TypedBundle}, replacing any existing value
     * for the given key.
     *
     * @param key   the key
     * @param value the value
     * @return the {@code TypedBundle} for chaining
     */
    public <T extends Serializable> TypedBundle put(@NonNull Key<T> key, T value) {
        bundle.putSerializable(key.name, value);
        return this;
    }

    /**
     * Inserts a value into the mapping of this {@code TypedBundle}, replacing any existing value
     * for the given key. While this methods works, it is marked as deprecated because you should
     * you should use one of the more specific {@code put<Type>ArrayList()} methods instead since
     * they can guarantee better typesaftey.
     *
     * @param key   the key
     * @param value the value
     * @return the {@code TypedBundle} for chaining
     * @see #putIntegerArrayList(Key, java.util.ArrayList)
     * @see #putStringArrayList(Key, java.util.ArrayList)
     * @see #putCharSequenceArrayList(Key, java.util.ArrayList)
     * @see #putParcelableArrayList(Key, java.util.ArrayList)
     */
    @Deprecated
    @SuppressWarnings("unchecked")
    public <T> TypedBundle put(@NonNull Key<ArrayList<T>> key, ArrayList<T> value) {
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
        return this;
    }

    /**
     * Inserts a value into the mapping of this {@code TypedBundle}, replacing any existing value
     * for the given key.
     *
     * @param key   the key
     * @param value the value
     * @return the {@code TypedBundle} for chaining
     */
    public TypedBundle putIntegerArrayList(@NonNull Key<ArrayList<Integer>> key, ArrayList<Integer> value) {
        bundle.putIntegerArrayList(key.name, value);
        return this;
    }

    /**
     * Inserts a value into the mapping of this {@code TypedBundle}, replacing any existing value
     * for the given key.
     *
     * @param key   the key
     * @param value the value
     * @return the {@code TypedBundle} for chaining
     */
    public TypedBundle putStringArrayList(@NonNull Key<ArrayList<String>> key, ArrayList<String> value) {
        bundle.putStringArrayList(key.name, value);
        return this;
    }

    /**
     * Inserts a value into the mapping of this {@code TypedBundle}, replacing any existing value
     * for the given key.
     *
     * @param key   the key
     * @param value the value
     * @return the {@code TypedBundle} for chaining
     */
    public TypedBundle putCharSequenceArrayList(@NonNull Key<ArrayList<CharSequence>> key, ArrayList<CharSequence> value) {
        bundle.putCharSequenceArrayList(key.name, value);
        return this;
    }

    public <T extends Parcelable> TypedBundle putParcelableArrayList(@NonNull Key<ArrayList<T>> key, ArrayList<T> value) {
        bundle.putParcelableArrayList(key.name, value);
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
    public TypedBundle putAll(@NonNull Bundle bundle) {
        this.bundle.putAll(bundle);
        return this;
    }

    /**
     * Inserts all mappings from the given {@code TypedBundle} into this {@code TypedBundle}.
     *
     * @param bundle the the bundle to copy
     * @return the {@code TypedBundle} for chaining
     */
    public TypedBundle putAll(@NonNull TypedBundle bundle) {
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
    public boolean containsKey(@NonNull Key<?> key) {
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
    public void remove(@NonNull Key<?> key) {
        bundle.remove(key.name);
    }

    @SuppressWarnings("CloneDoesntCallSuperClone")
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
