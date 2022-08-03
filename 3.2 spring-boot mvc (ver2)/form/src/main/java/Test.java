public class Test {

    public static void main(String[] args) {
        Cloth cloth1 = new Cloth();
        Color blue = new Blue("파랑");
        Color red = new Red("빨강");
        cloth1.wear(blue);

        cloth1.wear(blue);
    }
}
