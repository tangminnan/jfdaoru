package com.jifenchaxundaoru.information.service.impl;

import com.jifenchaxundaoru.common.config.BootdoConfig;
import com.jifenchaxundaoru.common.utils.R;
import com.jifenchaxundaoru.system.config.ExcelUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.jifenchaxundaoru.information.dao.IntegralListDao;
import com.jifenchaxundaoru.information.domain.IntegralListDO;
import com.jifenchaxundaoru.information.service.IntegralListService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


@Service
public class IntegralListServiceImpl implements IntegralListService {
	@Autowired
	private IntegralListDao integralListDao;
	@Autowired
	private BootdoConfig bootdoConfig;
	
	@Override
	public IntegralListDO get(Integer id){
		return integralListDao.get(id);
	}
	
	@Override
	public List<IntegralListDO> list(Map<String, Object> map){
		return integralListDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return integralListDao.count(map);
	}
	
	@Override
	public int save(IntegralListDO integralList){
		return integralListDao.save(integralList);
	}
	
	@Override
	public int update(IntegralListDO integralList){
		return integralListDao.update(integralList);
	}
	
	@Override
	public int remove(Integer id){
		return integralListDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return integralListDao.batchRemove(ids);
	}

	//@Override
	@ResponseBody
	@Transactional(propagation= Propagation.REQUIRED)
	public R importMember(MultipartFile file) {
		System.out.println("==============file================"+file);
		int num = 0;
		InputStream in=null;
		Workbook book=null;
		List<Integer> list = new ArrayList<>();
		try {
			if(file != null){
				in = file.getInputStream();
				book = ExcelUtils.getBook(in);
				Sheet sheet = book.getSheetAt(0);
				Row row=null;
				for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
					try {
						row = sheet.getRow(rowNum);
						
						//String ideentityType = ExcelUtils.getCellFormatValue(row.getCell((short)0));//序号
						String identityCard = ExcelUtils.getCellFormatValue(row.getCell((short)1));	//身份证号
						String uname = ExcelUtils.getCellFormatValue(row.getCell((short)2));	// 姓名
						String train_name = ExcelUtils.getCellFormatValue(row.getCell((short)3));			//培训名称
						String course_name =ExcelUtils.getCellFormatValue(row.getCell((short)4));	//课程名称
						String course_type = ExcelUtils.getCellFormatValue(row.getCell((short)5));		//课程类型
						String learn_state = ExcelUtils.getCellFormatValue(row.getCell((short)6));		//学习状态
						String course_result = ExcelUtils.getCellFormatValue(row.getCell((short)7));	//课程成绩
						String start_learn_time = ExcelUtils.getCellFormatValue(row.getCell((short)8));		//开始学习时间
						String end_learn_time = ExcelUtils.getCellFormatValue(row.getCell((short)9));		//最后学习时间
						String win_integral = ExcelUtils.getCellFormatValue(row.getCell((short)10));   //获得积分
						
						IntegralListDO integralList = new IntegralListDO();
						integralList.setUname(uname);
						integralList.setTrainName(train_name);
						integralList.setCourseName(course_name);
						integralList.setCourseType(course_type);
						integralList.setLearnState(learn_state);
						integralList.setCourseResult(course_result);
						integralList.setWinIntegral(win_integral);
						integralList.setStatus(1);
						
						if(start_learn_time != null && start_learn_time != ""){
							Calendar c = new GregorianCalendar(1900,0,-1);
							Date d = c.getTime();
							Date _d = DateUtils.addDays(d, Integer.parseInt(start_learn_time.substring(0,start_learn_time.indexOf("."))));
							integralList.setStartLearnTime(_d);
						}
						else
							integralList.setStartLearnTime(new SimpleDateFormat("yyyy-MM-dd").parse("1990-12-24"));

						if(end_learn_time != null && end_learn_time != ""){
							Calendar c = new GregorianCalendar(1900,0,-1);
							Date d = c.getTime();
							Date _d = DateUtils.addDays(d, Integer.parseInt(end_learn_time.substring(0,start_learn_time.indexOf("."))));
							integralList.setEndLearnTime(_d);
						}
						else
							integralList.setEndLearnTime(new SimpleDateFormat("yyyy-MM-dd").parse("1990-12-24"));


						if(identityCard != null && identityCard != ""){
							integralList.setIdentityCard(identityCard);

						}else{
							list.add(rowNum);
							continue;
						}
						integralListDao.save(integralList);
						num++;

					} catch (Exception e) {
						System.out.println("导入失败======第"+(rowNum)+"条==================");
						e.printStackTrace();
						return R.error("导入失败！第"+(rowNum)+"行");
					}

				}
				if(list.size()>0){
					return R.ok("上传成功,共增加["+num+"]条,第"+list+"行导入用户失败，原因：身份证号可能为空");
				}else{
					return R.ok("上传成功,共增加["+num+"]条");
				}
			}else{
				return R.error("请选择导入的文件!");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try {
				if(book!=null)
					book.close();
				if(in!=null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return R.error();
	}

	@Override
	public List<IntegralListDO> getIdentityCardByIntegral(String identityCard) {
		return integralListDao.getIdentityCardByIntegral(identityCard);
	}

	

}
