package hu.simplexion.z2.browser.material.form

import hu.simplexion.z2.browser.material.html.Z2
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty

fun Z2.field(context: FormContext): FormField {
    return FormField()
}

fun Z2.field(context: FormContext, accessor: Z2.() -> Any) {
    val value = accessor()
    when (value) {
        is KMutableProperty<*> -> mutablePropertyField(value)
        is KProperty<*> -> propertyField(value)
        else -> valueField(value)
    }
}

private fun Z2.mutablePropertyField(value: KMutableProperty<*>) {

}

private fun Z2.propertyField(value: KProperty<*>) {

}

private fun Z2.valueField(value: Any) {

}

class A(
    val s: String,
    val i: Int
)

fun Z2.formDemo() {
    val a = A("a", 1)

    val ctx = FormContext()

    field(ctx) .. a::s
    field(ctx) .. a::i
}
