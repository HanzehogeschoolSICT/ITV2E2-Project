package model;/*
    Copyright (C) 3/23/17  Hanze Hogeschool ITV2D

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

/**
 * A class that represent a player of a certain game.
 * Each player has a {@link Type} that defines the player as either a Human or Computer, or Opponent.
 * Each player also has a {@link Order} that defines when the player is allowed to take its turn,
 * this is either First or Second.
 */
public class Player {

    private Type type;
    private Order order;

    public Player(Type type, Order order) {
        this.type = type;
        this.order = order;
    }

    public enum Type {
        OPPONENT(0),
        COMPUTER(1),
        HUMAN(2);

        private int code;

        Type(int code) {
            this.code = code;
        }

        public boolean equals(Type type) {
            return this.code == type.code;
        }

        public int getCode() {
            return code;
        }

    }

    public enum Order {
        FIRST(1),
        SECOND(2);

        private int code;

        Order(int code) {
            this.code = code;
        }

        public boolean equals(Order order) {
            return this.code == order.code;
        }

        public int getCode() {
            return code;
        }

    }

    //<editor-fold desc="Getters and Setters">
    public Type getType() {
        return type;
    }

    public Order getOrder() {
        return order;
    }
    //</editor-fold>

}
