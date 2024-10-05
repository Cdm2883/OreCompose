package vip.cdms.orecompose.external.threejs.math

open external class Matrix4 {
    val elements: DoubleArray
    
    constructor()

    fun set(n11: Number, n12: Number, n13: Number, n14: Number,
            n21: Number, n22: Number, n23: Number, n24: Number,
            n31: Number, n32: Number, n33: Number, n34: Number,
            n41: Number, n42: Number, n43: Number, n44: Number)
    
    fun identity () : Matrix4
   
    fun clone () : Matrix4
    
    fun copy ( m: Matrix4) : Matrix4
   
    fun copyPosition ( m: Matrix4): Matrix4

    fun extractBasis (xAxis: Vector3, yAxis: Vector3, zAxis: Vector3) : Matrix4
    
    fun makeBasis (xAxis: Vector3, yAxis: Vector3, zAxis: Vector3) : Matrix4

    fun extractRotation (m: Matrix4) : Matrix4

    fun makeRotationFromEuler ( euler: Euler) : Matrix4

    fun makeRotationFromQuaternion ( q: Quaternion): Quaternion

    fun lookAt (eye : Vector3, target: Vector3, up: Vector3): Matrix4
    
    fun multiply ( m: Matrix4) : Matrix4
   
    fun premultiply ( m: Matrix4) : Matrix4
    
    fun multiplyMatrices (a: Matrix4, b: Matrix4) : Matrix4

    fun multiplyScalar ( s: Double ): Matrix4

    fun applyToBufferAttribute (attribute : BufferGeometry)

    fun determinant () : Double

    fun transpose () : Matrix4

    fun setPosition ( v: Vector3) : Matrix4

    /**
     * Set this matrix to the inverse of the passed matrix m, using the method outlined here.
     * If throwOnDegenerate is not set and the matrix is not invertible, set this to the 4x4 identity matrix.
     *
     * @m the matrix to take the inverse of.
     * @param throwOnDegenerate (optional) If true, throw an error if the matrix is degenerate (not invertible).
     */
    fun getInverse (m : Matrix4, throwOnDegenerate: Boolean = definedExternally )

    /**
     * Multiplies the columns of this matrix by vector v.
     */
    fun scale ( v: Vector3): Matrix4

    /**
     * Gets the maximum scale value of the 3 axes.
     */
    fun getMaxScaleOnAxis () : Double

    fun makeTranslation ( x: Double, y: Double, z: Double ) : Matrix4

    fun makeRotationX ( theta: Double ): Matrix4

    fun makeRotationY ( theta: Double ) : Matrix4

    fun makeRotationZ ( theta: Double ) : Matrix4

    fun makeRotationAxis (axis: Vector3, angle:Double ) : Matrix4

    fun makeScale ( x: Double, y: Double, z: Double ) : Matrix4

    fun makeShear ( x:Double, y:Double, z:Double ) : Matrix4

    fun compose (position: Vector3, quaternion: Vector3, scale: Vector3) : Matrix4

    fun decompose (position: Vector3, quaternion: Vector3, scale: Vector3) : Matrix4

    fun makePerspective ( left: Int, right: Int, top: Int, bottom: Int, near: Double, far:Double ) : Matrix4

    fun makeOrthographic ( left: Int, right: Int, top: Int, bottom: Int, near: Double, far: Double ) : Matrix4

    fun equals ( matrix: Matrix4) : Boolean

    fun fromArray ( array: DoubleArray, offset: Int = definedExternally ) : Matrix4

    fun toArray ( array: DoubleArray, offset: Int = definedExternally ) : DoubleArray
}
