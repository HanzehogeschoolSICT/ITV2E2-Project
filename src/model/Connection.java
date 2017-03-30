package model;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Connection {

    private String playerName;
    private String opponentName;
    private Integer challengeNumber;

    private Integer port;
    private String address;
    private Socket socket;

    public Connection(String address, Integer port) {
        this.address = address;
        this.port = port;
    }

    public void establish() throws IOException {
        this.socket = new Socket(this.address, this.port);
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
     * Sends a login request to the server to login the player with the given playerName.
     * <pre>
     *     {@code
     *      Inloggen:
     *      C: login <speler>
     *      S: OK
     *      ->Nu ingelogd met spelernaam <speler>.
     *     }
     * </pre>
     * @param playerName The name to register. (cannot be null)
     * @return True if the login was successful, else false.
     */
    public boolean login(@NotNull String playerName){
    	return false;
    }

    /**
     * Sends a logout request to the server to logout the current player.
     * Returns nothing when successful, else it returns a {@code Connection.ServerMessage.ERR} with the reason of failure.
     * <pre>
     *     {@code
     *      Uitloggen/Verbinding verbreken:
     *      C: logout | exit | quit | disconnect | bye
     *      S: -
     *      ->Verbinding is verbroken.
     *     }
     * </pre>
     * @return True if the logout was successful, else false.
     */
    public boolean logout(){
    	return false;
    }

    /**
     * Sends a request to the server in order to retrieve a list of available games on the server.
     * <pre>
     *     {@code
     *      Lijst opvragen met ondersteunde spellen:
     *      C: get gamelist
     *      S: OK
     *      S: SVR GAMELIST ["<speltype>", ...]
     *      ->Lijst met spellen ontvangen.
     *     }
     * </pre>
     * @return The list of available games on the server if successful, else null.
     */
    public ArrayList<String> getGameList(){
    	return null;
    }

    /**
     * Sends a request to the server in order to retrieve a list of logged-in players on the server.
     * <pre>
     *     {@code
     *      Lijst opvragen met verbonden spelers:
     *      C: get playerlist
     *      S: OK
     *      S: SVR PLAYERLIST ["<speler>", ...]
     *      ->Lijst met spelers ontvangen.
     *     }
     * </pre>
     * @return The list of logged-in players on the server if successful, else null.
     */
    public ArrayList<String> getPlayerList(){
    	return null;
    }

    /**
     * Sends a request to the server in order to subscribe to a gametype on the server.
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
     * @return True if the request was successful at subscribing, else false.
     */
    public boolean subscribe(@NotNull String gameType){
    	return false;
    }

    /**
     * Sends a request to the server to do a move.
     * <pre>
     *     {@code
     *      Een zet doen na het toegewezen krijgen van een beurt:
     *      C: move <zet>
     *      S: OK
     *      ->De zet is geaccepteerd door de server, gevolg voor spel zal volgen
     *     }
     * </pre>
     * @param row The row to do the move on.
     * @param column The column to do the move on.
     * @return True if the request was successful, else false if the move is invalid.
     */
    public boolean move(@NotNull Integer row, @NotNull Integer column){
    	return false;
    }

    /**
     * Sends a request to the server in order to forfeit and tell the other player you give up.
     * <pre>
     *     {@code
     *      Een match opgeven:
     *      C: forfeit
     *      S: OK
     *      ->De speler heeft het spel opgegeven, de server zal het resultaat van de match doorgeven.
     *     }
     * </pre>
     * @return True if the request was successful, else false.
     */
    public boolean forfeit(){
    	return false;
    }

    /**
     * Send a request to the server in order to challenge another player to a game using the players name and what game to play.
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
     * @return True if the request was successful, else false.
     */
    public boolean challenge(String opponentName, String gameType){
    	return false;
    }

    /**
     * Sends a request to the server in order to accept a pending challenge invite from another player.
     * <pre>
     *     {@code
     *      Een uitdaging accepteren:
     *      C: challenge accept <uitdaging nummer>
     *      S: OK
     *      ->De uitdaging is geaccepteerd. De match wordt gestart, bericht volgt.
     *     }
     * </pre>
     * @param challengeNumber The challenge number of the challenge to be accepted.
     * @return True if the request was successful, else false.
     */
    public boolean accept_challenge(Integer challengeNumber){
    	return false;
    }

    /**
     * Send a request to the server in order to view the help page of either a command or the full page.
     * <pre>
     *     {@code
     *      Help opvragen:
     *      C: help
     *      S: OK
     *      ->De client heeft nu help informatie opgevraagd, de server zal antwoorden met help informatie.
     *
     *      Help opvragen van een commando:
     *      C: help <commando>
     *      S: OK
     *      ->De client heeft nu help informatie opgevraagd voor een commando, de server zal antwoorden met help informatie.
     *     }
     * </pre>
     * @param command The command to view the help page of, if null, the full help page is shown.
     * @return A String with the help text that was send by the server.
     */
    public String help(@Nullable String command){
    	return null;
    }


    public enum ServerMessage {
        OK("OK"),
        ERR("ERR"),
        SVR_HELP("SVR HELP"),
        SVR_GAME_MATCH("SVR GAME MATCH"),
        SVR_GAME_YOURTURN("SVR GAME YOURTURN"),
        SVR_GAME_MOVE("SVR GAME MOVE"),
        SVR_GAME_CHALLENGE("SVR GAME CHALLENGE"),
        SVR_GAME_WIN("SVR GAME WIN"),
        SVR_GAME_LOSS("SVR GAME LOSS"),
        SVR_GAME_DRAW("SVR GAME DRAW");

        private String text;

        ServerMessage(String text) {
            this.text = text;
        }

    }

}
