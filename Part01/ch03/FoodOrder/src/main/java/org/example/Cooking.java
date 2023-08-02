package org.example;

public class Cooking {
    public Cooking() {
    }

    public Cook makeCook(MenuItem menuItem) {
//        return new Cook(menuItem.getName(), menuItem.getPrice());
//        return new Cook("돈까스", 5000);
        return new Cook(menuItem);
    }

}
