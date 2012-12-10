[#ftl]
[#if rowpackage?? && rowpackage != ""]package ${rowpackage};[/#if]

import [#if package?? && package != ""]${package}.[/#if]${className};
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
  *
  */
public class ${className}Mapper implements RowMapper<${className}> {
	@Override
	public ${className} mapRow(final ResultSet rs, final int rowNum) throws SQLException {
		final ${className} r = new ${className}();

		[#list props as p]
		r.set${p[2]}(rs.${p[3]}("${p[5]}"));
		[#if p[4]?? && p[4] != ""]
		if (rs.wasNull()) r.set${p[2]}(${p[4]});
		[/#if]

		[/#list]
		return r;
	}
}
