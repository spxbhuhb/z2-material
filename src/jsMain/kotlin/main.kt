import hu.simplexion.z2.browser.material.ComponentState
import hu.simplexion.z2.browser.material.basicIcons
import hu.simplexion.z2.browser.material.button.*
import hu.simplexion.z2.browser.material.calendar.year
import hu.simplexion.z2.browser.material.card.filledCard
import hu.simplexion.z2.browser.material.card.outlinedCard
import hu.simplexion.z2.browser.material.html.*
import hu.simplexion.z2.browser.material.icon.actionIcon
import hu.simplexion.z2.browser.material.layout.defaultLayout
import hu.simplexion.z2.browser.material.layout.defaultLayoutContent
import hu.simplexion.z2.browser.material.layout.defaultLayoutHeader
import hu.simplexion.z2.browser.material.menu.More.Companion.more
import hu.simplexion.z2.browser.material.menu.menuItem
import hu.simplexion.z2.browser.material.modal.confirm
import hu.simplexion.z2.browser.material.navigation.NavigationItem
import hu.simplexion.z2.browser.material.navigation.drawerItem
import hu.simplexion.z2.browser.material.navigation.navigationDrawer
import hu.simplexion.z2.browser.material.popup.Popup
import hu.simplexion.z2.browser.material.popup.Popup.Companion.popup
import hu.simplexion.z2.browser.material.snackbar.Snackbar.Companion.snackbar
import hu.simplexion.z2.browser.material.textfield.filledTextField
import hu.simplexion.z2.browser.material.textfield.outlinedTextField
import hu.simplexion.z2.commons.i18n.LocalizedText
import hu.simplexion.z2.commons.i18n.asToken
import kotlinx.browser.document
import kotlinx.datetime.DayOfWeek
import kotlinx.dom.addClass
import kotlinx.dom.clear

object strings {
    val button = "Button".asToken()
    val filledButton = "Filled Button".asToken()
    val calendar = "Calendar".asToken()
    val textButton = "Text Button".asToken()
    val smallDenseTextButton = "Small Dense Text Button".asToken()
    val settings = "Settings".asToken()
    val segment1 = "Segment 1".asToken()
    val segment2 = "Segment 2".asToken()
    val segment3 = "Segment 3".asToken()
    val textField = "Text Field".asToken()
    val label = "Label".asToken()
    val supportingText = "Supporting Text".asToken()
    val modal = "Modal".asToken()
    val confirmDialog = "Confirm Dialog".asToken()
    val confirmMessage = "Nothing happens, whichever button you click.".asToken()
    val card = "Card".asToken()
    val loremShort = "Lorem ipsum dolor sit amet, consectetur adipiscing elit".asToken()
    val lorem = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.".asToken()
    val headline = "Headline".asToken()
    val popup = "Popup".asToken()
    val menu = "Menu".asToken()
    val menuItem1 = "Menu Item 1".asToken()
    val menuItem2 = "Menu Item 2".asToken()
    val menuItem3 = "Menu Item 3".asToken()
    val snackbar = "Snackbar".asToken()
    val click = "Click".asToken()
}

val content = (document.createElement("div") as Z2).also {
    document.body!!.appendChild(it)
    it.style.height = "100vh"
    it.style.width = "100vw"
    it.style.padding = "0"
    it.style.margin = "0"
}

private fun render(item: NavigationItem) {
    with(content) {
        this.clear()
        defaultLayout {
            nav().apply { gridRow = "1/span2" }
            defaultLayoutHeader {

            } // top app bar
            defaultLayoutContent {
                content(item)
            }
        }
    }
}

private fun Z2.nav() =
    navigationDrawer {
        nav(strings.button)
        nav(strings.calendar)
        nav(strings.card)
        nav(strings.menu)
        nav(strings.modal)
        nav(strings.popup)
        nav(strings.snackbar)
        nav(strings.textField)
    }

private fun Z2.content(item: NavigationItem): Z2 =
    when (item.label) {
        strings.button -> div { buttonDemo() }
        strings.calendar -> year(2023, DayOfWeek.MONDAY)
        strings.card -> div { cardDemo() }
        strings.menu -> div { menuDemo() }
        strings.modal -> div { modalDemo() }
        strings.popup -> div { popupDemo() }
        strings.snackbar -> div { snackbarDemo() }
        strings.textField -> div { textFieldDemo() }
        else -> div { text { "TODO" } }
    }

private fun Z2.nav(label: LocalizedText) =
    drawerItem(NavigationItem(null, label) { render(it) })

fun main() {
    render(NavigationItem(null, strings.textField))
}

private fun Z2.buttonDemo() {
    grid {
        gridTemplateColumns = "min-content"
        gridAutoRows = "min-content"
        gridGap = "16px"

        filledButton(strings.filledButton) { snackbar(strings.filledButton) }
        textButton(strings.textButton) { snackbar(strings.textButton) }
        smallDenseTextButton(strings.smallDenseTextButton) { snackbar(strings.smallDenseTextButton) }
        iconButton(basicIcons.settings, strings.settings) { snackbar(strings.settings) }
        segmentedButton(
            strings.segment1 to false,
            strings.segment2 to true,
            strings.segment3 to false
        ) { snackbar("${strings.click}: $it")}
    }
}

private fun Z2.textFieldDemo() {
    grid {
        gridTemplateColumns = "300px 300px"
        gridAutoRows = "min-content"
        gridGap = "16px"

        textFieldDemo(ComponentState.Enabled, false)
        textFieldDemo(ComponentState.Enabled, true)
    }
}

private fun Z2.textFieldDemo(state: ComponentState, error: Boolean) {
    filledTextField("", strings.label, state = state, error = error) { this.error = it.isBlank() }
    outlinedTextField("", strings.label, state = state, error = error) { this.error = it.isBlank() }

    filledTextField("", strings.label, leadingIcon = basicIcons.search, trailingIcon = basicIcons.cancel, state = state, error = error) { this.error = it.isBlank() }
    outlinedTextField("", strings.label, leadingIcon = basicIcons.search, trailingIcon = basicIcons.cancel, state = state, error = error) { this.error = it.isBlank() }

    filledTextField("", strings.label, strings.supportingText, state = state, error = error) { this.error = it.isBlank() }
    outlinedTextField("", strings.label, strings.supportingText, state = state, error = error) { this.error = it.isBlank() }
}

private fun Z2.modalDemo() {
    textButton(strings.confirmDialog) { confirm(strings.confirmDialog, strings.confirmMessage) { } }
}

private fun Z2.cardDemo() {
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

private fun Z2.popupDemo() {
    grid("400px", gap = 16) {
        gridAutoRows = "min-content"

        div {
            var popup: Popup? = null
            textButton(strings.popup) {
                popup?.toggle()
                it.preventDefault()
                it.stopPropagation() // this is necessary for buttons
            }.apply {
                addClass("position-relative", "popup-parent")
                popup = popup { filledCard { text { strings.loremShort } } }
            }
        }

        div {
            var popup: Popup? = null
            actionIcon(basicIcons.settings) {
                popup?.toggle()
            }.apply {
                addClass("position-relative", "popup-parent")
                popup = popup { filledCard { text { strings.loremShort } } }
            }
        }
    }
}

private fun Z2.menuDemo() {
    grid("max-content min-content", gap = 16) {
        text { "Menu with 1 item, normal icon" }
        more {
            menuItem(1, basicIcons.search, strings.menuItem1) { }
        }
    }

    grid("max-content min-content", gap = 16) {
        text { "Menu with 2 items, inline icon" }
        more(inline = true) {
            menuItem(1, basicIcons.search, strings.menuItem1) { }
            menuItem(2, basicIcons.search, strings.menuItem2) { }
        }
    }

}

var snackbarClick = 0

private fun Z2.snackbarDemo() {
    grid("400px", gap = 16) {
        gridAutoRows = "min-content"

        textButton(strings.snackbar) { snackbar("${strings.snackbar} ${snackbarClick++}") }
    }
}