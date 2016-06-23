package orion.rssreader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ho Vu Anh Khoa on 23/06/2016.
 */
public class SubscribedFolder extends SubscribedItem {
    String mTitle;
    Map<String,SubscribedItem> itemList = new HashMap<>();

    public SubscribedFolder(String title) {
        this.mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public boolean addItem(SubscribedItem item){
        if(item != null && !itemList.containsKey(item.getName())){
            itemList.put(item.getName(),item);
            item.setParentItem(this);
            return true;
        }
        return false;
    }

    public boolean removeItem(String name){
        if(itemList.containsKey(name)){
           itemList.remove(name);
            return true;
        }
        return false;
    }

    public SubscribedItem getItem(String name){
        if(itemList.containsKey(name)){
            return itemList.get(name);
        }
        return null;
    }

    public List<SubscribedItem> getItemsList(){
        return new ArrayList<>(itemList.values());
    }

    @Override
    public String getName() {
        return getTitle();
    }
}
