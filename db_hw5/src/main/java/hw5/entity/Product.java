package hw5.entity;

import org.jetbrains.annotations.NotNull;

public final class Product {
    private int id;
    private @NotNull String name;
    private int code;

    public Product(int id, @NotNull String name, int code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @NotNull String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public @NotNull String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code=" + code +
                '}';
    }
}
