package model.io;

/*
    Copyright (C) 31-3-17  Hanze Hogeschool ITV2D

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OutputHandler {

    private static final String OK = "OK";
    private static final String ERR = "ERR";
    private static final String LOGIN_MESSAGE = "Strategic Game Server Fixed [Version 1.1.0](C) Copyright 2015 Hanzehogeschool Groningen";

    private static final String SVR_GAME = "SVR GAME";
    private static final String SVR_GAME_MOVE = "SVR GAME MOVE";
    private static final String SVR_GAME_YOURTURN = "SVR GAME YOURTURN";
    private static final String SVR_GAME_MATCH = "SVR GAME MATCH";
    private static final String SVR_GAME_CHALLENGE = "SVR GAME CHALLENGE";
    private static final String SVR_GAME_CHALLENGE_CANCELLED = "SVR GAME CHALLENGE CANCELLED";

    private static final String WIN = "WIN";
    private static final String DRAW= "DRAW";
    private static final String LOSS = "LOSS";

    private static final String SVR_PLAYERLIST = "SVR PLAYERLIST";
    private static final String SVR_GAMELIST = "SVR GAMELIST";

    private static final Pattern QUOTE_PATTERN = Pattern.compile("\"(.*?)\""); //This QUOTE_PATTERN extracts all the values between qoutes.
    private Connection.Observer observer;

    public OutputHandler(@NotNull Connection.Observer observer) {
        this.observer = observer;
    }

    /**
     * The super method for all 'sub handlers'.
     * This method redirects the line to the appropriate handler.
     * @param line The line returned from the output server.
     */
    public void handle(@NotNull String line) {
        System.out.println(line);

        if (!line.contains(OK)){

            if (!line.contains(ERR)){

                if (contains(line, SVR_GAMELIST, SVR_PLAYERLIST)){
                    //ToDo handle gamelist and playerlist.

                } else if (line.contains(SVR_GAME)) {

                    if (line.contains(SVR_GAME_MOVE)) {
                        handleMove(line);

                    } else if (line.contains(SVR_GAME_YOURTURN)) {
                        handleYourturn(line);

                    } else if (line.contains(SVR_GAME_CHALLENGE)) {

                        if (line.contains(SVR_GAME_CHALLENGE_CANCELLED)) {
                            handleChallengeCancelled(line);

                        } else {
                            handleChallenge(line);
                        }
                    } else if (contains(line, WIN, DRAW, LOSS)){
                        handleGameEnd(line);
                    }
                }
            } else {
                observer.onError(line);
            }
        }
    }


    /**
     * A {@link String#contains(CharSequence) String.contains()} method for multiple values.
     * If 1 or more from the string parameter is matched it returns true, else false.
     * @param line The line to check for values.
     * @param strings The values to check against
     * @return True if at least 1 match is found.
     */
    private boolean contains(String line, String... strings) {
        boolean result = false;
        for (String string : strings) {
            if (line.contains(string)) {
                result = true;
                break;
            }
        }

        return result;
    }


    /**
     * Extracts all the word between quotes like: "word".
     * @param line The line to extract the words from.
     * @return The words that were extracted, else an empty ArrayList.
     */
    private ArrayList<String> matchQoutes(String line) {
        ArrayList<String> result = new ArrayList<>();
        Matcher matcher = QUOTE_PATTERN.matcher(line.replace("\"\"", "\""));

        while (matcher.find()) {
            result.add(matcher.group().replace("\"", ""));
        }

        return result;
    }


    /**
     * Handles all incoming challenge request from the server.
     * The message, challenge number and opponent name extracted from the line are passed on to the
     * {@link model.io.Connection.Observer#onChallenge(String, int, String)}  Connection.Observer.onChallenge} method
     * for the UI to respond to.
     * @param line The line to extract the data from.
     */
    private void handleChallenge(String line) {
        ArrayList<String> result = matchQoutes(line);

        this.observer.onChallenge(
                result.get(0), //The name of the opponent.
                Integer.parseInt(result.get(1)),  //The challenge number.
                result.get(2)); //The game type.
    }


    /**
     * Handles the message received from the server informing the user a challenge has been refused or cancelled.
     * The message extracted from the line is passed on to the
     * {@link model.io.Connection.Observer#onChallengeCancelled(int, String)}  Connection.Observer.onChallengeCancelled} method
     * for the UI to respond to.
     * @param line The line to extract the data from.
     */
    private void handleChallengeCancelled(String line) {
        ArrayList<String> result = matchQoutes(line);

        this.observer.onChallengeCancelled(
                Integer.parseInt(result.get(0)), //The challenge number of the cancelled challenge.
                "" //The cause of the cancellation.
        );
    }


    /**
     * Handles the YourTurn message received from the server.
     * The message extracted from the line is passed on to the
     * {@link model.io.Connection.Observer#onYourTurn(String)}  Connection.Observer.onYourTurn} method
     * for the UI to respond to.
     * @param line The line to extract the data from.
     */
    private void handleYourturn(String line) {
        ArrayList<String> result = matchQoutes(line);

        this.observer.onYourTurn(
                result.size() >0 ? result.get(0) : "" //The message extracted from the line.
        );
    }


    /**
     * Handles the end of the game.
     * Possible end conditions are:
     * <ul>
     *     <li><code>-1: LOSS</code></li>
     *     <li><code>0: DRAW</code></li>
     *     <li><code>1: WINN</code></li>
     * </ul>
     *
     * The extracted information is passed on to the {@link model.io.Connection.Observer#onGameEnd(int, String)}  Connection.Observer.onGameEnd}
     * for the UI to respond to.
     * @param line The line to extract the data from.
     */
    private void handleGameEnd(String line) {
        ArrayList<String> result = matchQoutes(line);

        this.observer.onGameEnd(
                line.contains(WIN) ? 1 : //If the line contains WIN, the player has won the game.
                line.contains(LOSS) ? -1 : 0, //If the line does not contain win, but contains LOSS, the player has lost.
                result.get(2)); //If neither of these are the case, the game is a draw, the comment is also given as context.
    }


    /**
     * Handles the Move message received from the server.
     * The message extracted from the line is passed on to the
     * {@link model.io.Connection.Observer#onMove(String, String, int)}  Connection.Observer.onMove} method
     * for the UI to respond to.
     * @param line The line to extract the data from.
     */
    private void handleMove(String line) {
        ArrayList<String> result = matchQoutes(line);

        this.observer.onMove(
                result.get(0),
                result.size() > 0 ? result.get(2) : "",
                Integer.parseInt(result.get(1))
        );
    }


    /**
     *
     * @param line
     */
    private void handleList(String line) {

    }

}
