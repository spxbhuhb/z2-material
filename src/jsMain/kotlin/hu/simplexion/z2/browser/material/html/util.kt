package hu.simplexion.z2.browser.material.html

import org.w3c.dom.NodeList
import org.w3c.dom.get

fun NodeList.forEach(func : Z2.() -> Unit) {
    val size = this.length
    for (i in 0 until size) {
        (this[i] as? Z2)?.func()
    }
}