package act.reports.model;

import java.util.List;

public class AccountsReceivableAccTypeDetailList {

	private List<String> account;
	private List<String> unPaid;
	private List<String> balance;
	
	public List<String> getAccount() {
		return account;
	}
	public void setAccount(List<String> account) {
		this.account = account;
	}
	public List<String> getUnPaid() {
		return unPaid;
	}
	public void setUnPaid(List<String> unPaid) {
		this.unPaid = unPaid;
	}
	public List<String> getBalance() {
		return balance;
	}
	public void setBalance(List<String> balance) {
		this.balance = balance;
	}						
}
