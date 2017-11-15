import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Operations {
    public static List<String> people_data = new ArrayList<String>();
    public static List<String> friends_data = new ArrayList<String>();

    public Operations() {
        //This constructor is only for testing part. It automatically upload people and friends data to the lists.//
        add_people("People");
        add_friends("Friends");
//        add_people("8people");
//        add_people("17people");
//        add_friends("19friends");
//        add_friends("33friends");
//        add_friends("33friends");
    }

    public void add_people(String path) {
        try {
            Scanner sc_people = new Scanner(new File(path));
            String people_header = sc_people.nextLine();
            while (sc_people.hasNextLine()) {
                people_data.add(sc_people.nextLine());
            }
        } catch (IOException e) {
            System.out.println("\n" + path + " this path doesen't exist. Try again and make sure that path is written correctly");
        }
    }

    public void add_friends(String path) {
        try {
            Scanner sc_friends = new Scanner(new File(path));
            String friends_header = sc_friends.nextLine();
            while (sc_friends.hasNextLine()) {
                friends_data.add(sc_friends.nextLine());
            }
        } catch (IOException e) {
            System.out.println("\n" + path + " this path doesen't exist. Try again and make sure that path is written correctly");
        }
    }

    public void print(List<String> data) {
            if (data.size() > 0) {
                for (int i = 0; i < data.size(); i++) {
                    System.out.println(data.get(i));
                }
            } else {
                System.out.println("Is empty");
            }
    }

    public ArrayList<String> getUserID(String surname) {
        ArrayList user_id= new ArrayList<String>();
        for (int i = 0; i < people_data.size(); i++) {
            if (people_data.get(i).split(",")[2].equals(surname)) {
                //In case there are multiple people with the same surname
                user_id.add(people_data.get(i).split(",")[0]);
//                return people_data.get(i).split(",")[0];
            }
        }
        return user_id;
//        return "There is no user with this surname";
    }

    public void getFriendsByID(ArrayList<String> userID) {
        System.out.println(userID);
        List<String> list = new ArrayList<String>();
        List<String> results = new ArrayList<String>();
        for(int i= 0; i < userID.size(); i++){
            for (int j = 0; j < friends_data.size(); j++) {
                if (friends_data.get(j).split(",")[0].equals(userID.get(i))) {
                    list.add(userID.get(i) + " "+friends_data.get(j).split(",")[1]);
                }
                else if(friends_data.get(j).split(",")[1].equals(userID.get(i))){
                    list.add(userID.get(i) + " "+friends_data.get(j).split(",")[0]);
                }
            }
        }
        String user = "";
        String lastname;
        for (int i = 0; i < people_data.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                user = list.get(j).split(" ")[0];
                lastname = people_data.get(i).split(",")[2];
                if (people_data.get(i).split(",")[0].equals(list.get(j).split(" ")[1])) {
                    System.out.println(user+": "+people_data.get(i).split(",")[0]+ " "+lastname);
                    results.add(people_data.get(i).split(",")[2]);
                }
            }
        }
    }

    public void getPeopleByCity(String city) {
        HashSet<String> list = new HashSet();
        for (int i = 0; i < people_data.size(); i++) {
            if (people_data.get(i).split(",")[5].equals(city)) {
                list.add("id: "+people_data.get(i).split(",")[0] + "; Surname: "+people_data.get(i).split(",")[2]);
            }
        }
        List<String> printList = new ArrayList<String>(list);
        for(int i = 0; i < printList.size(); i++){
            System.out.println(printList.get(i));
        }
    }

    public List<String> FilterAndSortData(String date) throws ParseException {
        //12-09-1982 15-04-1992
        List<String> list = new ArrayList<String>();
        String birthdate;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date date1 = sdf.parse(date.split(" ")[0]);
        Date date2 = sdf.parse(date.split(" ")[1]);
        Date date3;
        for (int i = 0; i < people_data.size(); i++) {
            birthdate = people_data.get(i).split(",")[3];
            date3 = sdf.parse(birthdate);
            if (date3.compareTo(date1) >= 0) {
                if (date3.compareTo(date2) <= 0) {
                    list.add(people_data.get(i));
                }
            }
        }
        list.sort(comp);
        return list;
    }

    private Comparator<String> comp = new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            String[] first = o1.split(",");
            String[] second = o2.split(",");
            if(first[5].compareTo(second[5]) == 0){//Birthplace
                if (first[2].compareTo(second[2]) == 0) {
                        return first[1].compareTo(second[1]);//Name
                    }
                    return first[2].compareTo(second[2]); //Surname
            }
            return first[5].compareTo(second[5]);
//            String[] first = o1.split(",");
//            String[] second = o2.split(",");
//            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//            try {
//                Date date1 = sdf.parse(first[3]);
//                Date date2 = sdf.parse(second[3]);
//                if (date1.compareTo(date2) == 0) { //Dates
//                    if (first[2].compareTo(second[2]) == 0) { //Surname
//                        return first[1].compareTo(second[1]);
//                    }
//                    return first[2].compareTo(second[2]);
//                }
//                return date1.compareTo(date2);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            return 0;
        }
    };

    public void ReadResidentialFile() throws FileNotFoundException {
        List<String> residential_data = new ArrayList<String>();
        List<String> hometowns = new ArrayList<String>();
        Scanner sc = new Scanner(new File("residential.txt"));
        while (sc.hasNextLine()) {
            residential_data.add(sc.nextLine());
        }
        for(int i=0; i < residential_data.size(); i++){
            for(int j = 0; j < people_data.size(); j++){
                if(residential_data.get(i).equals(people_data.get(j).split(",")[0])){
                    System.out.println(residential_data.get(i) + " hometown: "+people_data.get(j).split(",")[6]);
                    hometowns.add(people_data.get(j).split(",")[6]);
                }
            }
        }
        System.out.println();
        HashSet home = new HashSet(hometowns);
        for(int i = 0; i < people_data.size();i++){
            for(int j=0 ; j<home.size(); j++){
                if(people_data.get(i).split(",")[5].equals(hometowns.get(j))){
                    System.out.println(people_data.get(i).split(",")[1]+" "+people_data.get(i).split(",")[2] + " "
                            +people_data.get(i).split(",")[5] + " "+ people_data.get(i).split(",")[7]);
                }
            }
        }
    }

    public void UsersProfiles(){
        List films = getAllFilmsNames();
        List<String> temp = new ArrayList<String>();
        for(int i = 0; i < films.size(); i++){
            for(int j=0; j < people_data.size(); j++){
                if(people_data.get(j).split(",")[9].equals(films.get(i))){
                    temp.add(people_data.get(j).split(",")[0]);
                }
            }
            if(temp.size() >= 2){
                System.out.println("***************"+films.get(i)+"***************");
                for(int j = 0; j < temp.size(); j++){
                    System.out.print(temp.get(j) + " ");
                }
                System.out.println();
            }
            temp = new ArrayList<String>();

        }
//        for(int i = 0; i < films.size(); i++){
//            System.out.println("***************"+films.get(i)+"***************");
//            for(int j=0; j < people_data.size(); j++){
//                for(int k = 0; k < people_data.get(j).split(",")[9].split(";").length; k++){
//                    if(people_data.get(j).split(",")[9].split(";")[k].equals(films.get(i))){
//                        System.out.print(people_data.get(j).split(",")[0] +" ");
//                    }
//                }
//            }
//            System.out.println();
//        }
    }

    public List getAllFilmsNames(){
        HashSet films = new HashSet();
        for(int i=0; i < people_data.size(); i++){
            films.add(people_data.get(i).split(",")[9]);
        }
//        for(int i=0; i < people_data.size(); i++){
//            for(int j = 0; j < people_data.get(i).split(",")[9].split(";").length; j++){
//                films.add(people_data.get(i).split(",")[9].split(";")[j]);
//        }
        return new ArrayList<String>(films);
    }
}
