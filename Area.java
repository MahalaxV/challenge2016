import java.util.*;

public class Area {
    Area parent;
    CodeName codeName;
    List<Area> subAreas;

    public Area() {
        parent = null;
        codeName = null;
        subAreas = new ArrayList<>();
    }

    public Area(Area parent, CodeName codeName) {
        this.parent = parent;
        this.codeName = codeName;
        subAreas = new ArrayList<>();
    }

    public CodeName codeName() {
        return codeName;
    }

    public Area parent() {
        return parent;
    }

    public Area addSubAreaIfNotExists(CodeName codeName) {
        for(Area subArea: subAreas) {
            if(codeName.equals(subArea.codeName()))
                return subArea;
        }
        Area newArea = new Area(this, codeName);
        subAreas.add(newArea);
        return newArea;
    }

    public Area findArea(String areaStr) {
        String[] hierarchy = areaStr.split("-");
        String reversedString = hierarchy[hierarchy.length - 1];
        for(int i = hierarchy.length - 2; i >= 0; i--) {
            reversedString = reversedString + "-" + hierarchy[i];
        }
        return findAreaInternal(reversedString);
    }

    Area findAreaInternal(String areaStr) {
        int hyphenIndex = areaStr.indexOf("-");
        if(hyphenIndex < 0) {
            for(Area subArea: subAreas) {
                if(subArea.codeName().getCode().equals(areaStr) ||
                        subArea.codeName().getName().equals(areaStr)) {
                    return subArea;
                }
            }
        } else {
            String searchStr = areaStr.substring(0, hyphenIndex);
            String remStr = areaStr.substring(hyphenIndex + 1);
            for(Area subArea: subAreas) {
                if(subArea.codeName().getCode().equals(searchStr) ||
                        subArea.codeName().getName().equals(searchStr)) {
                    return subArea.findAreaInternal(remStr);
                }
            }
        }
        return null;
    }
}