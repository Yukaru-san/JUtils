package jUtils.jTray;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionListener;

public class JTray {

	// Required variables
	private String name;
	private Image img;
	private PopupMenu popup;

	// Tray object
	private TrayIcon trayIcon;

	// Constructor containing all required variables
	public JTray(String name, Image img, PopupMenu popup) {
		this.name = name;
		this.popup = popup;
		this.img = img;
	}

	// Constructor with minimal functionality and custom name and image
	public JTray(String name, Image img) {
		this.name = name;
		this.img = img;
		popup = new PopupMenu();
	}

	// Adds an entry to the tray containing a callback function
	public MenuItem addEntry(String title, ActionListener actionListener) {
		MenuItem item = new MenuItem(title);
		item.addActionListener(actionListener);
		popup.add(item);

		return item;
	}

	// Adds a separator to the menu
	public void addSeparator() {
		popup.addSeparator();
	}

	// Removes the entry at the given index. Note that separators count aswell
	public void removeEntry(int index) {
		popup.remove(index);
	}

	// Removes the given entry
	public void removeEntry(MenuItem item) {
		popup.remove(item);
	}

	// Returns the whole PopupMenu object for custom handling
	public PopupMenu getMenu() {
		return popup;
	}

	// Returns the whole TrayIcon object for custom handling
	public TrayIcon getTrayIcon() {
		return trayIcon;
	}

	// Adds an icon to the Tray

	/**
	 * Shows the tray icon using the objects name, img and components
	 * 
	 * @throws NullPointerException     if {@code trayIcon} is {@code null}
	 * @throws IllegalArgumentException if the same instance of a {@code TrayIcon}
	 *                                  is added more than once
	 * @throws AWTException             if the desktop system tray is missing
	 */
	public void show() throws NullPointerException, IllegalArgumentException, AWTException {
		SystemTray tray = SystemTray.getSystemTray();

		if (img != null) {
			trayIcon = new TrayIcon(img, name, popup);
			tray.add(trayIcon);
			return;
		}
	}

	// Hides the tray icon if shown
	public void hide() {
		SystemTray tray = SystemTray.getSystemTray();

		tray.remove(trayIcon);
	}
}
