import java.util.*;

public class Distributor {
    String name;
    List<Area> include, exclude;
    Distributor parent;

    public Distributor(String name) {
        this.name = name;
        include = new ArrayList<>();
        exclude = new ArrayList<>();
    }

    public Distributor(String name, Distributor parent) {
        this.name = name;
        this.parent = parent;
        include = new ArrayList<>();
        exclude = new ArrayList<>();
    }

    public boolean hasPermission(Area area) {
        if(area == null)
            return false;
        if(area.codeName() == null)
            return false;
        if(parent != null) {
            boolean parentPermission = parent.hasPermission(area);
            if(!parentPermission)
                return false;
        }
        for(Area excluded: exclude) {
            if(excluded.codeName().equals(area.codeName()))
                return false;
        }
        for(Area included: include) {
            if(included.codeName().equals(area.codeName()))
                return true;
        }
        return hasPermission(area.parent());
    }

    public String name() { return name; }

    public void include(Area r) {
        include.add(r);
    }

    public void exclude(Area r) {
        exclude.add(r);
    }
}

