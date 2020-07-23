 $(function(){
	 register_validation($('#iformAdd'), $('#wordLinkAdd .modal-body'));
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

    			
    			$("#dayOfWeek option").each(function() {
    				$(this).attr("selected", "selected");
    			});
    			
    			$("#promoChannelIdAdd option").each(function() {
    				$(this).attr("selected", "selected");
    			});

    	});
	   var checkZipRight = false;
	  	  //提交表单
	  	  function save(){  
	  		  var versionId = $("#versionId").val();
	  		  var zipName = $("#upZipFile").val();
	  		  if(!checkZipRight && $('input:radio[name="relationType"]:checked').val() == 2 && (zipName!=null&& zipName!="")){
       				alert("上传的压缩文件必须为zip文件");
       		   }else if($("#validFromId").val()>$("#validToId").val()){
                	$("#validFromId").focus();
        			alert("有效期开始日期不能大于有效期结束日期!");
               }else if($("#startTimeId").val()>$("#endTimeId").val()){
                	$("#startTimeId").focus();
        			alert("有效开始时间不能大于有效结束时间!");
               }else{
	    			access=$('#iformAdd').validate().form();
	    			if(access==true){
	    				var dayOfWeekVal="";
	    				$("#dayOfWeek option").each(function() {
	    					if($(this).attr("selected")){
	    						dayOfWeekVal+=$(this).val();
	    						dayOfWeekVal+=",";
	    					}
	    				});
	    				var channelIdVal="";
	    				$("#promoChannelIdAdd option").each(function() {
	    					if($(this).attr("selected")){
	    						channelIdVal+=$(this).val();
	    						channelIdVal+=",";
	    					}
	    				});

	    				$.post("checkWordLinkWithHotLink.action", {
	    					 validFrom :$("#validFromId").val(),
	    					 validTo :$("#validToId").val(),
	    					 versionId :versionId,
	    					 dayOfWeek :dayOfWeekVal,
	    					 channelIdVal :channelIdVal 
	    					}, function(msg) {
	    						if (msg == "OK") {
	    							var validFrom=$("#validFromId").val();      
	    		    				var validFromDate= new Date(Date.parse(validFrom.replace(/-/g,"/")));
	    		    				var  validTo = $("#validToId").val();
	    		    				var validToDate= new Date(Date.parse(validTo.replace(/-/g,"/")));
	    		    				$("#validFromId").val(validFromDate);
	    		    				$("#validToId").val(validToDate);
	    		    				$("#versionId").val(versionId);
	    							$("#iformAdd").attr("action","linkSave.action?versionId="+versionId).submit();
	    						} else {
	    							alert("相同时间段内已存在热链!");
	    							return;
	    						}
	    					}, 'json');
	    			}
	    		}   	
	    	}
	  	  var zipName = $("#upZipFile").val();
	  	  //zip文件的校验
	  	function checkZip(){
	  		var type = $('input:radio[name="relationType"]:checked').val();
	  		var zipName = $("#upZipFile").val();
	  		var length = zipName.length;
	  		if(zipName != null && zipName != "" && type == 2 ){
	  			if(zipName.charAt(length-3)=="z" && zipName.charAt(length-2)=="i" && zipName.charAt(length-1)== "p"){	    				
	  				checkZipRight = true;			
	  			}
	  			    else{
	  			    	alert("上传文件必须为zip文件");
	  			    	checkZipRight = false;
	  			    }
	  			}
	  		}

	//类别与路径的切换
	function selectType() {
		var type = $('input:radio[name="relationType"]:checked').val();
		if (type == 1) {
			$("#linkedclassidtr").show();
			$("#linkedurltr").hide();
			$("#linkedurltrZip").hide();
		} else if (type == 2) {
			$("#linkedclassidtr").hide();
			$("#linkedurltr").show();
			$("#linkedurltrZip").show();
		}
	}
	
	function cleanOption(flag) {
		if (flag == 1) {
			$("#dayOfWeek option:selected").prop("selected", false);
		} else if (flag == 2) {
			$("#promoChannelIdAdd option:selected").prop("selected", false);
		}
	}
