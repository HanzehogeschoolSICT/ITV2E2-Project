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

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import model.Connection;

import java.io.IOException;
import java.util.ArrayList;


public class ConnectionTest {

    public static void main(String[] args) {

//        System.out.println(Connection.isAdressValid("145.33.225.170"));
//        System.out.println(Connection.isAdressValid("145.333.225.170"));
//        System.out.println(Connection.isAdressValid("145.33.225.170."));
//        System.out.println(Connection.isAdressValid("145.33.225.170.240"));
//        System.out.println(Connection.isAdressValid("255.255.255.255"));

        Connection connection = new Connection("145.33.225.170", 7789) {
            @Override
            public boolean logout() {
                return false;
            }

            @Override
            public ArrayList<String> getPlayerList() {
                return null;
            }

            @Override
            public boolean subscribe(@NotNull String gameType) {
                return false;
            }

            @Override
            public boolean move(@NotNull Integer row, @NotNull Integer column) {
                return false;
            }

            @Override
            public boolean forfeit() {
                return false;
            }

            @Override
            public boolean challenge(String opponentName, String gameType) {
                return false;
            }

            @Override
            public boolean accept_challenge(Integer challengeNumber) {
                return false;
            }

            @Override
            public String help(@Nullable String command) {
                return null;
            }
        };
        try {
            connection.setOutputObserver(() -> connection.login("Harjan"));
            connection.establish();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
