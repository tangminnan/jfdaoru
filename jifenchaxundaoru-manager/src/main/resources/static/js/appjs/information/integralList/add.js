$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});
function save() {
	$.ajax({
		cache : true,
		type : "POST",
		url : "/information/integralList/save",
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("操作成功");
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);

			} else {
				parent.layer.alert(data.msg)
			}

		}
	});

}
function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
            identityCard : {
				required : true
			},
            uname : {
                required : true
            },
            trainName : {
                required : true
            },
            courseName : {
                required : true
            },
            courseType : {
                required : true
            },
            learnState : {
                required : true
            },
            courseResult : {
                required : true
            },
            startLearnTime : {
                required : true
            },
            endLearnTime : {
                required : true
            },
            winIntegral : {
                required : true
            },
		},
		messages : {
            identityCard : {
				required : icon + "请输入身份证号"
			},
            uname : {
                required : icon + "请输入姓名"
            },
            trainName : {
                required : icon + "请输入培训名称"
            },
            courseName : {
                required : icon + "请输入课程名称"
            },
            courseType : {
                required : icon + "请输入课程类型"
            },
            learnState : {
                required : icon + "请输入学习状态"
            },
            courseResult : {
                required : icon + "请输入课程成绩"
            },
            startLearnTime : {
                required : icon + "请选择开始学习时间"
            },
            endLearnTime : {
                required : icon + "请选择最后学习时间"
            },
            winIntegral : {
                required : icon + "请输入获得积分"
            },
		}
	})
}