package me.tatarka.typedbundle;

/**
 * A TypedBundle id. This is a typesafe version of int id's used in TypedLoaderManager.
 *
 * @param <T> The id's type.
 */
public final class Id<T> {
    public final int id;

    /**
     * Construct a new id of the given int id.
     *
     * @param id the id's int id.
     */
    public Id(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Id<?> id1 = (Id<?>) o;
        return id == id1.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Key[" + id + "]";
    }
}
