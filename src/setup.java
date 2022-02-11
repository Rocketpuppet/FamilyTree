import java.io.*;
import java.util.*;

public class setup {
    HashMap<String, FamilyTree> existingChildrenMap = new HashMap<>();

    public File parseFile(String fileName){
        return new File(fileName);
    }

    public FamilyTree createFamily(String file) throws IOException {
        File familyPage = parseFile(file);
        FileInputStream fLN = new FileInputStream(familyPage);
        BufferedReader familyReader = new BufferedReader(new InputStreamReader(fLN));
        String currentLine = familyReader.readLine();
        FamilyTree currentTree = null;
        FamilyTree headTree = null;
        while (currentLine != null) {
            String[] personData = currentLine.split(",");
            String name = personData[0];
            ArrayList<String> childNames = new ArrayList<>();
            for (Map.Entry<String,FamilyTree> child : existingChildrenMap.entrySet()) {
                if (child.getKey().equals(name)) {
                    currentTree = createFamily(currentLine, name);
                    for(FamilyTree parent : currentTree.familyMember.parents){
                        for(FamilyTree small : parent.children){
                            if(currentTree.familyMember.name.equals(small.familyMember.name)&&!parent.children.contains(currentTree)){
                                parent.children.remove(small);
                            }
                        }
                        if(!parent.children.contains(currentTree)){
                            parent.children.add(currentTree);
                        }
                    }
                }
            }


            int birthYear = Integer.parseInt(personData[1]);
            int age = Integer.parseInt(personData[2]);

            ArrayList<FamilyTree> children = new ArrayList<>();
            person familyMember = new person(name, birthYear, age);
            for (int i = 3; i < personData.length; i++) {
                childNames.add(personData[i]);
            }
            if(currentTree==null) {
                currentTree = new FamilyTree(familyMember, children);
            }
            for (String child : childNames) {
                ArrayList<FamilyTree> parent = new ArrayList<>();
                person newFamilyMember = new person(child, 0, 0, parent);
                FamilyTree newMember = new FamilyTree(newFamilyMember);
                parent.add(currentTree);
                if (existingChildrenMap.size() == 0) {
                    existingChildrenMap.put(child, newMember);
                    children.add(newMember);
                } else {
                    if (existingChildrenMap.containsKey(child)) {
                        children.add(existingChildrenMap.get(child));
                        existingChildrenMap.get(child).familyMember.parents.add(currentTree);
                    } else {
                        existingChildrenMap.put(child, newMember);
                        children.add(newMember);
                    }
                }
            }

                if (headTree == null) {
                    headTree = new FamilyTree(familyMember, children);
                }
                currentLine = familyReader.readLine();
                currentTree = null;
            }
        return headTree;
        }


    public FamilyTree createFamily(String lineData, String childName) {

        FamilyTree currentTree = null;

        String[] personData = lineData.split(",");
        String name = personData[0];
        if(!name.equals(childName)){
            return null;
        }
        int birthYear = Integer.parseInt(personData[1]);
        int age = Integer.parseInt(personData[2]);
        ArrayList<FamilyTree> children = new ArrayList<>();
        person familyMember = new person(name, birthYear, age, existingChildrenMap.get(name).familyMember.parents);
        currentTree = new FamilyTree(familyMember, children);

        ArrayList<String> childNames = new ArrayList<>();
        for(int i=3;i<personData.length;i++){
            childNames.add(personData[i]);
        }

        for(String cName : childNames){
            ArrayList<FamilyTree> parents = new ArrayList<>();
            parents.add(currentTree);
            person childMember = new person(cName,0,0,parents);
            FamilyTree child = new FamilyTree(childMember);
            children.add(child);
        }




        if (familyMember.name.equals(childName)) {
            currentTree = new FamilyTree(familyMember, children);
            return currentTree;
        }
        return null;
    }
}
