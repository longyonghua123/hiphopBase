//日期控件
	$(function() {
		register_validation($('#bannerModifyForm'), $('#bannerEdit .modal-body'));
		$('#startdatetimepickModify').datetimepicker({
			language : 'zh-CN', // 汉化
			pickDate: false,
			hourStep: 1,
	        minuteStep: 15,
	        secondStep: 30,
	        inputMask: false,
	        pickTime:true
		});
		$('#enddatetimepickModify').datetimepicker({
			language : 'zh-CN', // 汉化
			pickDate: false,
			hourStep: 1,
	        minuteStep: 15,
	        secondStep: 30,
	        inputMask: false,
	        pickTime:true
		});
	 $('#datetimepickerFromModify').datetimepicker({
			language : 'zh-CN', // 汉化
			pickTime: false,
			inputMask: false
		});
		$('#enddatetimepickToModify').datetimepicker({
			language : 'zh-CN', // 汉化
			pickTime: false,
			inputMask: false
		});
		$("#bannerAdd").on("click",function(){
			$("#bannerAdd").css("overflow-y","auto");
		})
		
		$("#linkedClassIdModify").hide();
		$("#relationUrlZipModify").hide();
		$("#smallPicturePopupModify").hide();
		$("#marketCityModify").hide();
		$("#positionIdModify").hide();
		
		var linkedClassidOption = $("#linkedClassidOption").val();
		$("#linkedClassid option").each(function(){
			if($(this).val()==linkedClassidOption){
				$(this).prop("selected",true);
			}
		})
		
		var bannerTypeValue = $("#bannerTypeOption").val();
		$("#bannerTypeModify option").each(function(){
			if($(this).val()==bannerTypeValue){
				$(this).prop("selected",true);
			}
		})
		//图片与下小图
		if (bannerTypeValue == 2) {
			$("#positionIdModify").show();
		} else {
			$("#positionIdModify").hide();
		}
		//增加弹出海报逻辑 廖昌彬 20130815 start
		if(bannerTypeValue==3||bannerTypeValue==4){
			$("#smallPicturePopupModify").show();
		}else{
			$("#smallPicturePopupModify").hide();
		}
		//只有局部海报才需要配置市场和城市
		if(bannerTypeValue==4){
			$("#marketCityModify").show();
		}else{
			$("#marketCityModify").hide();
		}
		
		$("#dayOfWeek option").each(function() {
			$(this).prop("selected", true);
		});
		$("#channelId option").each(function() {
			$(this).prop("selected", true);
		});
		
		var relationTypeOption = $("#relationTypeOption").val();
		if (relationTypeOption == 1) {
			$("#linkedClassIdModify").show();
			$("#relationUrlZipModify").hide();
		}else {
			$("#linkedClassIdModify").hide();
			$("#relationUrlZipModify").show();
		}

		//类别与URL的切换
		var type = $("#relationTypeOption").val();
		if (type == 1) {
			$("#linkedclassidIdModify").show();
			$("#relationUrlIdModify").hide();
			$("#upzipURLidModify").hide();
		} else {
			$("#relationUrlIdModify").show();
			$("#upzipURLidModify").show();
			$("#linkedclassidIdModify").hide();
		}
		
		//渠道初始化
			if($("#promoChannelIdModifyV").val() != '-1'){
				var channelCopy = $("#promoChannelIdModifyV").val().split(",");
	            $.each(channelCopy,function(i, n){			 
					$("#promoChannelIdModify").find("option[value='"+n+"']").prop("selected",true);
				});    
			} else{            	
	        	$("#promoChannelIdModify option").each(function(index, val){ 
	        		$(this).prop("selected",false);
	        	}); 
	        }
		//周几可售初始化
			var dayOfweekModify = $("#dayOfweekModifyV").val().split("");
			$("#dayOfWeekModify option").each(function(index,val){
				if(dayOfweekModify[index]=="1"){
					$(this).prop("selected",true);
				}
			});
			$("#labelMarket").html($("#marketString").val());
			$("#labelCity").html($("#cityString").val());

			//是否节假日初始化
			var holidayModify = $("#holidayModify").val();
			$("[name='holiday']").each(function(){
				if($(this).val() == holidayModify){
					$(this).prop("checked",true);
				}
			})
			
			var relationTypeModify = $("#relationTypeModify").val();
			$("#relationType").find("option[value='"+relationTypeModify+"']").prop("selected",true);
	});
	
	//图片与下小图的切换
	function changeType() {
		var type = $("#bannerTypeModify").val();
		if (type == 2) {
			$("#positionIdModify").show();
		} else {
			$("#positionIdModify").hide();
		}
		//增加弹出海报逻辑 廖昌彬 20130815 start
		if(type==3||type==4){
			$("#smallPicturePopupModify").show();
		}else{
			$("#smallPicturePopupModify").hide();
		}
		//只有局部海报才需要配置市场和城市
		if(type==4){
			$("#marketCityModify").show();
		}else{
			$("#marketCityModify").hide();
		}
		//增加弹出海报逻辑 廖昌彬 20130815 end
	}
	
	//类别与路径的切换
	function changeReModify(){
		var relationTypeOption = $("#relationType").val();
		if (relationTypeOption == 1) {
			$("#linkedClassIdModify").show();
			$("#relationUrlZipModify").hide();
		}else {
			$("#linkedClassIdModify").hide();
			$("#relationUrlZipModify").show();
		}
	}
	
	//选择城市
	function selectCity()
	{
		$("#bannerCityLabel").text("城市配置");
    	$.ajax({
    		type: "post",
    		url: "bannerCity.action",
    		dataType: "html",
    		success: function(data){
    				$("#bannerCityAdd").html(data);
    				$("#bannerCityAdd").modal();
    		},
    		error: function(){}
    	});
	}
	
	//选择市场
	function selectMarket(){
		$("#bannerMarkLabel").text("市场配置");
    	$.ajax({
    		type: "post",
    		url: "bannerMarket.action",
    		dataType: "html",
    		success: function(data){
    				$("#bannerMarketAdd").html(data);
    				$("#bannerMarketAdd").modal();
    		},
    		error: function(){}
    	});
	}
	
	function cleanOption(flag) {
		if (flag == 1) {
			$("#dayOfWeek option:selected").prop("selected", false);
		} else if (flag == 2) {
			$("#promoChannelIdAdd option:selected").prop("selected", false);
		}
	}
	
	var checkZipRightModify = true;
	//zip文件校验
	function checkZipModify(){
		var type = $("#relationTypeOption").val();
		var zipName = $("#upzipURL").val();
		var length = zipName.length;
		if(zipName != null && zipName != "" && type == 2 ){
  			if(zipName.charAt(length-3)=="z" && zipName.charAt(length-2)=="i" && zipName.charAt(length-1)== "p"){	    				
  				checkZipRightModify = true;			
  			}
  			    else{
  			    	alert("上传文件必须为zip文件");
  			    	checkZipRightModify = false;
  			    }
  			}
  		}

// 类别与URL的切换
	function changeRe() {
		var type = $("#relationTypeOption").val();
		if (type == 1) {
			$("#linkedclassidId").show();
			$("#relationUrlId").hide();
			$("#upzipURLid").hide();
		} else {
			$("#relationUrlId").show();
			$("#linkedclassidId").hide();
			$("#upzipURLid").show();
		}
	}

	//提交验证
	function bannerModify() {
	 	$("div.alert.alert-danger").each(function(){
	 		if($(this).css("display")=='none'){
	 			$(this).remove();
	 		}
	 	})
	 	var divFlag = $("div.alert.alert-danger").css("display");
	 	if(divFlag!=null || divFlag=='block'){
	 		$("div.alert.alert-danger").css("display","none")
	 		register_validation($('#bannerModifyForm'), $('#bannerEdit .modal-body'));
	 	}else{
	 		register_validation($('#bannerModifyForm'), $('#bannerEdit .modal-body'));
	 	}
	 	
		var access=$('#bannerModifyForm').validate().form();
		if(access){
			if(!checkZipRightModify){
		 		alert("上传文件必须为zip文件");
		 		$("#upzipURL").focus();
		 		return;
		 	}else if($("#validFromId").val()>$("#validToId").val()){
	        	$("#validFromId").focus();
				alert("有效期开始日期不能大于有效期结束日期!");
				return;
	       }else if($("#startTimeId").val()>$("#endTimeId").val()){
	        	$("#startTimeId").focus();
				alert("有效开始时间不能大于有效结束时间!");
				return;
	       }
		}
		//售卖时间
			if (access) {
				var validFrom=$("#validFromId").val();      
				var validFromDate= new Date(Date.parse(validFrom.replace(/-/g,"/")));
				var  validTo = $("#validToId").val();
				var validToDate= new Date(Date.parse(validTo.replace(/-/g,"/")));
				$("#validFromId").val(validFromDate);
				$("#validToId").val(validToDate);
				var city = $("#city").val();
				$("#bannerModifyForm").attr("action", "bannerSave.action?city="+city).attr("method",
						"post").attr("enctype", "multipart/form-data").attr(
						"commandName", "nBanner").attr("onSubmit", "").submit();
			}
	}
