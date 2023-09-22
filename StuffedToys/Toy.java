package StuffedToys;

public class Toy {
  private int id;
  private String name;
  private int weight;
  private int quantity;

  public Toy(int id, String name, int weight, int quantity) {
    this.id = id;
    this.name = name;
    this.weight = weight;
    this.quantity = quantity;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getWeight() {
    return weight;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }

  public void decreaseQuantity() {
    if (quantity > 0) {
      quantity--;
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Toy toy = (Toy) o;
    return id == toy.id;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + id;
    return result;
  }

}
