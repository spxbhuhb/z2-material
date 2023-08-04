# CSS

## Shorthands

* `css/classes.kt` contains available CSS shorthands such as `p0`, which means `padding: 0;`
* `css/material.kt` contains available Material shorthands such as `titleLarge`
* `css/variables.kt` contains variables to use for general customization such as table borders etc.

## Variables

To customize general appearance use the variables defined in `css/variables.kt` during application startup:

```kotlin
fun customizeStyles() {
    val root = document.querySelector(":root") as? HTMLElement ?: return
    with(root.style) {
        setProperty(tableBorder, "none")
    }
}
```