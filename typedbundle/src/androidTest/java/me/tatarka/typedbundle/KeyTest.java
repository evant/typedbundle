package me.tatarka.typedbundle;

import android.test.AndroidTestCase;

/**
 * Created by evan on 1/1/15.
 */
public class KeyTest extends AndroidTestCase {
    public void testKeyNullName() {
        try {
            Key<String> key = new Key<>(null);
            fail("Key with null name should throw exception.");
        } catch (NullPointerException e) {
            // Success
        }
    }
}
