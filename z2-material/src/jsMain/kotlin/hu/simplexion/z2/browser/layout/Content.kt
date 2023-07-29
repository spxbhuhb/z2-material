package hu.simplexion.z2.browser.layout

import hu.simplexion.z2.browser.html.Z2
import kotlinx.browser.document
import org.w3c.dom.HTMLDivElement

object Content : Z2(
    null,
    document.createElement("div") as HTMLDivElement,
    arrayOf("layout-content"),
    { document.body?.appendChild(this.htmlElement) }
)