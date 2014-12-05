package jp.co.cos_mos.mdm.v1.dao.mapper;

import java.util.List;

import jp.co.cos_mos.mdm.v1.dao.entity.Owner;

public interface OwnerMapper {

	public Owner select(Long id);
	public int insert(Owner owner);
	public int update(Owner owner);
	public int delete(Owner owner);
	public List<Owner> selectAll();
	public int inactive(Owner owner);
	
}
