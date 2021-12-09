package proj;

public class Room {
    private int id;
    private String hotel;
    private String type;
    private int price;
    private int available;

    public Room(int id, String hotel, String type, int price, int available) {
        this.id = id;
        this.hotel = hotel;
        this.type = type;
        this.price = price;
        this.available = available;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public int getId() {
        return id;
    }

    public String getHotel() {
        return hotel;
    }

    public String getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }

    public int getAvailable() {
        return available;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", hotel='" + hotel + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", available=" + available +
                '}';
    }
}
