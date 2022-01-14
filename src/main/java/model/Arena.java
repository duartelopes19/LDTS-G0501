package model;

import com.googlecode.lanterna.graphics.TextGraphics;
import model.element.Drawable;
import model.element.Element;
import model.element.Snake;
import model.element.fruit.*;
import view.LanternaGUI;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.floor;
import static java.lang.Math.random;

public class Arena implements Drawable {

    int height;
    int width;
    Fruit fruit1;
    Fruit fruit2;

    private List<Drawable> elements = new ArrayList<>();
    private List<Snake> snakes = new ArrayList<>();
    private final List<Fruit> POSSIBLE_FRUITS = new ArrayList<>();

    public Arena(Snake snake, LanternaGUI screen) {
        height = screen.getHeight()-1;
        width = screen.getWidth();
        elements.add(snake);
        snakes.add(snake);
        POSSIBLE_FRUITS.add(new Apple(new Position(0,0)));
        POSSIBLE_FRUITS.add(new Orange(new Position(0,0)));
        POSSIBLE_FRUITS.add(new Kiwi(new Position(0,0)));
        POSSIBLE_FRUITS.add(new Banana(new Position(0,0)));
        addFruits();
    }

    @Override
    public void draw(TextGraphics screen) {
        for(Drawable drawable:elements){
            drawable.draw(screen);
        }
    }

    public Boolean execute(){
        for(Snake snake:snakes){
            snake.move(height,width);
            checkEatFruits(snake);
            check_snake_collisions(snake);
            if(!snake.isAlive())
                return true;
        }
        return false;
    }

    public void checkEatFruits(Snake snake){
        if(snake.getSnakeHead().getPosition().equals(fruit1.getPosition())){
            snake.eatFruit(fruit1,height,width);
            addFruits();
        }
        if(snake.getSnakeHead().getPosition().equals(fruit2.getPosition())){
            snake.eatFruit(fruit2,height,width);
            addFruits();
        }
    }

    public boolean check_snake_collisions(Snake snake){
        Position SnakeHeadPosition = snake.getSnakeHead().getPosition();
        for(Element b : snake.getSnake().subList(1,snake.getSnake().size())){
            if(b.getPosition().equals(SnakeHeadPosition))
                snake.set_Alive(false);
                return true;
        }
        return false;
    }




    public void addFruits(){
        elements.remove(fruit1);
        elements.remove(fruit2);
        double number1 = floor(random() * POSSIBLE_FRUITS.size());
        double number2 = number1;
        while(number2 == number1){
            number2 = floor(random() * POSSIBLE_FRUITS.size());
        }
        Fruit f1 = POSSIBLE_FRUITS.get(((int) number1));
        Fruit f2 = POSSIBLE_FRUITS.get(((int) number2));
        f1.setPosition(new Position((int)floor(random()*(width)),(int)floor(random()*(height))));
        f2.setPosition(new Position((int)floor(random()*(width)),(int)floor(random()*(height))));
        fruit1 = f1;
        fruit2 = f2;
        elements.add(fruit1);
        elements.add(fruit2);
    }



}
