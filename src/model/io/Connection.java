package model.io;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.io.*;
import java.lang.reflect.MalformedParametersException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Connection {

    private Integer port;
    private String address;
    private Socket socket;
    private String playerName;

    private OutputServer outputServer;
    private InputServer inputServer;

    private Observer observer;

    public Connection(String address, Integer port) {
        if (!isAdressValid(address)) throw new MalformedParametersException("Malformed address: " + address);
        if (port <= 0 || port > 65535) throw new MalformedParametersException("Malformed port: " + port);

        this.address = address;
        this.port = port;
    }

    public void establish(String playerName) throws IOException {
        this.outputServer = new OutputServer(this);
        this.inputServer = new InputServer(this);
        setObserver();
        this.outputServer.start();
        inputServer.start();
        login(playerName);
    }

    private void setObserver() {
        OutputHandler handler = new OutputHandler(observer);
        this.outputServer.setObserver(new OutputServer.Observer() {
            @Override
            public void onNewLine(String line) {
                handler.handle(line);
            }

            @Override
            public void onEndOfLine() {
                System.out.println("===============================");
            }
        });
    }
    
    public String getServerIpAddress(){
    	return this.address;
    }
    
    public String getPlayerName(){
    	return this.playerName;
    }
    
    public static boolean isAdressValid(String address) {
        Pattern pattern = Pattern.compile("" +
                "^" + //Starts with...
                    "(" +
                        "(" +
                            "[01]?\\d\\d?" + //If starts with 0 or 1, one or 2 digits, if three; it must start with 0 or 1
                                "|" +
                            "2[0-4]\\d" + //If starts with 2, can be followed by 0-4 and/or any digit.
                                "|" +
                            "25[0-5]" + //If starts with 25, can be followed by any number in the range 0-5
                        ")" +
                        "\\." + //Ends with a "."
                    "){3}" + //Repeat 3 times. eg: "255.255.255."

                    "(" +
                        "[01]?\\d\\d?" +
                            "|" +
                        "2[0-4]\\d" +
                            "|" +
                        "25[0-5]" +
                    ")" +
                "$");

        return pattern.matcher(address).find();
    }

    /**
     * Sends a login request to the outputServer to login the player with the given playerName.
     * <pre>
     *     {@code
     *      Inloggen:
     *      C: login <speler>
     *      S: OK
     *      ->Nu ingelogd met spelernaam <speler>.
     *     }
     * </pre>
     * @param playerName The name to register. (cannot be null)
     */
    public void login(@NotNull String playerName) {
    	this.playerName = playerName;
        this.inputServer.submit("login " + playerName);
    }

    /**
     * Sends a logout request to the outputServer to logout the current player.
     * Returns nothing when successful, else it returns a {@code Connection.ServerMessage.ERR} with the reason of failure.
     * <pre>
     *     {@code
     *      Uitloggen/Verbinding verbreken:
     *      C: logout | exit | quit | disconnect | bye
     *      S: -
     *      ->Verbinding is verbroken.
     *     }
     * </pre>
     */
    public void logout(){
        this.inputServer.submit("logout");
    }

    /**
     * Sends a request to the outputServer in order to retrieve a list of available games on the outputServer.
     * <pre>
     *     {@code
     *      Lijst opvragen met ondersteunde spellen:
     *      C: get gamelist
     *      S: OK
     *      S: SVR GAMELIST ["<speltype>", ...]
     *      ->Lijst met spellen ontvangen.
     *     }
     * </pre>
     */
    public void getGameList(){
        this.inputServer.submit("get gamelist");
    }

    /**
     * Sends a request to the outputServer in order to retrieve a list of logged-in players on the outputServer.
     * <pre>
     *     {@code
     *      Lijst opvragen met verbonden spelers:
     *      C: get playerlist
     *      S: OK
     *      S: SVR PLAYERLIST ["<speler>", ...]
     *      ->Lijst met spelers ontvangen.
     *     }
     * </pre>
     */
    public void getPlayerList(){
        this.inputServer.submit("get playerlist");
    }

    /**
     * Sends a request to the outputServer in order to subscribe to a gametype on the outputServer.
     * <pre>
     *     {@code
     *      Lijst opvragen met verbonden spelers:
     *      C: get playerlist
     *      S: OK
     *      S: SVR PLAYERLIST ["<speler>", ...]
     *      ->Lijst met spelers ontvangen.
     *     }
     * </pre>
     * @param gameType The gametype to subscribe to.
     */
    public void subscribe(@NotNull String gameType){
        this.inputServer.submit("subscribe " + gameType);
    }

    /**
     * Sends a request to the outputServer to do a move.
     * <pre>
     *     {@code
     *      Een zet doen na het toegewezen krijgen van een beurt:
     *      C: move <zet>
     *      S: OK
     *      ->De zet is geaccepteerd door de outputServer, gevolg voor spel zal volgen
     *     }
     * </pre>
     * @param move The move to do.
     */
    public void move(@NotNull Integer move){
        this.inputServer.submit("move " + move);
    }

    /**
     * Sends a request to the outputServer in order to forfeit and tell the other player you give up.
     * <pre>
     *     {@code
     *      Een match opgeven:
     *      C: forfeit
     *      S: OK
     *      ->De speler heeft het spel opgegeven, de outputServer zal het resultaat van de match doorgeven.
     *     }
     * </pre>
     */
    public void forfeit(){
        this.inputServer.submit("forfeit");
    }

    /**
     * Send a request to the outputServer in order to challenge another player to a game using the players name and what game to play.
     * <pre>
     *     {@code
     *      Een speler uitdagen voor een spel:
     *      C: challenge "<speler>" "<speltype>"
     *      S: OK
     *      ->De speler is nu uitgedaagd voor een spel. Eerder gemaakte uitdagingen zijn komen te vervallen.
     *     }
     * </pre>
     * @param opponentName The name of the opponent to challenge.
     * @param gameType The type of game to play.
     */
    public void challenge(String opponentName, String gameType){
        this.inputServer.submit("challenge \"" + opponentName + "\" \"" + gameType + "\"");
    }

    /**
     * Sends a request to the outputServer in order to accept a pending challenge invite from another player.
     * <pre>
     *     {@code
     *      Een uitdaging accepteren:
     *      C: challenge accept <uitdaging nummer>
     *      S: OK
     *      ->De uitdaging is geaccepteerd. De match wordt gestart, bericht volgt.
     *     }
     * </pre>
     * @param challengeNumber The challenge number of the challenge to be accepted.
     */
    public void accept_challenge(Integer challengeNumber){
        this.inputServer.submit("challenge accept " + challengeNumber);
    }

    /**
     * Send a request to the outputServer in order to view the help page of either a command or the full page.
     * <pre>
     *     {@code
     *      Help opvragen:
     *      C: help
     *      S: OK
     *      ->De client heeft nu help informatie opgevraagd, de outputServer zal antwoorden met help informatie.
     *
     *      Help opvragen van een commando:
     *      C: help <commando>
     *      S: OK
     *      ->De client heeft nu help informatie opgevraagd voor een commando, de outputServer zal antwoorden met help informatie.
     *     }
     * </pre>
     * @param command The command to view the help page of, if null, the full help page is shown.
     */
    public void help(@Nullable String command){
        this.inputServer.submit("help " + (command != null ? command : "")); //Add the command if it isn't null.
    }





    public interface Observer {
        void onMove(String player, String details, int move);
        void onYourTurn(String comment);

        void onGameMatch(String playerMove, String gameType, String Opponent);

        void onChallenge(String opponentName, int challengeNumber, String gameType);
        void onChallengeCancelled(int challengeNumber, String comment);
        void onHelp(String info);
        void onGameList(ArrayList<String> games);
        void onPlayerList(ArrayList<String> players);
        void onGameEnd(int statusCode, String comment);
        void onError(String comment);
    }

    //<editor-fold desc="Getters and Setters">
    public Integer getPort() {
        return port;
    }

    public String getAddress() {
        return address;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setObserver(Observer observer) {
        this.observer = observer;
    }

    public void stop(){
    	this.logout();
    	//Kill threads
    }
    
    //</editor-fold>

}
