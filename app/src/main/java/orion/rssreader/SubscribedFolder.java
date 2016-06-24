package orion.rssreader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubscribedFolder extends SubscribedItem {
    String mTitle;
    Map<String, SubscribedItem> mChildren;

    public SubscribedFolder(String title) {
        this.mTitle = title;
        mChildren = new HashMap<>();
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public boolean addItem(SubscribedItem item) {
        if (item != null && !mChildren.containsKey(item.getName())) {
            mChildren.put(item.getName(), item);
            item.setParent(this);
            return true;
        }
        return false;
    }

    public boolean removeItem(String name) {
        if (mChildren.containsKey(name)) {
            mChildren.remove(name);
            return true;
        }
        return false;
    }

    public SubscribedItem getItem(String name) {
        if (mChildren.containsKey(name)) {
            return mChildren.get(name);
        }
        return null;
    }

    public List<SubscribedItem> getItemsList() {
        return new ArrayList<>(mChildren.values());
    }

    @Override
    public String getName() {
        return getTitle();
    }
}
