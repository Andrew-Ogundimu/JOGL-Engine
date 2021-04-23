package Objects;

import Objects.MainWindow;
import Objects.Shader;
import com.jogamp.opengl.GL3;

import java.util.ArrayList;

public class ShaderProgram {
    public int id;
    ArrayList<Integer> shaders;
    public ShaderProgram() {
        this.id = MainWindow.OpenGL.glCreateProgram();
        shaders = new ArrayList<>();
    }
    public int addShader(Shader s) {
        int shaderId = MainWindow.OpenGL.glCreateShader(s.getType());
        MainWindow.OpenGL.glShaderSource(shaderId, s.getRaw().length, s.getRaw(),s.getRawLengths(),0);
        MainWindow.OpenGL.glCompileShader(shaderId);

        int[] result = new int[1];
        MainWindow.OpenGL.glGetShaderiv(shaderId,GL3.GL_COMPILE_STATUS,result,0);
        if (result[0]==GL3.GL_FALSE) {
            int[] length = new int[1];
            MainWindow.OpenGL.glGetShaderiv(shaderId,GL3.GL_INFO_LOG_LENGTH,length,0);
            byte[] message = new byte[length[0]];
            MainWindow.OpenGL.glGetShaderInfoLog(shaderId,length[0],new int[]{message.length},0,message,0);
            System.out.println((s.getType()==GL3.GL_VERTEX_SHADER?"Vertex":"Fragment")
                    +" shader compilation failed:");
            System.out.println(new String(message));
            MainWindow.OpenGL.glDeleteShader(shaderId);
            return -1;
        }
        MainWindow.OpenGL.glAttachShader(id,shaderId);
        shaders.add(shaderId);
        return shaderId;
    }
    public void linkAndValidate() {
        MainWindow.OpenGL.glLinkProgram(id);
        MainWindow.OpenGL.glValidateProgram(id);
        for (Integer shader: shaders) {
            MainWindow.OpenGL.glDeleteShader(shader);
        }
    }
    public void use() {
        MainWindow.OpenGL.glUseProgram(id);
    }
}
