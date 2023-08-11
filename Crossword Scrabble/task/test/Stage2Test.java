import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

import java.util.ArrayList;
import java.util.List;

public class Stage2Test  extends StageTest {
    String tray ="";
    List<String> bags = new ArrayList<>();
    @DynamicTest
    CheckResult test1() {
        TestedProgram pr = new TestedProgram();
        String output = pr.start("./src/scrabbleTilesDistribution.txt");
        String[] p1 = output.trim().replace(System.getProperty("line.separator")+System.getProperty("line.separator"),"\n").split("\n");
        String bag ="AAAAAAAAABBCCDDDDEEEEEEEEEEEEFFGGGHHIIIIIIIIIJKLLLLMMNNNNNNOOOOOOOOPPQRRRRRRSSSSTTTTTTUUUUVVWWXYYZ**";

        bags.add(bag);
        ArrayList<String> p = new ArrayList<>();

        for (String s : p1) {
            if (s.trim().length() != 0) {
                p.add(s);
            }
        }

        if (p.size() < 6) {
            throw new WrongAnswer("""
                    Your output is not formatted correctly. It should be as follows:
                    "Welcome to Crossword Scrabble"
                    Bag: 'tiles distribution'
                    What would you like to do?
                    A. Shuffle
                    B. Pick
                    C. Exit""");
        }

        if(!p.get(0).toLowerCase().trim().contains("Welcome to Crossword Scrabble".toLowerCase())){
            throw new WrongAnswer("Your output should contain the game title " +
                    "\"Welcome to Crossword Scrabble\"");
        }
        if (p.get(1).equalsIgnoreCase("tray: ") ){
            throw new WrongAnswer("Your program should not print an empty tray.");
        } else if (!p.get(1).startsWith("Bag: ") ){
            throw new WrongAnswer("Your output for bag should start with " +
                    "\"Bag: \",");
        }
        if (p.get(1).length()< 105){
            throw new WrongAnswer("Your program generates less tiles than expected. Your program should read  the data from the file correctly");
        }
        if (p.get(1).length() > 105){
            throw new WrongAnswer("Your program generates more more tiles than expected. Your program should read the data from the file correctly ");
        }
        if (!checkBagContents(p.get(1), bag)){
            throw new WrongAnswer("Your program does not read tiles distribution correctly.");
        }

        if (!p.get(2).equalsIgnoreCase("What would you like to do?")){
            throw new WrongAnswer("Wrong menu title expected 'What would you like to do?'");
        }

        if (!p.get(3).equalsIgnoreCase("A. Shuffle")){
            throw new WrongAnswer("Wrong menu option: Menu 'A' is not correct. Expected 'A. Shuffle'");
        }

        if (!p.get(4).equalsIgnoreCase("B. Pick")){
            throw new WrongAnswer("Wrong menu option: Menu 'B' is not correct. Expected 'B. Pick'");
        }

        if (!p.get(5).equalsIgnoreCase("C. Exit")){
            throw new WrongAnswer("Wrong menu option: Menu 'C' is not correct. Expected 'C. Exit'");
        }
        testShuffleMenu("A",pr,bags);
        testShuffleMenu("a",pr,bags);
        testShuffleMenu("Shuffle",pr,bags);
        testShuffleMenu("shuffle",pr,bags);

        testPickMenu("pick",pr,bags);

        testShuffleMenu("A",pr,bags);
        testPickMenu("pick",pr,bags);

        testShuffleMenu("A",pr,bags);
        testPickMenu("pick",pr,bags);

        testShuffleMenu("A",pr,bags);
        testPickMenu("b",pr,bags);

        testShuffleMenu("A",pr,bags);

        invalidCommand("E",pr);
        invalidCommand("B pick",pr);


        //Test empty bag
         for(int i =0; i<15;i++){
             testPickMenu("pick",pr,bags);
             testShuffleMenu("A",pr,bags);
        }

        //Test terminate with C
        testExitMenu("C",pr);
        //Test terminate with Exit
        TestedProgram pr1 = new TestedProgram();
        pr1.start("./src/scrabbleTilesDistribution.txt");
        testExitMenu("exIt",pr1);
        //Test terminate with Exit



        //test Invalid commands
        return CheckResult.correct();
    }

    private boolean checkBagContents(String bag1, String bag2) {
        int[] charCount1 = new int[27];
        int[] charCount2 = new int[27];

        String contents1 = bag1.toUpperCase().replace(" ", "").replace("BAG:", "");
        String contents2 = bag2.toUpperCase().replace(" ", "").replace("BAG:", "");

        for (int i=0; i < contents1.length(); i++){
            char c = contents1.charAt(i);
            if (c == '*'){
                charCount1[26]++;
                continue;
            }
            charCount1[(c-'A')]++;
        }

        for (int i=0; i < contents2.length(); i++){
            char c = contents2.charAt(i);
            if (c == '*'){
                charCount2[26]++;
                continue;
            }
            charCount2[(c-'A')]++;
        }

        for (int i=0; i < charCount1.length; i++){
            if (charCount1[i] != charCount2[i]){
                return false;
            }
        }

        return true;
    }

    Object[][] exitArray =  {
            {"c"},
            {"exIt"},
            {"C"}
    };

    @DynamicTest(data = "exitArray")
    CheckResult test4( String message) {
        TestedProgram pr2 = new TestedProgram();
        pr2.start("./src/scrabbleTilesDistribution.txt");
        testExitMenu(message,pr2);
        return CheckResult.correct();
    }
    Object[][] shuffleArray =  {
            {"a"},
            {"sHuffle"},
            {"A"}
    };

    @DynamicTest(data = "shuffleArray")
    CheckResult test5( String message) {
        String bag ="AAAAAAAAABBCCDDDDEEEEEEEEEEEEFFGGGHHIIIIIIIIIJKLLLLMMNNNNNNOOOOOOOOPPQRRRRRRSSSSTTTTTTUUUUVVWWXYYZ**";
        tray ="";
        bags.add(bag);
        TestedProgram pr2 = new TestedProgram();
        pr2.start("./src/scrabbleTilesDistribution.txt");
        testShuffleMenu(message,pr2,bags);
        return CheckResult.correct();
    }
    Object[][] pickArray =  {
            {"b"},
            {"pick"},
            {"picK"},
            {"B"}
    };
    @DynamicTest(data = "pickArray")
    CheckResult test6( String message) {
        String bag ="AAAAAAAAABBCCDDDDEEEEEEEEEEEEFFGGGHHIIIIIIIIIJKLLLLMMNNNNNNOOOOOOOOPPQRRRRRRSSSSTTTTTTUUUUVVWWXYYZ**";
        tray ="";
        bags.add(bag);
        TestedProgram pr2 = new TestedProgram();
        pr2.start("./src/scrabbleTilesDistribution.txt");
        testPickMenu(message,pr2,bags);
        return CheckResult.correct();
    }

    private void testShuffleMenu(String input, TestedProgram pr, List<String> previousBags){
        if(!pr.isFinished()){
            checkShuffleResponse(input, pr.execute(input), previousBags);
        }else{
            throw new WrongAnswer("Your program ended prematurely. Your program should terminate on Exit command");
        }
    }
    private void testPickMenu(String input, TestedProgram pr, List<String> previousBags){
        if(!pr.isFinished()){
            checkPickResponse(input, pr, previousBags);
        }else{
            throw new WrongAnswer("Your program ended prematurely. Your program should terminate on Exit command");
        }
    }
    private void testExitMenu(String input, TestedProgram pr){
        if(!pr.isFinished()){
            String output = pr.execute(input);
            if(output.contains("Invalid Command.")){
                throw new WrongAnswer("'" +input+ "' is a valid command");
            } else if(!output.trim().equals("Bye.")||!pr.isFinished()){
                throw new WrongAnswer("Your program should print 'Bye.' and terminate.");
            }
        }else{
            throw new WrongAnswer("Your program ended prematurely. Your program should terminate on Exit command");
        }
    }
    private static void invalidCommand(String input, TestedProgram pr){
        if(!pr.isFinished()){
            if(!pr.execute(input).contains("Invalid Command")){
                throw new WrongAnswer("Your program should not accept invalid commands.'" +input+ "' is not a valid command");
            }
        }else{
            throw new WrongAnswer("Your program ended prematurely. Your program should terminate on Exit command");
        }
    }
    private void checkShuffleResponse(String input, String output, List<String>  previousBags){
        String[] p1 = output.trim().replace(System.getProperty("line.separator")+System.getProperty("line.separator"),"\n").split("\n");
        ArrayList<String> p = new ArrayList<>();
        for (String s : p1) {
            if (s.trim().length() != 0) {
                p.add(s);
            }
        }
        checkOutputLength(p, 0);

        if(p.get(0).contains("Invalid Command.")){
            throw new WrongAnswer("'" +input+ "' is a valid command");
        }
        String bag;
        if(previousBags.size()!=0&&previousBags.get(previousBags.size()-1).length()==0){
            if(p.size() != 7){
                throw new WrongAnswer("Your program output is incorrect. Print 'The bag is empty.' Tray: Bag: Followed by menu items each on a new line");
            }
            if(!p.get(0).contains("The bag is empty.")){
                throw new WrongAnswer("Your program should print 'The bag is empty.' as your first line of output.");
            }
            return;

        } else if(tray.length()==0){
            if(p.size() != 5){
                throw new WrongAnswer("Your output is incorrect. Print Bag: Followed by menu items each on a new line");
            }
            if (p.get(0).length()< 105){
                throw new WrongAnswer("Your have less tiles in the bag than expected ");
            }
            if (p.get(0).length() > 105){
                throw new WrongAnswer("Your have more tiles in the bag than expected ");
            }
            bag = p.get(0).substring(5);
        }else{
            if(p.size() != 6){
                throw new WrongAnswer("Your program output is incorrect. Print Tray: Bag: Followed by menu items each on a new line");
            }
            if (p.get(1).length()+tray.length()< 105){
                throw new WrongAnswer("Your have less tiles in the bag than expected.");
            }
            if (p.get(1).length()+tray.length() > 105){
                throw new WrongAnswer("Your have more tiles in the bag than expected.");
            }
            bag = p.get(1).substring(5);
        }
        if(bag.length()!=2){
            for (String previousBag : previousBags) {
                if (previousBag.equalsIgnoreCase(bag)) {
                    throw new WrongAnswer("Your program is not shuffling the tiles");
                }
            }
        }

        previousBags.add(bag);
    }

    private void checkOutputLength(ArrayList<String> p, int maxIndexAccessed) {
        if (p.size() < maxIndexAccessed + 1){
            throw new WrongAnswer("Your output was shorter than expected. At least " + (maxIndexAccessed+1) +
                    " non-blank lines expected, but only " + p.size() + " found.");
        }
    }

    private void checkPickResponse(String input, TestedProgram pr, List<String>  previousBags){
        String[] p1 =pr.execute(input).trim().replace(System.getProperty("line.separator")+System.getProperty("line.separator"),"\n").split("\n");
        ArrayList<String> p = new ArrayList<>();
        String cTray =tray;
        for (String s : p1) {
            if (s.trim().length() != 0) {
                p.add(s);
            }
        }

        checkOutputLength(p, 0);

        if(p.get(0).contains("Invalid Command.")){
            throw new WrongAnswer("'" +input+ "' is a valid command");
        }
        if((previousBags.size() != 0) && (previousBags.get(previousBags.size() - 1).length() == 0)){
            if(p.size() != 7){
                throw new WrongAnswer("Your output is incorrect. Print 'No more tiles to pick.' Tray: Bag: Followed by menu items each on a new line");
            }
            if(!p.get(0).contains("No more tiles to pick.")){
                throw new WrongAnswer("Your program should print 'No more tiles to pick.' as your first line of output.");
            }
            return;

        }else if(p.size() != 6){
            throw new WrongAnswer("Your output is incorrect. Print Tray: Bag: Followed by menu items each on a new line");
        }
        if (p.get(0).length()+ p.get(1).length()< 111){
            throw new WrongAnswer("Your have less tiles than expected ");
        }
        if (p.get(0).length()+ p.get(1).length() > 111){
            throw new WrongAnswer("Your have more tiles than expected ");
        }
        String bag = p.get(1).substring(5);
        tray=p.get(0).substring(6);
        if(((cTray.length() + 7) != tray.length()) && (bag.length() != 0)){
            throw new WrongAnswer("Your program is picking more or less than 7 tiles. Only 7 tiles should be picked " +
                    "(or however many remain in the bag, if less than 7).");
        }
        for (String previousBag : previousBags) {
            if (previousBag.equalsIgnoreCase(bag)) {
                throw new WrongAnswer("Your program is not shuffling the tiles");
            }
        }
        previousBags.add(bag);
    }
}
