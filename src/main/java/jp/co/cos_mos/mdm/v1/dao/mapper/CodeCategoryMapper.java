package jp.co.cos_mos.mdm.v1.dao.mapper;

import java.util.List;

import jp.co.cos_mos.mdm.v1.dao.entity.CodeCategory;
import jp.co.cos_mos.mdm.v1.dao.entity.CodeCategoryCriteria;

public interface CodeCategoryMapper {

	public CodeCategory select(Long id);
	public List<CodeCategory> search(CodeCategoryCriteria criteria);
	public int insert(CodeCategory category);
	public int update(CodeCategory category);
	public int delete(CodeCategory category);
	public int inactive(CodeCategory category);
	
}
