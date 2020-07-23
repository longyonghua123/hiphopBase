$(function(){
	register_validation($('#formAdd'), $('#cornMarkAdd .modal-body'));
			if('${cornerMarkType}' == '2'){
				 $("#trCn").hide();
				 $("#trEn").hide();
			}
		});
		function checkPicIsNotNull() {
			var cn = false;
			var en = false;
			if($("#cornerMarkType").val() == "1"){
				//中文图片校验
				if ($("#filepathCn").val() != null && $("#filepathCn").val() != "") {
					cn = true;
				} else {
					alert("中文图片不能为空");
					$('#nameCn').focus();
					return false;
				}
				//英文图片校验  
				if ($("#filepathEn").val() != null && $("#filepathEn").val() != "") {
					en = true;
				} else {
					alert("英文图片不能为空");
					$('#nameEn').focus();
					return false;
				}
				return cn && en;
				}
			return true;
		}
		function checkNameCn(id){
			if(id == 'nameCn'){
				$.ajax({
					type : "post",
					dataType : 'json',
					async : false,
					url : "cornerMarkcheckNameRepeat.action",
	                data:{
	                	nameCn:$('#nameCn').val(),
	                	logic:"ADD"
	   				  },
	                success: function(data, textStatus){
	                	if(data == "RECN"){
		                	alert("中文名重复！");
		                	$('#nameCn').focus();
	                	}
					}
				});
			}
		}
		
		 function save(){
			 var flag = $('#formAdd').validate().form();
			 if(flag){
				 if(checkPicIsNotNull()){
						$("#formAdd").submit();
					}
			 }
		}
		
		function checkNameEn(id){
			if(id == 'nameEn'){
				$.ajax({
					type : "post",
					dataType : 'json',
					async : false,
					url : "cornerMarkcheckNameRepeat.action",
	                data:{
	                	nameEn:$('#nameEn').val(),
	                	logic:"ADD"
	   				  },
	                success: function(data, textStatus){
	                	if(data == "REEN"){
	                		alert("英文名重复！");
		                	$('#nameEn').focus();
	                	}
	            	 }
				});
			}
		}
		function  typeChanged(){
			 if($("#cornerMarkType").val() == "2"){
				 $("#trCn").hide();
				 $("#trEn").hide();
				 var fileCn = $("#filepathCn"); 
				 fileCn.after(fileCn.clone().val("")); 
				 fileCn.remove(); 
				 var fileEn = $("#filepathEn"); 
				 fileEn.after(fileEn.clone().val("")); 
				 fileEn.remove(); 
			 }else if($("#cornerMarkType").val() == "1"){
				 $("#trCn").show();
				 $("#trEn").show();
			 }
		}