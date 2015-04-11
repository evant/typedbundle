package me.tatarka.typedbundle;

/**
 * A TypedBundle key. This is a typesafe version of the string keys used for Bundles and Prefs.
 *
 * @param <T> The key's type. This <em>must</em> be a type that either bundle or shared preferences
 *            supports, depending on usage.
 * @see android.os.Bundle
 * @see android.content.SharedPreferences
 */
public final class Key<T> {
    public final String name;

    /**
     * Construct a new key of the given name.
     *
     * @param name the key's name.
     */
    public Key(String name) {
        if (name == null) {
            throw new NullPointerException("name cannot be null");
        }
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !o.getClass().equals(getClass())) {
            return false;
        }
        return name.equals(((Key) o).name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Key[" + name + "]";
    }
}
