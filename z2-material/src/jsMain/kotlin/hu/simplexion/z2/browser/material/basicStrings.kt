package hu.simplexion.z2.browser.material

import hu.simplexion.z2.commons.i18n.LocalizedTextStore
import hu.simplexion.z2.commons.util.UUID

@Suppress("ClassName")
object basicStrings : LocalizedTextStore(UUID("2c9624be-efc7-499d-a5e3-6ca5b3da2ff5")){
    val yes by "yes"
    val no by "no"
    val cancel by "cancel"
    val open by "Open"
    val discardChanges by "Discard changes?"
    val discardChangesMessage by "To save the changes click on cancel and save the changes before leaving the page."
    val searchHint by "type here to search"
    val add by "Add"
    val export by "Export"
}