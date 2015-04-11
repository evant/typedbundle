package me.tatarka.typedbundle;

import android.annotation.TargetApi;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.test.AndroidTestCase;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import me.tatarka.typedbundle.util.SparseArrayAssert;

import static org.assertj.core.api.Assertions.assertThat;

public class TypedBundleTest extends AndroidTestCase {
    @TargetApi(11)
    public void testNewWithClassLoader() {
        ClassLoader classLoader = getClass().getClassLoader();
        TypedBundle typedBundle = new TypedBundle(classLoader);

        assertThat(typedBundle.getBundle().getClassLoader()).isEqualTo(classLoader);
    }

    public void testNewWithTypedBundle() {
        Key<String> key = new Key<>("key");
        TypedBundle testTypedBundle = new TypedBundle();
        testTypedBundle.put(key, "value");
        TypedBundle typedBundle = new TypedBundle(testTypedBundle);

        assertThat(typedBundle.get(key)).isEqualTo("value");
        // Must copy the underlying bundle
        assertThat(typedBundle.getBundle()).isNotEqualTo(testTypedBundle.getBundle());
    }

    @TargetApi(18)
    public void testGetBinder() {
        Key<IBinder> key = new Key<>("key");
        Bundle bundle = new Bundle();
        TestBinder testBinder = new TestBinder();
        bundle.putBinder("key", testBinder);
        TypedBundle typedBundle = runThroughParcel(new TypedBundle(bundle));

        assertThat(typedBundle.get(key)).isEqualTo(testBinder);
    }

    public void testGetBoolean() {
        Key<Boolean> key = new Key<>("key");
        Bundle bundle = new Bundle();
        bundle.putBoolean("key", true);
        TypedBundle typedBundle = runThroughParcel(new TypedBundle(bundle));

        assertThat(typedBundle.get(key)).isTrue();
    }

    public void testGetBooleanDefaultValueAbsent() {
        Key<Boolean> key = new Key<>("key");
        TypedBundle typedBundle = new TypedBundle();

        assertThat(typedBundle.get(key, false)).isFalse();
    }

    public void testGetBooleanDefaultValuePresent() {
        Key<Boolean> key = new Key<>("key");
        TypedBundle typedBundle = new TypedBundle();
        typedBundle.put(key, true);

        assertThat(typedBundle.get(key, false)).isTrue();
    }

    public void testGetBooleanArray() {
        Key<boolean[]> key = new Key<>("key");
        Bundle bundle = new Bundle();
        boolean[] testBooleanArray = new boolean[]{true, false};
        bundle.putBooleanArray("key", testBooleanArray);
        TypedBundle typedBundle = runThroughParcel(new TypedBundle(bundle));

        assertThat(typedBundle.get(key)).isEqualTo(testBooleanArray);
    }

    public void testGetBundle() {
        Key<Bundle> key = new Key<>("key");
        Bundle bundle = new Bundle();
        Bundle testBundle = new Bundle();
        testBundle.putString("key", "value");
        bundle.putBundle("key", testBundle);
        TypedBundle typedBundle = runThroughParcel(new TypedBundle(bundle));

        assertThat(typedBundle.get(key).getString("key")).isEqualTo("value");
    }

    public void testGetByte() {
        Key<Byte> key = new Key<>("key");
        Bundle bundle = new Bundle();
        bundle.putByte("key", (byte) 1);
        TypedBundle typedBundle = runThroughParcel(new TypedBundle(bundle));

        assertThat(typedBundle.get(key)).isEqualTo((byte) 1);
    }

    public void testGetByteDefaultValue() {
        Key<Byte> key = new Key<>("key");
        TypedBundle typedBundle = new TypedBundle();

        assertThat(typedBundle.get(key, (byte) 0)).isEqualTo((byte) 0);
    }

    public void testGetByteArray() {
        Key<byte[]> key = new Key<>("key");
        Bundle bundle = new Bundle();
        byte[] testByteArray = new byte[]{1, 2};
        bundle.putByteArray("key", testByteArray);
        TypedBundle typedBundle = runThroughParcel(new TypedBundle(bundle));

        assertThat(typedBundle.get(key)).isEqualTo(testByteArray);
    }

    public void testGetChar() {
        Key<Character> key = new Key<>("key");
        Bundle bundle = new Bundle();
        bundle.putChar("key", 'a');
        TypedBundle typedBundle = runThroughParcel(new TypedBundle(bundle));

        assertThat(typedBundle.get(key)).isEqualTo('a');
    }

    public void testGetCharDefaultValue() {
        Key<Character> key = new Key<>("key");
        TypedBundle typedBundle = new TypedBundle();

        assertThat(typedBundle.get(key, 'z')).isEqualTo('z');
    }

    public void testGetCharArray() {
        Key<char[]> key = new Key<>("key");
        Bundle bundle = new Bundle();
        char[] testCharArray = new char[]{'a', 'b'};
        bundle.putCharArray("key", testCharArray);
        TypedBundle typedBundle = runThroughParcel(new TypedBundle(bundle));

        assertThat(typedBundle.get(key)).isEqualTo(testCharArray);
    }

    public void testGetCharSequence() {
        Key<CharSequence> key = new Key<>("key");
        Bundle bundle = new Bundle();
        bundle.putCharSequence("key", "value");
        TypedBundle typedBundle = new TypedBundle(bundle);

        assertThat(typedBundle.get(key)).isEqualTo("value");
    }

    public void testGetCharSequenceDefaultValue() {
        Key<CharSequence> key = new Key<>("key");
        TypedBundle typedBundle = new TypedBundle();

        assertThat(typedBundle.get(key, "")).isEqualTo("");
    }

    public void testGetCharSequenceArray() {
        Key<CharSequence[]> key = new Key<>("key");
        Bundle bundle = new Bundle();
        CharSequence[] testCharSequenceArray = new CharSequence[]{"value1", "value2"};
        bundle.putCharSequenceArray("key", testCharSequenceArray);
        TypedBundle typedBundle = runThroughParcel(new TypedBundle(bundle));

        assertThat(typedBundle.get(key)).isEqualTo(testCharSequenceArray);
    }

    public void testGetCharSequenceArrayList() {
        Key<ArrayList<CharSequence>> key = new Key<>("key");
        Bundle bundle = new Bundle();
        ArrayList<CharSequence> testCharSequenceArrayList = new ArrayList<>(Arrays.<CharSequence>asList("value1", "value2"));
        bundle.putCharSequenceArrayList("key", testCharSequenceArrayList);
        TypedBundle typedBundle = runThroughParcel(new TypedBundle(bundle));

        assertThat(typedBundle.get(key)).isEqualTo(testCharSequenceArrayList);
    }

    public void testGetDouble() {
        Key<Double> key = new Key<>("key");
        Bundle bundle = new Bundle();
        bundle.putDouble("key", 1d);
        TypedBundle typedBundle = runThroughParcel(new TypedBundle(bundle));

        assertThat(typedBundle.get(key)).isEqualTo(1d);
    }

    public void testGetDoubleDefaultValue() {
        Key<Double> key = new Key<>("key");
        TypedBundle typedBundle = new TypedBundle();

        assertThat(typedBundle.get(key, 0d)).isEqualTo(0d);
    }

    public void testGetDoubleArray() {
        Key<double[]> key = new Key<>("key");
        Bundle bundle = new Bundle();
        double[] testDoubleArray = new double[]{1d, 2d};
        bundle.putDoubleArray("key", testDoubleArray);
        TypedBundle typedBundle = runThroughParcel(new TypedBundle(bundle));

        assertThat(typedBundle.get(key)).isEqualTo(testDoubleArray);
    }

    public void testGetFloat() {
        Key<Float> key = new Key<>("key");
        Bundle bundle = new Bundle();
        bundle.putFloat("key", 1f);
        TypedBundle typedBundle = runThroughParcel(new TypedBundle(bundle));

        assertThat(typedBundle.get(key)).isEqualTo(1f);
    }

    public void testGetFloatDefaultValue() {
        Key<Float> key = new Key<>("key");
        TypedBundle typedBundle = new TypedBundle();

        assertThat(typedBundle.get(key, 0f)).isEqualTo(0f);
    }

    public void testGetFloatArray() {
        Key<float[]> key = new Key<>("key");
        Bundle bundle = new Bundle();
        float[] testFloatArray = new float[]{1f, 2f};
        bundle.putFloatArray("key", testFloatArray);
        TypedBundle typedBundle = runThroughParcel(new TypedBundle(bundle));

        assertThat(typedBundle.get(key)).isEqualTo(testFloatArray);
    }

    public void testGetInt() {
        Key<Integer> key = new Key<>("key");
        Bundle bundle = new Bundle();
        bundle.putInt("key", 1);
        TypedBundle typedBundle = runThroughParcel(new TypedBundle(bundle));

        assertThat(typedBundle.get(key)).isEqualTo(1);
    }

    public void testGetIntDefaultValue() {
        Key<Integer> key = new Key<>("key");
        TypedBundle typedBundle = new TypedBundle();

        assertThat(typedBundle.get(key, 0)).isEqualTo(0);
    }

    public void testGetIntArray() {
        Key<int[]> key = new Key<>("key");
        Bundle bundle = new Bundle();
        int[] testIntArray = new int[]{1, 2};
        bundle.putIntArray("key", testIntArray);
        TypedBundle typedBundle = runThroughParcel(new TypedBundle(bundle));

        assertThat(typedBundle.get(key)).isEqualTo(testIntArray);
    }

    public void testGetIntegerArrayList() {
        Key<ArrayList<Integer>> key = new Key<>("key");
        Bundle bundle = new Bundle();
        ArrayList<Integer> testIntegerArrayList = new ArrayList<>(Arrays.asList(1, 2));
        bundle.putIntegerArrayList("key", testIntegerArrayList);
        TypedBundle typedBundle = runThroughParcel(new TypedBundle(bundle));

        assertThat(typedBundle.get(key)).isEqualTo(testIntegerArrayList);
    }

    public void testGetLong() {
        Key<Long> key = new Key<>("key");
        Bundle bundle = new Bundle();
        bundle.putLong("key", 1L);
        TypedBundle typedBundle = runThroughParcel(new TypedBundle(bundle));

        assertThat(typedBundle.get(key)).isEqualTo(1L);
    }

    public void testGetLongDefaultValue() {
        Key<Long> key = new Key<>("key");
        TypedBundle typedBundle = new TypedBundle();

        assertThat(typedBundle.get(key, 0L)).isEqualTo(0L);
    }

    public void testGetLongArray() {
        Key<long[]> key = new Key<>("key");
        Bundle bundle = new Bundle();
        long[] testLongArray = new long[]{1, 2};
        bundle.putLongArray("key", testLongArray);
        TypedBundle typedBundle = runThroughParcel(new TypedBundle(bundle));

        assertThat(typedBundle.get(key)).isEqualTo(testLongArray);
    }

    public void testGetParcelable() {
        Key<TestParcelable> key = new Key<>("key");
        Bundle bundle = new Bundle();
        TestParcelable testParcelable = new TestParcelable(1);
        bundle.putParcelable("key", testParcelable);
        TypedBundle typedBundle = runThroughParcel(new TypedBundle(bundle));

        assertThat(typedBundle.get(key)).isEqualTo(testParcelable);
    }

    public void testGetParcelableArray() {
        Key<TestParcelable[]> key = new Key<>("key");
        Bundle bundle = new Bundle();
        TestParcelable[] testParcelableArray = new TestParcelable[]{new TestParcelable(1), new TestParcelable(2)};
        bundle.putParcelableArray("key", testParcelableArray);
        TypedBundle typedBundle = runThroughParcel(new TypedBundle(bundle));

        assertThat(typedBundle.get(key)).isEqualTo(testParcelableArray);
    }

    public void testGetParcelableArrayList() {
        Key<ArrayList<TestParcelable>> key = new Key<>("key");
        Bundle bundle = new Bundle();
        ArrayList<TestParcelable> testParcelableArrayList = new ArrayList<>(Arrays.asList(new TestParcelable(1), new TestParcelable(2)));
        bundle.putParcelableArrayList("key", testParcelableArrayList);
        TypedBundle typedBundle = runThroughParcel(new TypedBundle(bundle));

        assertThat(typedBundle.get(key)).isEqualTo(testParcelableArrayList);
    }

    public void testGetSerializable() {
        Key<TestSerializable> key = new Key<>("key");
        Bundle bundle = new Bundle();
        TestSerializable testSerializable = new TestSerializable(1);
        bundle.putSerializable("key", testSerializable);
        TypedBundle typedBundle = runThroughParcel(new TypedBundle(bundle));

        assertThat(typedBundle.get(key)).isEqualTo(testSerializable);
    }

    public void testGetShort() {
        Key<Short> key = new Key<>("key");
        Bundle bundle = new Bundle();
        bundle.putShort("key", (short) 1);
        TypedBundle typedBundle = runThroughParcel(new TypedBundle(bundle));

        assertThat(typedBundle.get(key)).isEqualTo((short) 1);
    }

    public void testGetShortDefaultValue() {
        Key<Short> key = new Key<>("key");
        TypedBundle typedBundle = new TypedBundle();

        assertThat(typedBundle.get(key, (short) 0)).isEqualTo((short) 0);
    }

    public void testGetShortArray() {
        Key<short[]> key = new Key<>("key");
        Bundle bundle = new Bundle();
        short[] testShortArray = new short[]{1, 2};
        bundle.putShortArray("key", testShortArray);
        TypedBundle typedBundle = runThroughParcel(new TypedBundle(bundle));

        assertThat(typedBundle.get(key)).isEqualTo(testShortArray);
    }

    @TargetApi(21)
    public void testGetSize() {
        Key<Size> key = new Key<>("key");
        Bundle bundle = new Bundle();
        Size testSize = new Size(1, 2);
        bundle.putSize("key", testSize);
        TypedBundle typedBundle = runThroughParcel(new TypedBundle(bundle));

        assertThat(typedBundle.get(key)).isEqualTo(testSize);
    }

    @TargetApi(21)
    public void testGetSizeF() {
        Key<SizeF> key = new Key<>("key");
        Bundle bundle = new Bundle();
        SizeF testSize = new SizeF(1, 2);
        bundle.putSizeF("key", testSize);
        TypedBundle typedBundle = runThroughParcel(new TypedBundle(bundle));

        assertThat(typedBundle.get(key)).isEqualTo(testSize);
    }

    public void testGetSparseParcelableArray() {
        Key<SparseArray<TestParcelable>> key = new Key<>("key");
        Bundle bundle = new Bundle();
        SparseArray<TestParcelable> testSparseParcelableArray = new SparseArray<>();
        testSparseParcelableArray.put(1, new TestParcelable(1));
        testSparseParcelableArray.put(2, new TestParcelable(2));
        bundle.putSparseParcelableArray("key", testSparseParcelableArray);
        TypedBundle typedBundle = runThroughParcel(new TypedBundle(bundle));

        SparseArrayAssert.assertThat(typedBundle.get(key)).isEqualTo(testSparseParcelableArray);
    }

    public void testGetString() {
        Key<String> key = new Key<>("key");
        Bundle bundle = new Bundle();
        bundle.putString("key", "value");
        TypedBundle typedBundle = runThroughParcel(new TypedBundle(bundle));

        assertThat(typedBundle.get(key)).isEqualTo("value");
    }

    public void testGetStringDefaultValue() {
        Key<String> key = new Key<>("key");
        TypedBundle typedBundle = new TypedBundle();

        assertThat(typedBundle.get(key, "")).isEqualTo("");
    }

    public void testGetStringArray() {
        Key<String[]> key = new Key<>("key");
        Bundle bundle = new Bundle();
        String[] testStringArray = new String[]{"value1", "value2"};
        bundle.putStringArray("key", testStringArray);
        TypedBundle typedBundle = runThroughParcel(new TypedBundle(bundle));

        assertThat(typedBundle.get(key)).isEqualTo(testStringArray);
    }

    public void testGetStringArrayList() {
        Key<ArrayList<String>> key = new Key<>("key");
        Bundle bundle = new Bundle();
        ArrayList<String> testStringArrayList = new ArrayList<>(Arrays.asList("value1", "value2"));
        bundle.putStringArrayList("key", testStringArrayList);
        TypedBundle typedBundle = new TypedBundle(bundle);

        assertThat(typedBundle.get(key)).isEqualTo(testStringArrayList);
    }

    public void testGetTypedBundle() {
        Key<TypedBundle> key = new Key<>("key");
        Key<String> testTypedBundleKey = new Key<>("key");
        Bundle bundle = new Bundle();
        TypedBundle testTypedBundle = new TypedBundle();
        testTypedBundle.put(testTypedBundleKey, "value");
        bundle.putParcelable("key", testTypedBundle);
        TypedBundle typedBundle = new TypedBundle(bundle);

        assertThat(typedBundle.get(key)).isEqualTo(testTypedBundle);
    }

    public void testPutAllBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("key", "value");
        TypedBundle typedBundle = new TypedBundle();
        typedBundle.putAll(bundle);

        assertThat(runThroughParcel(typedBundle).getBundle().getString("key")).isEqualTo("value");
    }

    public void testPutAllTypedBundle() {
        Key<String> key = new Key<>("key");
        TypedBundle bundle = new TypedBundle();
        bundle.put(key, "value");
        TypedBundle typedBundle = new TypedBundle();
        typedBundle.putAll(bundle);

        assertThat(runThroughParcel(typedBundle).getBundle().getString("key")).isEqualTo("value");
    }

    @TargetApi(18)
    public void testPutBinder() {
        Key<IBinder> key = new Key<>("key");
        IBinder testBinder = new TestBinder();
        TypedBundle typedBundle = new TypedBundle();
        typedBundle.put(key, testBinder);

        assertThat(runThroughParcel(typedBundle).getBundle().getBinder("key")).isEqualTo(testBinder);
    }

    public void testPutBoolean() {
        Key<Boolean> key = new Key<>("key");
        TypedBundle typedBundle = new TypedBundle();
        typedBundle.put(key, true);

        assertThat(runThroughParcel(typedBundle).getBundle().getBoolean("key")).isEqualTo(true);
    }

    public void testPutBooleanArray() {
        Key<boolean[]> key = new Key<>("key");
        boolean[] testBooleanArray = new boolean[]{true, false};
        TypedBundle typedBundle = new TypedBundle();
        typedBundle.put(key, testBooleanArray);

        assertThat(runThroughParcel(typedBundle).getBundle().getBooleanArray("key")).isEqualTo(testBooleanArray);
    }

    public void testPutBundle() {
        Key<Bundle> key = new Key<>("key");
        Bundle testBundle = new Bundle();
        testBundle.putString("key", "value");
        TypedBundle typedBundle = new TypedBundle();
        typedBundle.put(key, testBundle);

        assertThat(runThroughParcel(typedBundle).getBundle().getBundle("key").getString("key")).isEqualTo("value");
    }

    public void testPutByte() {
        Key<Byte> key = new Key<>("key");
        TypedBundle typedBundle = new TypedBundle();
        typedBundle.put(key, (byte) 1);

        assertThat(runThroughParcel(typedBundle).getBundle().getByte("key")).isEqualTo((byte) 1);
    }

    public void testPutByteArray() {
        Key<byte[]> key = new Key<>("key");
        byte[] testByteArray = new byte[]{1, 2};
        TypedBundle typedBundle = new TypedBundle();
        typedBundle.put(key, testByteArray);

        assertThat(runThroughParcel(typedBundle).getBundle().getByteArray("key")).isEqualTo(testByteArray);
    }

    public void testPutChar() {
        Key<Character> key = new Key<>("key");
        TypedBundle typedBundle = new TypedBundle();
        typedBundle.put(key, 'a');

        assertThat(runThroughParcel(typedBundle).getBundle().getChar("key")).isEqualTo('a');
    }

    public void testPutCharArray() {
        Key<char[]> key = new Key<>("key");
        char[] testCharArray = new char[]{'a', 'b'};
        TypedBundle typedBundle = new TypedBundle();
        typedBundle.put(key, testCharArray);

        assertThat(runThroughParcel(typedBundle).getBundle().getCharArray("key")).isEqualTo(testCharArray);
    }

    public void testPutCharSequence() {
        Key<CharSequence> key = new Key<>("key");
        CharSequence testCharSequence = new StringBuffer("value");
        TypedBundle typedBundle = new TypedBundle();
        typedBundle.put(key, testCharSequence);

        assertThat(typedBundle.getBundle().getCharSequence("key")).isEqualTo(testCharSequence);
    }

    // CharSequences always parcel to strings.
    public void testPutCharSequenceParcel() {
        Key<CharSequence> key = new Key<>("key");
        CharSequence testCharSequence = new StringBuffer("value");
        TypedBundle typedBundle = new TypedBundle();
        typedBundle.put(key, testCharSequence);

        assertThat(runThroughParcel(typedBundle).getBundle().getCharSequence("key")).isEqualTo("value");
    }

    public void testPutCharSequenceArray() {
        Key<CharSequence[]> key = new Key<>("key");
        CharSequence[] testCharSequenceArray = new CharSequence[]{ new StringBuilder("value1"), new StringBuilder("value2") };
        TypedBundle typedBundle = new TypedBundle();
        typedBundle.put(key, testCharSequenceArray);

        assertThat(typedBundle.getBundle().getCharSequenceArray("key")).isEqualTo(testCharSequenceArray);
    }

    // CharSequences always parcel to strings.
    public void testPutCharSequenceArrayParcel() {
        Key<CharSequence[]> key = new Key<>("key");
        CharSequence[] testCharSequenceArray = new CharSequence[]{ new StringBuilder("value1"), new StringBuilder("value2") };
        TypedBundle typedBundle = new TypedBundle();
        typedBundle.put(key, testCharSequenceArray);

        assertThat(runThroughParcel(typedBundle).getBundle().getCharSequenceArray("key")).isEqualTo(new String[] { "value1", "value2" });
    }

    public void testPutCharSequenceArrayList() {
        Key<ArrayList<CharSequence>> key = new Key<>("key");
        ArrayList<CharSequence> testCharSequenceArrayList = new ArrayList<>(Arrays.<CharSequence>asList(new StringBuilder("value1"), new StringBuilder("value2")));
        TypedBundle typedBundle = new TypedBundle();
        typedBundle.putCharSequenceArrayList(key, testCharSequenceArrayList);

        assertThat(typedBundle.getBundle().getCharSequenceArrayList("key")).isEqualTo(testCharSequenceArrayList);
    }

    // CharSequences always parcel to strings.
    public void testPutCharSequenceArrayListParcel() {
        Key<ArrayList<CharSequence>> key = new Key<>("key");
        ArrayList<CharSequence> testCharSequenceArrayList = new ArrayList<>(Arrays.<CharSequence>asList(new StringBuilder("value1"), new StringBuilder("value2")));
        TypedBundle typedBundle = new TypedBundle();
        typedBundle.putCharSequenceArrayList(key, testCharSequenceArrayList);

        assertThat(runThroughParcel(typedBundle).getBundle().getCharSequenceArrayList("key")).isEqualTo(new ArrayList<>(Arrays.asList("value1", "value2")));
    }

    public void testPutDouble() {
        Key<Double> key = new Key<>("key");
        TypedBundle typedBundle = new TypedBundle();
        typedBundle.put(key, 1d);

        assertThat(runThroughParcel(typedBundle).getBundle().getDouble("key")).isEqualTo(1d);
    }

    public void testPutDoubleArray() {
        Key<double[]> key = new Key<>("key");
        double[] testDoubleArray = new double[] { 1, 2 };
        TypedBundle typedBundle = new TypedBundle();
        typedBundle.put(key, testDoubleArray);

        assertThat(runThroughParcel(typedBundle).getBundle().getDoubleArray("key")).isEqualTo(testDoubleArray);
    }

    public void testPutFloat() {
        Key<Float> key = new Key<>("key");
        TypedBundle typedBundle = new TypedBundle();
        typedBundle.put(key, 1f);

        assertThat(runThroughParcel(typedBundle).getBundle().getFloat("key")).isEqualTo(1f);
    }

    public void testPutFloatArray() {
        Key<float[]> key = new Key<>("key");
        float[] testFloatArray = new float[]{1, 2};
        TypedBundle typedBundle = new TypedBundle();
        typedBundle.put(key, testFloatArray);

        assertThat(runThroughParcel(typedBundle).getBundle().getFloatArray("key")).isEqualTo(testFloatArray);
    }

    public void testPutInt() {
        Key<Integer> key = new Key<>("key");
        TypedBundle typedBundle = new TypedBundle();
        typedBundle.put(key, 1);

        assertThat(runThroughParcel(typedBundle).getBundle().getInt("key")).isEqualTo(1);
    }

    public void testPutIntArray() {
        Key<int[]> key = new Key<>("key");
        int[] testIntArray = new int[] { 1, 2 };
        TypedBundle typedBundle = new TypedBundle();
        typedBundle.put(key, testIntArray);

        assertThat(runThroughParcel(typedBundle).getBundle().getIntArray("key")).isEqualTo(testIntArray);
    }

    public void testPutIntegerArrayList() {
        Key<ArrayList<Integer>> key = new Key<>("key");
        ArrayList<Integer> testIntegerArrayList = new ArrayList<>(Arrays.asList(1, 2));
        TypedBundle typedBundle = new TypedBundle();
        typedBundle.putIntegerArrayList(key, testIntegerArrayList);

        assertThat(runThroughParcel(typedBundle).getBundle().getIntegerArrayList("key")).isEqualTo(testIntegerArrayList);
    }

    public void testPutLong() {
        Key<Long> key = new Key<>("key");
        TypedBundle typedBundle = new TypedBundle();
        typedBundle.put(key, 1L);

        assertThat(runThroughParcel(typedBundle).getBundle().getLong("key")).isEqualTo(1L);
    }

    public void testPutLongArray() {
        Key<long[]> key = new Key<>("key");
        long[] testLongArray = new long[] { 1, 2 };
        TypedBundle typedBundle = new TypedBundle();
        typedBundle.put(key, testLongArray);

        assertThat(runThroughParcel(typedBundle).getBundle().getLongArray("key")).isEqualTo(testLongArray);
    }

    public void testPutParcelable() {
        Key<TestParcelable> key = new Key<>("key");
        TestParcelable testParcelable = new TestParcelable(1);
        TypedBundle typedBundle = new TypedBundle();
        typedBundle.put(key, testParcelable);

        assertThat(runThroughParcel(typedBundle).getBundle().getParcelable("key")).isEqualTo(testParcelable);
    }

    public void testPutParcelableArray() {
        Key<TestParcelable[]> key = new Key<>("key");
        TestParcelable[] testParcelableArray = new TestParcelable[]{new TestParcelable(1), new TestParcelable(2)};
        TypedBundle typedBundle = new TypedBundle();
        typedBundle.put(key, testParcelableArray);

        assertThat(runThroughParcel(typedBundle).getBundle().getParcelableArray("key")).isEqualTo(testParcelableArray);
    }

    public void testPutParcelableArrayList() {
        Key<ArrayList<TestParcelable>> key = new Key<>("key");
        ArrayList<TestParcelable> testParcelableArrayList = new ArrayList<>(Arrays.asList(new TestParcelable(1), new TestParcelable(2)));
        TypedBundle typedBundle = new TypedBundle();
        typedBundle.putParcelableArrayList(key, testParcelableArrayList);

        assertThat(runThroughParcel(typedBundle).getBundle().getParcelableArrayList("key")).isEqualTo(testParcelableArrayList);
    }

    public void testPutSerializable() {
        Key<TestSerializable> key = new Key<>("key");
        TestSerializable testSerializable = new TestSerializable(1);
        TypedBundle typedBundle = new TypedBundle();
        typedBundle.put(key, testSerializable);

        assertThat(runThroughParcel(typedBundle).getBundle().getSerializable("key")).isEqualTo(testSerializable);
    }

    public void testPutShort() {
        Key<Short> key = new Key<>("key");
        TypedBundle typedBundle = new TypedBundle();
        typedBundle.put(key, (short) 1);

        assertThat(runThroughParcel(typedBundle).getBundle().getShort("key")).isEqualTo((short) 1);
    }

    public void testPutShortArray() {
        Key<short[]> key = new Key<>("key");
        short[] testShortArray = new short[]{1, 2,};
        TypedBundle typedBundle = new TypedBundle();
        typedBundle.put(key, testShortArray);

        assertThat(runThroughParcel(typedBundle).getBundle().getShortArray("key")).isEqualTo(testShortArray);
    }

    @TargetApi(21)
    public void testPutSize() {
        Key<Size> key = new Key<>("key");
        Size testSize = new Size(1, 2);
        TypedBundle typedBundle = new TypedBundle();
        typedBundle.put(key, testSize);

        assertThat(runThroughParcel(typedBundle).getBundle().getSize("key")).isEqualTo(testSize);
    }

    @TargetApi(21)
    public void testPutSizeF() {
        Key<SizeF> key = new Key<>("key");
        SizeF testSize = new SizeF(1, 2);
        TypedBundle typedBundle = new TypedBundle();
        typedBundle.put(key, testSize);

        assertThat(runThroughParcel(typedBundle).getBundle().getSizeF("key")).isEqualTo(testSize);
    }

    public void testPutSparseParcelableArray() {
        Key<SparseArray<TestParcelable>> key = new Key<>("key");
        SparseArray<TestParcelable> testParcelableSparseArray = new SparseArray<>();
        testParcelableSparseArray.put(1, new TestParcelable(1));
        testParcelableSparseArray.put(2, new TestParcelable(2));
        TypedBundle typedBundle = new TypedBundle();
        typedBundle.put(key, testParcelableSparseArray);

        SparseArrayAssert.assertThat(runThroughParcel(typedBundle).getBundle().getSparseParcelableArray("key")).isEqualTo(testParcelableSparseArray);
    }

    public void testPutString() {
        Key<String> key = new Key<>("key");
        TypedBundle typedBundle = new TypedBundle();
        typedBundle.put(key, "value");

        assertThat(runThroughParcel(typedBundle).getBundle().getString("key")).isEqualTo("value");
    }

    public void testPutStringArray() {
        Key<String[]> key = new Key<>("key");
        String[] testStringArray = new String[] { "value1", "value2" };
        TypedBundle typedBundle = new TypedBundle();
        typedBundle.put(key, testStringArray);

        assertThat(runThroughParcel(typedBundle).getBundle().getStringArray("key")).isEqualTo(testStringArray);
    }

    public void testPutStringArrayList() {
        Key<ArrayList<String>> key = new Key<>("key");
        ArrayList<String> testStringArrayList = new ArrayList<>(Arrays.asList("value1", "value2"));
        TypedBundle typedBundle = new TypedBundle();
        typedBundle.putStringArrayList(key, testStringArrayList);

        assertThat(runThroughParcel(typedBundle).getBundle().getStringArrayList("key")).isEqualTo(testStringArrayList);
    }

    public void testPutTypedBundle() {
        Key<TypedBundle> key = new Key<>("key");
        Key<String> testTypedBundleKey = new Key<>("key");
        TypedBundle testTypedBundle = new TypedBundle();
        testTypedBundle.put(testTypedBundleKey, "value");
        TypedBundle typedBundle = new TypedBundle();
        typedBundle.put(key, testTypedBundle);

        assertThat(runThroughParcel(typedBundle).getBundle().<TypedBundle>getParcelable("key").get(testTypedBundleKey)).isEqualTo("value");
    }

    // Doesn't compile!
//    public void testPutBadType() {
//        Key<TestBadType> key = new Key<>("key");
//        TypedBundle typedBundle = new TypedBundle();
//
//        try {
//            typedBundle.put(key, new TestBadType());
//            fail("Should throw for invalid bundle type");
//        } catch (IllegalArgumentException e) {
//            // Success
//        }
//    }

    // This only works if the list has at least one item, but this is the best we can do with type
    // erasure.
    public void testPutBadArrayListType() {
        Key<ArrayList<TestBadType>>  key = new Key<>("key");
        TypedBundle typedBundle = new TypedBundle();

        try {
            // Deprecated to warn user of this method
            typedBundle.put(key, new ArrayList<>(Arrays.asList(new TestBadType())));
            fail("Should throw for invalid bundle type");
        } catch (IllegalArgumentException e) {
            // Success
        }
    }

    public void testClear() {
        Bundle bundle = new Bundle();
        bundle.putString("key", "value");
        TypedBundle typedBundle = new TypedBundle(bundle);
        typedBundle.clear();

        assertThat(typedBundle.getBundle().isEmpty()).isTrue();
    }

    public void testContainsKey() {
        Key<String> key = new Key<>("key");
        Bundle bundle = new Bundle();
        bundle.putString("key", "value");
        TypedBundle typedBundle = new TypedBundle(bundle);

        assertThat(typedBundle.containsKey(key)).isTrue();
    }

    public void testIsEmpty() {
        Bundle bundle = new Bundle();
        bundle.putString("key", "value");
        TypedBundle typedBundle = new TypedBundle(bundle);

        assertThat(typedBundle.isEmpty()).isFalse();
    }

    public void testKeySet() {
        Bundle bundle = new Bundle();
        bundle.putString("stringKey", "value");
        bundle.putInt("intKey", 1);
        TypedBundle typedBundle = new TypedBundle(bundle);

        assertThat(typedBundle.keySet())
                .isEqualTo(new HashSet<>(Arrays.asList(new Key<String>("stringKey"), new Key<Integer>("intKey"))));
    }

    public void testRemove() {
        Key<String> key = new Key<>("key");
        Bundle bundle = new Bundle();
        bundle.putString("key", "value");
        TypedBundle typedBundle = new TypedBundle(bundle);
        typedBundle.remove(key);

        assertThat(typedBundle.getBundle().containsKey("key")).isFalse();
    }

    public void testSize() {
        Bundle bundle = new Bundle();
        bundle.putString("key", "value");
        TypedBundle typedBundle = new TypedBundle(bundle);

        assertThat(typedBundle.size()).isEqualTo(1);
    }

    private static TypedBundle runThroughParcel(TypedBundle bundle) {
        Parcel parcel = Parcel.obtain();
        bundle.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        return TypedBundle.CREATOR.createFromParcel(parcel);
    }

    private static class TestBinder extends Binder {
    }

    public static class TestParcelable implements Parcelable {
        private int value;

        public TestParcelable(int value) {
            this.value = value;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(value);
        }

        private TestParcelable(Parcel in) {
            this.value = in.readInt();
        }

        public static final Creator<TestParcelable> CREATOR = new Creator<TestParcelable>() {
            @Override
            public TestParcelable createFromParcel(Parcel source) {
                return new TestParcelable(source);
            }

            @Override
            public TestParcelable[] newArray(int size) {
                return new TestParcelable[size];
            }
        };

        @Override
        public boolean equals(Object o) {
            if (o == null || !o.getClass().equals(getClass())) {
                return false;
            }
            return value == ((TestParcelable) o).value;
        }

        @Override
        public int hashCode() {
            return value;
        }
    }

    public static class TestSerializable implements Serializable {
        private int value;

        public TestSerializable(int value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || !o.getClass().equals(getClass())) {
                return false;
            }
            return value == ((TestSerializable) o).value;
        }

        @Override
        public int hashCode() {
            return value;
        }
    }

    public static class TestBadType {};
}
