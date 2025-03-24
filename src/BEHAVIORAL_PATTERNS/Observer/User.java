package BEHAVIORAL_PATTERNS.Observer;

class User implements Observer{
    String name;
    public User(String name){
        this.name = name;
    }
    @Override
    public void update(String message) {
        System.out.println(name + "received notification: " + message);
    }
}
