package Test.Objects;

import java.util.ArrayList;

public class VertexArray {
    private int offset;
    private int[] vao;
    public VertexArray() {
        vao = new int[1];
        MainWindow.OpenGL.glGenVertexArrays(1,vao,0);
    }
    public void finalize() {
        MainWindow.OpenGL.glDeleteVertexArrays(1,vao,0);
    }
    void addBuffer(VertexBuffer vb, BufferLayout layout) {
        bind();
        vb.bind();
        ArrayList<BufferLayout.BufferElement> elements = layout.getElements();
        offset = 0;
        for (int i=0; i<elements.size(); i++) {
            BufferLayout.BufferElement element = elements.get(i);
            MainWindow.OpenGL.glEnableVertexAttribArray(i);
            MainWindow.OpenGL.glVertexAttribPointer(i, element.getCount(), element.getType(), element.getNormalized(), layout.getStride(), offset);
            offset += element.getCount()*element.getSize();
        }
    }
    void bind() {
        MainWindow.OpenGL.glBindVertexArray(vao[0]);
    }
    void unbind() {
        MainWindow.OpenGL.glBindVertexArray(0);
    }
}
