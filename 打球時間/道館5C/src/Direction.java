public enum Direction {
    UP("上"),
    DOWN("下"),
    LEFT("左"),
    RIGHT("右");

    private String attribute;

    Direction(String attribute) {
        this.attribute = attribute;
    }

    public String getAttribute() {
        return attribute;
    }
}
