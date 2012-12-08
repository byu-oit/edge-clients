package edu.byu.edge.jdbc.impl;

import edu.byu.edge.jdbc.Exporter;
import edu.byu.edge.jdbc.JdbcGen;
import edu.byu.edge.jdbc.domain.Column;
import edu.byu.edge.jdbc.domain.ColumnComparator;
import edu.byu.edge.jdbc.domain.Table;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.*;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 12/04/2012
 *
 * @author Wyatt Taylor (wyatt_taylor@byu.edu)
 * @since 12/04/2012
 */
public class ExporterImpl implements Exporter {

	private static final PrintWriter OUT = JdbcGen.OUT;

	private Configuration configuration;

	public ExporterImpl() {
		JdbcGen.setExporter(this);
	}

	public void setConfiguration(final Configuration configuration) {
		this.configuration = configuration;
	}

	@Override
	public void export(final List<Table> tables, final String baseFolder, final String pkgName) {
		OUT.println("Exporting tables to " + baseFolder + "...");
		final File bd = new File(baseFolder);
		if (bd.exists() && ! bd.isDirectory()) throw new IllegalArgumentException(baseFolder + " is not a valid folder");
		if (! bd.exists() && ! bd.mkdirs()) throw new IllegalArgumentException("Unable to create base folder: " + baseFolder);
		final File outPath = new File(bd, pkgName.replaceAll("\\.", "/"));
		if (outPath.exists() && ! outPath.isDirectory()) throw new IllegalArgumentException(outPath + " is not a valid folder");
		if (!outPath.exists() && !outPath.mkdirs()) throw new IllegalArgumentException("Unable to create folder: " + outPath);
		for (final Table t : tables) {
			try {
				exportTable(outPath, pkgName, t);
			} catch (final Exception e) {
				OUT.println("Error generating class for " + t.getTableName());
			}
		}
	}

	private void exportTable(final File outPath, final String pkgName, final Table t) throws IOException {
		final String className = createClassName(t.getTableName());

		OUT.println("Exporting " + t.getTableName() + " to " + className);

		final Set<String> imps = new LinkedHashSet<String>(5, .9999f);
		final List<String[]> props = new LinkedList<String[]>();
		final List<List<String[]>> constructors = new LinkedList<List<String[]>>();

		final List<Column> columns = t.getColumns();
		Collections.sort(columns, ColumnComparator.INSTANCE);
		for (final Column c : columns) {
			final String colType = determineColumnType(c);
			OUT.println("\t" + colType + " <- " + c.getDataType() + " " + c.getColType());
			final String[] inf = IMPORT_MAP.get(colType);
			if (inf[0] != null) imps.add(inf[0]);
			final String cn = determineColumnName(c.getColumnName());
			props.add(new String[]{inf[1], cn.substring(0, 1).toLowerCase() + cn.substring(1), cn});
		}

		final HashMap<String, Object> map = new HashMap<String, Object>(8, .9999f);
		map.put("package", pkgName);
		map.put("imports", imps);
		map.put("props", props);
		map.put("className", className);

		final File fout = new File(outPath, className + ".java");
		fout.createNewFile();

		final Template temp = configuration.getTemplate("jdbcDomain.ftl");

		final FileWriter out = new FileWriter(fout);

		try {
			temp.process(map, out);
		} catch (final TemplateException e) {
			OUT.println("Error processing template. " + e.getMessage());
		}
		try {
			out.close();
		} catch (final Throwable ignore) {
		}
	}

	private static String determineColumnType(final Column c) {
		final String rawType = c.getDataType().toLowerCase();
		if (!TYPE_MAPPING.containsKey(rawType)) {
			OUT.println("Unknown data type: " + rawType + ". Defaulting to String.");
			return "java.lang.String";
		}
		final boolean nullable = c.getNullable();
		final String[] type = TYPE_MAPPING.get(rawType);

		if (type == TLONG || type == TDOUBLE || type == TBOOL) {
			return nullable ? NULL_MAP.get(type[0]) : type[0];
		}
		if (type.length == 1) {
			return type[0];
		}
		if (c.getNumScale() > 0) {
			return type[0];
		}
		if (c.getNumPrec() > 9) {
			return nullable ? NULL_MAP.get(type[2]) : type[2];
		}
		return nullable ? NULL_MAP.get(type[1]) : type[1];
	}

	private static String determineColumnName(final String base) {
		if (base == null || "".equals(base)) return "";
		final String[] parts = base.split(DB_IDENT_SEP);
		final StringBuilder sb = new StringBuilder(base.length());
		for (final String p : parts) {
			sb.append(p.substring(0, 1).toUpperCase());
			sb.append(p.substring(1).toLowerCase());
		}
		return sb.toString();
	}

	private static String createClassName(final String base) {
		if (base == null || "".equals(base)) return "";
		final String[] parts = base.split(DB_IDENT_SEP);
		final StringBuilder sb = new StringBuilder(base.length());
		for (final String p : parts) {
			sb.append(p.substring(0, 1).toUpperCase());
			sb.append(p.substring(1).toLowerCase());
		}
		return sb.toString();
	}

	private static final String DB_IDENT_SEP = "[_\\.\\/]+";

	private static final String[] STRING = {"java.lang.String"};
	private static final String[] TBOOL = {"boolean"};
	private static final String[] TINT = {"int"};
	private static final String[] TLONG = {"long"};
	private static final String[] TDOUBLE = {"double"};
	private static final String[] BYTEARR = {"byte[]"};
	private static final String[] DATE = {"java.util.Date"};
	private static final String[] TBIGDEC = {"java.math.BigDecimal"};
	private static final String[] TNUMBER = {"java.math.BigDecimal", "int", "long"};

	private static final Map<String, String[]> TYPE_MAPPING = createTypeMapping();
	private static final Map<String, String> NULL_MAP = createNullMap();
	private static final Map<String, String[]> IMPORT_MAP = createImportMap();

	private static Map<String, String[]> createTypeMapping() {
		final Map<String, String[]> map = new HashMap<String, String[]>(36, .9999f);

		map.put("bigint", TLONG);
		map.put("binary", BYTEARR);
		map.put("bit", TBOOL);
		map.put("blob", BYTEARR);
		map.put("boolean", TBOOL);
		map.put("char", STRING);
		map.put("date", DATE);
		map.put("datetime", DATE);
		map.put("decimal", TBIGDEC);
		map.put("double", TDOUBLE);
		map.put("enum", STRING);
		map.put("float", TDOUBLE);
		map.put("int", TNUMBER);
		map.put("interval day", STRING);
		map.put("interval year", STRING);
		map.put("long raw", BYTEARR);
		map.put("longblob", BYTEARR);
		map.put("longtext", STRING);
		map.put("mediumblob", BYTEARR);
		map.put("mediumint", TINT);
		map.put("mediumtext", STRING);
		map.put("number", TNUMBER);
		map.put("numeric", TNUMBER);
		map.put("raw", BYTEARR);
		map.put("set", STRING);
		map.put("smallint", TINT);
		map.put("text", STRING);
		map.put("time", STRING);
		map.put("timestamp", DATE);
		map.put("tinyblob", BYTEARR);
		map.put("tinyint", TINT);
		map.put("tinytext", STRING);
		map.put("varbinary", BYTEARR);
		map.put("varchar", STRING);
		map.put("varchar2", STRING);

		return Collections.unmodifiableMap(map);
	}

	private static Map<String, String> createNullMap() {
		final Map<String, String> map = new HashMap<String, String>(5, .9999f);

		map.put("int", "java.lang.Integer");
		map.put("long", "java.lang.Long");
		map.put("boolean", "java.lang.Boolean");
		map.put("double", "java.lang.Double");

		return Collections.unmodifiableMap(map);
	}

	private static Map<String, String[]> createImportMap() {
		final Map<String, String[]> map = new HashMap<String, String[]>(12, .9999f);

		map.put("int", new String[]{null, "int"});
		map.put("long", new String[]{null, "long"});
		map.put("boolean", new String[]{null, "boolean"});
		map.put("double", new String[]{null, "double"});
		map.put("java.lang.Integer", new String[]{null, "Integer"});
		map.put("java.lang.Long", new String[]{null, "Long"});
		map.put("java.lang.Boolean", new String[]{null, "Boolean"});
		map.put("java.lang.Double", new String[]{null, "Double"});
		map.put("java.lang.String", new String[]{null, "String"});
		map.put("java.util.Date", new String[]{"java.util.Date", "Date"});
		map.put("java.math.BigDecimal", new String[]{"java.math.BigDecimal", "BigDecimal"});

		return Collections.unmodifiableMap(map);
	}
}
