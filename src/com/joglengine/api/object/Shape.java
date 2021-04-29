package com.joglengine.api.object;

import com.joglengine.api.buffer.VertexBuffer;
import org.joml.Vector3d;

public abstract class Shape {
    Vector3d[] positions;
    public abstract VertexBuffer getVbo();
}
