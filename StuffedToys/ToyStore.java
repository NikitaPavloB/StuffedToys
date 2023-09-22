package StuffedToys;

import java.io.*;
import java.util.*;

public class ToyStore {
  private List<Toy> toyList = new ArrayList<>();
  private static final String DATA_FILE = "toys.txt";

  public ToyStore() {
    loadToys();
    addInitialToysIfNeeded();
  }

  private void addInitialToysIfNeeded() {
    boolean constructorExists = false;
    boolean robotExists = false;
    boolean dollExists = false;

    for (Toy toy : toyList) {
      if (toy.getId() == 1) {
        constructorExists = true;
      } else if (toy.getId() == 2) {
        robotExists = true;
      } else if (toy.getId() == 3) {
        dollExists = true;
      }
    }

    if (!constructorExists) {
      addToy(new Toy(1, "Конструктор", 2, 10));
    }
    if (!robotExists) {
      addToy(new Toy(2, "Робот", 2, 8));
    }
    if (!dollExists) {
      addToy(new Toy(3, "Кукла", 6, 5));
    }
  }

  public void addToy(Toy toy) {
    // Добавляем игрушку только если её нет в списке
    if (!toyList.contains(toy)) {
      toyList.add(toy);
    }
  }

  public void updateToyWeight(int toyId, int newWeight) {
    if (newWeight < 0) {
      throw new IllegalArgumentException("Вес игрушки не может быть отрицательным.");
    }

    boolean toyFound = false;
    for (Toy toy : toyList) {
      if (toy.getId() == toyId) {
        toy.setWeight(newWeight);
        toyFound = true;
        break;
      }
    }

    if (!toyFound) {
      throw new IllegalArgumentException("Игрушка с указанным ID не найдена.");
    }
  }

  public void removeToy(int toyId) {
    Iterator<Toy> iterator = toyList.iterator();
    while (iterator.hasNext()) {
      Toy toy = iterator.next();
      if (toy.getId() == toyId) {
        iterator.remove();
        break;
      }
    }
  }

  public Toy selectRandomToy() {
    if (toyList.isEmpty()) {
      return null;
    }

    Random random = new Random();
    int randomIndex = random.nextInt(toyList.size());
    return toyList.get(randomIndex);
  }

  public String getToysInfo() {
    StringBuilder info = new StringBuilder("Информация о всех игрушках:\n");
    for (Toy toy : toyList) {
      info.append("ID: ").append(toy.getId()).append(", Имя: ").append(toy.getName()).append(", Вес: ")
          .append(toy.getWeight()).append(", Количество: ").append(toy.getQuantity()).append("\n");
    }
    return info.toString();
  }

  public void drawToys(int count) {
    try (FileWriter writer = new FileWriter("draw_results.txt")) {
      for (int i = 0; i < count; i++) {
        Toy selectedToy = selectRandomToy();

        if (selectedToy != null) {
          writer.write("Выбранная игрушка: " + selectedToy.getName() + "\n");
          selectedToy.decreaseQuantity();
        } else {
          writer.write("Игрушка не выбрана\n");
        }
      }
      System.out.println("Розыгрыш завершен. Результаты записаны в файл 'draw_results.txt'.");
    } catch (IOException e) {
      System.err.println("Ошибка при работе с файлом: " + e.getMessage());
    }
  }

  public void saveToys() {
    try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE))) {
      for (Toy toy : toyList) {
        writer.println(toy.getId() + "," + toy.getName() + "," + toy.getWeight() + "," + toy.getQuantity());
      }
      System.out.println("Данные об игрушках успешно сохранены в файле " + DATA_FILE);
    } catch (IOException e) {
      System.err.println("Ошибка при сохранении данных: " + e.getMessage());
    }
  }

  public void loadToys() {
    try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        if (parts.length == 4) {
          int id = Integer.parseInt(parts[0]);
          String name = parts[1];
          int weight = Integer.parseInt(parts[2]);
          int quantity = Integer.parseInt(parts[3]);
          Toy toy = new Toy(id, name, weight, quantity);
          toyList.add(toy);
        }
      }
      System.out.println("Данные об игрушках успешно загружены из файла " + DATA_FILE);
    } catch (IOException e) {
      System.err.println("Ошибка при загрузке данных: " + e.getMessage());
    }
  }
}
