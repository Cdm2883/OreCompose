package vip.cdms.orecompose.layout.panorama

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import orecompose.oreui_panorama.generated.resources.Res
import orecompose.oreui_panorama.generated.resources.panorama_default
import org.jetbrains.compose.resources.imageResource
import vip.cdms.orecompose.utils.argb

object PanoramaDefaults {
    val Equirectangular = Res.drawable.panorama_default
    val Modifier = androidx.compose.ui.Modifier
        .fillMaxSize()
        .foreground(0x55000000.argb)  // WTF, different platforms with different color ?!
}

fun Modifier.foreground(color: Color) = drawWithContent {
    drawContent()
    drawRect(color)
}

/**
 * ```python
 * import py360convert
 * import cv2
 *
 * resource_packs = "<PATH_TO_YOURS>/textures/ui/"
 * out_path = "<PATH_TO_YOURS>/panorama.png"
 * quality_para = 0
 * target_size_w = 3000
 *
 * cube_dice0 = cv2.imread(f"{resource_packs}panorama_0.png")
 * cube_dice1 = cv2.imread(f"{resource_packs}panorama_1.png")
 * cube_dice2 = cv2.imread(f"{resource_packs}panorama_2.png")
 * cube_dice3 = cv2.imread(f"{resource_packs}panorama_3.png")
 * cube_dice4 = cv2.imread(f"{resource_packs}panorama_4.png")
 * cube_dice5 = cv2.imread(f"{resource_packs}panorama_5.png")
 *
 * target_size_w = int(target_size_w)
 * target_size_h = int(target_size_w // 2)
 * quality_map = {
 *     0: 40,
 *     1: 60,
 *     2: 90,
 *     3: 100
 * }
 * quality_save = quality_map.get(quality_para, 70)
 * cube_dice1 = cv2.flip(cube_dice1, 1)
 * cube_dice2 = cv2.flip(cube_dice2, 1)
 * cube_dice4 = cv2.flip(cube_dice4, 0)
 *
 * res = py360convert.c2e(
 *     [cube_dice0, cube_dice1, cube_dice2, cube_dice3, cube_dice4, cube_dice5],
 *     target_size_h, target_size_w, cube_format='list'
 * )
 * cv2.imwrite(out_path, res, [int(cv2.IMWRITE_JPEG_QUALITY), quality_save])
 * print("Panorama image saved at: " + out_path)
 * ```
 */
@Suppress("SpellCheckingInspection")
@Composable
expect fun Panorama(
    equirectangular: ImageBitmap = imageResource(PanoramaDefaults.Equirectangular),
    modifier: Modifier = PanoramaDefaults.Modifier,
    rotating: Boolean = true,
)
