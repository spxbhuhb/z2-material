package hu.simplexion.z2.browser.material.card

import hu.simplexion.z2.browser.material.html.Z2
import hu.simplexion.z2.browser.material.html.div
import hu.simplexion.z2.browser.material.html.text
import hu.simplexion.z2.commons.i18n.LocalizedText
import org.w3c.dom.HTMLDivElement

fun Z2.elevatedCard(
    headline: LocalizedText? = null,
    actions: (Z2.() -> Unit)? = null,
    builder: Z2.() -> Unit
): HTMLDivElement =

    div("elevated-card-container") {
        if (headline != null) cardHeadline(headline, actions)
        builder()
    }

fun Z2.filledCard(
    headline: LocalizedText? = null,
    actions: (Z2.() -> Unit)? = null,
    builder: Z2.() -> Unit
): HTMLDivElement =

    div("filled-card-container") {
        if (headline != null) cardHeadline(headline, actions)
        builder()
    }

fun Z2.outlinedCard(
    headline: LocalizedText? = null,
    actions: (Z2.() -> Unit)? = null,
    builder: Z2.() -> Unit
): HTMLDivElement =

    div("outlined-card-container") {
        if (headline != null) cardHeadline(headline, actions)
        builder()
    }


fun Z2.cardHeadline(
    headline: LocalizedText? = null,
    actions: (Z2.() -> Unit)? = null,
): Z2 =
    div("card-headline", "title-large") {

        div("align-self-center") {
            text { headline }
        }

        actions?.let { it() }
    }
