import com.joglengine.api.display.*;
import com.joglengine.api.event.Event;
import com.joglengine.api.object.Polygon;
import com.joglengine.api.object.Sprite;
import org.joml.Vector3d;

public class main {
    public static void main(String args[]) throws InterruptedException {
        Window w = new Window(400,400, false);
        while (w.initFinished == false) {
            System.out.println(w.initFinished);
        }
        w.setFramerate(144);
        Sprite dababy = new Sprite();
        System.out.println("set shape");
        dababy.setShape(new Polygon(new double[]{
                -1.0d,-1.0d,0.0d,
                1.0d,-1.0d,0.0d,
                1.0d,1.0d,0.0d,
                -1.0d,1.0d,0.0d
        }));
        while (true) {
            w.draw(dababy);
            w.update();
            for (Event e: w.pollEvents()) {
                if (e.type == Event.KEY_DOWN) {
                    System.out.println("press: "+e.getArgs()[0]); //print keycodes
                } else if (e.type == Event.KEY_UP) {
                    System.out.println("release: "+e.getArgs()[0]);
                }
            }
        }
    }
}
