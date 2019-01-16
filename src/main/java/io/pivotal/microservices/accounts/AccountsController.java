package io.pivotal.microservices.accounts;

import io.pivotal.microservices.exceptions.AccountNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * A RESTFul controller for accessing account information.
 * 
 * @author Paul Chapman
 */
@RestController
public class AccountsController {

	protected Logger logger = Logger.getLogger(AccountsController.class.getName());
	
	protected AccountRepository accountRepository;
	
	/**
	 * Create an instance plugging in the repository of Accounts.
	 * 
	 * @param accountRepository
	 *            An account repository implementation.
	 */
	@Autowired
	public AccountsController(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;

		logger.info("AccountRepository says system has "
				+ accountRepository.countAccounts() + " accounts");
	}

	/**
	 * Fetch an account with the specified account number.
	 * 
	 * @param accountNumber
	 *            A numeric, 9 digit account number.
	 * @return The account if found.
	 * @throws AccountNotFoundException
	 *             If the number is not recognised.
	 */
	@RequestMapping("/accounts/{accountNumber}")
	public <T> T byNumber(@PathVariable("accountNumber") String accountNumber) {

		logger.info("accounts-service byNumber() invoked: " + accountNumber);
		Account account = accountRepository.findByNumber(accountNumber);
		logger.info("accounts-service byNumber() found: " + account);

		if (account == null)
			throw new AccountNotFoundException(accountNumber);
		
		return getSuccessMessage(account);		
	}
	
	public <T> T getSuccessMessage(Object object){
		
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("apimessage", "ok");
		map.put("apiresult", 0);
		map.put("apivalue", object);
		
		return (T) map;
	}

	/**
	 * Fetch accounts with the specified name. A partial case-insensitive match
	 * is supported. So <code>http://.../accounts/owner/a</code> will find any
	 * accounts with upper or lower case 'a' in their name.
	 * 
	 * @param partialName
	 * @return A non-null, non-empty set of accounts.
	 * @throws AccountNotFoundException
	 *             If there are no matches at all.
	 */
	@RequestMapping("/accounts/owner/{name}")
	public <T> T byOwner(@PathVariable("name") String partialName) {
		logger.info("accounts-service byOwner() invoked: "
				+ accountRepository.getClass().getName() + " for "
				+ partialName);

		List<Account> accounts = accountRepository
				.findByOwnerContainingIgnoreCase(partialName);
		logger.info("accounts-service byOwner() found: " + accounts);

		if (accounts == null || accounts.size() == 0)
			throw new AccountNotFoundException(partialName);
		
		return getSuccessMessage(accounts);
	}
	
	// https://stackoverflow.com/questions/6416706/easy-way-to-convert-iterable-to-collection
	public static <E> Collection<E> makeCollection(Iterable<E> iter) {
	    Collection<E> list = new ArrayList<E>();
	    for (E item : iter) {
	        list.add(item);
	    }
	    return list;
	}
}
