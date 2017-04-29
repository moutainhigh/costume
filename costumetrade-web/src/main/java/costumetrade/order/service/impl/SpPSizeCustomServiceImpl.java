package costumetrade.order.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import costumetrade.order.domain.SpPSizeCustom;
import costumetrade.order.mapper.SpPSizeCustomMapper;
import costumetrade.order.service.SpPSizeCustomService;

@Transactional
@Service
public class SpPSizeCustomServiceImpl implements SpPSizeCustomService{
	@Autowired
	private SpPSizeCustomMapper spPSizeCustomMapper;
	
	public List<SpPSizeCustom> getSpPSizeCustoms(int cropId) {

		return spPSizeCustomMapper.getSpPSizeCustoms(cropId);
	}
	public int saveSpPSizeCustom(SpPSizeCustom spPSizeCustom) {
		int save = 0;
		//查询对应ID的是否存在，存在的话进行update 不存在save
		if(spPSizeCustom.getId() != null){
			SpPSizeCustom getSize = spPSizeCustomMapper.selectByPrimaryKey(spPSizeCustom.getId());
			if(getSize != null){
				save = spPSizeCustomMapper.updateByPrimaryKeySelective(spPSizeCustom);
			}else {
				save = spPSizeCustomMapper.insert(spPSizeCustom) ;
			}
		}else {
			save = spPSizeCustomMapper.insert(spPSizeCustom) ;
		}
		 
		
		return save;
		
		 
	}
	public int deleteSpPSizeCustom(int id) {
	
		return spPSizeCustomMapper.deleteByPrimaryKey(id);
	}
	
	

}