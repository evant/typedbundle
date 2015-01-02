package me.tatarka.typedbundle;

import android.util.SparseArray;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Condition;
import org.assertj.core.internal.Objects;

/**
 * Custom assertion for SparseArray that correctly implements isEqualsTo().
 */
public class SparseArrayAssert<E> extends AbstractAssert<SparseArrayAssert<E>, SparseArray<E>> {
    protected SparseArrayAssert(SparseArray<E> actual) {
        super(actual, SparseArrayAssert.class);
    }

    public static <E> SparseArrayAssert<E> assertThat(SparseArray<E> actual) {
        return new SparseArrayAssert<>(actual);
    }

    @Override
    public SparseArrayAssert<E> isEqualTo(Object expected) {
        Objects.instance().assertHasSameClassAs(info, actual, expected);
        final SparseArray<E> expectedSparseArray = (SparseArray<E>) expected;
        assertThat(actual).is(new Condition<SparseArray<E>>() {
            @Override
            public boolean matches(SparseArray<E> eSparseArray) {
                return isEqualTo(eSparseArray, expectedSparseArray);
            }
        });
        return this;
    }
    
    private static <E> boolean isEqualTo(SparseArray<E> a, SparseArray<E> b) {
        for (int i = 0; i < a.size(); ++i) {
            int key = a.keyAt(i);
            E valA = a.get(key);
            E valB = b.get(key);
            if (valA != null && valB != null) {
                if (!valA.equals(valB)) {
                    return false;
                }
            } else {
                // at least one is null; unless they're both null, they aren't equal
                if (valA != valB) {
                    return false;
                }
            }
        }
        return true;
    }
}
