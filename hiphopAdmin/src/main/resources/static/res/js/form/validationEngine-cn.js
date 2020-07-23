/*edited by fukai*/

(function($) {
	$.fn.validationEngineLanguage = function() {};
	$.validationEngineLanguage = {
		newLang: function() {
			$.validationEngineLanguage.allRules = 	{
					"required":{   
						"regex":"none",
						"alertText":"* 非空选项.",
						"alertTextCheckboxMultiple":"* 请选择一个单选框.",
						"alertTextCheckboxe":"* 请选择一个复选框."},
					"length":{
						"regex":"none",
						"alertText":"* 长度必须在",
						"alertText2":" 至 ",
						"alertText3": "之间."},
					"maxCheckbox":{
						"regex":"none",
						"alertText":"* 最多选择 ",
						"alertText2":" 项."},	
					"minCheckbox":{
						"regex":"none",
						"alertText":"* 至少选择 ",
						"alertText2":" 项."},	
					"confirm":{
						"regex":"none",
						"alertText":"* 两次输入不一致,请重新输入."},		
					"telephone":{
						"regex":"/^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/",
						"alertText":"* 请输入有效的电话号码,如:010-29292929."},
					"mobilephone":{
						"regex":"/(^0?[1][358][0-9]{9}$)/",
						"alertText":"* 请输入有效的手机号码."},	
					"email":{
						"regex":"/^[a-zA-Z0-9_\.\-]+\@([a-zA-Z0-9\-]+\.)+[a-zA-Z0-9]{2,4}$/",
						"alertText":"* 请输入有效的邮件地址."},	
					"date":{
                         "regex":"/^(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)$/",
                         "alertText":"* 请输入有效的日期,如:2008-08-08."},
					"ip":{
                         "regex":"/^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/",
                         "alertText":"* 请输入有效的IP."},
					"chinese":{
						"regex":"/^[^|]+$/",
						"alertText":"* 只能输入中文、字母、数字、()、（）."},
					"cornermarkimage":{
						"regex":" /(\.(jpg|gif|jpeg|bmp|png))$/",
					    "alertText":"* 请输入正确的图片文件."},
					"url":{
						"regex":"/^[a-zA-Z]:\\/\\/[^s]$/",
						"alertText":"* 请输入有效的网址."},
				    "BannerUrl":{
							"regex":"/^[a-zA-Z]+:\/\/.+$/",
							"alertText":"* 请输入有效的网址."},
					"zipcode":{
						"regex":"/^[1-9]\\d{5}$/",
						"alertText":"* 请输入有效的邮政编码."},
					"qq":{
						"regex":"/^[1-9]\\d{4,9}$/",
						"alertText":"* 请输入有效的QQ号码."},
					"onlyNumber":{
						"regex":"/^[0-9]+$/",
						"alertText":"* 请输入数字."},
					"onlyNumberWide":{
						"regex":"/^[-]?\\d+(\\.\\d{1,4})?$/",
						"alertText":"* 请输入整数或小数（正负均可）."},
					"onlyDecimal":{
						"regex":"/^[-]?\\d+(\\.\\d{1,4})$/",
						"alertText":"* 请输入4位以内的小数（正负均可）."},
					"illegalLetter":{
						"regex":"/^[^\`\{\}\[!\(\+~@#%\^&\*\)\|\\\\:;\'\"><\?/=]+$/",
						"alertText":"* 含有非法字符,请检查."},
					"chineseDesc":{
						"regex":"/^[^|]+$/",
						"alertText":"* 含有非法字符,请检查."},
					"chineseSpace":{
						"regex":"/^[^|]+$/",
						"alertText":"* 含有非法字符,请检查."},	
					"onlyNumberBut":{
							"regex":"/^[0-9]+$/",
							"alertText":"* 请输入数字."},
					"onlyLetter":{
						"regex":"/^[a-zA-Z]+$/",
						"alertText":"* 请输入英文字母."},
					"noSpecialCaracters":{
						"regex":"/^[^\u4E00-\u9FA5]+$/",
						"alertText":"* 请输入字母、数字、符号"},	
					"noSpecialCaractersAndNone":{
						"regex":"/^[^\u4E00-\u9FA5]+$/",
						"alertText":"* 请输入字母、数字、符号"},	
						"noSpecialCaractersAndNoSpace":{
							"regex":"/^[a-zA-Z0-9_]+$/",
							"alertText":"* 请输入英文字母或下划线."},							
					"ajaxUser":{
						"file":"validate.action",
						"alertTextOk":"* 帐号可以使用.",	
						"alertTextLoad":"* 检查中, 请稍后...",
						"alertText":"* 帐号不能使用."},	
					"ajaxName":{
						"file":"validateUser.php",
						"alertText":"* This name is already taken",
						"alertTextOk":"* This name is available",	
						"alertTextLoad":"* Loading, please wait"},						
					"custom1":{
    					"nname":"customFunc1",
    					"alertText":"* 必须输入123"},
					"desccnIsRepeat":{
						"nname":"checkDesccnIsRepeat",
						"alertText":"* 分类中文名称已存在."},	 
					"descenIsRepeat":{
						"nname":"checkDescenIsRepeat",
						"alertText":"* 分类英文名称已存在."},	 
					"susIsRepeat":{
    					"nname":"checksusIsRepeat",
    					"alertText":"* susId已存在."},
					"IMenucnIsRepeat":{
    					"nname":"checkIMenucnIsRepeat",
    					"alertText":"* 键位中文名称已存在."},	 
					"IMenuenIsRepeat":{
    					"nname":"checkIMenuenIsRepeat",
    					"alertText":"* 键位英文名称已存在."},
					"halfSusIdIsRepeat":{
    					"nname":"checkHalfsusIdIsRepeat",
    					"alertText":"* 原价susId不存在."},
					"blSusIdIsRepeat":{
    					"nname":"checkBLsusIdIsRepeat",
					    "alertText":"* 原价susId不存在."},
					"noSpace":{
						"regex":"/^[^|]+$/",
						"alertText":"* 请输入版本号"},
					"noChinese":{
						"regex":"/^[^\u4E00-\u9FA5]+$/",
						"alertText":"* 请输入字母、数字、符号"},
					"onlyTime":{
						"regex":"/^(([0-1]*[0-9])|(2[0-3])):[0-5][0-9]$/",
						"alertText":"* 请输入有效的小时分钟格式HH:mm(如:23:59)."}
					}	
		}
	}
})(jQuery);

$(document).ready(function() {	
	$.validationEngineLanguage.newLang()
});