package vip.cdms.orecompose.layout.panorama

import android.content.Context
import android.graphics.Bitmap
import android.graphics.SurfaceTexture
import android.util.AttributeSet
import android.view.TextureView
import android.widget.FrameLayout
import com.zzt.panorama.cg.GLProducerThread
import com.zzt.panorama.sphere.SphereRenderer
import java.util.concurrent.atomic.AtomicBoolean

class GlPanoramaView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs), TextureView.SurfaceTextureListener {
    var onGLThreadReady = {}
    lateinit var bitmap: Bitmap

    private val view by lazy { TextureView(context) }
    val renderer by lazy { SphereRenderer(context) }

    private val rendererBiasMatrixField by lazy {
        SphereRenderer::class.java.getDeclaredField("mBiasMatrix")
            .apply { isAccessible = true }
    }
    val rendererBiasMatrix
        get() = rendererBiasMatrixField.get(renderer) as FloatArray

//    val camera by lazy {
//        SphereRenderer::class.java.getDeclaredField("mCamera")
//            .apply { isAccessible = true }
//            .get(renderer) as Camera
//    }

    private var isGLThreadAvailable = false
    var glThread: GLProducerThread? = null

    init {
        renderer.enableGyroTracking(false)
        view.surfaceTextureListener = this
        addView(view)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        renderer.onAttached()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        renderer.onDetached()
        glThread?.enqueueEvent { glThread!!.releaseEglContext() }
    }

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
        if (!isGLThreadAvailable) {
            isGLThreadAvailable = true
            glThread = GLProducerThread(surface, renderer, AtomicBoolean(true))
            glThread!!.start()
            onGLThreadReady()
            glThread!!.enqueueEvent { renderer.onSurfaceChanged(width, height) }
            glThread!!.enqueueEvent { renderer.loadBitmap(bitmap) }
        } else {
            glThread!!.refreshSurfaceTexture(surface)
            glThread!!.enqueueEvent { renderer.changeTextureBitmap(bitmap) }
            glThread!!.onResume()
        }
    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {
        glThread?.enqueueEvent { renderer.onSurfaceChanged(width, height) }
    }

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
        glThread?.onPause()
        return true
    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {}
}
