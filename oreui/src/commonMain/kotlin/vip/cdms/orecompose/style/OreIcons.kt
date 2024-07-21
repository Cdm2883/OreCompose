package vip.cdms.orecompose.style

import vip.cdms.orecompose.utils.buildPixelIcon

object OreIcons {
    val Close by lazy {
        buildPixelIcon(size = 5) {
            W +   +   +   + W    +
              + W +   + W +      +
              +   + W +   +      +
              + W +   + W +      +
            W +   +   +   + W
        }
    }
    val Back by lazy {
        buildPixelIcon(width = 4) {
              +   +   + B    +
              +   + B +      +
              + B +   +      +
            B +   +   +      +
              + B +   +      +
              +   + B +      +
              +   +   + B
        }
    }
}
