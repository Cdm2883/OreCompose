package vip.cdms.orecompose.utils

@Suppress("MemberVisibilityCanBePrivate", "ConstPropertyName", "unused", "SpellCheckingInspection", "NOTHING_TO_INLINE")
object McFormatBuilderScope {
    inline infix fun String.with(format: Char) = s(format) + this
    fun String.attach(vararg format: Char) = SS + format.joinToString(SS.toString()) + this
    infix fun String.with(format: String) = attach(*format.toCharArray())

    inline fun s(format: Char) = SS + format.toString()

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
    val String.black              inline get() = with(this@McFormatBuilderScope.black)
    val String.dark_blue          inline get() = with(this@McFormatBuilderScope.dark_blue)
    val String.dark_green         inline get() = with(this@McFormatBuilderScope.dark_green)
    val String.dark_aqua          inline get() = with(this@McFormatBuilderScope.dark_aqua)
    val String.dark_red           inline get() = with(this@McFormatBuilderScope.dark_red)
    val String.dark_purple        inline get() = with(this@McFormatBuilderScope.dark_purple)
    val String.gold               inline get() = with(this@McFormatBuilderScope.gold)
    val String.gray               inline get() = with(this@McFormatBuilderScope.gray)
    val String.dark_gray          inline get() = with(this@McFormatBuilderScope.dark_gray)
    val String.blue               inline get() = with(this@McFormatBuilderScope.blue)
    val String.green              inline get() = with(this@McFormatBuilderScope.green)
    val String.aqua               inline get() = with(this@McFormatBuilderScope.aqua)
    val String.red                inline get() = with(this@McFormatBuilderScope.red)
    val String.light_purple       inline get() = with(this@McFormatBuilderScope.light_purple)
    val String.yellow             inline get() = with(this@McFormatBuilderScope.yellow)
    val String.white              inline get() = with(this@McFormatBuilderScope.white)
    val String.minecoin_gold      inline get() = with(this@McFormatBuilderScope.minecoin_gold)
    val String.material_quartz    inline get() = with(this@McFormatBuilderScope.material_quartz)
    val String.material_iron      inline get() = with(this@McFormatBuilderScope.material_iron)
    val String.material_netherite inline get() = with(this@McFormatBuilderScope.material_netherite)
//    val String.material_redstone  inline get() = with(this@McFormatBuilderScope.material_redstone)
//    val String.material_copper    inline get() = with(this@McFormatBuilderScope.material_copper)
    val String.material_gold      inline get() = with(this@McFormatBuilderScope.material_gold)
    val String.material_emerald   inline get() = with(this@McFormatBuilderScope.material_emerald)
    val String.material_diamond   inline get() = with(this@McFormatBuilderScope.material_diamond)
    val String.material_lapis     inline get() = with(this@McFormatBuilderScope.material_lapis)
    val String.material_amethyst  inline get() = with(this@McFormatBuilderScope.material_amethyst)

    val String.obfuscated    inline get() = with(this@McFormatBuilderScope.obfuscated)
    val String.bold          inline get() = with(this@McFormatBuilderScope.bold)
    val String.strikethrough inline get() = with(this@McFormatBuilderScope.strikethrough)
    val String.underline     inline get() = with(this@McFormatBuilderScope.underline)
    val String.italic        inline get() = with(this@McFormatBuilderScope.italic)

    // mimic connecting symbols (e.g. PHP)
    inline operator fun String.rangeTo(string: String) = this + SS + clear + string
}

const val SS = '§'

typealias McFormat = McFormatBuilderScope

inline fun <T : String?> mcFormat(builder: McFormatBuilderScope.() -> T) = builder(McFormatBuilderScope)
