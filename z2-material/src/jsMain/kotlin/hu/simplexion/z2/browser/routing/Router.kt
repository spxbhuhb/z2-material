package hu.simplexion.z2.browser.routing

import hu.simplexion.z2.commons.i18n.LocalizedIcon
import hu.simplexion.z2.commons.i18n.LocalizedText
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

abstract class Router<R> : RoutingTarget<R> {

    override var parent: Router<R>? = null

    override var relativePath = ""

    override val label: LocalizedText? = null

    override val icon: LocalizedIcon? = null

    val targets = mutableListOf<RoutingTarget<R>>()

    override fun open(receiver: R, path: List<String>) {
        trace { "[routing]  OPEN  $absolutePath  remaining=${path.joinToString("/")}"}

        val segment = path.first()

        val target = targets.firstOrNull { it.relativePath == segment }

        if (target == null) {
            parent?.notFound(receiver, path) ?: notFound(receiver, path)
            return
        }

        target.open(receiver, path.drop(1))
    }

    fun render(label: LocalizedText? = null, icon: LocalizedIcon? = null, renderFun: R.() -> Unit): Renderer<R> {
        return Renderer(label, icon, renderFun)
    }

    open fun notFound(receiver: R, path: List<String>) {
        trace { "[routing]  NOTFOUND  $absolutePath  remaining=$path"}
        throw IllegalStateException("routing path not found: fullPath=${absolutePath.joinToString { ", " }}  path=${path.joinToString { "/" }}")
    }

    class RendererDelegate<R>(
        val renderer : Renderer<R>
    ) : ReadOnlyProperty<Router<R>, Renderer<R>> {
        override fun getValue(thisRef: Router<R>, property: KProperty<*>): Renderer<R>  = renderer
    }

    class RouterDelegate<R>(
        val router : Router<R>
    ) : ReadOnlyProperty<Router<R>, Router<R>> {
        override fun getValue(thisRef: Router<R>, property: KProperty<*>): Router<R> = router
    }

    operator fun Renderer<R>.provideDelegate(thisRef: Router<R>, prop: KProperty<*>): ReadOnlyProperty<Router<R>, Renderer<R>> {
        this.parent = thisRef
        this.relativePath = prop.name
        thisRef.targets += this
        return RendererDelegate(this)
    }

    operator fun Router<R>.provideDelegate(thisRef: Router<R>, prop: KProperty<*>): ReadOnlyProperty<Router<R>, Router<R>> {
        this.parent = thisRef
        this.relativePath = prop.name
        thisRef.targets += this
        return RouterDelegate(this)
    }

    fun trace(message : () -> Any?) {
        if (traceRouting) console.log(message())
    }
}
