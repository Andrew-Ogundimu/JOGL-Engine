package Objects;

import com.jogamp.opengl.GL3;

import java.util.ArrayList;

import static Objects.MainWindow.OpenGL;
import static Objects.BufferLayout.BufferElement;

public class VertexArray {
    private int offset;
    private int[] vao;
    public VertexArray() {
        vao = new int[1];
        OpenGL.glGenVertexArrays(1,vao,0);
    }
    public void finalize() {
        OpenGL.glDeleteVertexArrays(1,vao,0);
    }
    void addBuffer(VertexBuffer vb, BufferLayout layout) {
        bind();
        vb.bind();
        ArrayList<BufferElement> elements = layout.getElements();
        offset = 0;
        for (int i=0; i<elements.size(); i++) {
            BufferElement element = elements.get(i);
            OpenGL.glEnableVertexAttribArray(i);
            OpenGL.glVertexAttribPointer(i, element.getCount(), element.getType(), element.getNormalized(), layout.getStride(), offset);
            offset += element.getCount()*element.getSize();
        }
    }
    void bind() {
        OpenGL.glBindVertexArray(vao[0]);
    }
    void unbind() {
        OpenGL.glBindVertexArray(0);
    }
}
