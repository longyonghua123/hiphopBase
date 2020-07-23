// 引入日期控件 
	$(function() {
		register_validation($('#bannerAddForm'), $('#bannerAdd .modal-body'));
		$('#startdatetimepickAdd').datetimepicker({
			language : 'zh-CN', // 汉化
			pickDate: false,
			hourStep: 1,
	        minuteStep: 15,
	        secondStep: 30,
	        inputMask: false,
	        pickTime:true
		});
		$('#enddatetimepickAdd').datetimepicker({
			language : 'zh-CN', // 汉化
			pickDate: false,
			hourStep: 1,
	        minuteStep: 15,
	        secondStep: 30,
	        inputMask: false,
	        pickTime:true
		});
	 $('#datetimepickerFromAdd').datetimepicker({
			language : 'zh-CN', // 汉化
			pickTime: false,
			inputMask: false
		});
		$('#enddatetimepickToAdd').datetimepicker({
			language : 'zh-CN', // 汉化
			pickTime: false,
			inputMask: false
		});
		$("#bannerAdd").on("click",function(){
			$("#bannerAdd").css("overflow-y","auto");
		})
		$("#linkedClassId").hide();
		$("#relationUrlZip").hide();
		$("#smallPicturePopup").hide();
		$("#marketCity").hide();
		$("#positionId").hide();
		$("#dayOfWeek option").each(function() {
			$(this).prop("selected", true);
		});
		$("#channelId option").each(function() {
			$(this).prop("selected", true);
		});
		
		var relationType = $("#relationType").val();
		if (relationType == 1) {
			$("#linkedClassId").show();
			$("#relationUrlZip").hide();
		}else {
			$("#linkedClassId").hide();
			$("#relationUrlZip").show();
		}
	});

	//图片与下小图的切换
	function changeType() {
		var type = $("#bannerTypeAdd").val();
		if (type == 2) {
			$("#positionId").show();
		} else {
			$("#positionId").hide();
		}
		//增加弹出海报逻辑 廖昌彬 20130815 start
		if(type==3||type==4){
			$("#smallPicturePopup").show();
		}else{
			$("#smallPicturePopup").hide();
		}
		//只有局部海报才需要配置市场和城市
		if(type==4){
			$("#marketCity").show();
		}else{
			$("#marketCity").hide();
		}
		//增加弹出海报逻辑 廖昌彬 20130815 end
	}

	//类别与路径的切换
	function changeRe() {
		var relationType = $("#relationType").val();
		if (relationType == 1) {
			$("#linkedClassId").show();
			$("#relationUrlZip").hide();
		}else {
			$("#linkedClassId").hide();
			$("#relationUrlZip").show();
		}
	}

	//提交验证
	function bannerSave() {
	 	$("div.alert.alert-danger").each(function(){
	 		if($(this).css("display")=='none'){
	 			$(this).remove();
	 		}
	 	})
	 	var divFlag = $("div.alert.alert-danger").css("display");
	 	if(divFlag!=null || divFlag=='block'){
	 		$("div.alert.alert-danger").css("display","none")
	 		register_validation($('#bannerAddForm'), $('#bannerAdd .modal-body'));
	 	}else{
	 		register_validation($('#bannerAddForm'), $('#bannerAdd .modal-body'));
	 	}
		var access=$('#bannerAddForm').validate().form();
		if(access){
			if(!checkZipRight){
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
				$("#bannerAddForm").attr("action", "bannerSave.action?city="+city).attr("method",
						"post").attr("enctype", "multipart/form-data").attr(
						"commandName", "nBanner").attr("onSubmit", "").submit();
			}
		}

	var checkZipRight = true;
	function checkZip() {
		var type = $("#relationType").val();
		var zipName = $("#upzipURL").val();
		var length = zipName.length;
		if (zipName != null && zipName != "" && type == 2) {
			if (zipName.charAt(length - 3) == "z"
					&& zipName.charAt(length - 2) == "i"
					&& zipName.charAt(length - 1) == "p") {
				checkZipRight = true;
			} else {
				alert("上传文件必须为zip文件");
				checkZipRight = false;
			}
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