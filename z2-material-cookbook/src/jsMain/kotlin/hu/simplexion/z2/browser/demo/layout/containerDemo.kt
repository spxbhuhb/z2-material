package hu.simplexion.z2.browser.demo.layout

import hu.simplexion.z2.browser.html.*
import hu.simplexion.z2.browser.layout.*
import hu.simplexion.z2.browser.material.px

fun Z2.containerDemo() =
    div {
        grid {
            gridTemplateColumns = "repeat(5, 1fr)"
            gridTemplateRows = 60.px
            gridGap = 16.px

            lowest("border-primary") { text { "lowest" } }
            low("border-primary") { text { "low" } }
            container("border-primary") { text { "container" } }
            high("border-primary") { text { "high" } }
            highest("border-primary") { text { "highest" } }
        }
    }
