package demo.mob.player.inventory;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

    private List<String> items = new ArrayList<>();

    public void add(@NotNull String item) {
        items.add(item);
    }

    public boolean contains(@NotNull String item) {
        if(items.contains(item))
            return true;
        return false;
    }

    public boolean remove(@NotNull String itemName) {
        if(items.contains(itemName)) {
            int index = items.indexOf(itemName);
            items.remove(index);
            return true;
        }
        return false;
    }

    public int getCount(@NotNull String item) {
        int count = 0;
        List inventory = new ArrayList<>(this.items);
        while (inventory.contains(item)) {
            inventory.remove(item);
            count++;
        }
        return count;
    }
}
