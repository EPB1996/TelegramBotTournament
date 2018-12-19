
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class Basic extends TelegramLongPollingBot {

    //tournaments(name,active), active = true, not active = false

    private static List<String[]> tournaments = new ArrayList<>();
    private static List<String[]> teams = new ArrayList<>();;
    private static boolean inProgress = false;
    private static long inProgress_ID = 0;
    private static String[] tournamentProgress = new String[5]; //[Owner, Name, active, Mode, #ppl]

    private HashMap<Long, String> groupQueue = new HashMap<Long,String>();
    private HashMap<Long, String> groupCreationQueue = new HashMap<Long, String>();
    private HashMap<Long, String> createCodeQueue= new HashMap<Long, String>();

    private HashMap<String, String> VoteMatch= new HashMap<String, String>();

    private int VoteCount;





    private static final long OP = 198057550;



    public void onUpdateReceived(Update update) {




        if (update.hasMessage() && update.getMessage().hasText()) {


            long chat_id = update.getMessage().getChatId();
            String command = update.getMessage().getText();

            /*
            System.out.println(chat_id);
            System.out.println(command);
            System.out.println(inProgress_ID);
            System.out.println(inProgress);

            */
            //OP stuff
            if(chat_id == OP){
                if(command.equals("start")) {

                    SendMessage msg = new SendMessage();
                    msg.setChatId(chat_id);

                    //show tournaments:
                    List<String[]> objects = getActiveTournaments();

                    //get ongoing Tournaments

                    InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                    List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
                    List<InlineKeyboardButton> rowInline = new ArrayList<>();

                    for(int i = 0; i < objects.size(); i++) {
                        rowInline.add(new InlineKeyboardButton().setText(objects.get(i)[0] + " (" + objects.get(i)[1] + ")" ).setCallbackData("Start:" + objects.get(i)[0]));
                    }

                    // Set the keyboard to the markup
                    rowsInline.add(rowInline);
                    // Add it to the message
                    markupInline.setKeyboard(rowsInline);
                    msg.setReplyMarkup(markupInline);
                    msg.setText("Chose tournament to start!");

                    try {

                        execute(msg);


                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }

                }


            }


            //menu creation:
            if (command.equals("menu")){

                //TODO activate Profil
                String[] items = {"New tournament", "test", "Join", "Match Result" /*, "My Profile"*/};

                SendMessage message = new SendMessage()
                        .setChatId(update.getMessage().getChatId());

                message.enableMarkdown(true);
                message.setReplyMarkup(getSettingsKeyboard(items));
                message.setReplyToMessageId(message.getReplyToMessageId());
                message.setChatId(chat_id);
                message.setText("SAY WHAT: ");

                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

            //saving file && updating local datastructure
            if (chat_id == inProgress_ID && inProgress){



                tournamentProgress[0] = String.valueOf(chat_id);
                tournamentProgress[1] = command;
                tournamentProgress[2] = "true";
                //selecting the kind

                SendMessage msg = new SendMessage();
                msg.setChatId(chat_id);
                List<String> objects = new ArrayList<>();
                objects.add("Sudden Death");
                objects.add("Normal");

                InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
                List<InlineKeyboardButton> rowInline = new ArrayList<>();

                for(int i = 0; i < objects.size(); i++) {
                    rowInline.add(new InlineKeyboardButton().setText(objects.get(i)).setCallbackData("Mode:" + objects.get(i)));
                }

                // Set the keyboard to the markup
                rowsInline.add(rowInline);
                // Add it to the message
                markupInline.setKeyboard(rowsInline);
                msg.setReplyMarkup(markupInline);
                msg.setText("Choose the mode:");

                try {

                    execute(msg);


                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }









            }

            //testing stuff
            if(command.equals("test")){

                getLocaly();
                System.out.println(teams);

            }

            if(command.equals("Match Result")){

                SendMessage msg = new SendMessage();
                msg.setChatId(chat_id);

                //show tournaments:
                List<String[]> objects = getongoingTournaments();

                //get ongoing Tournaments

                InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
                List<InlineKeyboardButton> rowInline = new ArrayList<>();

                for(int i = 0; i < objects.size(); i++) {
                    rowInline.add(new InlineKeyboardButton().setText(objects.get(i)[0] + " (" + objects.get(i)[1] + ")" ).setCallbackData("Match Outcome:" + objects.get(i)[0]));
                }

                // Set the keyboard to the markup
                rowsInline.add(rowInline);
                // Add it to the message
                markupInline.setKeyboard(rowsInline);
                msg.setReplyMarkup(markupInline);
                msg.setText("Chose tournament to report result.");

                try {

                    execute(msg);


                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            }



            //testing stuff
            if(command.equals("My Profile")){

                SendMessage msg = new SendMessage();
                msg.setChatId(chat_id);
                msg.setText("Not ready for use yet.");

                try {

                    execute(msg);


                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

                /*

                SendMessage msg = new SendMessage();
                msg.setChatId(chat_id);

                //inline keyboard
                String[] objects = {"My Stats", "Global Ranking"};

                //get ongoing Tournaments

                InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
                List<InlineKeyboardButton> rowInline = new ArrayList<>();

                for(int i = 0; i < objects.length; i++) {
                    rowInline.add(new InlineKeyboardButton().setText(objects[i]).setCallbackData(objects[i] +":" + chat_id));
                }

                // Set the keyboard to the markup
                rowsInline.add(rowInline);
                // Add it to the message
                markupInline.setKeyboard(rowsInline);
                msg.setReplyMarkup(markupInline);
                msg.setText("My profile");

                try {

                    execute(msg);


                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

                */


            }

            //commands
            if(command.equals("New tournament") && chat_id == OP) {

                SendMessage msg = new SendMessage();
                msg.setChatId(chat_id);

                //check if tournament already running


                //If no tournament creation in progress
                if(!inProgress){

                    inProgress = true;
                    inProgress_ID = chat_id;

                    /*
                    System.out.println(inProgress_ID);
                    System.out.println(inProgress);
                    */

                    msg.setText("Tournament creating Progress \n Please enter Tournament name: ");

                    //set chatID for verification
                    try {
                        execute(msg);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }


            }

            if(command.equals("/stop")) {
                groupQueue.remove(chat_id);
                groupCreationQueue.remove(chat_id);
                createCodeQueue.remove(chat_id);
            }


            //Joining
            if(command.equals("Join")){

                //always check for latest update
                getLocaly();

                SendMessage msg = new SendMessage();
                msg.setChatId(chat_id);

                //if no tournaments
                if(tournaments.size() == 0){

                    msg.setText("No tournament ongoing. Talk to OP (Etienne)");

                    try {
                        execute(msg);
                    }catch (TelegramApiException e){
                        e.printStackTrace();
                    }
                }

                //show tournaments:
                List<String[]> objects = getActiveTournaments();

                //get ongoing Tournaments

                InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
                List<InlineKeyboardButton> rowInline = new ArrayList<>();

                for(int i = 0; i < objects.size(); i++) {
                    rowInline.add(new InlineKeyboardButton().setText(objects.get(i)[0] + " (" + objects.get(i)[1] + ")" ).setCallbackData("Tournament:" + objects.get(i)[0]));
                }

                // Set the keyboard to the markup
                rowsInline.add(rowInline);
                // Add it to the message
                markupInline.setKeyboard(rowsInline);
                msg.setReplyMarkup(markupInline);
                msg.setText("List of active tournaments");

                try {

                    execute(msg);


                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            }


                //queue code
            if (createCodeQueue.containsKey(chat_id)) {
                boolean bool = saveGroupCode(chat_id, command,  createCodeQueue.get(chat_id));
                if (bool) {
                    //take id out of queue
                    createCodeQueue.remove(chat_id);
                }
            }

                if (groupQueue.containsKey(chat_id)) {
                    boolean bool = saveNewMember(chat_id, command, groupQueue.get(chat_id));
                    if (bool) {
                        //take id out of queue
                        groupQueue.remove(chat_id);
                    }
                }

                if (groupCreationQueue.containsKey(chat_id)) {
                    saveGroupName(chat_id, command,  groupCreationQueue.get(chat_id));
                    System.out.println("Testzone 0: groupCreationQueue ");



                }


        }



        //CallbackProcess
        if(update.hasCallbackQuery()){
            String callback = update.getCallbackQuery().getData();
            Long chat_id = update.getCallbackQuery().getMessage().getChatId();
            System.out.println(callback);



            //profile


            if(callback.contains("My Stats")){
                // split callback to get chatid
               String[] temp = callback.split(":");
               openPlayerStats(Long.valueOf(temp[1]));
            }

            if(callback.contains("Global Ranking")){
                //split callback
                String[] temp = callback.split(":");
                openGlobalRanking(Long.valueOf(temp[1]));
            }

            //Join
            if(callback.contains("Tournament:")){
                //name of tournament
                String tourn = callback.substring(11);


                SendMessage msg = new SendMessage();
                msg.setChatId(chat_id);
                //show tournaments:
                List<String> objects = new ArrayList<>();
                objects.add("Join existing group");
                objects.add("Create group");

                //get ongoing Tournaments

                InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
                List<InlineKeyboardButton> rowInline = new ArrayList<>();

                for(int i = 0; i < objects.size(); i++) {
                    rowInline.add(new InlineKeyboardButton().setText(objects.get(i)).setCallbackData("Group:" + objects.get(i)+ "," +tourn));
                }

                // Set the keyboard to the markup
                rowsInline.add(rowInline);
                // Add it to the message
                markupInline.setKeyboard(rowsInline);
                msg.setReplyMarkup(markupInline);
                msg.setText("Join or create group:");

                try {

                    execute(msg);


                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }


            }

            //starting tournament process
            if(callback.contains("Start:")){
                //name of tournament
                String tourn = callback.substring(6);

                SendMessage msg = new SendMessage();
                msg.setChatId(chat_id);
                //set starting flag & send to participants
                try {
                    SavingFile.setStartedFlag(tourn);
                    msg.setText("Tournament <" + tourn + "> is starting! \n Flag been set to 1!");
                }catch(FileNotFoundException e){
                    e.printStackTrace();
                }

                sendToParticipants(tourn);


                try {
                    execute(msg);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

                SavingFile.makeMatches(tourn);


            }

            if(callback.contains("Match Outcome:")){


                String tourn = callback.substring(14);




                String[] playingTeams = new String[2];

                try {
                   playingTeams =  SavingFile.getCurrentMatch(tourn);
                }catch(FileNotFoundException e){
                    e.printStackTrace();
                }

                SendMessage msg = new SendMessage();
                msg.setChatId(chat_id);
                //show tournaments:
                List<String> objects = new ArrayList<>();
                objects.add(playingTeams[0]);
                objects.add(playingTeams[1]);

                //get corresponding chat_id of creator
                try {
                    VoteMatch.put(SavingFile.getTeamCreator(playingTeams[0],tourn), playingTeams[0]);
                    VoteMatch.put(SavingFile.getTeamCreator(playingTeams[1],tourn), playingTeams[1]);
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }


                InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
                List<InlineKeyboardButton> rowInline = new ArrayList<>();

                for(int i = 0; i < objects.size(); i++) {
                    rowInline.add(new InlineKeyboardButton().setText(objects.get(i)).setCallbackData("Result:" + objects.get(i) +","+tourn));
                }

                // Set the keyboard to the markup
                rowsInline.add(rowInline);
                // Add it to the message
                markupInline.setKeyboard(rowsInline);
                msg.setReplyMarkup(markupInline);
                msg.setText("Report winner:");

                try {

                    execute(msg);


                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }




                try {
                    execute(msg);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }



            }

            if(callback.contains("Result:")){
                String rest= callback.substring(7);
                //[winning team, tourn]
                String[] infos = rest.split(",");

                SendMessage msg = new SendMessage();
                msg.setChatId(chat_id);

                if(VoteMatch.containsKey(String.valueOf(chat_id))){
                    SavingFile.updateTeams(String.valueOf(chat_id),infos[0],infos[1],"1");
                    VoteMatch.remove(String.valueOf(chat_id));
                    msg.setText("Thanks for the reporting. We wait for the other party to report as well.");
                    VoteCount++;

                    //TODO: Just to try
                    try {

                        SavingFile.refreshMatches(infos[1]);
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                }else{
                    msg.setText("You have no rights to report the outcome of this match. Only the Creators of the groups can.");
                }
                try {
                    execute(msg);
                }catch (TelegramApiException e){
                    e.printStackTrace();
                }

                //TODO: do it when two guys are there
                if(VoteCount ==2){


                }
            }


            //Group selection
            if(callback.contains("Group:")){
                //feature
                String feature = callback.substring(6);
                SendMessage msg = new SendMessage();
                msg.setChatId(chat_id);

                //Joining procedure
                if(feature.contains("Join")){
                    //get tournament to join
                    String tourn= feature.split(",")[1];
                    msg.setText("Please insert the group code:");
                    groupQueue.put(chat_id,tourn);


                    try {
                        execute(msg);
                    }catch(TelegramApiException e){
                        e.printStackTrace();
                    }

                }
                if(feature.contains("Create")) {
                    //get tournament to join
                    String tourn = feature.split(",")[1];
                    msg.setText("Please enter the Name of your Group:");
                    groupCreationQueue.put(chat_id,tourn);

                    try {
                        execute(msg);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }


            //Mode selection
            if(callback.contains("Mode:")){

                tournamentProgress[3] = callback.substring(5);

                SendMessage msg = new SendMessage();
                msg.setChatId(chat_id);


                 //adding the group
                SavingFile.saveTournament(tournamentProgress[0], tournamentProgress[1], tournamentProgress[2],tournamentProgress[3]);
                //lookup from group everytime something changes:
                getLocaly();
                //reset progress

                msg.setText("Tournament has been created:\n " +
                            " Name: " + tournamentProgress[1] + "\n" +
                            " Mode: " + tournamentProgress[3]);

                //emty the stuff
                for(int i = 0; i< tournamentProgress.length; i++){
                    tournamentProgress[i] = " ";
                }

                try {
                    execute(msg);
                }catch(TelegramApiException e){
                    e.printStackTrace();
                }


                inProgress = false;
                inProgress_ID = 0;



            }


        }



    }




    private void sendToParticipants(String tourn){
        //geting the list of participants of the specific tournament
        List<String> participants = new ArrayList<>();
        try {
            participants = SavingFile.getParticipants(tourn);
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }

        //send notification to all participants
        SendMessage msg = new SendMessage();
        msg.setText(tourn + " Tournament is starting! Get ready!");

        try {
            for(int i = 0; i < participants.size(); i++){
                String id = participants.get(i);

                if(!id.equals("0")){
                    msg.setChatId(id);
                    execute(msg);
                }

            }
        }catch (TelegramApiException e){
            e.printStackTrace();
        }




    }

    private void openGlobalRanking(long chat_id){

        SendMessage msg = new SendMessage();
        msg.setChatId(chat_id);

        List<String[]> players = new ArrayList<>();
        //get players
        try {
            players = SavingFile.getGlobalRanking(chat_id);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

        msg.setText(players.get(0)[1] + ".  " + players.get(0)[0] + " (" + players.get(0)[2] + ")");



        try {
            execute(msg);
        }catch (TelegramApiException e){
            e.printStackTrace();
        }

    }

    private void openPlayerStats(long chat_id){

        SendMessage msg = new SendMessage();
        msg.setChatId(chat_id);
        String[] playerStats = new String[7];
        //get player stats
        try {
            playerStats = SavingFile.getPlayerStats(chat_id);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

        //check for null
        if(playerStats == null){
            msg.setText("Play First your not in my Database");
        }else{
            msg.setText("|Player: " + playerStats[0] + "|\n" +
                        "Played tournaments: "+ playerStats[3] + "\n" +
                        "Wins: "+ playerStats[2] + "\n" +
                        "Total Points: "+ playerStats[4] + "\n" +
                        "Winrate: "+ playerStats[5] + "\n" +
                        "Overall Ranking: "+ playerStats[6]);
        }

        try {
            execute(msg);
        }catch (TelegramApiException e){
            e.printStackTrace();
        }



    }

    private boolean saveGroupCode(long chat_id, String code, String file){
        SendMessage msg = new SendMessage();
        msg.setChatId(chat_id);

        SavingFile.saveGroupCode(String.valueOf(chat_id),code,file);

        msg.setText("Code has been updated & group creation process is finished.");
        try {
            execute(msg);
        }catch (TelegramApiException e){
            e.printStackTrace();
        }

        return true;


    }

    private boolean checkForName(String code, String file){
        try {
            teams = SavingFile.readTeams(file);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

        for(int i = 0; i < teams.size(); i++) {
            //look for code
            if (teams.get(i)[0].equals(code)) {
                return true;
            }
        }

            return false;

    }

    private void saveGroupName(long chat_id, String code, String file) {

        SendMessage msg = new SendMessage();
        msg.setChatId(chat_id);

        if (!checkForName(code,file)) {

            SavingFile.createTeams(String.valueOf(chat_id), code, file,null);

            groupCreationQueue.remove(chat_id);

            msg.setText("Your group has been created under the name: " + code + "\n" +
                    "Pleas enter a group code for your members:");

            try {
                execute(msg);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            createCodeQueue.put(chat_id,file);

        }else{
            msg.setText("Group with name <" + code + "> already exists. \n" +
                    "Pleas enter another name:");
            try {
                execute(msg);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        //if writen to team -> not in queue anymore


    }
    //saving process
    private boolean saveNewMember(long chat_id, String code, String file){

        SendMessage msg = new SendMessage();
        msg.setChatId(chat_id);

        //TODO: check for group & code & if member is free
        try {
            teams = SavingFile.readTeams(file);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

        boolean test;

        for(int i = 0; i < teams.size(); i++) {
            //look for code

            if (teams.get(i)[3].equals(code)) {
                if(teams.get(i)[2].equals("0")) {

                    SavingFile.saveTeams(String.valueOf(chat_id), teams.get(i),file);

                    msg.setText("You have been successfully assigned to Team " + teams.get(i)[0]);

                    try {
                        execute(msg);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    //if written to team -> not in queue anymore
                    return true;
                    //wrong code
                }
                else{
                    msg.setText("Group already full. To join a group you need to redo the process. ");

                    try {
                        execute(msg);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                        //if not written you can again send code
                    }
                    return true;

                }
            }
        }

            msg.setText("Sorry no group associated with such code. Be sure to use a valid one. \n " +
                    "Please reenter the the code or write </stop> to stop the process.");

            try {
                execute(msg);
            } catch (TelegramApiException e) {
                e.printStackTrace();
                //if not written you can again send code
            }
        return false;
    }


    //get names of all active tournaments
    private List<String[]> getActiveTournaments(){

        //always get it locally
        getLocaly();
        List<String[]> active = new ArrayList<>();

        //check what cmd


            for (int i = 0; i < tournaments.size(); i++) {
                if (tournaments.get(i)[1].equals("true")) {
                    if (tournaments.get(i)[4].equals("0")) {
                        if (tournaments.get(i)[3].equals("Sudden Death")) {
                            active.add(new String[]{tournaments.get(i)[0], "SD"});
                        } else {
                            active.add(new String[]{tournaments.get(i)[0], "N"});
                        }
                    }
                }
            }
        return active;
    }
    private List<String[]> getongoingTournaments(){

        //always get it locally
        getLocaly();
        List<String[]> active = new ArrayList<>();

        //check what cmd


            for (int i = 0; i < tournaments.size(); i++) {
                if (tournaments.get(i)[1].equals("true")) {
                    if (tournaments.get(i)[4].equals("1")) {
                        if (tournaments.get(i)[3].equals("Sudden Death")) {
                            active.add(new String[]{tournaments.get(i)[0], "SD"});
                        } else {
                            active.add(new String[]{tournaments.get(i)[0], "N"});
                        }
                    }
                }
            }
        return active;
    }


    private void getLocaly(){
        try {
            tournaments = SavingFile.readTournaments();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }
    private static ReplyKeyboardMarkup getSettingsKeyboard(String[] items) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(false);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);


        List<KeyboardRow> keyboardRow = new ArrayList<>();
        KeyboardRow keyboard = new KeyboardRow();
        for (int i = 0; i < items.length; i++){
            keyboard.add(items[i]);
        }


        keyboardRow.add(keyboard);
        replyKeyboardMarkup.setKeyboard(keyboardRow);



        return replyKeyboardMarkup;
    }

    public String getBotUsername() {
        // Return bot username
        // If bot username is @MyAmazingBot, it must return 'MyAmazingBot'
        return "Tournament_Seebach_bot.";
    }

    @Override
    public String getBotToken() {
        // Return bot token from BotFather
        return "627345733:AAGMSCOF9u2tOpGCa3ySuhK5m15UbhTCwGk";


}

    }

    /*TODO:
        1) Set tournament to active = false if finished.
        2) After deletion from tournament save stats to team/player

     */