import hu.simplexion.z2.browser.material.ComponentState
import hu.simplexion.z2.browser.material.button.*
import hu.simplexion.z2.browser.material.calendar.year
import hu.simplexion.z2.browser.material.html.*
import hu.simplexion.z2.browser.material.layout.defaultLayout
import hu.simplexion.z2.browser.material.layout.defaultLayoutContent
import hu.simplexion.z2.browser.material.layout.defaultLayoutHeader
import hu.simplexion.z2.browser.material.navigation.NavigationItem
import hu.simplexion.z2.browser.material.navigation.drawerItem
import hu.simplexion.z2.browser.material.navigation.navigationDrawer
import hu.simplexion.z2.browser.material.textfield.filledTextField
import hu.simplexion.z2.browser.material.textfield.outlinedTextField
import hu.simplexion.z2.commons.i18n.LocalizedText
import hu.simplexion.z2.commons.i18n.asToken
import kotlinx.browser.document
import kotlinx.datetime.DayOfWeek
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
}

object icons {
    val settings = "settings".asToken()
    val search = "search".asToken()
    val cancel = "cancel".asToken()
    val error = "error".asToken()
}

fun render(item: NavigationItem) {
    with(document.body!!) {
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

fun Z2.nav() =
    navigationDrawer {
        nav(strings.button)
        nav(strings.calendar)
        nav(strings.textField)
    }

fun Z2.content(item: NavigationItem): Z2 =
    when (item.label) {
        strings.button -> div { button() }
        strings.calendar -> year(2023, DayOfWeek.MONDAY)
        strings.textField -> div { textField() }
        else -> div { text { "TODO" } }
    }


fun Z2.nav(label: LocalizedText) =
    drawerItem(NavigationItem(null, label) { render(it) })

fun main() {
    render(NavigationItem(null, strings.textField))
}

fun Z2.button() {
    filledButton(strings.filledButton)
    textButton(strings.textButton) { }
    smallDenseTextButton(strings.smallDenseTextButton) { }
    iconButton(icons.settings, strings.settings) { }
    segmentedButton(
        strings.segment1 to false,
        strings.segment2 to true,
        strings.segment3 to false
    ) { }
}

fun Z2.textField() {
    grid {
        gridTemplateColumns = "300px 300px"
        gridAutoRows = "min-content"
        gridGap = "16px"

        textField(ComponentState.Enabled, false)
        textField(ComponentState.Enabled, true)
    }
}

fun Z2.textField(state: ComponentState, error: Boolean) {
    filledTextField("", strings.label, errorIcon = icons.error, state = state, error = error) { this.error = it.isBlank() }
    outlinedTextField("", strings.label, errorIcon = icons.error, state = state, error = error) { this.error = it.isBlank() }

    filledTextField("", strings.label, leadingIcon = icons.search, trailingIcon = icons.cancel, errorIcon = icons.error, state = state, error = error) { this.error = it.isBlank() }
    outlinedTextField("", strings.label, leadingIcon = icons.search, trailingIcon = icons.cancel, errorIcon = icons.error, state = state, error = error) { this.error = it.isBlank() }

    filledTextField("", strings.label, strings.supportingText, errorIcon = icons.error, state = state, error = error) { this.error = it.isBlank() }
    outlinedTextField("", strings.label, strings.supportingText, errorIcon = icons.error, state = state, error = error) { this.error = it.isBlank() }
}