package main.events;

public interface GameSubject {
	public void subscribe(GameEventListener listener);
	public void unsubscribe(GameEventListener listener);
}
