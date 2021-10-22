import java.util.HashMap;

public interface RoleAPI {

    public void addMoney(int moneyValue, int amount);
    public void removeMoney(int moneyValue, int amount);
    public String walletToString();
}
