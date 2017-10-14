package demo.mob.player.inventory;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

    private List<String> inventory = new ArrayList<>();

    public void add(@NotNull String item) {
        inventory.add(item);
    }

    public boolean contains(@NotNull String item) {
        if(inventory.contains(item))
            return true;
        return false;
    }

    public boolean remove(@NotNull String itemName) {
        if(inventory.contains(itemName)) {
            int index = inventory.indexOf(itemName);
            System.out.println(inventory.remove(index));
            return true;
        }
        return false;
    }

    public int getCount(@NotNull String item) {
        int count = 0;
        List inventory = new ArrayList<>(this.inventory);
        while (inventory.contains(item)) {
            inventory.remove(item);
            count++;
        }
        return count;
    }
}
