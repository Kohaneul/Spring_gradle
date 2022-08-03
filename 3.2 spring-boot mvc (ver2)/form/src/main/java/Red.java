public class Red implements Color {
    String colorName;

    Red(String colorName){
        this.colorName = colorName;
    }

    @Override
    public String setColor() {
        return colorName;
    }
}
