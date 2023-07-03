package hu.simplexion.z2.browser.material.navigation

import hu.simplexion.z2.commons.i18n.Token

open class NavigationItem(
    val icon: Token<String>? = null,
    val label: Token<String>? = null,
    val smallBadge: Boolean = false,
    val largeBadge: Boolean = false,
    val badgeLabel: String? = null,
    var active: Boolean = true,
    val onClick: ((item : NavigationItem) -> Unit)? = null
)