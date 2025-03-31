package STRUCTURAL_PATTERNS.Proxy;

public class RealUser implements IUser{
    private String name;
    public RealUser(String name){
        this.name = name;
    }
    @Override
    public void showDetails() {
        System.out.println("CREATIONAL_PATTERNS.User: " + name);
    }
}
