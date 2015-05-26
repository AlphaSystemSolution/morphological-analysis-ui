package com.alphasystem.morphologicalanalysis.treebank.jfx.ui.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import static com.alphasystem.morphologicalanalysis.treebank.jfx.ui.model.NodeType.TERMINAL;

/**
 * @author sali
 */
public class TerminalNode extends GraphNode {

    /**
     * x1 location for line
     */
    protected final DoubleProperty x1;

    /**
     * y1 location for line
     */
    protected final DoubleProperty y1;

    /**
     * x2 location for line
     */
    protected final DoubleProperty x2;

    /**
     * y2 location for line
     */
    protected final DoubleProperty y2;

    /**
     *
     */
    public TerminalNode() {
        this(null, -1, -1, -1, -1, -1, -1);
    }

    /**
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    public TerminalNode(String text, double x, double y, double x1, double y1, double x2, double y2) {
        this(TERMINAL, text, x, y, x1, y1, x2, y2);
    }

    /**
     * @param nodeType
     * @param text
     * @param x
     * @param y
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    protected TerminalNode(NodeType nodeType, String text, double x, double y,
                           double x1, double y1, double x2, double y2) {
        super(nodeType, text, x, y);
        this.x1 = new SimpleDoubleProperty(x1);
        this.y1 = new SimpleDoubleProperty(y1);
        this.x2 = new SimpleDoubleProperty(x2);
        this.y2 = new SimpleDoubleProperty(y2);
    }

    public double getX1() {
        return x1.get();
    }

    public void setX1(double x1) {
        this.x1.set(x1);
    }

    public final DoubleProperty x1Property() {
        return x1;
    }

    public double getX2() {
        return x2.get();
    }

    public void setX2(double x2) {
        this.x2.set(x2);
    }

    public final DoubleProperty x2Property() {
        return x2;
    }

    public double getY1() {
        return y1.get();
    }

    public void setY1(double y1) {
        this.y1.set(y1);
    }

    public final DoubleProperty y1Property() {
        return y1;
    }

    public double getY2() {
        return y2.get();
    }

    public void setY2(double y2) {
        this.y2.set(y2);
    }

    public final DoubleProperty y2Property() {
        return y2;
    }
}
