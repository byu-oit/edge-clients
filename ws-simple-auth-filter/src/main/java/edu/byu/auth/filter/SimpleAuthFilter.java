package edu.byu.auth.filter;

import edu.byu.security.userdetails.IdentityDetails;
import edu.byu.security.userdetails.IdentityDetailsDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 05/07/2013
 *
 * @author Wyatt Taylor (wyatt_taylor@byu.edu)
 * @since 05/07/2013
 */
public class SimpleAuthFilter extends AbstractAuthenticationProcessingFilter implements Filter, InitializingBean {

	private static final Logger LOG = Logger.getLogger(SimpleAuthFilter.class);

	private static final Pattern NET_ID_PATTERN = Pattern.compile("^[a-z]{1}[a-z0-9]*?$");

	private static final List<AuthHeaders> HEADER_NAMES;
	static {
		final List<AuthHeaders> list = Arrays.asList(AuthHeaders.values());
		Collections.sort(list, new Comparator<AuthHeaders>() {
			@Override
			public int compare(final AuthHeaders o1, final AuthHeaders o2) {
				return o1.getOrder() - o2.getOrder();
			}
		});
		HEADER_NAMES = Collections.unmodifiableList(list);
	}

	protected IdentityDetailsDao iddao;
	protected List<String> allowedIpAddresses;
	protected String allowedIpFileName;
	protected volatile String allowedIpFilePropertyName = "byu.allowed.ip.file";

	public SimpleAuthFilter(final String defaultFilterProcessesUrl) {
		super(defaultFilterProcessesUrl);
	}

	public void setIddao(final IdentityDetailsDao iddao) {
		this.iddao = iddao;
	}

	public void setAllowedIpAddresses(final List<String> allowedIpAddresses) {
		this.allowedIpAddresses = allowedIpAddresses;
	}

	public void setAllowedIpFileName(final String allowedIpFileName) {
		this.allowedIpFileName = allowedIpFileName;
	}

	public void setAllowedIpFilePropertyName(final String allowedIpFilePropertyName) {
		this.allowedIpFilePropertyName = allowedIpFilePropertyName;
	}

	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();
		if (!checkList()) throw new IllegalArgumentException("Specify either a file name or a list of allowed IP addresses or a System property that points to a file of allowed IP addresses.");
		if (iddao == null) throw new IllegalStateException("An IdentityDetailsDao is required for this filter.");
	}

	protected boolean checkList() {
		if ((allowedIpAddresses == null || allowedIpAddresses.isEmpty()) && !checkFileName()) return false;
		allowedIpAddresses.add("127.0.0.1");
		return true;
	}

	protected boolean checkFileName() {
		if ((allowedIpFileName == null || "".equals(allowedIpFileName)) && ! checkProperty()) return false;
		final File file = new File(allowedIpFileName);
		if (!file.exists() || !file.isFile() || file.length() < 7) return false;
		BufferedReader in = null;
		final List<String> list = new LinkedList<String>();
		try {
			in = new BufferedReader(new FileReader(file));
			String line;
			while ((line = in.readLine()) != null) {
				if (!line.startsWith("#")) list.add(line);
			}
			return true;
		} catch (final Throwable t) {
			return false;
		} finally {
			allowedIpAddresses = new ArrayList<String>(list.size() + 2);
			allowedIpAddresses.addAll(list);
			if (in != null) try {
				in.close();
			} catch (IOException e) {
			}
		}
	}

	protected boolean checkProperty() {
		if (allowedIpFilePropertyName == null || "".equals(allowedIpFilePropertyName)) return false;
		String propVal = System.getProperty(allowedIpFilePropertyName);
		if (propVal == null || "".equals(propVal)) propVal = System.getenv(allowedIpFilePropertyName);
		if (propVal == null || "".equals(propVal)) return false;
		allowedIpFileName = propVal;
		return true;
	}

	@Override
	public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		final String remoteAddr = request.getRemoteAddr();
		if (!allowedIpAddresses.contains(remoteAddr)) {
			LOG.info("Rejecting request due to unauthorized source host. " + remoteAddr);
			throw new IllegalArgumentException("Request does not come from an allowed host.");
		}
		final Map<String, String> map = extractAuthHeaders(request);
		if (map.isEmpty()) return null;
		final String[] idToLookup = findBestId(map);
		if ("".equals(idToLookup[1])) throw new IllegalArgumentException("Unable to find correct token header.");;
		try {
			final IdentityDetails details = (IdentityDetails) iddao.loadUserByUsername(idToLookup[1]);
			return new HeaderAuth(details, idToLookup[0]);
		} catch (final Throwable t) {
			LOG.error(String.format("Error processing authentication: '%s' '%s'.", idToLookup[0], idToLookup[1]), t);
			throw new IllegalArgumentException("Unable to process authentication.", t);
		}
	}

	@Override
	public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain) throws IOException, ServletException {
		super.doFilter(req, res, chain);
		chain.doFilter(req, res);
	}

	private static String[] findBestId(final Map<String, String> map) {
		for (final AuthHeaders headerName : HEADER_NAMES) {
			if (map.containsKey(headerName.getHeaderName())) return new String[]{headerName.getHeaderName(), map.get(headerName.getHeaderName())};
		}
		return new String[]{"", ""};
	}

	private static Map<String, String> extractAuthHeaders(final HttpServletRequest req) {
		final Map<String, String> result = new HashMap<String, String>(7, .999999f);
		for (final AuthHeaders ah : HEADER_NAMES) {
			final String hn = ah.getHeaderName();
			final String hv = req.getHeader(hn);
			if (hv != null && !"".equals(hv)) result.put(hn, hv);
		}
		return result;
	}

	/**
	 * Determines if the given httpServletRequest matches the filterProcessesUrl.
	 * @param httpServletRequest HttpServletRequest to match against the filterProcessesUrl.
	 * @return boolean true if the request path startes with the filterProcessesUrl, false otherwise.
	 */
	private boolean matchesFilterProcessesUrl(final HttpServletRequest httpServletRequest) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("matchesFilterProcessesURL: " + getRequestPath(httpServletRequest).startsWith(getFilterProcessesUrl()));
		}
		return getRequestPath(httpServletRequest).startsWith(getFilterProcessesUrl());
	}

	/**
	 * Constructs the request path from the given httpServletRequest.
	 * @param httpServletRequest HttpServletRequest to build the request path from.
	 * @return request path which includes the servletPath and pathInfo fro the httpServletRequest.
	 */
	private String getRequestPath(final HttpServletRequest httpServletRequest) {
		StringBuilder path = new StringBuilder();
		String servletPath = httpServletRequest.getServletPath();
		String pathInfo = httpServletRequest.getPathInfo();

		if (servletPath != null && servletPath.length() > 0) {
			path.append(servletPath);
		}

		if (pathInfo != null && pathInfo.length() > 0) {
			path.append(pathInfo);
		}

		if (path.length() == 0) {
			path.append("/");
		}
		return path.toString();
	}

	@Override
	protected boolean requiresAuthentication(final HttpServletRequest request, final HttpServletResponse response) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("requires Authentication = " + matchesFilterProcessesUrl(request));
		}
		return matchesFilterProcessesUrl(request);
	}

	@Override
	protected boolean getAllowSessionCreation() {
		return false;
	}
}
