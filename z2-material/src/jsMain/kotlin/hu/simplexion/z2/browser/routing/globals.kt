package hu.simplexion.z2.browser.routing

import hu.simplexion.z2.commons.util.UUID

val routing = BrowserRouting()

inline fun <reified T> Map<String, String>.uuid() = UUID<T>(requireNotNull(this["uuid"]))