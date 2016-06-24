package orion.rssreader;

public abstract class SubscribedItem {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    protected int id;
    protected SubscribedItem parent;

    public String getPath() {
        if (parent != null) {
            return parent.getPath() + "\\" + getName();
        }
        return getName();
    }

    public SubscribedItem getParent() {
        return parent;
    }

    public void setParent(SubscribedItem parent) {
        this.parent = parent;
    }

    public abstract String getName();
}
