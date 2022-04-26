package jShortcut;
import java.util.ArrayList;
import java.util.List;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class JShortcut implements NativeKeyListener {

	// List of all KeyActions
	private List<KeyAction> actionList;

	// -- Constructor
	
	public JShortcut(List<KeyAction> keyActions) {
		actionList = keyActions;
	}
	
	public JShortcut() {
		actionList = new ArrayList<KeyAction>();
	}
	
	// --- Action handling
	
	// Add an action to the list
	public KeyAction addAction(KeyAction action) throws UnsupportedOperationException, ClassCastException, NullPointerException, IllegalArgumentException{
		actionList.add(action);
		return action;
	}
	
	// Remove a specific KeyAction
	public KeyAction removeAction(KeyAction action) throws ClassCastException, UnsupportedOperationException, IndexOutOfBoundsException{
		actionList.remove(action);
		return action;
	}
	
	// Remove a KeyAction at the given index
	public KeyAction removeActionAt(int i) throws UnsupportedOperationException, IndexOutOfBoundsException {
		KeyAction action = actionList.get(i);
		actionList.remove(i);
		return action;
	}
	
	// --- Start / Stop	
	
	// Starts listening for KeyActions
	public void start() throws NativeHookException {
		GlobalScreen.registerNativeHook();
		GlobalScreen.addNativeKeyListener(this);
	}
	
	// Stops listening
	public void stop() throws NativeHookException {
		GlobalScreen.unregisterNativeHook();
		GlobalScreen.removeNativeKeyListener(this);
	}
	
	// --- Native events
	
	public void nativeKeyPressed(NativeKeyEvent e) {
		try {
			for (KeyAction action : actionList) {
				action.callIfMatches(e.getKeyCode(), e.getModifiers());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			emergencyExit();
		}
	}

	public void nativeKeyReleased(NativeKeyEvent e) {
		System.out.println("released");
		for (KeyAction action : actionList) {
			action.setIgnored(action.matches(e.getKeyCode(), e.getModifiers()));
		}
	}

	// Close when there are issues with the action's function (fault of the programmer anyway)
	private void emergencyExit() {
		System.out.println("Can't recover from Exceptions made by \"Callable\" Methods. Hook closed!");
		try {
			stop();
		} catch (NativeHookException e) {}
	}
}
