package hu.simplexion.z2.browser.routing

interface RoutingTarget<R> {

    var parent: Router<R>?

    var relativePath : String

    fun open(receiver: R, path: List<String>)

    val absolutePath : List<String>
        get() {
            val path = mutableListOf(relativePath)
            var current = this
            while (current.parent != null) {
                current = current.parent!!
                path += current.relativePath
            }
            return path.reversed()
        }

    val root: RoutingTarget<R>
        get() {
            var current = this
            while (current.parent != null) {
                current = current.parent!!
            }
            return current
        }

}