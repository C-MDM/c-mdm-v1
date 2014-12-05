package jp.co.cos_mos.mdm.v1.dao.mapper;

import java.util.List;

import jp.co.cos_mos.mdm.v1.dao.entity.Code;
import jp.co.cos_mos.mdm.v1.dao.entity.CodeCriteria;

public interface CodeMapper {

	public Code select(Long id);

	@Deprecated
	public Code selectActiveCode(String code);
	public Code selectActiveCode(CodeCriteria criteria);

	public List<Code> search(CodeCriteria criteria);

	public int insert(Code code);
	public int update(Code code);
	public int delete(Code code);

	
}
