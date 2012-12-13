[#ftl]
[#if package?? && package != ""]package ${package}.da;[/#if]

import [#if package?? && package != ""]${package}.[/#if]domain.${className};

/**
  *
  */
public interface ${className}Dao {

}
