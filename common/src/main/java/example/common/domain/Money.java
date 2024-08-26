package example.common.domain;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;
public class Money extends ValueObject {

    public static Money ZERO = new Money(0);

    private BigDecimal amount;

    public Money(BigDecimal amount) {
        assertArgumentNotEmpty(amount.toString(),"Money cannot be empty");
        assertValueIsGreaterThan(amount, BigDecimal.ZERO,"Money cannot be greater than zero");
        this.amount = amount;
    }

    public Money(String s) {
        this.amount = new BigDecimal(s);
    }

    public Money(int i) {
        this.amount = new BigDecimal(i);
    }
    //Shallow copy
    public Money(Money money){
        this(money.amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Money money = (Money) o;
        return this.amount.equals(money.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

    @Override
    public String toString() {
        return String.format("amount %s",amount.toString());
    }

    public Money add(Money delta) {
        return new Money(amount.add(delta.amount));
    }

    public boolean isGreaterThanOrEqual(Money other) {
        return amount.compareTo(other.amount) >= 0;
    }

    public BigDecimal asBigDecimal() {return amount;}
    public double asDouble() {return amount.doubleValue();}
    public String asString() {
        return amount.toPlainString();
    }

    public Money multiply(int x) {
        return new Money(amount.multiply(new BigDecimal(x)));
    }
}

