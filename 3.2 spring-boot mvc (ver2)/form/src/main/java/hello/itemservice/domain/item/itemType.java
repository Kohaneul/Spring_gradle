package hello.itemservice.domain.item;

public enum itemType {
    BOOK("도서"), FOOD("음식"), ETC("기타");

    private final String description;

    itemType(String description) {
        this.description = description;
    }
}
