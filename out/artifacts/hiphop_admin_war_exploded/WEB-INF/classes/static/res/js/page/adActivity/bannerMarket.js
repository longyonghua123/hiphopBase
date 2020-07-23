	//接收父页面传值
	function transferMarketsVal(marketsVal){
		$("#markets").val(marketsVal);
		var markets=$("#markets").val();
		var marketArr=new Array();
		marketArr=markets.split(',');
		for(var i=0;i<marketArr.length;i++){
			$('[name=market]').each(function(){
				if($(this).val()==marketArr[i]){
					 $(this).attr('checked', true);
				}
			});
		}
	}

  function checkEvent(name,allCheckId){
	  var allCk=document.getElementById(allCheckId);
	  if(allCk.checked==true)
	    checkAll(name);
	  else
	    checkAllNo(name);  
	}
	//全选
	function checkAll(name){
	  var names=$("[name="+name+"]");
	  if(names){
		  names.each(function(){
			  if($(this).is(':visible')){
				  $(this).prop('checked', true);
//				  $(this).attr('disabled', 'disabled');
			  }
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
//					  $(this).attr('disabled', false);
				  }
			  });
		  }
		  
	}

	function cancel(){
		$("#bannerMarketAdd").modal('hide');
	}
	
	//查询市场
	function chooseMarket(){
		var selectMarket=$("#selectMarket").val().trim();
		if(selectMarket!=null&&selectMarket!=""){
			$('[name=labelMarket]').each(function(){
				if($(this).text().indexOf(selectMarket)>=0){
					//等于就显示
					$(this).parent().parent().show();
					$(this).parent().parent().attr("disabled", "");
				}else{
					//不等于不显示
					$(this).parent().parent().hide();
					$(this).parent().parent().attr("disabled", "disabled");
					//同时去除全选
					$('#ckall').prop('checked',false);
					$(this).siblings().prop('checked',false);
				}
			});
		}
		$("#cityAndmarket").show();
	}
	
	//保存
	function bannerMarketAdd(){
		var marketName="";
	    var store="";
	    var found=false;
	    	$('[name=market]').each(function(){
	    		if($(this).prop('checked'))
	    			{
	    			if(found){
	    				store+=",";
	    				marketName+=",";
	    			}
	  	          	found=true;
	  	          	store=store+$(this).val();
	  	          	marketName=marketName+$(this).attr('marketName');
	    			}
	    	});
	    	$("#market").val(store);
	    	$("#labelMarket").html(marketName);
	    	$("#bannerMarketAdd").modal('hide');
	}
	
	function getMarketName()
	{
		return marketName;
	}
	
	$(function(){
		var marketCode = $("#market").val();
		$("input:checkbox[name='market']").each(function(){
			if(marketCode.indexOf($(this).attr("value"))>-1){
				$(this).prop("checked",true);
			}
    	});
	});