	//接收父页面传值
	function transferCityVal(citiesVal){
		$("#cities").val(citiesVal);
		var cities=$("#cities").val();
		var cityArr=new Array();
		cityArr=cities.split(',');
		for(var i=0;i<cityArr.length;i++){
			$('[name=city]').each(function(){
				if($(this).val()==cityArr[i]){
					 $(this).attr('checked', true);
				}
			});
		}
	}


    function checkEvent(name,allCheckId){
	  if($("#"+allCheckId).prop('checked')){
	    checkAll(name);
	  }else
	    checkAllNo(name);  
	}
	//全选
	function checkAll(name){
	  var names=$("[name="+name+"]");
	  var i=1;
	  if(names){
		  names.each(function(){
			  //全选只能选择前50个
			  if(i<=50){
				  if($(this).is(':visible')){
					  $(this).prop('checked', true);
				  }
				  i++;
			  }
			  $(this).attr("disabled", "disabled");
		  });
	  }
	  
	}
	//全不选
	function checkAllNo(name){
		  var names=$("[name="+name+"]");
		  if(names){
			  names.each(function(){
				  if($(this).is(':visible')){
					  $(this).prop('checked', false);
					  $(this).prop('disabled', false);
				  }
			  });
		  }
		  
	}

	function cityCancel(){
		$("#bannerCityAdd").modal('hide');
	}
	
	//查询城市
	//查询市场
	function chooseCity(){
		var selectCity=$("#selectCity").val().trim();
		if(selectCity!=null&&selectCity!=""){
			$('[name=labelCity]').each(function(){
				if($(this).text().indexOf(selectCity)>-1){
					//等于就显示
					$(this).parent().parent().show();
					$(this).parent().parent().attr("disabled", "");
				}else{
					//不等于不显示
					$(this).parent().parent().hide();
					$(this).parent().parent().attr("disabled", "disabled");
					//同时去除全选
					$('#cityCkall').attr('checked',false);
					$(this).siblings().attr('checked',false);
				}
			});
		}else{
			//输入为空，则显示全部
			$('[name=labelCity]').each(function(){
					//等于就显示
					$(this).parent().parent().show();
					$(this).parent().parent().attr("disabled", "");
			});
		}
		$("#cityAndmarket").show();
	}
	
	//保存
	function bannerCityAdd(){
	    var store="";
	    var storeName="";
	    var found=false;
	    	$('[name=city]').each(function(){
	    		if($(this).prop('checked'))
	    			{
	    			if(found){
	    				store+=",";
	    				storeName+=",";
	    			}
	  	          	found=true;
	  	          	store=store+$(this).val();
	  	          	storeName=storeName+$(this).attr('cityName');
	    			}
	    	});
    	$("#city").val(store);
    	$("#labelCity").html(storeName);
    	$("#bannerCityAdd").modal('hide');
	}
	
	function getcityName()
	{
		return storeName;
	}
	
	function checkMax(obj)
	{
		var i =0;
		$('[name=city]').each(function(){
    		if($(this).prop('checked'))
    			{
	    			i++;
    			}
    	});
		if(i>=50){
			alert('最多选择50个城市');
			$(obj).prop("checked",false);
		}
	}
	
	$(function(){
		var cityCode = $("#city").val();
		$("input:checkbox[name='city']").each(function(){
			if(cityCode.indexOf($(this).attr("value"))>-1){
				$(this).prop("checked",true);
			}
    	});
	});