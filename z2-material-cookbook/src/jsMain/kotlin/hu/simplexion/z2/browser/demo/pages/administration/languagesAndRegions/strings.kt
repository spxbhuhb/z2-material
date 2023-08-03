package hu.simplexion.z2.browser.demo.pages.administration.languagesAndRegions

import hu.simplexion.z2.commons.i18n.LocalizedTextStore
import hu.simplexion.z2.commons.util.UUID

internal object strings : LocalizedTextStore(UUID("a5ce25ef-9467-4df5-841e-8945808a7cfd")) {

    val languagesAndRegions by "Languages & Regions"
    val languagesAndRegionSupport by languagesAndRegions.support("Register new language and region settings. Translate user interface labels and icons.")

    val name by "Name"
    val nativeName by "Native Name"
    val actions by "Actions"

}
