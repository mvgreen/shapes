package com.mvgreen.shapes

import com.mvgreen.shapes.modulo_circle.CircleAndLines
import java.awt.*
import java.awt.geom.Rectangle2D
import javax.swing.JFrame
import javax.swing.JPanel

/**
 * The main window that is used to render a given shape.
 *
 * @param windowSize the width and height of the window. On Windows 10, the window size is slightly different.
 * @param updatableShape the [UpdatableShape] to render. If it is null, then [updateFrame] will just clear the window.
 */
class MainFrame(windowSize: Int, updatableShape: UpdatableShape?) : JFrame() {
    /** The [UpdatableShape] to render */
    var updatableShape: UpdatableShape?
        set(value) = with(panel) { shape = value }
        get() = panel.shape
    /** The color of window's background */
    var backgroundColor: Color
        get() = panel.backgroundColor
        set(value) = with(panel) { backgroundColor = value }
    /** The [updatableShape]'s color */
    var shapeColor: Color
        get() = panel.shapeColor
        set(value) = with(panel) { shapeColor = value }

    /** The [JPanel] that is used to render shapes on it */
    private val panel: MainPanel

    init {
        size = Dimension(windowSize, windowSize)
        defaultCloseOperation = EXIT_ON_CLOSE
        isVisible = true
        isResizable = false

        panel = MainPanel(windowSize, updatableShape)
        title = "Updatable Shapes"
        this.contentPane.add(panel)
    }

    /**
     * Sends an update request to the [panel], which then calls [JPanel.paint] and updates the shape.
     */
    fun updateFrame() {
        panel.repaint()
    }

    /**
     * The [JPanel] that is used to render [updatableShape] on it.
     *
     * @param panelSize the width and height of the panel.
     * @property [shape] the [UpdatableShape] to render. If it is null, then [paint] will just clear the window.
     */
    private inner class MainPanel(panelSize: Int, var shape: UpdatableShape? = null) : JPanel() {
        /** The [Rectangle2D] to clear the currently displayed frame */
        private var rectangle: Rectangle2D = Rectangle2D.Double(0.0, 0.0, panelSize.toDouble(), panelSize.toDouble())
        /** The color of window's background */
        var backgroundColor: Color = Color.WHITE
        /** The [shape]'s color */
        var shapeColor: Color = Color.BLACK

        init {
            size = Dimension(panelSize, panelSize)
        }

        /**
         * Updates the currently displayed frame. This method should not be called directly.
         * To update the frame, call the [updateFrame] method instead.
         * If [shape] is null, then it just clears the frame.
         */
        override fun paint(g: Graphics) {
            val g2 = g as Graphics2D
            g2.stroke = BasicStroke(1f)
            g2.paint = backgroundColor
            g2.fill(rectangle)

            g2.paint = shapeColor
            for (sh in shape?.update() ?: return)
                g2.draw(sh)
        }
    }
}

fun main() {
    val frame = MainFrame(500, null)
    frame.updatableShape = CircleAndLines(
        shift = 500.0 / 2, delta = 0.005, radius = 200.0,
        pointCount = 200, initialMultiplier = 0.0, multiplierLimit = 91.0
    )
    frame.backgroundColor = Color.GRAY
    frame.shapeColor = Color.WHITE
    while (frame.updatableShape?.canUpdate() == true) {
        Thread.sleep(3)
        frame.updateFrame()
    }
}