package base;

public class BankItem {
	private String name;
	private int withdraw;

	public BankItem(String name, int withdraw) {
		this.name = name;
		this.withdraw = withdraw;
	}

	public String getName() {
		return name;
	}

	public int getWithdrawAmount() {
		return withdraw;
	}

	@Override
	public String toString() {
		return name + " -" + withdraw;
	}
}