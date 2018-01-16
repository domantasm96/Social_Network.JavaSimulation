import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/*
In Operations class all Social Network functions are implemented.
 */

public class Operations {
    public static List<String> people_data = new ArrayList<String>(); // List stores all people in the social network
    public static List<String> friends_data = new ArrayList<String>(); // List stores all friends in the social network
    public static Graph graph;

    public Operations() {
        add_people("People");
        add_friends("Friends");
    }

    /*
    Method add_people stores all people in the people_data list.
    Method arguments:
    1. Path or file name to the social network people data.
    Method working:
    1. As an input, all people file name is written into console. File must be in the same file directory as a project.
    2. While Scanner have next line - each line is added to people_data list.
    3. In order to prevent multiple the same people information, list is converted to the set and then convert to the list.
     */

    public void add_people(String path) {
        try {
            Scanner sc_people = new Scanner(new File(path));
            String people_header = sc_people.nextLine();
            while (sc_people.hasNextLine()) {
                people_data.add(sc_people.nextLine());
            }
            setUniquePeopleList();
        } catch (IOException e) {
            System.out.println("\n" + path + " this path doesen't exist. Try again and make sure that path is written correctly");
        }
    }

     /*
    Method add_friends stores all people in the friends_data list:
    1. As an input, friends file name is written into console. File must be in the same file directory as a project.
    2. While Scanner have next line - each line is added to friends_data list.
    3. In order to prevent multiple the same people information, list is converted to the set and then convert to the list.
     */

    public void add_friends(String path) {
        try {
            Scanner sc_friends = new Scanner(new File(path));
            String friends_header = sc_friends.nextLine();
            while (sc_friends.hasNextLine()) {
                friends_data.add(sc_friends.nextLine());
            }
            setUniqueFrienList();
        } catch (IOException e) {
            System.out.println("\n" + path + " this path doesen't exist. Try again and make sure that path is written correctly");
        }
    }

    private void setUniquePeopleList(){
        people_data = new ArrayList<String>(new HashSet<String>(people_data));
    }

    private void setUniqueFrienList(){
        friends_data = new ArrayList<String>(new HashSet<String>(friends_data));
    }

    /*
    Method print is
     */

    public void print(List<String> data) {
            if (data.size() > 0) {
                for (int i = 0; i < data.size(); i++) {
                    System.out.println(data.get(i));
                }
            } else {
                System.out.println("Is empty");
            }
    }

    /*
    Method getUserID returns person ID by surname.
    Method argument: Person surname.
    Method working: Traversing people_list list and comparing if people_list person matches given surname as an argument.
    Method returns: ArrayList of all people user_id with the same surname.
     */

    public ArrayList<String> getUserID(String surname) {

        ArrayList user_id= new ArrayList<String>();
        String person_surname;
        String person_id;

        for (int i = 0; i < people_data.size(); i++) {

            person_surname = people_data.get(i).split(",")[2];
            person_id = people_data.get(i).split(",")[0];

            if (person_surname.equals(surname)) {
                //In case there are multiple people with the same surname
                user_id.add(person_id);
            }
        }
        return user_id;
    }

    /*
    For each person which have the same userID as in the list, all his friends are printed in this order:
        userID: FriendID FriendSurname
    */
    public void getFriendsByID(ArrayList<String> userID) {
        System.out.println(userID);
        List<String> list = new ArrayList<String>();

        String friend_id;
        String friend_name;

        //Storing person name accoring his userID. This information is stored into list.
        for(int i= 0; i < userID.size(); i++){
            for (int j = 0; j < friends_data.size(); j++) {

                friend_id = friends_data.get(j).split(",")[0];
                friend_name = friends_data.get(j).split(",")[1];

                if (friend_id.equals(userID.get(i))) {
                    list.add(userID.get(i) + " "+friend_name);
                }
                else if(friend_name.equals(userID.get(i))){
                    list.add(userID.get(i) + " "+friend_id);
                }
            }
        }
        String person_id;
        String person_lastname;
        for (int i = 0; i < people_data.size(); i++) {
            for (int j = 0; j < list.size(); j++) {

                friend_id = list.get(j).split(" ")[0];
                person_lastname = people_data.get(i).split(",")[2];
                person_id = people_data.get(i).split(",")[0];

                if (person_id.equals(list.get(j).split(" ")[1])) {
                    System.out.println( friend_id+": "+person_id+ " "+person_lastname);
                }
            }
        }
    }

    private List<Integer> GetFriendsListByID(String user_id){
        List<Integer> friend_list = new ArrayList<Integer>();
        int person_number;
        for(int i = 0; i < friends_data.size(); i++){
            String first_id = friends_data.get(i).split(",")[0];
            String second_id = friends_data.get(i).split(",")[1];
            if (first_id.equals(user_id)) {
                person_number = getPersonNumber(second_id);
                friend_list.add(person_number);
            }
        }
        return friend_list;
    }

    private int getPersonNumber(String user_id){
        for(int i = 0; i < people_data.size(); i++){
            String person_id = people_data.get(i).split(",")[0];
            if(person_id.equals(user_id)){
                return i;
            }
        }
        return -1;
    }

    public void getPeopleByCity(String city) {
        List<String> cityList = new ArrayList<String>();
        String person_birthplace;
        String person_id;
        String person_surname;

        for (int i = 0; i < people_data.size(); i++) {
            person_birthplace = people_data.get(i).split(",")[5];
            if (person_birthplace.equals(city)) {

                person_id = people_data.get(i).split(",")[0];
                person_surname = people_data.get(i).split(",")[2];

                cityList.add("id: "+ person_id + "; Surname: "+ person_surname);
            }
        }

        for(int i = 0; i < cityList.size(); i++){
            System.out.println(cityList.get(i));
        }
    }

    public List<String> FilterAndSortData(String date) throws ParseException {
        List<String> list = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        Date date1 = sdf.parse(date.split(" ")[0]);
        Date date2 = sdf.parse(date.split(" ")[1]);
        Date date3;
        String birthdate;

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
        }
    };

    public void ReadResidentialFile() throws FileNotFoundException {
        List<String> residential_data = new ArrayList<String>();
        List<String> hometowns = new ArrayList<String>();
        Scanner sc = new Scanner(new File("residential.txt"));

        String person_id;
        String person_home;
        String person_birthplace;
        String person_name;
        String person_lastname;
        String person_studied;

        while (sc.hasNextLine()) {
            residential_data.add(sc.nextLine());
        }
        for(int i=0; i < residential_data.size(); i++){
            for(int j = 0; j < people_data.size(); j++){

                person_id = people_data.get(j).split(",")[0];

                if(residential_data.get(i).equals(person_id)){

                    person_home = people_data.get(j).split(",")[6];
                    System.out.println(residential_data.get(i) + " hometown: "+ person_home);
                    hometowns.add(person_home);
                }
            }
        }
        System.out.println();
        HashSet home = new HashSet(hometowns);
        for(int i = 0; i < people_data.size();i++){
            for(int j=0 ; j<home.size(); j++){
                person_birthplace = people_data.get(i).split(",")[5];
                if(person_birthplace.equals(hometowns.get(j))){

                    person_name = people_data.get(i).split(",")[1];
                    person_lastname = people_data.get(i).split(",")[2];
                    person_studied = people_data.get(i).split(",")[7];

                    System.out.println(person_name+" "+ person_lastname + " "
                            + person_birthplace + " "+ person_studied);
                }
            }
        }
    }

    public void UsersProfiles(){
        List<String> films = getAllFilmsNames();
        List<String> temp = new ArrayList<String>();

        String films_profile;
        String userID;

        for(int i = 0; i < films.size(); i++){
            for(int j=0; j < people_data.size(); j++){

                userID = people_data.get(j).split(",")[0];
                films_profile = "";
                String[] films_list = people_data.get(j).split(",")[9].split(";");
                Arrays.sort(films_list);

                for(int k = 0; k < films_list.length; k++){
                    films_profile += films_list[k] + ";";
                }

                films_profile = films_profile.substring(0, films_profile.length() - 1);

                if(films_profile.equals(films.get(i))){
                    temp.add(userID);
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
    }

    public List getAllFilmsNames(){
        Set<String> films = new HashSet();
        String films_profile;

        for(int i=0; i < people_data.size(); i++){
            films_profile = "";
            String[] films_list = people_data.get(i).split(",")[9].split(";");
            Arrays.sort(films_list);
            for(int j = 0; j < films_list.length; j++){
                films_profile += films_list[j] + ";";
            }
            films.add(films_profile.substring(0, films_profile.length() - 1));
        }

        return new ArrayList<String>(films);
    }

    public void CreateGraph(String user_ids){
        String first_id = user_ids.split(",")[0];
        String second_id = user_ids.split(",")[1];
        int first_number = -1 , second_number = -1;
        graph = new Graph(people_data.size());
        List<Integer> edges_list;
        for(int i=0; i < people_data.size(); i++){
            String vertex_id = people_data.get(i).split(",")[0];
            if(first_id.equals(vertex_id)){
                first_number = i;
            }
            if(second_id.equals(vertex_id)){
                second_number = i;
            }
            edges_list = GetFriendsListByID(vertex_id);
            for(int j = 0; j < edges_list.size(); j++){
                graph.addEdge(i, edges_list.get(j));
            }
        }
        System.out.print(graph);

        int s = first_number;
        BreadthFirstPaths bfs = new BreadthFirstPaths(graph, s);
        System.out.println(bfs.pathTo(second_number));
    }
}
