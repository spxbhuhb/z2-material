package hu.simplexion.z2.browser.material.textfield

import hu.simplexion.z2.browser.material.ComponentState
import hu.simplexion.z2.browser.material.html.*
import hu.simplexion.z2.browser.material.icon.icon
import hu.simplexion.z2.commons.i18n.LocalizedIcon
import hu.simplexion.z2.commons.i18n.LocalizedText
import kotlinx.browser.document
import kotlinx.dom.addClass
import kotlinx.dom.clear
import kotlinx.dom.removeClass
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.KeyboardEvent

class TextField(
    val value: String,
    val label: LocalizedText? = null,
    val supportingText: LocalizedText? = null,
    val filled: Boolean = false,
    val outlined: Boolean = false,
    val leadingIcon: LocalizedIcon? = null,
    val trailingIcon: LocalizedIcon? = null,
    val errorIcon: LocalizedIcon? = null,
    var state: ComponentState = ComponentState.Enabled,
    error : Boolean = false,
    val onChange: TextField.(value: String) -> Unit
) {

    lateinit var element: Z2
    lateinit var main: Z2
    lateinit var content: Z2
    lateinit var leading: Z2
    lateinit var trailing: Z2
    lateinit var labelOuter: Z2
    lateinit var labelInner: Z2
    lateinit var support: Z2

    var error: Boolean = error
        set(value) {
            field = value
            setState(state)
        }

    val input = document.createElement("input") as HTMLInputElement

    var beforeEditValue = value

    fun Z2.main(): Z2 =
        div("text-field") {
            element = this

            if (outlined) labelOutlined()

            div(*classes()) {
                main = this
                gridRow = "1"
                gridColumn = "1"
                leadingIcon()
                div("align-self-center") {
                    content = this
                    if (filled) labelFilled()
                    input()
                }
                trailingIcon()
            }

            supportingText()

            on("mousedown") {
                if (it.target != input) {
                    input.focus()
                    it.preventDefault()
                }
            }

            setState(state)
        }

    fun leave() {
        if (beforeEditValue != input.value) {
            beforeEditValue = input.value
            onChange(input.value)
        }
    }

    fun classes(): Array<String> {
        val classes = mutableListOf("text-field-main")
        if (value.isEmpty()) classes += "empty"
        if (filled) classes += "filled"
        if (outlined) classes += "outlined"
        return classes.toTypedArray()
    }

    fun Z2.labelFilled() =
        div {
            labelOuter = this
            addClass("text-field-label-filled", "body-small")
            if (value.isEmpty()) addClass("hidden")
            text { label }
        }

    fun Z2.labelOutlined() =
        div("text-field-label-outer") {
            labelOuter = this

            div("text-field-top-left-corner") {}

            div("text-field-label-inner", "body-small") {
                labelInner = this
                if (value.isNotEmpty()) labelOutlinedContent()
            }

            div("text-field-top-right-corner") {}
        }

    fun Z2.labelOutlinedContent() {
        clear()
        div("text-field-label-outlined-content") { text { label } }
    }

    fun Z2.leadingIcon(): Z2 =
        div("text-field-leading-icon") {
            leading = this
            if (leadingIcon != null) {
                icon(leadingIcon)
            }
        }

    fun Z2.input() {
        val parent = if (filled) div { labelFilled() } else this

        parent.appendChild(input)

        input.addClass("text-field-input", "body-large")
        input.value = value
        label?.let { input.placeholder = it.toString() }

        input.on("mousedown") {
            if (input.readOnly) it.preventDefault()
        }

        input.on("focus") {
            setState(ComponentState.Focused)
            when {
                filled -> labelOuter.removeClass("hidden")
                outlined -> labelInner.labelOutlinedContent()
            }
        }

        input.on("blur") {
            if (input.value.isEmpty()) {
                when {
                    filled -> labelOuter.addClass("hidden")
                    outlined -> labelInner.innerText = ""
                }
            }
            leave()
            setState(ComponentState.Enabled)
        }

        input.on("keydown") { event ->
            event as KeyboardEvent
            when (event.key) {
                "Enter" -> leave()
                "Escape" -> input.value = beforeEditValue
            }
        }
    }

    fun Z2.trailingIcon(): Z2 =
        div("text-field-trailing-icon") {
            trailing = this
            trailingState()
        }

    fun trailingState() {
        trailing.clear()
        if (error) {
            trailingError()
        } else {
            trailingNormal()
        }
    }

    fun trailingError() {
        with(trailing) {
            if (errorIcon != null) {
                icon(errorIcon, fill = 1)
            }
        }
    }

    fun trailingNormal() {
        with(trailing) {
            if (trailingIcon != null) {
                icon(trailingIcon)
            }
        }
    }

    fun Z2.supportingText() =
        div("text-field-support", "body-small") {
            support = this
            text { supportingText }
        }

    fun setState(newState: ComponentState) {
        val oldClass = state.fieldClass
        val newClass = newState.fieldClass
        state = newState

        element.querySelectorAll("*").forEach {
            removeClass(oldClass)
            addClass(newClass)
            if (error) {
                addClass("field-error")
            } else {
                removeClass("field-error")
            }
        }

        trailingState()
    }

    val ComponentState.fieldClass
        get() = this.name.lowercase()
}