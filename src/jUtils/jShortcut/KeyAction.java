package jUtils.jShortcut;

import jUtils.interfaces.Callback;

public class KeyAction {

	// Initial variables
	private Callback onActionPerformed;
	private int[] modifiers; // look in NativeKeyEvent.
	private int keyCode;

	// Used to prevent action spam
	private boolean ignoreMatch = false;

	// Create a new KeyAction
	public KeyAction(int keyCode, int[] modifiers, Callback onActionPerformed) {
		this.onActionPerformed = onActionPerformed;
		this.modifiers = modifiers;
		this.keyCode = keyCode;
	}

	// Create a new KeyAction (alternative)
	public KeyAction(int keyCode, Callback onActionPerformed, int... modifiers) {
		this.onActionPerformed = onActionPerformed;
		this.modifiers = modifiers;
		this.keyCode = keyCode;
	}

	// Checks if this action's keys and modifiers match with the given ones
	public boolean matches(int keyCode, int modifierMask) {
		// Ignore if already matched
		if (ignoreMatch) {
			return false;
		}

		// Match key code
		if (this.keyCode != keyCode) {
			return false;
		}

		// Match mask
		for (int modifier : modifiers) {
			if ((modifier & modifierMask) == 0) {
				return false;
			}
		}

		return true;
	}

	// Calls the target function if an input matched the action's keys
	public void callIfMatches(int keyCode, int modifierMask) throws Exception {
		if (matches(keyCode, modifierMask)) {
			ignoreMatch = true;
			onActionPerformed.call();
		}
	}

	// Returns the target function of this action
	public Callback getFunction() {
		return onActionPerformed;
	}

	// Sets whether this action should be ignored or not
	public void setIgnored(boolean ignoreMatch) {
		this.ignoreMatch = ignoreMatch;
	}
}