package hu.simplexion.z2.commons.i18n

import hu.simplexion.z2.commons.util.UUID
import kotlin.reflect.KProperty

/**
 * A globally unique identification for some displayed textual information. When fix
 * textual information such as field names, button labels is displayed on the screen
 * the UI element uses a token to identify the information.
 *
 * @param      T          The data type this token belongs to. Typically `String`.
 * @property   uuid       The uuid that belongs to the token. The compiler generates an
 *                        error when the value is not a constant string.
 *
 * @property   fallback   A fallback value to use when the token has no customizations.
 */
class Token<T>(
    val uuid : UUID<Token<T>>,
    val fallback : T
) : LocalizedText, LocalizedIcon {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return fallback
    }

    override fun toString(): String = fallback.toString()

}