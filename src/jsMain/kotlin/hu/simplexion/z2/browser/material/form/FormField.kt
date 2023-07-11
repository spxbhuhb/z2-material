package hu.simplexion.z2.browser.material.form

import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty

class FormField {

    operator fun rangeTo(property : KMutableProperty<String>) : FormField {
        return this
    }

    operator fun rangeTo(property : KMutableProperty<Int>) : FormField {
        return this
    }

    operator fun rangeTo(property : KProperty<String>) : FormField {
        return this
    }

    operator fun rangeTo(property : KProperty<Int>) : FormField {
        return this
    }

    operator fun rangeTo(value : String) : FormField {
        return this
    }

}