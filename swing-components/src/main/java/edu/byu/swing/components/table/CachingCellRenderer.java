package edu.byu.swing.components.table;

import edu.byu.framework.swing.util.BYUCallable;
import edu.byu.framework.swing.util.RendererUtil;
import edu.byu.framework.swing.util.Retry;
import java.awt.Component;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import org.apache.log4j.Logger;
import org.jvnet.substance.api.renderers.SubstanceDefaultTableCellRenderer;

/**
 *
 * @author jmooreoa
 */
public abstract class CachingCellRenderer<K, V> extends SubstanceDefaultTableCellRenderer {

    private final ConcurrentMap<K, Entry<V>> CACHE = new ConcurrentHashMap<K, Entry<V>>();
	private final Logger LOG = Logger.getLogger(String.format("%s-%s", CachingCellRenderer.class.getName(), getClass().getSimpleName()));
    private final ConcurrentMap<K, Long> BLACKLIST = new ConcurrentHashMap<K, Long>();
    private final Set<K> PROCESSING = Collections.synchronizedSet(new HashSet<K>());
    private final ExecutorService EXECUTOR = Executors.newCachedThreadPool();
    private final Set<JTable> TABLES = Collections.synchronizedSet(new HashSet<JTable>());

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		storeTable(table);
		RendererUtil.getDefaultListRenderer();
		return super.getTableCellRendererComponent(table, convert(value), isSelected, hasFocus, row, column);
    }

    private void storeTable(JTable table) {
	TABLES.add(table);
    }

    private Object convert(Object o) {
	if (o == null) {
	    return null;
	}
	K key = (K) o;
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
            LOG.debug("Key " + key + " is being loaded");
	    return;
	}
	EXECUTOR.execute(new ValueFetcher(key));
    }

    private void blacklist(K key) {
	LOG.debug("Blacklisting key " + key);
	BLACKLIST.putIfAbsent(key, System.currentTimeMillis());
    }

    protected class ValueFetcher implements Runnable {

	private final K key;

	public ValueFetcher(K key) {
	    this.key = key;
	}

	@Override
	public void run() {
	    Future<V> f = EXECUTOR.submit(new ValueCallable(key));
	    try {
		V value = f.get();
		cacheValue(key, value);
	    } catch (Exception ex) {
		LOG.error("Error fetching value", ex);
	    } finally {
		processed(key);
		notifyTables();
	    }

	}
    }

    private class ValueCallable extends BYUCallable<V> {

	private final K key;

	public ValueCallable(K key) {
	    this.key = key;
	}

	@Override
	@Retry
	protected V doInBackground() throws Exception {
	    LOG.debug("Finding value of " + key);
	    return getValue(key);
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

    private synchronized void notifyTables() {
	for (JTable each : TABLES) {
	    each.repaint();
	}
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
