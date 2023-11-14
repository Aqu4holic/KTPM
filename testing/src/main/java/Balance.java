public class Balance {
	private static volatile Balance currentInstance = null;

	private int currentBalance;

	private Balance() {
		currentBalance = 0;
	}

	public void increase(int val) {
		synchronized (this) {
			currentBalance += val;
		}
	}

	public boolean decrease(int val) {
		synchronized (this) {
			if (val > currentBalance) {
				return false;
			}

			currentBalance -= val;

			return true;
		}
	}

	public static synchronized Balance getCurrentInstance() {
		if (currentInstance == null) {
			synchronized (Balance.class) {
				Balance inst = currentInstance;

				if (inst == null) {
					synchronized (Balance.class) {
						currentInstance = new Balance();
					}
				}
			}
		}

		return currentInstance;
	}

	public int getCurrentBalance() {
		synchronized (this) {
			return currentBalance;
		}
	}
}
