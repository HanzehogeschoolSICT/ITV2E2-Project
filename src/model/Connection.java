package model;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public abstract class Connection {

    private String playerName;
    private String opponentName;
    private Integer challengeNumber;

    private ServerSocket serverSocket;
    private Socket socket;

    /**
     * Sends a login request to the server to login the player with the given playerName.
     * Returns {@code Connection.Message.OK} when successful, else it returns a {@code Connection.Message.ERR} with the reason of failure.
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
    public abstract boolean login(@NotNull String playerName);

    /**
     * Sends a logout request to the server to logout the current player.
     * Returns nothing when successful, else it returns a {@code Connection.Message.ERR} with the reason of failure.
     * <pre>
     *     {@code
     *      Uitloggen/Verbinding verbreken:
     *      C: logout | exit | quit | disconnect | bye
     *      S: -
     *      ->Verbinding is verbroken.
     *     }
     * </pre>
     * @return true if the logout was successful, else false.
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
    public abstract ArrayList<String> getGameList();

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

    public enum Message {
        OK,
        ERR,
        SVR_HELP,
        SVR_GAME_MATCH,
        SVR_GAME_YOURTURN,
        SVR_GAME_MOVE,
        SVR_GAME_CHALLENGE,
        SVR_GAME_WIN,
        SVR_GAME_LOSS,
        SVR_GAME_DRAW;

        Message() {
        }
    }

    public enum Command {
        LOGIN,
        LOGOUT,
        GET_GAMELIST,
        GET_PLAYERLIST,
        SUBSCRIBE,
        MOVE,
        CHALLENGE,
        CHALLENGE_ACCEPT,
        FORFEIT,
        HELP

    }

}
