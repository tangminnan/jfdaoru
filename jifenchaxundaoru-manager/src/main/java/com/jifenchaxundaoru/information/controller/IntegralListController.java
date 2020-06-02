package com.jifenchaxundaoru.information.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jifenchaxundaoru.information.domain.IntegralListDO;
import com.jifenchaxundaoru.information.service.IntegralListService;
import com.alibaba.fastjson.JSONObject;
import com.jifenchaxundaoru.common.config.BootdoConfig;

import freemarker.template.Configuration;
import freemarker.template.Template;

import com.jifenchaxundaoru.common.utils.PageUtils;
import com.jifenchaxundaoru.common.utils.Query;
import com.jifenchaxundaoru.common.utils.R;
import org.springframework.web.multipart.MultipartFile;

/**
 * 积分列表
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-05-26 18:42:54
 */
 
@Controller
@RequestMapping("/information/integralList")
public class IntegralListController {
	@Autowired
	private IntegralListService integralListService;
	@Autowired
	private BootdoConfig bootdoConfig;
	
	@GetMapping()
	@RequiresPermissions("information:integralList:integralList")
	String IntegralList(){
	    return "information/integralList/integralList";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("information:integralList:integralList")
	public PageUtils list(@RequestParam Map<String, Object> params) throws UnsupportedEncodingException{
		//查询列表数据
		Object object = params.get("uname")==""?"":params.get("uname");
		object = ((String) object).replaceAll("%(?![0-9a-fA-F]{2})", "%25");
		String uname = URLDecoder.decode((String) object, "UTF-8");
		params.put("uname", uname);
		Object object1 = params.get("courseName")==""?"":params.get("courseName");
		object1 = ((String) object1).replaceAll("%(?![0-9a-fA-F]{2})", "%25");
		String courseName = URLDecoder.decode((String) object1, "UTF-8");
		params.put("courseName", courseName);
        Query query = new Query(params);
		List<IntegralListDO> integralListList = integralListService.list(query);
		for (IntegralListDO integralListDO : integralListList) {
	    	List<Double> ite = new ArrayList<Double>();
	    	List<IntegralListDO> integral = integralListService.getIdentityCardByIntegral(integralListDO.getIdentityCard());
	    	for (IntegralListDO integralListDO2 : integral) {
	    		ite.add(Double.valueOf(integralListDO2.getWinIntegral()));
			}
	    	integralListDO.setTotalIntegral(String.valueOf(ite.stream().reduce(Double::sum).orElse((double) 0)));
		}
		int total = integralListService.count(query);
		PageUtils pageUtils = new PageUtils(integralListList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("information:integralList:add")
	String add(){
	    return "information/integralList/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("information:integralList:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		IntegralListDO integralList = integralListService.get(id);
		String identityCard = integralList.getIdentityCard();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("identityCard", identityCard);
		List<IntegralListDO> list = integralListService.list(map);
		double tot = 0d;
		for (IntegralListDO integralListDO : list) {
			tot += Double.valueOf(integralListDO.getWinIntegral());
		}
		integralList.setTotalIntegral(String.valueOf(tot));
		model.addAttribute("integralList", integralList);
	    return "information/integralList/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("information:integralList:add")
	public R save( IntegralListDO integralList){
		integralList.setStatus(1);
		if(integralListService.save(integralList)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("information:integralList:edit")
	public R update( IntegralListDO integralList){
		integralListService.update(integralList);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("information:integralList:remove")
	public R remove( Integer id){
		if(integralListService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("information:integralList:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		integralListService.batchRemove(ids);
		return R.ok();
	}


	@GetMapping("/importtemplate")
	String importtemplate(){
		return "information/integralList/importtemplate";
	}


	@PostMapping("/importMember")
	@ResponseBody
	public R importMember(MultipartFile file){
		return integralListService.importMember(file);
	}
	
	@GetMapping("/templatedaochu")
	void templatedaochu(HttpServletRequest request, HttpServletResponse response){
		try {
			Map<String, Object> shuju = shuju( request,  response);
			createDoc(response,shuju, new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), "daochumoban.ftl");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			File file=new File(bootdoConfig.getPoiword());
	        if(file.exists()) {
	           File[] files = file.listFiles();
	           for(File f :files)
	              f.delete();
	        }
	        
	     } 
	}
	
	
	Map<String, Object> shuju(HttpServletRequest request, HttpServletResponse response){
		String courseName = request.getParameter("courseName");
		String startLearnTime = request.getParameter("startLearnTime");
		String uname = request.getParameter("uname");
		String cn = null;
		String un = null;
		String object1 = courseName.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
		String object3 = uname.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
		try {
			cn = URLDecoder.decode(object1, "UTF-8");
			un = URLDecoder.decode(object3, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Map<String, Object> mapP = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		List<Map<String, Object>> list = new ArrayList<>();
		int i = 1;
		Map<String, Object> params = new HashMap<>();
		params.put("status", "1");
		params.put("courseName", cn);
		params.put("startLearnTime", startLearnTime);
		params.put("uname", un);
		List<IntegralListDO> list2 = integralListService.list(params);
		for (IntegralListDO integralListDO : list2) {
			Map<String, Object> map = new HashMap<>();
			List<Double> ite = new ArrayList<Double>();
	    	List<IntegralListDO> integral = integralListService.getIdentityCardByIntegral(integralListDO.getIdentityCard());
	    	for (IntegralListDO integralListDO2 : integral) {
	    		ite.add(Double.valueOf(integralListDO2.getWinIntegral()));
			}
	    	integralListDO.setTotalIntegral(String.valueOf(ite.stream().reduce(Double::sum).orElse((double) 0)));
	    	
	    	map.put("sort", i++);
	    	map.put("identityCard", integralListDO.getIdentityCard());
	    	map.put("uname", integralListDO.getUname());
	    	map.put("trainName", integralListDO.getTrainName());
	    	map.put("courseName", integralListDO.getCourseName());
	    	map.put("courseType", integralListDO.getCourseType());
	    	map.put("learnState", integralListDO.getLearnState());
	    	map.put("courseResult", integralListDO.getCourseResult());
	    	map.put("startLearnTime", sdf.format(integralListDO.getStartLearnTime()));
	    	map.put("endLearnTime", sdf.format(integralListDO.getEndLearnTime()));
	    	map.put("winIntegral", integralListDO.getWinIntegral());
	    	map.put("totalIntegral", integralListDO.getTotalIntegral());
	    	list.add(map);
		}
		mapP.put("userlist", list);
		return mapP;
	}

	
	public void createDoc(HttpServletResponse response,Map<String, Object> dataMap, String fileName, String template) {
        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");                                       
        configuration.setClassForTemplateLoading(IntegralListController.class, "/");
        Template t = null;
		//File outFile = new File(realPath + fileName);
//		Writer out = null;
        try {
            //word.xml是要生成Word文件的模板文件
            t = configuration.getTemplate(template,"utf-8"); 
 //           out = new BufferedWriter(new OutputStreamWriter(
 //                   new FileOutputStream(bootdoConfig.getPoiword()+new File(new String(fileName.getBytes(),"utf-8")))));                 //还有这里要设置编码
   //         t.process(dataMap, out);
            response.setContentType("multipart/form-data");
			response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes(), "iso-8859-1")+".xlsx");
  		
			Writer out = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
            t.process(dataMap, out);
            out.flush();
            out.close();
           
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
	
	/**
	 * freemarker导出工具类
	 */
	
	public void download(HttpServletRequest request,HttpServletResponse response, String fileUrl, String fileName) {
		InputStream bis = null;
		OutputStream bos = null;
		try{
			fileUrl = fileUrl + fileName;
			response.setContentType("multipart/form-data");
			response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes(), "iso-8859-1")+".xlsx");
			bis = new BufferedInputStream(new FileInputStream((fileUrl)));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[1024];
			int bytesRead;
			int i = 0;
	
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
				i++;
			}
			bos.flush();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				bis = null;
			}
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				bos = null;
			}
		}

    }
}
