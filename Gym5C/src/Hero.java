import java.util.Scanner;



public class Hero extends Role{
    public void printHeroMsg() {
        System.out.println("Hero is ready for battle!");
    }

    public void command() {
        Scanner cin = new Scanner(System.in);

        String input = cin.nextLine();
        System.out.println("Hero received command: " + input);

        if("1".equals(input)){
            String direction = cin.nextLine();
            move(direction);
        } 
        else if("2".equals(input)){
            String direction = cin.nextLine();
            attack(direction);
        }

        cin.close();
    }

}
