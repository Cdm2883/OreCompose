@file:JsQualifier("THREE")

package vip.cdms.orecompose.external.threejs.core

import vip.cdms.orecompose.external.threejs.math.Matrix4
import vip.cdms.orecompose.external.threejs.math.Vector3

open external class Object3D {

    companion object {
        val DefaultUp: Vector3
        val DefaultMatrixAutoUpdate: Boolean
    }

    val id: Int

    val uuid: String
    val name: String

    val parent: Object3D?
    val children: Array<Object3D>

    val up: Vector3

    val position: Vector3
    val rotation: Euler
    val quaternion: Quaternion
    val scale: Vector3
    val modelViewMatrix: Matrix4
    val normalMatrix: Matrix3

    var matrix: Matrix4
    var matrixWorld: Matrix4

    var matrixAutoUpdate: Boolean
    var matrixWorldNeedsUpdate: Boolean

    var layers: Layers
    var visible: Boolean

    var castShadow: Boolean
    var receiveShadows: Boolean

    var frustrumCulled: Boolean
    var renderOrder: Int

    var userData: Map<String, Any>

    var onBeforeRender: () -> Unit
    var onAfterRender: () -> Unit

    fun applyMatrix(matrix: Matrix4)

    fun applyQuaternion(q: Quaternion)
    fun setRotationFromAxisAngle(axis: Vector3, angle: Number)
    fun setRotationFromEuler ( euler: Euler)
    fun setRotationFromMatrix ( m: Matrix3)
    fun setRotationFromQuaternion ( q: Quaternion)
    fun rotateOnAxis(axis: Vector3, angle: Number)
    fun rotateOnWorldAxis(axis: Vector3, angle: Double)
    fun rotateX(angle: Number)
    fun rotateY (angle: Number)
    fun rotateZ (angle: Number)
    fun translateOnAxis (axis: Vector3, distance: Number)
    fun translateX (distance: Number)
    fun translateY (distance: Number)
    fun translateZ (distance: Number)

    fun localToWorld ( vector: Vector3) : Vector3
    fun worldToLocal (vector: Vector3) : Vector3

    fun lookAt(v: Vector3)
    fun lookAt (x: Number, y: Number, z: Number)

    fun add ( `object`: Object3D)

    fun remove ( `object`: Object3D)

    fun getObjectById ( id: Int ) : Object3D?

    fun getObjectByName ( name: String ) : Object3D?
    fun getObjectByProperty ( name : String, value: dynamic ) : Object3D?
    fun getWorldPosition ( optionalTarget: Vector3 = definedExternally ) : Vector3
    fun getWorldQuaternion (optionalTarget: Quaternion = definedExternally) : Quaternion

    fun getWorldRotation (optionalTarget: Euler = definedExternally) : Euler

    fun getWorldScale (optionalTarget: Vector3 = definedExternally) : Vector3
    open fun getWorldDirection (optionalTarget: Vector3 = definedExternally) : Vector3
    open fun raycast ()
    fun traverse ( callback: (Object3D) -> Unit)
    fun traverseVisible ( callback: (Object3D) -> Unit )
    fun traverseAncestors ( callback: (Object3D) -> Unit )
    fun updateMatrix ()
    open fun updateMatrixWorld ( force: Boolean = definedExternally )

    fun toJSON ( meta: String = definedExternally ) : Any
    open fun clone ( recursive:Boolean = definedExternally) : Object3D
    open fun copy (source: Object3D, recursive: Boolean = definedExternally ) : Object3D
}
