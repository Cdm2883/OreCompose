package vip.cdms.orecompose.utils

import androidx.compose.runtime.Composable

private typealias Component = @Composable () -> Unit
private typealias ModuleWrapper = @Composable (Component) -> Unit

interface ComposeNester {
    @Composable
    operator fun invoke(content: @Composable () -> Unit)

    companion object {
        inline fun wrap(crossinline wrapper: ModuleWrapper) = object : ComposeNester {
            @Composable
            override operator fun invoke(content: Component) = wrapper(content)
        }

        /**
         * ```kt
         * Apply(
         *     modules = arrayOf(
         *         wrap { print("1"); it() },
         *         wrap { print("2"); it() },
         *     ),
         *     core = wrap { print("3"); it() }
         * ) { print("4") }  // prints 1234
         * ```
         */
        @Composable
        inline fun <reified T : ComposeNester> Apply(modules: Array<T>?, core: T, noinline content: Component) =
//            ((modules ?: emptyArray()) + core).foldRight(content) { module, acc -> { module(acc) } }()
            if (modules == null) core { content() }
            else (modules + core).foldRight(content) { module, acc -> { module(acc) } }()

        @Composable
        inline fun <reified T : ComposeNester> Apply(modules: Array<T>?, noinline content: Component, crossinline core: ModuleWrapper) =
            Apply(modules, wrap(core) as T, content)
    }
}
