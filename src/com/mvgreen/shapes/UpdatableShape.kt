package com.mvgreen.shapes

import java.awt.Shape

/**
 * An abstract class that represents some shape as a list of [java.awt.Shape]s.
 */
abstract class UpdatableShape {
    /**
     * Updates this shape's state and returns its representation as a list of [Shape]s.
     *
     * @return this shape's representation as a list of [java.awt.Shape]s
     */
    abstract fun update() : List<Shape>

    /**
     * @return this shape's representation as a list of [java.awt.Shape]s
     */
    abstract fun getLastUpdate() : List<Shape>

    /**
     * @return `false` if the next call to [update] will be identical to [getLastUpdate]; `true` otherwise
     */
    abstract fun canUpdate() : Boolean
}