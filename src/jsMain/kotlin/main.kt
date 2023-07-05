import hu.simplexion.z2.browser.material.ComponentState
import hu.simplexion.z2.browser.material.basicIcons
import hu.simplexion.z2.browser.material.button.*
import hu.simplexion.z2.browser.material.calendar.year
import hu.simplexion.z2.browser.material.html.*
import hu.simplexion.z2.browser.material.layout.defaultLayout
import hu.simplexion.z2.browser.material.layout.defaultLayoutContent
import hu.simplexion.z2.browser.material.layout.defaultLayoutHeader
import hu.simplexion.z2.browser.material.modal.confirm
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
    val modal = "Modal".asToken()
    val confirmDialog = "Confirm Dialog".asToken()
    val confirmMessage = "Nothing happens, whichever button you click.".asToken()
}

val content = (document.createElement("div") as Z2).also {
    document.body!!.appendChild(it)
    it.style.height = "100vh"
    it.style.width = "100vw"
    it.style.padding = "0"
    it.style.margin = "0"
}

fun render(item: NavigationItem) {
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

fun Z2.nav() =
    navigationDrawer {
        nav(strings.button)
        nav(strings.calendar)
        nav(strings.textField)
        nav(strings.modal)
    }

fun Z2.content(item: NavigationItem): Z2 =
    when (item.label) {
        strings.button -> div { button() }
        strings.calendar -> year(2023, DayOfWeek.MONDAY)
        strings.textField -> div { textField() }
        strings.modal -> div { modal() }
        else -> div { text { "TODO" } }
    }


fun Z2.nav(label: LocalizedText) =
    drawerItem(NavigationItem(null, label) { render(it) })

fun main() {
    render(NavigationItem(null, strings.textField))
}

fun Z2.button() {
    grid {
        gridTemplateColumns = "min-content"
        gridAutoRows = "min-content"
        gridGap = "16px"

        filledButton(strings.filledButton)
        textButton(strings.textButton) { }
        smallDenseTextButton(strings.smallDenseTextButton) { }
        iconButton(basicIcons.settings, strings.settings) { }
        segmentedButton(
            strings.segment1 to false,
            strings.segment2 to true,
            strings.segment3 to false
        ) { }
    }
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
    filledTextField("", strings.label, state = state, error = error) { this.error = it.isBlank() }
    outlinedTextField("", strings.label, state = state, error = error) { this.error = it.isBlank() }

    filledTextField("", strings.label, leadingIcon = basicIcons.search, trailingIcon = basicIcons.cancel, state = state, error = error) { this.error = it.isBlank() }
    outlinedTextField("", strings.label, leadingIcon = basicIcons.search, trailingIcon = basicIcons.cancel, state = state, error = error) { this.error = it.isBlank() }

    filledTextField("", strings.label, strings.supportingText, state = state, error = error) { this.error = it.isBlank() }
    outlinedTextField("", strings.label, strings.supportingText, state = state, error = error) { this.error = it.isBlank() }
}

fun Z2.modal() {
    textButton(strings.confirmDialog) { confirm(strings.confirmDialog, strings.confirmMessage) {  } }
}