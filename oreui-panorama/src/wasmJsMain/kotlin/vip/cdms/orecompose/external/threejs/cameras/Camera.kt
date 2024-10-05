@file:JsQualifier("THREE")

package vip.cdms.orecompose.external.threejs.cameras

import vip.cdms.orecompose.external.threejs.core.Object3D
import vip.cdms.orecompose.external.threejs.math.Matrix4
import vip.cdms.orecompose.external.threejs.math.Vector3

external interface View {
    var enabled: Boolean
    var fullwidth: Int
    var fullHeight: Int
    var offsetX: Int
    var offsetY: Int
    var width: Int
    var height: Int
}

open external class Camera : Object3D {
    var matrixWorldInverse: Matrix4
    var projectionMatrix: Matrix4

    fun getWorldDirection() : Vector3

    override fun getWorldDirection(optionalTarget: Vector3) : Vector3

    override fun updateMatrixWorld(force: Boolean)

    fun clone(): Camera
    fun copy(source: Camera, recursive: Boolean)
}
