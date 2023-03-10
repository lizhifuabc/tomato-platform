package ${package}.${moduleName}.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.tomato.domain.entity.BaseEntity;
<#list importList as i>
import ${i!};
</#list>
<#if baseClass??>
import ${baseClass.packageName}.${baseClass.code};
</#if>

/**
 * ${tableComment}
 *
 * @author ${author} ${email}
 * @since ${version} ${date}
 */
<#if baseClass??>@EqualsAndHashCode(callSuper=false)</#if>
@Data
public class ${ClassName}Entity extends BaseEntity {
<#list fieldList as field>
<#if !field.baseField>
	<#if field.fieldComment!?length gt 0>
	/**
	* ${field.fieldComment}
	*/
	</#if>
	private ${field.attrType} ${field.attrName};
</#if>

</#list>
}