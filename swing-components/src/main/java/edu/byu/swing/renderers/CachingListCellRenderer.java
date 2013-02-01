package edu.byu.swing.renderers;

import edu.byu.framework.swing.util.BYURunnable;
import java.awt.Component;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.SwingUtilities;

import edu.byu.framework.swing.util.Retry;
import org.apache.log4j.Logger;

/**
 *
 * @author jmooreoa
 */
public abstract class CachingListCellRenderer<K, V> extends DefaultListCellRenderer {

	private final ConcurrentMap<K, Entry<V>> CACHE = new ConcurrentHashMap<K, Entry<V>>();
	private final Logger LOG = Logger.getLogger(String.format("%s-%s", CachingListCellRenderer.class.getName(), getClass().getSimpleName()));
	private final ConcurrentMap<K, Long> BLACKLIST = new ConcurrentHashMap<K, Long>();
	private final Set<K> PROCESSING = Collections.synchronizedSet(new HashSet<K>());
	private final ExecutorService EXECUTOR = Executors.newFixedThreadPool(10);
	private final Set<JList> LISTS = Collections.synchronizedSet(new HashSet<JList>());

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		storeList(list);
		return super.getListCellRendererComponent(list, convert(value), index, isSelected, cellHasFocus);
	}

	private void storeList(JList list) {
		LISTS.add(list);
	}

	private Object convert(Object o) {
		if (o == null) {
			return null;
		}
		K key = (K) o;
		if (PROCESSING.contains(key)) {
			LOG.debug(String.format("Value is being loaded for %s", key));
			return getLoadingMessage(key);
		}
		LOG.debug("Finding information for key: " + key);
		if (isBlacklisted(key)) {
			LOG.debug("Key " + key + " has been blacklisted");
			return getBlacklistMessage(key);
		}
		LOG.debug("Checking cache for key " + key);

		Entry<V> entry = CACHE.get(key);

		if (entry == null || isExpired(entry)) {
			LOG.debug(key + " is not in cache or is expired");
			fetchValue(key);
			return getLoadingMessage(key);
		}

		return getDisplayValue(entry.getValue());
	}

	private boolean isExpired(Entry<V> entry) {
		return entry.getTime() < (System.currentTimeMillis() - getRefreshInterval());
	}

	private boolean isBlacklisted(K key) {
		Long blacklisted = BLACKLIST.get(key);
		if (blacklisted == null) {
			return false;
		}
		if (blacklisted > (System.currentTimeMillis() - getRefreshInterval())) {
			return true;
		}
		BLACKLIST.remove(key);
		return false;
	}

	private void fetchValue(K key) {
		if (!PROCESSING.add(key)) {
			return;
		}
		EXECUTOR.submit(new ValueFetcher(key));
	}

	private void blacklist(K key) {
		LOG.debug("Blacklisting key " + key);
		BLACKLIST.putIfAbsent(key, System.currentTimeMillis());
	}

	protected class ValueFetcher extends BYURunnable {

		private final K key;

		public ValueFetcher(K key) {
			this.key = key;
		}

		@Retry
		@Override
		protected void doInBackground() throws Exception {
			LOG.debug("Finding value of " + key);
			V value = getValue(key);
			LOG.trace("Found value of " + value);
			cacheValue(key, value);
			LOG.trace("Cached value");
			processed(key);
			LOG.trace("Removed from processing queue");
			notifyLists();
			LOG.trace("Fired list notification event");
		}
	}

	private void cacheValue(K key, V value) {
		if (value == null) {
			LOG.debug("Could not find value for key " + key);
			blacklist(key);
		} else {
			LOG.debug("Caching value of key " + key);
			CACHE.put(key, new Entry<V>(value));
		}
	}

	private void processed(K key) {
		PROCESSING.remove(key);
	}

	private synchronized void notifyLists() {
		Runnable r = new Runnable() {
			public void run() {
				for (JList each : LISTS) {
					each.repaint();
				}
			}
		};
		SwingUtilities.invokeLater(r);
	}

	protected abstract Object getDisplayValue(V o);

	protected abstract long getRefreshInterval();

	protected abstract Object getBlacklistMessage(K key);

	protected abstract Object getLoadingMessage(K key);

	protected abstract V getValue(K key);

	private class Entry<V> {

		private V value;
		private long time;

		public Entry(V value) {
			this.value = value;
			this.time = System.currentTimeMillis();
		}

		public long getTime() {
			return time;
		}

		public void setTime(long time) {
			this.time = time;
		}

		public V getValue() {
			return value;
		}

		public void setValue(V value) {
			this.value = value;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final Entry<V> other = (Entry<V>) obj;
			if (this.value != other.value && (this.value == null || !this.value.equals(other.value))) {
				return false;
			}
			if (this.time != other.time) {
				return false;
			}
			return true;
		}

		@Override
		public int hashCode() {
			int hash = 5;
			hash = 59 * hash + (this.value != null ? this.value.hashCode() : 0);
			hash = 59 * hash + (int) (this.time ^ (this.time >>> 32));
			return hash;
		}
	}
}
