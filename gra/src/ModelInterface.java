
public interface ModelInterface {
    void setBoardFile(String fileName);  //setter
    void writeKeys(boolean[] keys);  //setter
    double[] readBallPosition(); //getter
    void nextFrame();  //server
}
