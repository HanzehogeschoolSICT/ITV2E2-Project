package test;/*
    Copyright (C) 28-3-17  Hanze Hogeschool ITV2D

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

import model.io.Connection;

import java.io.IOException;


public class ConnectionTest {

    public static void main(String[] args) {

//        System.out.println(Connection.isAdressValid("145.33.225.170"));
//        System.out.println(Connection.isAdressValid("145.333.225.170"));
//        System.out.println(Connection.isAdressValid("145.33.225.170."));
//        System.out.println(Connection.isAdressValid("145.33.225.170.240"));
//        System.out.println(Connection.isAdressValid("255.255.255.255"));

        System.out.println("SVR GAMELIST".contains("SVR GAME"));

        try {
            Connection connection = new Connection("145.33.225.170", 7789);
            connection.setObserver(new Connection.Observer() {
                @Override
                public void onChallenge(String opponentName, int challengeNumber, String gameType) {
                    connection.accept_challenge(challengeNumber);
                }

                @Override
                public void onChallengeCancelled(int challengeNumber, String comment) {
                    //ToDo Show dialog informing the user the challenge was cancelled.
                }

                @Override
                public void onYourTurn(String comment) {
                    //ToDo Wait for board to update.
                }

                @Override
                public void onMove(String player, String details, int move) {
                    //ToDo Update board with the new data.

                }

                @Override
                public void onGameEnd(int statusCode, String comment) {
                    //ToDo Inform the user the game has ended, and how he or she performed.
                }

                @Override
                public void onError(String comment) {
                    //ToDo Inform the user an error has occurred.
                }
            });


            connection.establish("test");
            connection.subscribe("Tic-tac-toe");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
