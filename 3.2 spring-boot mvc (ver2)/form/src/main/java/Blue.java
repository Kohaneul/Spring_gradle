public class Blue implements Color {
    String colorName;

    Blue(String colorName){
        this.colorName = colorName;
    }

    @Override
    public String setColor() {
        return colorName;
    }
}
