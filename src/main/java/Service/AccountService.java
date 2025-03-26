package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    // overloaded constructor if needed for testing 
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    /**
     * Use this for registering an account 
     *  NOTE: username must not be blank and must not already exist, password must be at least 4 char long
     * 
     * @param account an account obj
     * @return persisted account if successful
     */
    public Account registerAccount(Account account){
        if(account.username == "" || accountDAO.getAccountByUsername(account.username) != null || account.password.length() < 4 ){
            return null;
        } else {
            return accountDAO.insertAccount(account);
        }
    }

    public Account loginAccount(Account account){
        Account accountFromDB = accountDAO.getAccountByUsername(account.username);

        if(accountFromDB == null || !accountFromDB.password.equals(account.password)){
            return null;
        } else {
            return accountFromDB;
        }
    }
}
