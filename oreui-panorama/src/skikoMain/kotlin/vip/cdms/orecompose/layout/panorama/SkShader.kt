package vip.cdms.orecompose.layout.panorama

import org.intellij.lang.annotations.Language

@Language("AGSL")  // IDEA has no support for sksl :(
@Suppress("SpellCheckingInspection")
internal val Sksl = """
    uniform float2 iResolution;
    uniform shader panorama;
    uniform float2 panoramaSize;
    uniform float yaw;
    uniform float pitch;
    uniform float fov;

    const float PI = 3.141592653589793;

    half4 main(float2 fragCoord) {
        float2 uv = (fragCoord - 0.5 * iResolution) / iResolution.y;
        
        float fovScale = tan(fov * 0.5);
        float3 rd = normalize(float3(uv.x * fovScale, uv.y * fovScale, 1.0));
        
        float cosPitch = cos(pitch);
        float sinPitch = sin(pitch);
        float3 rd1 = float3(rd.x,
                            rd.y * cosPitch - rd.z * sinPitch,
                            rd.y * sinPitch + rd.z * cosPitch);
        
        float cosYaw = cos(yaw);
        float sinYaw = sin(yaw);
        float3 rdRot = float3(rd1.x * cosYaw + rd1.z * sinYaw,
                              rd1.y,
                              -rd1.x * sinYaw + rd1.z * cosYaw);
        
        float theta = atan(rdRot.z, rdRot.x);
        float phi   = asin(clamp(rdRot.y, -1.0, 1.0));
        
        float u = (theta + PI) / (2.0 * PI);
        float v = (phi + 0.5 * PI) / PI;
        // float v = 1.0 - ((phi + 0.5 * PI) / PI);  // flip Y
        
        float2 panoramaCoord = float2(u * panoramaSize.x, v * panoramaSize.y);
        return panorama.eval(panoramaCoord);
    }
""".trimIndent()
