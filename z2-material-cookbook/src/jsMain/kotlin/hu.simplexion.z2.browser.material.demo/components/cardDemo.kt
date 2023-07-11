package hu.simplexion.z2.browser.material.demo.components

import hu.simplexion.z2.browser.material.card.filledCard
import hu.simplexion.z2.browser.material.card.outlinedCard
import hu.simplexion.z2.browser.material.demo.strings
import hu.simplexion.z2.browser.material.html.Z2
import hu.simplexion.z2.browser.material.html.grid
import hu.simplexion.z2.browser.material.html.gridAutoRows
import hu.simplexion.z2.browser.material.html.text

fun Z2.cardDemo() {
    grid("400px", gap = 16) {
        gridAutoRows = "min-content"

        filledCard { text { strings.loremShort } }
        filledCard { text { strings.lorem } }
        filledCard(strings.headline) { text { strings.loremShort } }
        filledCard(strings.headline) { text { strings.lorem } }

        outlinedCard { text { strings.loremShort } }
        outlinedCard { text { strings.lorem } }
        outlinedCard(strings.headline) { text { strings.loremShort } }
        outlinedCard(strings.headline) { text { strings.lorem } }
    }
}