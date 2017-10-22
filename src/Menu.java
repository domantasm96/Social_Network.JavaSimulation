import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

    public Menu(){
        Operations operations = new Operations();
        DefaultMeniuText();
        Scanner input = new Scanner(System.in);
        Scanner input_path;
        int choice = -1;
        while(choice != 0){
            try{
                choice = Integer.parseInt(input.nextLine());
                switch(choice){
                    case 1:
                        input_path = new Scanner(System.in);
                        System.out.print("Path to the people file: ");
                        operations.upload_people(input_path.nextLine());
                        DefaultMeniuText();
                        break;
                    case 2:
                        input_path = new Scanner(System.in);
                        System.out.print("Path to the friends file: ");
                        operations.upload_friends(input_path.nextLine());
                        DefaultMeniuText();
                        break;
                    case 3:
                        operations.print(Operations.people_data);
                        DefaultMeniuText();
                        break;
                    case 4:
                        operations.print(Operations.friends_data);
                        DefaultMeniuText();
                        break;
                    case 5:
                        input_path = new Scanner(System.in);
                        System.out.print("Enter a surname: ");
                        String userID = operations.getUserID(input_path.nextLine());
                        System.out.println(operations.getFriendsByID(userID));
                        DefaultMeniuText();
                        break;
                    case 6:
                        input_path = new Scanner(System.in);
                        System.out.print("Enter a city name: ");
                        System.out.println(operations.getPeopleByCity(input_path.nextLine()));
                        DefaultMeniuText();
                        break;
                    case 7:
                        input_path = new Scanner(System.in);
                        System.out.print("Enter D1 and D2 dates: ");
                        String dates = input_path.nextLine();
                        operations.print(operations.FilterAndSortData(dates));
                        DefaultMeniuText();
                        break;
                    default:
                        System.out.println("An unknow error has occured.");
                }
            }
            catch(NumberFormatException e){
                System.out.println("wrong");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

    public static void DefaultMeniuText(){
        System.out.println("\n************Social Network Meniu***********");
        System.out.println("[1] To upload the people file");
        System.out.println("[2] To upload the friend file");
        System.out.println("[3] Print all People");
        System.out.println("[4] Print all Friends");
        System.out.println("[5] Retrieve friends by surname");
        System.out.println("[6] Retrieve all people who born in the same city");
        System.out.println("[7] Retrieve all people who born between D1 and D2 dates and sort data by birthday, surname, name");
        System.out.println("[0] To exit the program");
    }
}