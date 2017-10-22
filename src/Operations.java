import java.io.File;
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

    public String getUserID(String surname) {
        System.out.println(surname);
        for (int i = 0; i < people_data.size(); i++) {
            if (people_data.get(i).split(",")[2].equals(surname)) {
                return people_data.get(i).split(",")[0];
            }
        }
        return "There is no user with this surname";
    }

    public List<String> getFriendsByID(String userID) {
        List<String> list = new ArrayList<String>();
        List<String> results = new ArrayList<String>();
        for (int i = 0; i < friends_data.size(); i++) {
            if (friends_data.get(i).split(",")[0].equals(userID)) {
                list.add(friends_data.get(i).split(",")[1]);
            }
        }
        for (int i = 0; i < people_data.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (people_data.get(i).split(",")[0].equals(list.get(j))) {
                    results.add(people_data.get(i).split(",")[2]);
                }
            }
        }
        return results;
    }

    public List<String> getPeopleByCity(String city) {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < people_data.size(); i++) {
            if (people_data.get(i).split(",")[5].equals(city)) {
                list.add(people_data.get(i).split(",")[2]);
            }
        }
        return list;
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
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            try {
                Date date1 = sdf.parse(first[3]);
                Date date2 = sdf.parse(second[3]);
                if (date1.compareTo(date2) == 0) { //Dates
                    if (first[2].compareTo(second[2]) == 0) { //Surname
                        return first[1].compareTo(second[1]);
                    }
                    return first[2].compareTo(second[2]);
                }
                return date1.compareTo(date2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return 0;
        }
    };
}
