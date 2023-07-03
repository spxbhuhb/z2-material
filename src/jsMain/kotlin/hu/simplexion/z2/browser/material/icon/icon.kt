package hu.simplexion.z2.browser.material.icon

import hu.simplexion.z2.browser.material.html.Z2
import hu.simplexion.z2.browser.material.html.div
import hu.simplexion.z2.browser.material.html.text
import hu.simplexion.z2.commons.i18n.Token
import kotlinx.dom.addClass
import org.w3c.dom.HTMLDivElement

fun Z2.icon(
    icon: Token<String>,
    size: Int = 24,
    weight : Int = 400,
    fill : Int = 0,
    pointer : Boolean = true,
    cssClass: String? = null
) : HTMLDivElement =
    div {
        cssClass?.let { addClass(it) }

        addClass("icon-$size")
        if (pointer) style.cursor = "pointer"

        innerHTML = """<span class="material-symbols-rounded symbols-$weight-$size-$fill">${icon.fallback}</span>"""

        addEventListener("mousedown", { it.preventDefault() }) // to avoid focus
    }

fun Z2.actionIcon(
    icon: Token<String>,
    hint: Token<String>? = null,
    size: Int = 24,
    weight : Int = 400,
    fill : Int = 0,
    cssClass: String? = null,
    inline : Boolean = false,
    onClick: () -> Unit
) : HTMLDivElement =
    div {
        cssClass?.let { addClass(it) }

        addClass("icon-$size", if (inline) "inline-action-icon" else "action-icon")

        innerHTML = """<span class="material-symbols-rounded symbols-$weight-$size-$fill">${icon.fallback}</span>"""

        if (hint != null) {
            addClass("tooltip")
            div("plain-tooltip", "body-small") { text { hint } }
        }

        addEventListener("mousedown", { it.preventDefault() }) // to avoid focus
        addEventListener("click", { onClick() })
    }

fun Z2.inlineActionIcon(
    icon: Token<String>,
    hint: Token<String>? = null,
    size: Int = 24,
    weight : Int = 400,
    fill : Int = 0,
    cssClass: String? = null,
    onClick: () -> Unit
) : HTMLDivElement =
    actionIcon(icon, hint, size, weight, fill, cssClass, true, onClick)