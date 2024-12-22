package vip.cdms.orecompose.utils

@Suppress("MemberVisibilityCanBePrivate", "ConstPropertyName", "unused", "SpellCheckingInspection")
object McFormatBuilderScope {
    infix fun String.with(format: Char) = s(format) + this
    fun String.attach(vararg format: Char) = SS + format.joinToString(SS.toString()) + this
    infix fun String.with(format: String) = attach(*format.toCharArray())

    fun s(format: Char) = SS + format.toString()

    const val black              = '0'
    const val dark_blue          = '1'
    const val dark_green         = '2'
    const val dark_aqua          = '3'
    const val dark_red           = '4'
    const val dark_purple        = '5'
    const val gold               = '6'
    const val gray               = '7'
    const val dark_gray          = '8'
    const val blue               = '9'
    const val green              = 'a'
    const val aqua               = 'b'
    const val red                = 'c'
    const val light_purple       = 'd'
    const val yellow             = 'e'
    const val white              = 'f'
    const val minecoin_gold      = 'g'
    const val material_quartz    = 'h'
    const val material_iron      = 'i'
    const val material_netherite = 'j'
//    const val material_redstone  = 'm'
//    const val material_copper    = 'n'
    const val material_gold      = 'p'
    const val material_emerald   = 'q'
    const val material_diamond   = 's'
    const val material_lapis     = 't'
    const val material_amethyst  = 'u'

    const val obfuscated    = 'k'
    const val bold          = 'l'
    const val strikethrough = 'm'
    const val underline     = 'n'
    const val italic        = 'o'
    const val clear         = 'r'

    fun String.clear() = with(clear)
    val String.black              get() = with('0')
    val String.dark_blue          get() = with('1')
    val String.dark_green         get() = with('2')
    val String.dark_aqua          get() = with('3')
    val String.dark_red           get() = with('4')
    val String.dark_purple        get() = with('5')
    val String.gold               get() = with('6')
    val String.gray               get() = with('7')
    val String.dark_gray          get() = with('8')
    val String.blue               get() = with('9')
    val String.green              get() = with('a')
    val String.aqua               get() = with('b')
    val String.red                get() = with('c')
    val String.light_purple       get() = with('d')
    val String.yellow             get() = with('e')
    val String.white              get() = with('f')
    val String.minecoin_gold      get() = with('g')
    val String.material_quartz    get() = with('h')
    val String.material_iron      get() = with('i')
    val String.material_netherite get() = with('j')
//    val String.material_redstone  get() = with('m')
//    val String.material_copper    get() = with('n')
    val String.material_gold      get() = with('p')
    val String.material_emerald   get() = with('q')
    val String.material_diamond   get() = with('s')
    val String.material_lapis     get() = with('t')
    val String.material_amethyst  get() = with('u')

    val String.obfuscated    get() = with('k')
    val String.bold          get() = with('l')
    val String.strikethrough get() = with('m')
    val String.underline     get() = with('n')
    val String.italic        get() = with('o')

    // Mimic connecting symbols (e.g. PHP)
    operator fun String.rangeTo(string: String) = this + SS + "r" + string
}

const val SS = '§'

typealias McFormat = McFormatBuilderScope

inline fun <T : String?> mcFormat(builder: McFormatBuilderScope.() -> T) = builder(McFormatBuilderScope)
