package main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import lib.Block;
import lib.Map;

public class Snake {
 
 private Map map;
 private List<Block> body = new ArrayList<Block>();
 private int dx = 0;
 private int dy = -1;
 private Color color;
 
 public Snake(int x, int y, Map map) {
  this.map = map;
  
  //Random color
  int r = (int)(Math.random() * 256);
  int g = (int)(Math.random() * 256);
  int b = (int)(Math.random() * 256);
  color = new Color(r, g, b);  
  
  body.add(new Block(x, y, color));
  body.add(new Block(x, y+1, color));
  body.add(new Block(x, y+2, color));
 }
 
 public List<Block> getBody() {
  return body;
 }
 
 public void move() {
  System.out.println("Snake is moving");
  Block lastBlock = body.remove(body.size()-1);
  Block firstBlock = body.get(0);
  
  lastBlock.setY(firstBlock.getY() + dy);
  lastBlock.setX(firstBlock.getX() + dx);
  
  if(lastBlock.getX() < 0) {
   lastBlock.setX(map.getSize() - 1);
  } else if(lastBlock.getX() >= map.getSize()){
   lastBlock.setX(0);
  }
  
  if(lastBlock.getY() < 0) {
   lastBlock.setY(map.getSize() - 1);
  } else if(lastBlock.getY() >= map.getSize()) {
   lastBlock.setY(0);
  }
  
  body.add(0,lastBlock);
 }
 
 public void setDx(int dx) {
  this.dx = dx;
 }
 
 public void setDy(int dy) {
  this.dy = dy;
 }
 
 public int getDx() {
  return this.dx;
 }
 
 public int getDy() {
  return this.dy;
 }
 
 public Block expand() {
  Block lastBlock = body.get(body.size() - 1);
  Block last2Block = body.get(body.size() - 2);
  int ddx = lastBlock.getX() - last2Block.getX();
  int ddy = lastBlock.getY() - last2Block.getY();
  
  Block newBlock = new Block(lastBlock.getX() + ddx, lastBlock.getY() + ddy, color);
  body.add(newBlock);
  return newBlock;
 }
 
 public boolean hitItself() {
  Block head = body.get(0);
  for(int i = 1; i < body.size(); i++) {
   if(head.overlapped(body.get(i))) {
    return true;
   }
  }
  return false;
 }
}