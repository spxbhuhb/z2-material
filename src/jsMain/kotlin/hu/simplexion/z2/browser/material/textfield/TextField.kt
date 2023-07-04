package hu.simplexion.z2.browser.material.textfield

import hu.simplexion.z2.browser.material.html.Z2
import hu.simplexion.z2.browser.material.html.div
import hu.simplexion.z2.browser.material.html.on
import hu.simplexion.z2.browser.material.icon.icon
import hu.simplexion.z2.commons.i18n.LocalizedIcon
import hu.simplexion.z2.commons.i18n.LocalizedText
import kotlinx.browser.document
import kotlinx.dom.addClass
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.KeyboardEvent

class TextField(
    val value: String,
    val leadingIcon: LocalizedIcon? = null,
    val trailingIcon: LocalizedIcon? = null,
    val placeholder: LocalizedText? = null,
    val supportingText: LocalizedText? = null,
    val filled: Boolean = false,
    val outlined: Boolean = false,
    val onChange: (value: String) -> Unit
) {

    val element = document.createElement("div") as HTMLDivElement
    val input = document.createElement("input") as HTMLInputElement

    var beforeEditValue = value

    fun Z2.main(): Z2 {

        with(element) {
            addClass("text-field")

            div(*classes()) {
                leadingIcon()
                input()
                trailingIcon()
            }

            div("text-field-support") {
                supportingText()
            }
        }

        appendChild(element)
        return element
    }

    fun leave() {
        if (beforeEditValue != input.value) {
            beforeEditValue = input.value
            onChange(input.value)
        }
    }

    fun Z2.classes() : Array<String> {
        val classes = mutableListOf("text-field-main")
        if (value.isEmpty()) classes += "empty"
        if (filled) classes += "filled"
        if (outlined) classes += "outlined"
        return classes.toTypedArray()
    }

    fun Z2.leadingIcon(): Z2 =
        if (leadingIcon == null) {
            div { }
        } else {
            icon(leadingIcon).also { addClass("text-field-leading-icon") }
        }

    fun Z2.input() {
        appendChild(input)

        input.addClass("text-field-input", "body-large")
        input.value = value
        placeholder?.let { input.placeholder = it.toString() }

        input.on("mousedown") {
            if (input.readOnly) it.preventDefault()
        }

        input.on("blur") { leave() }

        input.on("keydown") { event ->
            event as KeyboardEvent
            when (event.key) {
                "Enter" -> leave()
                "Escape" -> input.value = beforeEditValue
            }
        }
    }

    fun Z2.trailingIcon(): Z2 =
        if (trailingIcon == null) {
            div { }
        } else {
            icon(trailingIcon).also { addClass("text-field-trailing-icon") }
        }

    fun Z2.supportingText() {

    }

}