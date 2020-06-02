<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD//XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <title>积分查询</title>
    <link rel="stylesheet" type="text/css" href="/css/css.css" />
    
    <script type="text/javascript" src="/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="/js/plugins/layer/laydate/laydate.js"></script>
    <script type="text/javascript">

        $(function(){
            selectMenu(0);
            selectMenu(1);
            function selectMenu(index){
                $(".select-menu-input").eq(index).val($(".select-this").eq(index).html());//在输入框中自动填充第一个选项的值
                $(".select-menu-div").eq(index).on("click",function(e){
                    e.stopPropagation();
                    if($(".select-menu-ul").eq(index).css("display")==="block"){
                        $(".select-menu-ul").eq(index).hide();
                        $(".select-menu-ul").eq(index).animate({marginTop:"0px",opacity:"0"},"fast");
                    }else{
                        $(".select-menu-ul").eq(index).show();
                        $(".select-menu-ul").eq(index).animate({marginTop:"0",opacity:"1"},"fast");
                    }
                    for(var i=0;i<$(".select-menu-ul").length;i++){
                        if(i!==index && $(".select-menu-ul").eq(i).css("display")==="block"){
                            $(".select-menu-ul").eq(i).hide();
                            $(".select-menu-ul").eq(i).animate({marginTop:"0px",opacity:"0"},"fast");
                        }
                    }

                });
                $(".select-menu-ul").eq(index).on("click","li",function(){//给下拉选项绑定点击事件
                    $(".select-menu-input").eq(index).val($(this).html());//把被点击的选项的值填入输入框中
                    $(".select-menu-div").eq(index).click();
                    $(this).siblings(".select-this").removeClass("select-this");
                    $(this).addClass("select-this");
                    var thval = $(this).attr("value");
                    if(thval == "startLearnTime"){
                    	$("input[id='xm']").val("");
	            		laydate.render({
	            	        elem: '#xm',
	            	        type: 'date',
	            	    });
            		}else{
            			$("#churu").empty();
                        $("#churu").append('<input class="xm" id="xm" type="text" placeholder="请输入"/>');
            		} 
                });
                $("body").on("click",function(event){
                    event.stopPropagation();
                    if($(".select-menu-ul").eq(index).css("display")==="block"){
                        console.log(1);
                        $(".select-menu-ul").eq(index).hide();
                        $(".select-menu-ul").eq(index).animate({marginTop:"0px",opacity:"0"},"fast");

                    }

                });

            }
        })

        function searchquery(){
        	var seaval = $(".select-menu-ul .select-this").attr("value");
        	var xm = $("input[id='xm']").val();
        	if(xm.length<=0 || xm == ""){
        		console.info("kong");
        	}else{
        		$.ajax({
        			cache:true,
        			type:"get",
        			url:"/integral/getSecIntegral",
        			data:{key:seaval,val:encodeURI(xm)},
        			success:function(data){
        				$("#xiang").empty();
        				var html = "";
	    					html += ' <tr class="Num1" id="liebiao">'
	    					html += ' <!-- <td width="90">序号</td> -->'
	    					html += ' <td width="160">身份证</td>'
	    					html += ' <td width="100">姓名</td>'
	    					html += '  <td>培训名称</td>'
	    					html += '  <td>课程名称</td>'
	    					html += '  <td>课程类型</td>'
	    					html += ' <td>学习状态</td>'
	    					html += '  <td>课程成绩</td>'
	    					html += '  <td>开始学习时间</td>'
	    					html += '  <td>最后学习时间</td>'
	    					html += '  <td>获得积分</td>'
	    					html += '   <td>总积分</td>'
	    					html += ' </tr>'
        				if(data.length>0){
        					for(var i =0;i<data.length;i++){
        						html += '<tr>'
        						/* html += '  <td></td>' */
        						html += ' <td>'+data[i].identityCard+'</td>'
        						html += '<td>'+data[i].uname+'</td>'
        						html += ' <td>'+data[i].trainName+'</td>'
        						html += ' <td>'+data[i].courseName+'</td>'
        						html += ' <td>'+data[i].courseType+'</td>'
        						html += '  <td>'+data[i].learnState+'</td>'
        						html += ' <td>'+data[i].courseResult+'</td>'
        						html += ' <td>'+data[i].startLearnTime+'</td>'
        						html += ' <td>'+data[i].endLearnTime+'</td>'
        						html += ' <td>'+data[i].winIntegral+'</td>'
        						html += '  <td>'+data[i].totalIntegral+'</td>'
        						html += ' </tr>'
            				}
        					$("#xiang").html(html);
        				}else{
        					$("#xiang").empty();
        					$("#xiang").html(html);
        					alert("暂无数据！！！");
        				}
        				
        			}
        		})
        	}
        	
        }
        

    </script>

</head>
<body>
<%
   
%>

<img class="topimg" src="/images/topBg.png" alt=""/>
<div class="topnav">
    <ul class="main">
        <li class="on">积分查询</li>
        <li>积分商城</li>
        <a href="http://39.107.78.243:8079/"><li>后台管理</li></a>
    </ul>
</div>
<div class="main shaixuan">
    <div class="select-menu">
        <div class="select-menu-div">
            <input id="No1" readonly class="select-menu-input"/>
        </div>
        <ul class="select-menu-ul">
            <li value="uname" class="select-this">姓名</li>
            <li value="courseName" >课程名称</li>
            <li value="startLearnTime">参加时间</li>
        </ul>
    </div>
    <div id="churu">
    	<input class="xm" id="xm" type="text" placeholder="请输入"/>
    </div>
    
    <input class="btn" type="button" onclick="searchquery();" value="查询"/>
</div>
<div style="clear:both;margin-bottom:50px;"></div>
<div class="main">
    <div class="tit">查询结果</div>
    <table  border="1" cellspacing="0" cellpadding="0">
        <tbody id="xiang">
        <tr class="Num1" id="liebiao">
            <!-- <td width="90">序号</td> -->
            <td width="160">身份证</td>
            <td width="100">姓名</td>
            <td>培训名称</td>
            <td>课程名称</td>
            <td>课程类型</td>
            <td>学习状态</td>
            <td>课程成绩</td>
            <td>开始学习时间</td>
            <td>最后学习时间</td>
            <td>获得积分</td>
            <td>总积分</td>
        </tr>
       
 
        </tbody>
    </table>
</div>


</body>
</html>