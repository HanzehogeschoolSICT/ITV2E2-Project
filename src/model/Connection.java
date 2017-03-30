package model;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.regex.Pattern;

public abstract class Connection {

    private static final String LOGIN_MESSAGE = "Strategic Game Server Fixed [Version 1.1.0](C) Copyright 2015 Hanzehogeschool Groningen";

    private boolean active;
    private Queue<String> writeQueue;
    private Observer outputObserver;

    private String playerName;
    private String opponentName;
    private Integer challengeNumber;

    private Integer port;
    private String address;
    private Socket socket;

    private BufferedReader inputReader;
    private InputStream inputStream;
    private PrintWriter printWriter;

    public Connection(String address, Integer port) {
        if (!isAdressValid(address)) throw new DataException("Malformed address: " + address);
        if (port <= 0 || port > 65535) throw new DataException("Malformed port: " + port);

        this.address = address;
        this.port = port;
        this.active = false;
        this.writeQueue = new ArrayDeque<>();
    }

    public void establish() throws IOException {
        this.socket = new Socket(this.address, this.port);

        InputStream inputStream = socket.getInputStream();
        this.inputStream = inputStream;
        this.inputReader = new BufferedReader(new  InputStreamReader(inputStream));

        OutputStream outputStream = socket.getOutputStream();
        this.printWriter = new PrintWriter(new OutputStreamWriter(outputStream), true);

        startServer();
    }

    private void startServer() {
        try {
            while (socket.isConnected()) {
                int read;
                while ((read = inputReader.read()) != 0) {
                    System.out.println((char) read);
                }
                if(writeQueue.size() >= 1) printWriter.println(writeQueue.poll());
//
//                if (inputStream.available() != 0) {
//
//                } else {
//                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private String streamToString(InputStream inputStream) throws IOException {
        StringWriter stringWriter = new StringWriter();
        IOUtils.copy(inputStream, stringWriter);
        return stringWriter.toString();
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
    public boolean login(@NotNull String playerName) {
        writeQueue.add("login " + playerName);
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
    public abstract boolean logout();

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
        printWriter.write("get gamelist");
        printWriter.flush();
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
    public abstract ArrayList<String> getPlayerList();

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
    public abstract boolean subscribe(@NotNull String gameType);

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
    public abstract boolean move(@NotNull Integer row, @NotNull Integer column);

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
    public abstract boolean forfeit();

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
    public abstract boolean challenge(String opponentName, String gameType);

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
    public abstract boolean accept_challenge(Integer challengeNumber);

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
    public abstract String help(@Nullable String command);

    class DataException extends RuntimeException {
        public DataException(String message) {
            super(message);
        }
    }

    public interface Observer {
        void onConnect();
    }

    //<editor-fold desc="Getters and Setters">
    public boolean isActive() {
        return active;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getOpponentName() {
        return opponentName;
    }

    public Integer getChallengeNumber() {
        return challengeNumber;
    }

    public Integer getPort() {
        return port;
    }

    public String getAddress() {
        return address;
    }

    public void setOutputObserver(Observer outputObserver) {
        this.outputObserver = outputObserver;
    }

    //</editor-fold>

}
