package Test.Objects;

import com.jogamp.opengl.GL3;

import java.util.ArrayList;

public class BufferLayout {
    public static class BufferElement {
        private int size;
        private int count;
        private int type;
        private boolean normalized;
        public BufferElement(int type, int count, boolean normalized) {
            if (type==GL3.GL_FLOAT) {
                size = 4;
            } else if (type== GL3.GL_INT) {
                size = 4;
            } else if (type == GL3.GL_BYTE) {
                size = 1;
            } else if (type == GL3.GL_BOOL) {
                size = 1;
            }
            this.normalized = normalized;
            this.type = type;
            this.count = count;
        }
        public int getSize() {
            return size;
        }
        public int getCount() {
            return count;
        }
        public int getType() {
            return type;
        }

        public boolean getNormalized() {
            return normalized;
        }
    }
    ArrayList<BufferLayout.BufferElement> elements = new ArrayList<>();
    int stride = 0;
    void push(int type,int count) {
        BufferElement elem = new BufferElement(type,count,false);
        elements.add(new BufferElement(type,count,false));
        stride += elem.getSize()*count;
    }
    ArrayList<BufferElement> getElements() {
        return elements;
    }
    int getStride() {
        return stride;
    }
}
