package vip.cdms.orecompose.external.threejs.core

import vip.cdms.orecompose.external.threejs.math.Matrix4
import vip.cdms.orecompose.external.threejs.math.Vector3
import vip.cdms.orecompose.external.threejs.objects.Mesh

external interface MorphTarget {
    val name: String
    val vertices: JsArray<Vector3>
}

external interface MorphNormal {
    val name: String
    val normals: JsArray<Vector3>
}

open external class Geometry {

    val id: Int

    var vertices: JsArray<Vector3>
    var colors: JsArray<Color>
    var faces: JsArray<Face3>
    var faceVertexUvs: JsArray<JsArray<Vector2>>

    var morphTargets: JsArray<MorphTarget>
    var morphNormals: JsArray<MorphNormal>

    var skinWeights: JsArray<Vector4>
    var skinIndices: JsArray<Vector4>

    var lineDistances: JsArray<Double>

    var boundingBox: Box3?
    var boundingSphere: Sphere?

    // update flags

    var elementsNeedUpdate: Boolean
    var verticesNeedUpdate: Boolean
    var uvsNeedUpdate: Boolean
    var normalsNeedUpdate: Boolean
    var colorsNeedUpdate: Boolean
    var lineDistancesNeedUpdate: Boolean
    var groupsNeedUpdate: Boolean

    fun applyMatrix(matrix: Matrix4): Geometry
    fun rotateX(angle: Number) : Geometry
    fun rotateY(angle: Number) : Geometry
    fun rotateZ(angle: Number) : Geometry
    fun translate(x: Number, y: Number, z: Number) : Geometry
    fun scale(x: Number, y: Number, z: Number): Geometry
    fun lookAt(vector: Vector3) : Geometry
    fun fromBufferGeometry(geometry: BufferGeometry) : Geometry
    fun addFace(a: Int, b: Int, c: Int, materialIndexOffset: Int = definedExternally)
    fun center() : Vector3
    fun normalize() : Geometry
    fun computeFaceNormals()
    fun computeVertexNormals(areaWeighted : Boolean = definedExternally)
    fun computeFlatVertexNormals()
    fun computeMorphNormals()
    fun computeLineDistances()
    fun computeBoundingBox()
    fun computeBoundingSphere()

    fun merge(geometry: Geometry, matrix: Matrix4 = definedExternally, materialIndexOffset: Int = definedExternally)

    fun mergeMesh(mesh: Mesh)

    fun mergeVertices()

    fun setFromPoint(points: JsArray<Vector3>) : Geometry

    fun sortFacesByMaterialIndex()

    fun toJSON() : JsAny

    open fun clone() : Geometry
    fun copy(geometry: Geometry) : Geometry

    fun dispose()

}
