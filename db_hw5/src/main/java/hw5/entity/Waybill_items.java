package hw5.entity;

import org.jetbrains.annotations.NotNull;

public final class Waybill_items {
    private int id;
    private int waybill;
    private int price;
    private int product;
    private int number;
    public Waybill_items(int id, int waybill, int price, int product, int number) {
        this.id = id;
        this.waybill = waybill;
        this.price = price;
        this.product = product;
        this.number = number;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWaybill() {
        return waybill;
    }

    public void setWaybill(int waybill) {
        this.waybill = waybill;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getProduct() {
        return product;
    }

    public void setProduct(int product) {
        this.product = product;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public @NotNull String toString() {
        return "Person{" +
                "id=" + id +
                ", waybill='" + waybill +
                ", price=" + price +
                ", product=" + product +
                ", number=" + number +
                '}';
    }
}
