import java.util.ArrayList;

public class person {
    String name;
    int birthYear;
    int age;
    ArrayList<FamilyTree> parents;

    public person(String name, int birthYear, int age){
        this.name = name;
        this.birthYear = birthYear;
        this.age = age;
    }
    public person(String name, int birthYear, int age, ArrayList<FamilyTree> parents){
        this.name = name;
        this.birthYear = birthYear;
        this.age = age;
        this.parents = parents;
    }
}
