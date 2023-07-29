package hu.simplexion.z2.browser.material.demo.components

import hu.simplexion.z2.browser.html.*
import hu.simplexion.z2.browser.material.card.filledCard
import hu.simplexion.z2.browser.material.card.outlinedCard
import hu.simplexion.z2.browser.material.demo.strings
import hu.simplexion.z2.browser.material.px

fun Z2.cardDemo() {
    grid {
        gridTemplateColumns = 400.px
        gridAutoRows = "min-content"
        gridGap = 16.px

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