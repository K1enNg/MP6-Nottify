package STRUCTURAL_PATTERNS.Proxy;

public class UserProxy implements IUser{
    private RealUser realUser;

    UserProxy(String name){
        realUser = new RealUser(name);
    }

    @Override
    public void showDetails() {
        realUser.showDetails();
    }
}
