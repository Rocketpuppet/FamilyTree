import java.io.IOException;
import java.util.Map;

public class main {
    public static void main(String[] args) throws IOException {
        setup familyConstructor = new setup();
        FamilyTree tree = familyConstructor.createFamily("C:\\Users\\user\\Downloads\\TheFamilyTree\\src\\familyFiles\\test_family.csv");
        tree.getLineage(tree,0);
    }
}
