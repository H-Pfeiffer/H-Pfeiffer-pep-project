package Service;

import DAO.AccountDAO;
import Model.Account;

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
     * This registers a new account 
     *  NOTE: username must not be blank and must not already exist, password must be at least 4 char long
     * 
     * @param account an account obj
     * @return persisted account if successful, else null
     */
    public Account registerAccount(Account account){
        if(account.getUsername() == "" || accountDAO.getAccountByUsername(account.getUsername()) != null || account.getPassword().length() < 4 ){
            return null;
        } else {
            return accountDAO.insertAccount(account);
        }
    }

    /**
     * This logs a user in - checking for valid username and correct password
     *  NOTE: username must not be blank and in the database, password must match password stored in database
     * 
     * @param account an account obj
     * @return persisted account if successful, else null
     */
    public Account loginAccount(Account account){
        Account accountFromDB = account.getUsername() != "" ? accountDAO.getAccountByUsername(account.getUsername()) : null;

        if(accountFromDB == null || !accountFromDB.password.equals(account.getPassword())){
            return null;
        } else {
            return accountFromDB;
        }
    }
}
