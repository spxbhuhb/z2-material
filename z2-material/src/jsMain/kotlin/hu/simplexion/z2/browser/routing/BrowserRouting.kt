/*
 * Copyright © 2020-2023, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
package hu.simplexion.z2.browser.routing

import hu.simplexion.z2.browser.html.Z2
import hu.simplexion.z2.browser.layout.Content
import hu.simplexion.z2.browser.material.basicStrings
import hu.simplexion.z2.browser.material.modal.confirm
import hu.simplexion.z2.browser.util.decodeURIComponent
import hu.simplexion.z2.browser.util.io
import hu.simplexion.z2.browser.util.keys
import kotlinx.browser.window
import org.w3c.dom.events.Event
import org.w3c.dom.get
import org.w3c.dom.set
import org.w3c.dom.url.URLSearchParams

class BrowserRouting(
    var homePath: String = "/",
    var receiver: Z2 = Content
) {
    val route = Route("") { }

    val lastShownKey = "z2-nav-last-shown"

    var pendingModificationsEnabled = false
    var pendingModifications = false

    fun start() {
        window.addEventListener("popstate", ::onPopState)

        val current = window.history.state?.toString() ?: ""
        if (current.isEmpty()) {
            window.history.replaceState(incrementNavCounter(), "")
        } else {
            window.sessionStorage[lastShownKey] = current
        }

        val path = decodeURIComponent(window.location.pathname)

        open(path, window.location.search, window.location.hash)
    }

    fun home() {
        open(homePath, "", "")
    }

    fun open(route: Route?) {
        checkNotNull(route) { "trying to open a null route" }
        open(route.text)
    }

    fun open(pathname: String, search: String = "", hash: String = "") {
        io {
            if (stopNavigationOnPending()) return@io

            val newPath = if (!pathname.startsWith('/')) {
                window.location.pathname.substringBeforeLast('/') + pathname
            } else {
                pathname
            }

            var url = if (hash.isEmpty()) newPath else "$newPath#$hash"
            url = if (search.isEmpty()) url else "$url?$search"

            window.history.pushState(incrementNavCounter(), "", url)

            val segments = newPath.removePrefix("/").split('/')
            val parameters = mutableMapOf("#" to hash)

            val searchParams = URLSearchParams(search)
            for (key in searchParams.keys()) {
                searchParams.get(key)?.let { parameters.put(key, it) }
            }

            receiver.clear()


        }
    }

    /**
     * Called when:
     *  - the user clicks on the "Back" button
     *  - the user clicks on the "Forward" button
     *  - the [back] function is called
     *  - direct call to window.history.back
     *  - direct call to window.history.forward
     */
    fun onPopState(event: Event) {
        io {
            if (stopNavigationOnPending()) return@io

            val current = (window.history.state as? Int) ?: 0
            window.sessionStorage[lastShownKey] = current.toString()

//            val last = window.sessionStorage[lastShownKey]?.toInt() ?: 0
//            val backward = last > current

            val path = decodeURIComponent(window.location.pathname)

            open(path, window.location.search, window.location.hash)
        }
    }

    suspend fun stopNavigationOnPending(): Boolean {
        if (pendingModificationsEnabled && pendingModifications) {
            if (!confirm(basicStrings.discardChanges, basicStrings.discardChangesMessage)) {
                return true
            } else {
                pendingModifications = false
            }
        }
        return false
    }

    fun incrementNavCounter(): Int {
        val navCounter = (window.sessionStorage["zk-nav-counter"]?.toInt() ?: window.history.length) + 1
        window.sessionStorage["zk-nav-counter"] = navCounter.toString()
        window.sessionStorage["zk-nav-last-shown"] = navCounter.toString()
        return navCounter
    }

    fun replace(
        path: String = decodeURIComponent(window.location.pathname),
        query: String = window.location.search,
        hash: String = window.location.hash
    ) {
        var url = if (hash.isEmpty()) path else "$path#$hash"
        url = if (query.isEmpty()) url else "$url?$query"
        window.history.replaceState(window.history.state, "", url)
    }

    fun forward() = window.history.forward()

    fun back() = window.history.back()

}