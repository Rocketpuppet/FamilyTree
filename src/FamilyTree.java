import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FamilyTree {
    person familyMember;
    ArrayList<FamilyTree> children;

    public FamilyTree(person person){
        this.familyMember = person;
    }
    public FamilyTree(person person, ArrayList<FamilyTree> children){
        this.familyMember = person;
        this.children = children;
    }

    public boolean isLeaf() {
        if (this.children.size()==0) {
            return true;
        }
        return false;
    }


    public ArrayList<String> getContent(FamilyTree currentLeaf){
        ArrayList<String> treeContent = new ArrayList<>();
        treeContent.add(currentLeaf.familyMember.name);
        if (!currentLeaf.isLeaf()){
            for(FamilyTree leaf : currentLeaf.children){
                treeContent.addAll(leaf.getContent(leaf));
            }
        }
        return treeContent;
    }

    public int findOldest(){
        int oldest = familyMember.age;
        if(this.isLeaf()){
            return oldest;
        } else {
            for(FamilyTree child : this.children){
                int oldestAge = child.findOldest();
                if(oldest<oldestAge){
                    oldest = oldestAge;
                }
            }
            return oldest;
        }
    }

    public HashMap<person,Integer> getAges(){
        HashMap<person,Integer> ages = new HashMap<>();
        if(this.isLeaf()){
            if(this.familyMember.parents.size()>0){
                for(FamilyTree parent : familyMember.parents){
                    ages.put(parent.familyMember, parent.familyMember.age);
                }
            }
            ages.put(this.familyMember, this.familyMember.age);
        } else {
            for(FamilyTree leaf : this.children){
                    HashMap<person, Integer> childAges = new HashMap<>();
                    childAges = leaf.getAges();
                    for(Map.Entry<person, Integer> child : childAges.entrySet()){
                        ages.put(child.getKey(),child.getValue());
                }
                if(!ages.containsKey(this.familyMember)){
                    ages.put(this.familyMember, this.familyMember.age);
                }
                }
            }
        return ages;
    }

    public int avgAge(HashMap<person,Integer> familyAges){
        int avgAge = 0;
        for(Map.Entry<person, Integer> age : familyAges.entrySet()){
            avgAge = avgAge+age.getValue();
        }
        avgAge = avgAge/familyAges.size();
        return avgAge;
    }

    public boolean isFirstMember(){
        if(this.familyMember.parents==null){
            return true;
        }
        return false;
    }

    public ArrayList<FamilyTree> getLineage(FamilyTree parent, int childCount) {
        int childCounter = 1;
        ArrayList<FamilyTree> lineage = new ArrayList<>();
        ArrayList<ArrayList<FamilyTree>> lineages = new ArrayList<>();
        if (this.isFirstMember()||this.children.size()>0) {
            if(this.familyMember== parent.familyMember){
                for (FamilyTree child : this.children) {
                    lineage = child.getLineage(this, childCounter);
                    lineages.add(lineage);
                    childCounter+=1;
                }
            }


        } else {
            int pi = this.getParentIndex(parent);
            lineage.add(parent);
            lineage.add(this);
            if (pi + 1 >= this.familyMember.parents.size()) {
                return lineage;
            } else {
                if (childCount != parent.children.size()) {
                    lineages.add(this.familyMember.parents.get(pi + 1).getLineage(this.familyMember.parents.get(pi + 1), childCounter + 1));
                }
            }

        }
        if (this.isFirstMember()) {
            for (ArrayList<FamilyTree> familyLines : lineages) {
                System.out.println("Lineages of " + this.familyMember.name);
                for (FamilyTree familyMember : familyLines) {

                    System.out.println(familyMember.familyMember.name);
                }
            }

        }

        return lineage;
    }

    public int getParentIndex(FamilyTree parent){
        if(this.familyMember.parents.contains(parent)){
            return this.familyMember.parents.indexOf(parent);
        } else {
            return 0;
        }
    }
}
