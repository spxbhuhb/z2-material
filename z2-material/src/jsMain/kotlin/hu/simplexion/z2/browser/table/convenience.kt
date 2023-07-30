package hu.simplexion.z2.browser.table

import hu.simplexion.z2.browser.html.Z2

fun <T> Z2.table(
    builder : TableBuilder<T>.() -> Unit
) = Table(this, builder)