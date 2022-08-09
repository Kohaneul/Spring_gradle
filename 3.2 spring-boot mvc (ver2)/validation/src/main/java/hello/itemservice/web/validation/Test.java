package hello.itemservice.web.validation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Test {


    public static void main(String[] args) {
        Money money1 = new Money();
        money1.setAmount(10000);
        System.out.println("money1 = " + money1);
        Money money2 = new Money();
        money2.setAmount(30000);
        System.out.println("money2 = " + money2);
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class Money{
        private int amount;
        private String called;
        private Currency currency;

        public void setCalled(){
            this.called = this.called+Currency.DOLLAR;
        }



    }

}
enum Currency{
    KRW,DOLLAR
}
