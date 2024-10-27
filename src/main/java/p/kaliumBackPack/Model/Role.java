package p.kaliumBackPack.Model;

public class Role {
    private final String name;
    private final int rows;
    private final int maxBackpacks;

    public Role(String name, int rows, int maxBackpacks) {
        this.name = name;
        this.rows = rows;
        this.maxBackpacks = maxBackpacks;
    }

    public String getName() {
        return name;
    }

    public int getRows() {
        return rows;
    }

    public int getMaxBackpacks() {
        return maxBackpacks;
    }
}
