TypedBundle
===========

Typesafe key-value pairings for Android Bundles.

## Usage

The idea here is extreamly simple. Instead of using string contants for you bundle's keys, you just use intances of `Key<T>` instead. This way the type of the value can be enforced at compile time.

```java
import me.tatarka.typedbundle.Key;

public static final Key<String> EXTRA_NAME = new Key<>("name");
public static final Key<Integer> EXTRA_AGE = new Key<>("age");
```

You can then use the `TypedBundle` wrapper class to put and get values from your Bundle.

```java
import me.tatarka.typedbundle.TypedBundle;

TypedBundle typedBundle = new TypedBundle()
  .put(EXTRA_NAME, "Bob")
  .put(EXTRA_AGE, 42);
startActivity(new Intent().putExtras(typedBundle.getBundle()));
...
TypedBundle typedBundle = new TypedBundle(getIntent().getExtras());
String name = typedBundle.get(EXTRA_NAME);
int age = typedBundle.get(EXTRA_AGE, 0); // defaults supported for any value.
```

That's it! The values are stored in the bundle exactly how you'd expect so you can use this to interface with existing code no problem.
