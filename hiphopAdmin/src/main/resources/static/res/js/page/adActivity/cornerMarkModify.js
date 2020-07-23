$(function(){
	register_validation($('#formModify'), $('#cornMarkModify .modal-body'));
			// 文字角标时隐藏中英文图片
			 if($("#cornerMarkType").val() == "2"){
				 $("#trCn").hide();
				 $("#trEn").hide();
			 }
			$(".selBtn").attr("disabled","disabled").addClass("selBtn_disabled");
			$(".selectbox").attr("disabled","disabled").addClass("inputDisabled");
		});

		function save(){
			 var flag = $('#formModify').validate().form();
			 if(flag){
				 if(checkNameCn() && checkNameEn()){
						$("#formModify").submit();
					}
			 }
		}

		function checkNameCn(){
			var flag = true;
			$.ajax({
				type : "post",
				dataType : 'json',
				async : false,
				url : "cornerMarkcheckNameRepeat.action",
                data:{
                	nameCn:$('#nameCn').val(),
                	cornerMarkId:$('#cornerMarkId').val(),
                	logic:"UPDATE"
   				  },
                success: function(data, textStatus){
                	if(data == "RECN"){
	                	alert("中文名重复！");
	                	$('#nameCn').focus();
	                	flag = false;
                	}
				}
			});
			return flag;
		}
		function checkNameEn(){
			var flag = true;
			$.ajax({
				type : "post",
				dataType : 'json',
				async : false,
				url : "cornerMarkcheckNameRepeat.action",
                data:{
                	nameEn:$('#nameEn').val(),
                	cornerMarkId:$('#cornerMarkId').val(),
                	logic:"UPDATE"
   				  },
                success: function(data, textStatus){
                	if(data == "REEN"){
	                	alert("英文名重复！");
	                	$('#nameEn').focus();
	                	flag = false;
                	}
            	 }
			});
			return flag;
		}
		function  typeChanged(){
			 if($("#cornerMarkType").val() == "2"){
				 $("#trCn").hide();
				 $("#trEn").hide();
			 }else if($("#cornerMarkType").val() == "1"){
				 $("#trCn").show();
				 $("#trEn").show();
			 }
		}