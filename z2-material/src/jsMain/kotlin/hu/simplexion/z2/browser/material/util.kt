package hu.simplexion.z2.browser.material

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
fun io(func: suspend () -> Unit) = GlobalScope.launch {
    try {
        func()
    } catch (ex: Throwable) {
        // TODO add a function to Application to channel all errors into one place, notify the user, upload the error report, etc.
        ex.printStackTrace()
    }
}

