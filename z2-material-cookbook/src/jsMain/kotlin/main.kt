import hu.simplexion.z2.browser.css.*
import hu.simplexion.z2.browser.demo.calendar.calendarDemo
import hu.simplexion.z2.browser.demo.layout.containerDemo
import hu.simplexion.z2.browser.demo.material.*
import hu.simplexion.z2.browser.demo.pages.*
import hu.simplexion.z2.browser.demo.strings
import hu.simplexion.z2.browser.demo.table.icons
import hu.simplexion.z2.browser.demo.table.tableDemo
import hu.simplexion.z2.browser.html.*
import hu.simplexion.z2.browser.layout.Content
import hu.simplexion.z2.browser.material.basicIcons
import hu.simplexion.z2.browser.material.basicStrings
import hu.simplexion.z2.browser.material.button.iconButton
import hu.simplexion.z2.browser.material.navigation.navigationDrawer
import hu.simplexion.z2.browser.material.px
import hu.simplexion.z2.browser.routing.BrowserRouter
import hu.simplexion.z2.browser.routing.RoutedRenderer
import hu.simplexion.z2.browser.routing.Router
import hu.simplexion.z2.commons.i18n.LocalizedIcon
import hu.simplexion.z2.commons.i18n.LocalizedText

fun main() {
    mainRouter.receiver = Content
    mainRouter.start()
}

@Suppress("unused")
object mainRouter : BrowserRouter() {
    override val label = strings.main

    // @formatter:off
    val components       by componentRouter
    val pages            by pagesRouter
    // @formatter:on

    override fun notFound(receiver: Z2, path: List<String>) {
        receiver.defaultLayout(this, { navigationDrawer(targets) }) { }
    }
}

@Suppress("unused")
object componentRouter : Router<Z2>() {
    override val label = strings.components
    // @formatter:off
    val button           by render(strings.button)           { buttonDemo() }
    val calendar         by render(strings.calendar)         { calendarDemo() }
    val card             by render(strings.card)             { cardDemo() }
    val container        by render(strings.container)        { containerDemo() }
    val menu             by render(strings.menu)             { menuDemo() }
    val modal            by render(strings.modal)            { modalDemo() }
    val navigationDrawer by render(strings.navigationDrawer) { navigationDrawerDemo() }
    val popup            by render(strings.popup)            { popupDemo() }
    val snackbar         by render(strings.snackbar)         { snackbarDemo() }
    val switch           by render(strings.switch)           { switchDemo() }
    val textField        by render(strings.textField)        { textFieldDemo() }
    val table            by render(strings.table)            { tableDemo() }
    // @formatter:on

    val nav : Z2.() -> Unit = { navigationDrawer(targets) }

    override fun render(label: LocalizedText?, icon: LocalizedIcon?, renderFun: Z2.() -> Unit): RoutedRenderer<Z2> {
        return super.render(label, icon) { defaultLayout(this@componentRouter, nav, renderFun) }
    }
    override fun default(receiver: Z2, path: List<String>) {
        receiver.defaultLayout(this, nav) {  }
    }
}

@Suppress("unused")
object pagesRouter : Router<Z2>() {
    override val label = strings.pages
    // @formatter:off
    val login          by render(loginStrings.login)          { loginDemo() }
    val administration by render(adminStrings.administration) { adminDemo() }
    val account        by render(accountStrings.account)      { accountDemo() }
    // @formatter:on

    val nav : Z2.() -> Unit = { navigationDrawer(targets) }

    override fun render(label: LocalizedText?, icon: LocalizedIcon?, renderFun: Z2.() -> Unit): RoutedRenderer<Z2> {
        return super.render(label, icon) { defaultLayout(this@pagesRouter, nav, renderFun) }
    }
    override fun default(receiver: Z2, path: List<String>) {
        receiver.defaultLayout(this, nav) {  }
    }
}

fun Z2.defaultLayout(router: Router<Z2>, nav: Z2.() -> Unit, content: Z2.() -> Unit) {
        grid(wFull, hFull) {
            gridTemplateRows = "1fr"
            gridTemplateColumns = "min-content 1fr"
            gridGap = 16.px
            grid {
                gridTemplateRows = "min-content 1fr"
                gridTemplateColumns = "1fr"
                div(displayFlex, alignItemsCenter, h60, pl8) {
                    if (router != mainRouter) {
                        iconButton(basicIcons.back, basicStrings.back) { router.up() }
                    } else {
                        iconButton(icons.favourites, strings.favourites) {  }
                    }
                    text { router.label }
                }
                nav()
            }
            content()
        }
    }
