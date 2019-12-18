package com.mvgreen.shapes.modulo_circle

import com.mvgreen.shapes.UpdatableShape
import java.awt.Shape
import java.awt.geom.Line2D
import kotlin.math.*

/**
 * Shape that consists of multiple lines that starts from equidistant points on a circle of a given radius.
 * Every update, the end of each line moves in a circle at a speed proportional to its index.
 *
 * @param pointCount the total number of points on the circle.
 * @param initialMultiplier the initial value of the coefficient, that is used to calculate the coordinates of the line.
 * @property delta the term that represents the rate of change of [multiplier]
 * @property multiplierLimit [multiplier]'s max value; if [multiplier] >= [multiplierLimit], then canUpdate returns false.
 * @property radius the circle's size.
 * @property shift moves this shape by its value both vertically and horizontally.
 */
class CircleAndLines(
    pointCount: Int = 100,
    initialMultiplier: Double = 0.0,
    private var delta: Double = 0.1,
    val multiplierLimit: Double = pointCount.toDouble(),
    private val radius: Double = 100.0,
    private val shift: Double
) : UpdatableShape() {
    /** Variable that defines coordinates of  line's end */
    private var multiplier = initialMultiplier
    /** Support variable that corrects floating point arithmetic when the [multiplier] should become an integer number */
    private var nextIntMultiplier: Int = (floor(initialMultiplier) + 1).toInt()
    /** Coefficient that converts line's index into radian coordinates of its origin */
    private val padding = 2 * PI / pointCount
    /** List of lines that make up the shape */
    private val list: List<Line2D> = MutableList(pointCount) { index ->
        val angle = padding * index
        Line2D.Double(cos(angle) * radius + shift, sin(angle) * radius + shift, 0.0, 0.0)
    }

    /**
     * @see UpdatableShape.update
     */
    override fun update(): List<Shape> {
        if (!canUpdate())
            return list
        multiplier += delta
        if (abs(multiplier - nextIntMultiplier) < delta) {
            multiplier = nextIntMultiplier.toDouble()
            nextIntMultiplier++
        }
        for (lineIndex in list.indices) {
            with(list[lineIndex] as Line2D.Double) {
                val angle = lineIndex * multiplier * padding
                x2 = cos(angle) * radius + shift
                y2 = sin(angle) * radius + shift
            }
        }
        return list
    }

    /**
     * @see UpdatableShape.getLastUpdate
     */
    override fun getLastUpdate(): List<Shape> {
        return list
    }

    /**
     * @see UpdatableShape.canUpdate
     */
    override fun canUpdate(): Boolean {
        return multiplier < multiplierLimit
    }
}