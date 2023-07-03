package hu.simplexion.z2.commons.i18n

import hu.simplexion.z2.commons.util.UUID

fun token() = Token(UUID(), "")

fun String.asToken() = Token(UUID(), this)

fun String.asToken(uuid : String) = Token(UUID(uuid), this)