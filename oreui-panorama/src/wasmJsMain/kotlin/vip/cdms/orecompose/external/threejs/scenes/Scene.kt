package vip.cdms.orecompose.external.threejs.scenes

import vip.cdms.orecompose.external.threejs.core.Object3D

open external class Scene : Object3D {
   
    var fog: JsAny
    
    var overideMaterial: Material
    
    var autoUpdate: Boolean
    var background: JsAny

    fun copy(source: Scene, recursive: Boolean = definedExternally) : Scene

}
