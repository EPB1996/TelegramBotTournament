import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.glassfish.grizzly.utils.ArraySet;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.*;
import java.util.*;

public class SavingFile extends Basic {

   6
    private static final String COMMA_DELIMITER = ",";

    public static void saveTournament(String chat_id, String tourName, String active, String mode){
        try {

            List<String[]> test = new ArrayList<>();
            // string to add
            String[] ele0 ={"Name","Creator","Member","Code"};


            // System.out.println(ele);
            //System.out.println("removeLine: " + elements.get(i));

            test.add(ele0);
            FileWriter sw0 = new FileWriter(Resources.TEAMS_FILE+tourName+"teams.csv");
            CSVWriter writer0 = new CSVWriter(sw0);

            writer0.writeAll(test,false);
            writer0.close();



            CSVReader reader2 = new CSVReader(new FileReader(Resources.TOURNAMENTS_FILE),',');
            List<String[]> elements = reader2.readAll();

                // string to add
                String[] ele = {tourName, active, chat_id, mode,"0"};


               // System.out.println(ele);
                //System.out.println("removeLine: " + elements.get(i));
                elements.add(ele);

               // System.out.println(elements);

            //convert back


            FileWriter sw = new FileWriter(Resources.TOURNAMENTS_FILE);
            CSVWriter writer = new CSVWriter(sw);

            writer.writeAll(elements,false);
            writer.close();


        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e1){
            e1.printStackTrace();
        }




    }

    public static void saveTeams(String newMember, String[] team ,String file){
        try {

            CSVReader reader2 = new CSVReader(new FileReader(Resources.TEAMS_FILE+file+"teams.csv"),',');
            List<String[]> elements = reader2.readAll();
                // string to add
                String[] ele =team;
                ele[2] = newMember;

                for(int i = 0; i< elements.size(); i++){
                    if(elements.get(i)[0].equals(ele[0])){
                        elements.set(i,ele);
                    }
                }
               // System.out.println(ele);
                //System.out.println("removeLine: " + elements.get(i));


               // System.out.println(elements);

            //convert back


            FileWriter sw = new FileWriter(Resources.TEAMS_FILE+file+"teams.csv");
            CSVWriter writer = new CSVWriter(sw);

            writer.writeAll(elements,false);
            writer.close();


        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e1){
            e1.printStackTrace();
        }




    }

    public static void saveGroupCode(String owner, String code, String file ){
        try {

            CSVReader reader2 = new CSVReader(new FileReader(Resources.TEAMS_FILE+file+"teams.csv"),',');
            List<String[]> elements = reader2.readAll();
                // string to add

                for(int i = 0; i< elements.size(); i++){
                    if(elements.get(i)[1].equals(owner)){
                        elements.get(i)[3] = code;
                    }
                }
               // System.out.println(ele);
                //System.out.println("removeLine: " + elements.get(i));


               // System.out.println(elements);

            //convert back


            FileWriter sw = new FileWriter(Resources.TEAMS_FILE+file+"teams.csv");
            CSVWriter writer = new CSVWriter(sw);

            writer.writeAll(elements,false);
            writer.close();


        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e1){
            e1.printStackTrace();
        }




    }

    public static void createTeams(String creator, String teamName, String file, String win ){
        try {


            CSVReader reader2 = new CSVReader(new FileReader(Resources.TEAMS_FILE+file+"teams.csv"),',');
            List<String[]> elements = reader2.readAll();
                // string to add
                String[] ele ={teamName, creator, "0","0","0"};


                elements.add(ele);


               // System.out.println(ele);
                //System.out.println("removeLine: " + elements.get(i));




                for(int i = 0; i < elements.size(); i++){
                    System.out.println(elements.get(i)[0] +elements.get(i)[1] +elements.get(i)[2] +elements.get(i)[3]);
                }
               // System.out.println(elements);

            //convert back


            FileWriter sw = new FileWriter(Resources.TEAMS_FILE+file+"teams.csv");
            CSVWriter writer = new CSVWriter(sw);

            writer.writeAll(elements,false);
            writer.close();


        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e1){
            e1.printStackTrace();
        }




    }

    public static void updateTeams(String creator, String teamName, String file, String win ){
        try {


            CSVReader reader2 = new CSVReader(new FileReader(Resources.TEAMS_FILE+file+"teams.csv"),',');
            List<String[]> elements = reader2.readAll();
                // string to add
            for(int i = 0; i < elements.size(); i++){
                String name = elements.get(i)[0];
                String owner = elements.get(i)[1];
                if(name.equals(teamName) && owner.equals(creator)){
                    elements.get(i)[4] += win;
                }
            }
               // System.out.println(ele);
                //System.out.println("removeLine: " + elements.get(i));

                for(int i = 0; i < elements.size(); i++){
                    System.out.println(elements.get(i)[0] +elements.get(i)[1] +elements.get(i)[2] +elements.get(i)[3]);
                }
               // System.out.println(elements);

            //convert back


            FileWriter sw = new FileWriter(Resources.TEAMS_FILE+file+"teams.csv");
            CSVWriter writer = new CSVWriter(sw);

            writer.writeAll(elements,false);
            writer.close();


        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e1){
            e1.printStackTrace();
        }




    }

    public static List<String[]> readTournaments() throws FileNotFoundException {


        List<String[]> current = new ArrayList<>();
        BufferedReader fileReader = null;
        try {


            String line = "";
            //return array


            //create file reader
            fileReader = new BufferedReader(new FileReader(Resources.TOURNAMENTS_FILE));

            //skip first row
            fileReader.readLine();
            while ((line = fileReader.readLine()) != null) {

                //Get all tokens available in line

                String[] tokens = line.split(COMMA_DELIMITER);



                    String[] res = new String[tokens.length];
                    for(int i = 0; i < tokens.length; i++){
                        res[i] = tokens[i];
                    }

                    current.add(res);




            }


        }catch(Exception e){
            System.out.println("Error in SAVEFILE");
            e.printStackTrace();
        }finally{
            try {
                fileReader.close();
            }catch(Exception e){
                System.out.println("Error when closing");
                e.printStackTrace();
            }
        }
        return current;
    }

    public static List<String[]> readTeams(String file) throws FileNotFoundException {


        List<String[]> current = new ArrayList<>();
        BufferedReader fileReader = null;
        try {


            String line = "";
            //return array


            //create file reader
            fileReader = new BufferedReader(new FileReader(Resources.TEAMS_FILE+file+"teams.csv"));

            //skip first row
            fileReader.readLine();
            while ((line = fileReader.readLine()) != null) {

                //Get all tokens available in line

                String[] tokens = line.split(COMMA_DELIMITER);



                    String[] res = new String[tokens.length];
                    for(int i = 0; i < tokens.length; i++){
                        res[i] = tokens[i];
                    }

                    current.add(res);




            }


        }catch(Exception e){
            System.out.println("Error in SAVEFILE");
            e.printStackTrace();
        }finally{
            try {
                fileReader.close();
            }catch(Exception e){
                System.out.println("Error when closing");
                e.printStackTrace();
            }
        }
        return current;
    }

    public static List<String[]> getGlobalRanking(long chat_id) throws FileNotFoundException {


        List<String[]> current = new ArrayList<>();
        BufferedReader fileReader = null;
        try {


            String line = "";
            //return array


            //create file reader
            fileReader = new BufferedReader(new FileReader(Resources.PLAYER_FILE));

            //skip first row
            fileReader.readLine();
            while ((line = fileReader.readLine()) != null) {

                //Get all tokens available in line

                String[] tokens = line.split(COMMA_DELIMITER);



                    String[] res = {tokens[0],tokens[6], tokens[4],};

                    current.add(res);




            }


        }catch(Exception e){
            System.out.println("Error in SAVEFILE");
            e.printStackTrace();
        }finally{
            try {
                fileReader.close();
            }catch(Exception e){
                System.out.println("Error when closing");
                e.printStackTrace();
            }
        }

        //TODO sort
        return current;
    }

    public static String[] getPlayerStats(long chat_id) throws FileNotFoundException {


        String[] res = new String[7];
        BufferedReader fileReader = null;
        try {


            String line = "";
            //return array


            //create file reader
            fileReader = new BufferedReader(new FileReader(Resources.PLAYER_FILE));

            //skip first row
            fileReader.readLine();
            while ((line = fileReader.readLine()) != null) {

                //Get all tokens available in line



                String[] tokens = line.split(COMMA_DELIMITER);
                System.out.println(tokens[1]);

                if(tokens[1].equals(String.valueOf(chat_id))){
                   res =  tokens;
                }else{
                    res = null;
                }


            }


        }catch(Exception e){
            System.out.println("Error in SAVEFILE");
            e.printStackTrace();
        }finally{
            try {
                fileReader.close();
            }catch(Exception e){
                System.out.println("Error when closing");
                e.printStackTrace();
            }
        }
        return res;
    }

    public static List<String> getParticipants(String tourn) throws FileNotFoundException {


        List<String> participants = new ArrayList<>();

        BufferedReader fileReader = null;
        try {


            String line = "";
            //return array


            //create file reader
            fileReader = new BufferedReader(new FileReader(Resources.TEAMS_FILE+tourn+"teams.csv"));

            //skip first row
            fileReader.readLine();
            while ((line = fileReader.readLine()) != null) {

                //Get all tokens available in line



                String[] tokens = line.split(COMMA_DELIMITER);

                System.out.println(tokens[1]);
                System.out.println(tokens[2]);

                participants.add(tokens[1]);
                participants.add(tokens[2]);



            }


        }catch(Exception e){
            System.out.println("Error in SAVEFILE");
            e.printStackTrace();
        }finally{
            try {
                fileReader.close();
            }catch(Exception e){
                System.out.println("Error when closing");
                e.printStackTrace();
            }
        }
        return participants;
    }

    public static List<String> getTeams(String tourn) throws FileNotFoundException {


        List<String> participants = new ArrayList<>();

        BufferedReader fileReader = null;
        try {


            String line = "";
            //return array


            //create file reader
            fileReader = new BufferedReader(new FileReader(Resources.TEAMS_FILE+tourn+"teams.csv"));

            //skip first row
            fileReader.readLine();
            while ((line = fileReader.readLine()) != null) {

                //Get all tokens available in line



                String[] tokens = line.split(COMMA_DELIMITER);

                participants.add(tokens[0]);


            }


        }catch(Exception e){
            System.out.println("Error in SAVEFILE");
            e.printStackTrace();
        }finally{
            try {
                fileReader.close();
            }catch(Exception e){
                System.out.println("Error when closing");
                e.printStackTrace();
            }
        }
        return participants;
    }

    public static void makeMatches(String tourn) {

            List<String> teams = new ArrayList<>();
            try {
                teams = getTeams(tourn);
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }


            //if only one left == winner
            if(teams.size()==1) {

                File file = new File(Resources.TOURNAMENTS_MATCHES);

                FileWriter fileWriter = null;
                BufferedWriter bufferedWriter = null;
                try {
                    fileWriter = new FileWriter(file);
                    bufferedWriter = new BufferedWriter(fileWriter);

                    String htmlPage = "<html><body style=’background-color:#ccc’>";

                    bufferedWriter.write(htmlPage);

                    bufferedWriter.append("<center><p><font color=\"red\" size=\"7\">The Winner is:</font></p></center>");
                    bufferedWriter.append("<center><p><font color=\"red\" size=\"7\">" + teams.get(0) + "</font></p></center></body></html>");


                    System.out.println("Html page created");
                    bufferedWriter.flush();
                    fileWriter.flush();

                } catch (IOException e) {

                    e.printStackTrace();
                } finally {
                    try {

                        bufferedWriter.close();
                        fileWriter.close();
                    } catch (IOException e) {

                        e.printStackTrace();
                    }


                }
            }else if(teams.size()%2 != 0){
                teams.add("!Free pass!");
                //randomize teams
                Collections.shuffle(teams);

                try {
                    List<String[]> test = new ArrayList<>();
                    // string to add
                    String[] ele0 = {"TeamA", "TeamB"};


                    test.add(ele0);
                    FileWriter sw0 = new FileWriter(Resources.TEAMS_FILE + tourn + "Matches.csv");
                    CSVWriter writer0 = new CSVWriter(sw0);

                    writer0.writeAll(test, false);
                    writer0.close();


                    CSVReader reader2 = new CSVReader(new FileReader(Resources.TEAMS_FILE+tourn+"Matches.csv"), ',');
                    List<String[]> elements = reader2.readAll();

                    //Write to file
                    for (int i = 0; i< teams.size()-1; i++) {
                        // string to add
                        String teamA = teams.get(i);
                        String teamB = teams.get(i+1);
                        String[] ele = {teamA, teamB};

                        elements.add(ele);
                        i++;
                    }

                    matchesToHTML(elements);

                    FileWriter sw = new FileWriter(Resources.TEAMS_FILE+tourn+"Matches.csv");
                    CSVWriter writer = new CSVWriter(sw);

                    writer.writeAll(elements, false);
                    writer.close();

                }catch(FileNotFoundException e){
                    e.printStackTrace();
                }catch (IOException e1){
                    e1.printStackTrace();
                }
            }





}

    public static void setStartedFlag(String tourn)throws FileNotFoundException{
        try {

            CSVReader reader2 = new CSVReader(new FileReader(Resources.TOURNAMENTS_FILE),',');
            List<String[]> elements = reader2.readAll();

           for(int i = 0; i < elements.size(); i++){

               if(elements.get(i)[0].equals(tourn)){
                   elements.get(i)[4] = "1";
               }
           }



            FileWriter sw = new FileWriter(Resources.TOURNAMENTS_FILE);
            CSVWriter writer = new CSVWriter(sw);

            writer.writeAll(elements,false);
            writer.close();


        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e1){
            e1.printStackTrace();
        }



    }

    public static void matchesToHTML(List<String[]> matches){

        //TODO: decide which matches are displayed, maybe global counter?
        File file = new File(Resources.HTML_MATCHES);

        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(file);
            bufferedWriter = new BufferedWriter(fileWriter);

            String htmlPage = "<html><body style=’background-color:#ccc’>";

            bufferedWriter.write(htmlPage);

            bufferedWriter.append("<center><p><font color=\"grey\" size=\"7\">TEAM A     VS     TEAM B</font></p></center>");
            bufferedWriter.append("<center><p><font color=\"grey\" size=\"4\"> NOW</font></p></center></body></html>");
            bufferedWriter.append("<center><p><font size=\"6\">" + matches.get(1)[0] + "     vs     " + matches.get(1)[1]+ "</font></p></center>");
            bufferedWriter.append("<center><p><font color=\"grey\" size=\"4\"> NEXT</font></p></center></body></html>");
            bufferedWriter.append("<center><p><font size=\"5\">" + matches.get(2)[0] + "     vs     " + matches.get(2)[1]+ "</font></p></center></body></html>");

            System.out.println("Html page created");
            bufferedWriter.flush();
            fileWriter.flush();

        } catch (IOException e) {

            e.printStackTrace();
        }finally{
            try {

                bufferedWriter.close();
                fileWriter.close();
            } catch (IOException e) {

                e.printStackTrace();
            }

        }















    }

    public static String[] getCurrentMatch(String tourn) throws FileNotFoundException {




        BufferedReader fileReader = null;
        try {


            String line = "";
            //return array


            //create file reader
            fileReader = new BufferedReader(new FileReader(Resources.TEAMS_FILE + tourn + "Matches.csv"));

            //skip first row
            fileReader.readLine();
            while ((line = fileReader.readLine()) != null) {

                //Get all tokens available in line



                String[] tokens = line.split(COMMA_DELIMITER);


               return new String[]{tokens[0],tokens[1]};


            }


        }catch(Exception e){
            System.out.println("Error in SAVEFILE");
            e.printStackTrace();
        }finally{
            try {
                fileReader.close();
            }catch(Exception e){
                System.out.println("Error when closing");
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String getTeamCreator(String team, String tourn) throws FileNotFoundException {




        BufferedReader fileReader = null;
        try {


            String line = "";
            //return array


            //create file reader
            fileReader = new BufferedReader(new FileReader(Resources.TEAMS_FILE+tourn+"teams.csv"));

            //skip first row
            fileReader.readLine();
            while ((line = fileReader.readLine()) != null) {

                //Get all tokens available in line



                String[] tokens = line.split(COMMA_DELIMITER);

                if(tokens[0].equals(team)){
                    return tokens[1];
                }


            }


        }catch(Exception e){
            System.out.println("Error in SAVEFILE");
            e.printStackTrace();
        }finally{
            try {
                fileReader.close();
            }catch(Exception e){
                System.out.println("Error when closing");
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void refreshMatches(String tourn) throws FileNotFoundException{

        List<String[]> teams = new ArrayList<>();
        try {
            teams = getMatches(tourn);
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }

        teams.subList(0,teams.size());

        try {
            List<String[]> test = new ArrayList<>();
            // string to add
            String[] ele0 = {"TeamA", "TeamB"};


            test.add(ele0);
            FileWriter sw0 = new FileWriter(Resources.TEAMS_FILE + tourn + "Matches.csv");
            CSVWriter writer0 = new CSVWriter(sw0);

            writer0.writeAll(test, false);
            writer0.close();


            CSVReader reader2 = new CSVReader(new FileReader(Resources.TEAMS_FILE+tourn+"Matches.csv"), ',');
            List<String[]> elements = new ArrayList<>();

            //Write to file
            elements.addAll(teams);

            matchesToHTML(elements);

            FileWriter sw = new FileWriter(Resources.TEAMS_FILE+tourn+"Matches.csv");
            CSVWriter writer = new CSVWriter(sw);

            writer.writeAll(elements, false);
            writer.close();

        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e1){
            e1.printStackTrace();
        }

    }

    public static List<String[]> getMatches(String tourn) throws FileNotFoundException{
        List<String[]> participants = new ArrayList<>();

        BufferedReader fileReader = null;
        try {


            String line = "";
            //return array


            //create file reader
            fileReader = new BufferedReader(new FileReader(Resources.TEAMS_FILE + tourn + "Matches.csv"));

            //skip first row
            fileReader.readLine();
            while ((line = fileReader.readLine()) != null) {

                //Get all tokens available in line



                String[] tokens = line.split(COMMA_DELIMITER);

                participants.add(tokens);


            }


        }catch(Exception e){
            System.out.println("Error in SAVEFILE");
            e.printStackTrace();
        }finally{
            try {
                fileReader.close();
            }catch(Exception e){
                System.out.println("Error when closing");
                e.printStackTrace();
            }
        }
        return participants;
    }
}




