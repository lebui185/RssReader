package orion.rssreader;

/**
 * Created by Ho Vu Anh Khoa on 23/06/2016.
 */
public abstract class SubscribedItem {

    SubscribedItem parentItem;

    public String getPath() {
        if (parentItem != null) {
            return parentItem.getPath() + "\\" + getName();
        }
        return getName();
    }

    public SubscribedItem getParentItem() {
        return parentItem;
    }

    public void setParentItem(SubscribedItem parentItem) {
        this.parentItem = parentItem;
    }

    public abstract String getName();
}
