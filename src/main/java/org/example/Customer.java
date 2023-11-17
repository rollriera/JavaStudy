package org.example;

import org.example.cookTest.Cook;
import org.example.cookTest.Cooking;
import org.example.cookTest.Menu;
import org.example.cookTest.MenuItem;

public class Customer {

    public void order(String menuName, Menu menu, Cooking cooking){
        MenuItem menuItem = menu.choose(menuName);
        Cook cook = cooking.makeCook(menuItem);
    }
}
