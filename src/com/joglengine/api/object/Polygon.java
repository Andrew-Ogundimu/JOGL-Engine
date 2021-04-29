package com.joglengine.api.object;

import com.joglengine.api.buffer.VertexBuffer;
import org.joml.Vector3d;

import java.util.ArrayList;

public class Polygon extends Shape {
    VertexBuffer vbo;
    public Polygon(double[] positions) {
        vbo = new VertexBuffer(positions);
    }
    public VertexBuffer getVbo() {
        return vbo;
    }
}
