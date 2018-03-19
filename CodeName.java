public class CodeName {
    String code, name;

    public CodeName(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() { return code; }
    public String getName() { return name; }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!CodeName.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final CodeName other = (CodeName) obj;
        return code.equals(other.code);
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }
}