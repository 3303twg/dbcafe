//captcha
function refresh_captcha(){
	document.getElementById("capt_img").src="/manager/common/captcha.php?waste="+Math.random(); 
}
//영문+숫자
function onlyNumberEng(obj) {
	var val = obj.value;
	var pattern = /[ㄱ-힣~!@#$%^&*()_+|<>?:{}= ]/g;
	if(pattern.test(val)){
		obj.value = val.replace(pattern,"");
	}
};
//한글만제거
function removeKorean(obj) {
	var val = obj.value;
	var pattern = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g;
	if(pattern.test(val)){
		obj.value = val.replace(pattern,"");
	}
};

//특수문자
function specialCharRemove(obj) {
	var val = obj.value;
	var pattern = /[^(가-힣ㄱ-ㅎㅏ-ㅣa-zA-Z0-9)]/gi;
	if(pattern.test(val)){
		obj.value = val.replace(pattern,"");
	}
};

//한글
function onlyKorean(form_name) {
	for (var i = 0; i < form_name.value.length; i++) {
		var chr = form_name.value.charAt(i);
		if (chr.charCodeAt() < '129') {
			form_name.focus();
			form_name.value = "";
		}
	}
};
//숫자
function onlyNumber(event) {
	event = event || window.event;
	var keyID = (event.which)
		? event.which
		: event.keyCode;
	if ((keyID >= 48 && keyID <= 57) || (keyID >= 96 && keyID <= 105) || keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39) 
		return;
	 else 
		return false;
};
//숫자2
function removeChar(event) {
	event = event || window.event;
	var keyID = (event.which)
		? event.which
		: event.keyCode;
	if (keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39) 
		return;
	 else 
		event.target.value = event
			.target
			.value
			.replace(/[^0-9]/g, "");
};


//이메일 유효성 검사
function email_check( email ) {    
	var regex=/([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
	return (email != '' && email != 'undefined' && regex.test(email)); 
};
//전화번호 11자리가 인푸트 1개로 표현된 페이지에 유효성 검사
$(function() {
	$(".tel_check").on("keyup", function() {
		var value = $(this).val(),
		addValue = [];
		value = value.replace(/-/gi, '');
		if (value.length >= 3) { 
			if (value.substring(0, 2) == '02') { // 서울 번호를 체크하기 위한 조건
				addValue.push(value.substring(0, 2)); 
				if (value.length >= 3) { 
					var endKey = (value.length >= 10 ? 6 : 5); // 00-000-000, 00-0000-0000 처리 
					addValue.push(value.substring(2, endKey)); 
					if (value.length >= 6) { 
						if (value.length >= 10) { // 10자리 이상 입력 방지
							value = value.substring(0, 10); 
						}
						addValue.push(value.substring(endKey, value.length)); 
					}
				}
			} else { 
				addValue.push(value.substring(0, 3)); 
				if (value.length >= 4) { 
					var endKey = (value.length >= 11 ? 7 : 6); // 000-000-0000, 000-0000-0000 처리 
					addValue.push(value.substring(3, endKey)); 
					if (value.length >= 7) { 
						if (value.length >= 11) {
							value = value.substring(0, 11); 
						}
						addValue.push(value.substring(endKey, value.length)); 
					}
				}
			}
			$(this).val(addValue.join("-")); 
		}
	});
});
//바이트
$(function () {
	$(".input_limit_byte").each(function () {
		// count 정보 및 count 정보와 관련된 textarea/input 요소를 찾아내서 변수에 저장한다.
		var $maxcount = $(this).find(".maxcount", this);
		var $count = $(this).find(".count", this);
		var $title = $(this).parents(".cont_text").find(".count_input");

		// .text()가 문자열을 반환하기에 이 문자를 숫자로 만들기 위해 1을 곱한다.
		var maximumByte = $maxcount.text() * 1;
		// update 함수는 keyup, paste, input 이벤트에서 호출한다.
		var update = function () {
			var before = $count.text() * 1;
			var str_len = $title.val().length;
			var cbyte = 0;
			var li_len = 0;
			for (i = 0; i < str_len; i++) {
				var ls_one_char = $title.val().charAt(i);
				if (escape(ls_one_char).length > 4) {
					cbyte += 3; //한글이면 2를 더한다
				} else {
					cbyte++; //한글아니면 1을 다한다
				}
				if (cbyte <= maximumByte) {
					li_len = i + 1;
				}
			}
			// 사용자가 입력한 값이 제한 값을 초과하는지를 검사한다.
			if (parseInt(cbyte) > parseInt(maximumByte)) {
				var str = $title.val();
				var str2 = $title.val().substr(0, li_len);
				$title.val(str2);
				var cbyte = 0;
				for (i = 0; i < $title.val().length; i++) {
					var ls_one_char = $title.val().charAt(i);
					if (escape(ls_one_char).length > 4) {
						cbyte += 2; //한글이면 2를 더한다
					} else {
						cbyte++; //한글아니면 1을 다한다
					}
				}
			}
			$count.text(cbyte);
		};
		// input, keyup, paste 이벤트와 update 함수를 바인드한다
		$title.bind("input keyup keydown paste change", function () {
			setTimeout(update, 0)
		});
		update();
	});
});

function head_menu(){
	var path = location.pathname;
	path = path.split("/")[1];
	path = path.toLowerCase();

	if ( path == "about" ){ //내정보
		$(".head_wrap .head_menu .w1280 > ul > li").eq(0).addClass("check");
	} else if ( path == "mshaped" ) {
		$(".head_wrap .head_menu .w1280 > ul > li").eq(1).addClass("check");
	} else if ( path == "male" ) {
		$(".head_wrap .head_menu .w1280 > ul > li").eq(2).addClass("check");
	} else if ( path == "female" ) {
		$(".head_wrap .head_menu .w1280 > ul > li").eq(3).addClass("check");
	} else if ( path == "circular" ) {
		$(".head_wrap .head_menu .w1280 > ul > li").eq(4).addClass("check");
	} else if ( path == "beauty" ) {
		$(".head_wrap .head_menu .w1280 > ul > li").eq(5).addClass("check");
	} else if ( path == "seborrheic" ) {
		$(".head_wrap .head_menu .w1280 > ul > li").eq(6).addClass("check");
	} else if ( path == "oriental" ) {
		$(".head_wrap .head_menu .w1280 > ul > li").eq(7).addClass("check");
	} else if ( path == "community" ) {
		$(".head_wrap .head_menu .w1280 > ul > li").eq(8).addClass("check");
	}
}

/* input comma */
	function inputNumberFormat(obj) {
		obj.value = comma(uncomma(obj.value));
	}
	function comma(str) {
		str = String(str);
		return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
	}
	function uncomma(str) {
		str = String(str);
		return str.replace(/[^\d]+/g, '');
	}
	Number.prototype.format = function(){
		if(this==0) return 0;
		var reg = /(^[+-]?\d+)(\d{3})/;
		var n = (this + '');
		while (reg.test(n)) n = n.replace(reg, '$1' + ',' + '$2');
		return n;
	};
	String.prototype.format = function(){
		var num = parseFloat(this);
		if( isNaN(num) ) return "0";
		return num.format();
	};
/* text comma */
$(document).ready(function(){
	$(".comma").text(function() {
		$(this).text(
			$(this).text().format()
		);
	});
	$(".comma").each(function() {
		$(this).val($(this).val().toString().replace(/,/g, "").replace(/\B(?=(\d{3})+(?!\d))/g, ","));
	});
	$(".comma").on("keyup",function(){
		inputNumberFormat(this);
	});
	head_menu();
});
$(document).on("click",".modal_open",function(){
	if ( $(this).is(".modal_open_policy1") ) {
		$.ajax({
			url: "/common/modal/policy1.php",
			type: "GET",
			success:function(data){
				$(".modal-dialog").addClass("modal-dialog_720");
				$(".modal .modal-content").html(data);
				$(".modal").modal();
			}
		});
	};
	if ( $(this).is(".modal_open_policy2") ) {
		$.ajax({
			url: "/common/modal/policy2.php",
			type: "GET",
			success:function(data){
				$(".modal-dialog").addClass("modal-dialog_720");
				$(".modal .modal-content").html(data);
				$(".modal").modal();
			}
		});
	};
	if ( $(this).is(".modal_open_map") ) {
		var store_idx = $(this).data("store-idx");
		$.ajax({
			url: "/common/modal/store_detail.php?store_idx="+store_idx,
			type: "GET",
			success:function(data){
				$(".modal-dialog").addClass("modal-dialog_720");
				$(".modal .modal-content").html(data);
				$(".modal").modal();
			}
		});
	};
	if ( $(this).is(".modal_open_store") ) {
		var store_idx = $(this).data("store-idx");
		$.ajax({
			url: "store.php?store_idx="+store_idx,
			type: "GET",
			success:function(data){
				$(".modal-dialog").addClass("modal-dialog_480");
				$(".modal .modal-content").html(data);
				$(".modal").modal();
			}
		});
	};
});
$(document).on("click",".inner_modal_open",function(){
	$(this).siblings(".inner_modal").show(0);
});
$(document).on("click",".inner_modal .inner_modal_title .close_wrap",function(){
	$(this).parents(".inner_modal").hide(0);
});

//파일관련 시작
$(document).on("change",".filebox_wrap .filebox_input input",function(){
	if ( $(this).parents(".filebox_input").is(".filebox_type_img") ) {
		var file_type=$(this).val().split(".").pop().toLowerCase();
		if ($.inArray(file_type, [ "jpg","jpeg","png" ])==-1){
			alert("jpg, png파일만 업로드 가능합니다.");
			$(this).parents(".filebox_wrap").find(".filebox_name input").val("");
			$(this).parents(".filebox_wrap").find(".filebox_input input").val("");
			$(this).parents(".filebox_wrap").find(".filebox_img img").attr("src", "");
			return;
		}
	} else if ( $(this).parents(".filebox_input").is(".filebox_type_date") ) {
		var file_type=$(this).val().split(".").pop().toLowerCase();
		if ($.inArray(file_type, [ "jpg","jpeg","png","pdf" ])==-1){
			alert("jpg, jpeg, png, pdf파일만 업로드 가능합니다.");
			$(this).parents(".filebox_wrap").find(".filebox_name input").val("");
			$(this).parents(".filebox_wrap").find(".filebox_input input").val("");
			$(this).parents(".filebox_wrap").find(".filebox_img img").attr("src", "");
			return;
		}
	} else if ( $(this).parents(".filebox_input").is(".filebox_type_custom") ) {
		var file_type=$(this).val().split(".").pop().toLowerCase();
		if ($.inArray(file_type, ["jpg", "png", "pdf","doc","docx","ppt","pptx","hwp","gul","txt","xlsx","xls" ])==-1){
			alert("jpg, png, pdf, doc, docx, ppt, pptx, hwp, gul, txt, xlsx, xls파일만 업로드 가능합니다.");
			$(this).parents(".filebox_wrap").find(".filebox_name input").val("");
			$(this).parents(".filebox_wrap").find(".filebox_input input").val("");
			$(this).parents(".filebox_wrap").find(".filebox_img img").attr("src", "");
			return;
		}
	} else if ( $(this).parents(".filebox_input").is(".filebox_type_recruit") ) {
		var file_type=$(this).val().split(".").pop().toLowerCase();
		if ($.inArray(file_type, ["doc", "docx", "hwp","pdf" ])==-1){
			alert("doc, docx, hwp, pdf파일만 업로드 가능합니다.");
			$(this).parents(".filebox_wrap").find(".filebox_name input").val("");
			$(this).parents(".filebox_wrap").find(".filebox_input input").val("");
			$(this).parents(".filebox_wrap").find(".filebox_img img").attr("src", "");
			return;
		}
	};
	if(window.FileReader) {
		var filename = $(this)[0].files[0].name;
		var parent = $(this).parent();
		var reader = new FileReader();
		reader.onload = function(e) {
			var src = e.target.result;
			parent.parents(".filebox_wrap").find(".filebox_img img").attr("src", src);
		}
		reader.readAsDataURL($(this)[0].files[0]);
	} else {
		var filename = $(this).val().split("/").pop().split("\\").pop();
	};
	$(this).parents(".filebox_wrap").find(".filebox_name input").val(filename);
});
$(document).on("click",".file_reset",function(){
	$(this).parents(".filebox_wrap").find(".filebox_name input").val("");
	$(this).parents(".filebox_wrap").find(".filebox_input input").val("");
	if ( $(this).parents(".filebox_wrap").find(".filebox_img") ) {
		$(this).parents(".filebox_wrap").find(".filebox_img img").attr("src", "");
	};
});
//파일관련 끝
$(document).on("change",".email_select select",function() {
	if ( $(this).val() == "" ){
		$(".email_input input").val("");
		$(".email_input input").attr("readonly",false);
		$(".email_input input").focus();
	} else {
		$(".email_input input").val( $(this).val() );
		$(".email_input input").attr("readonly",true);
	}
});
function addpostCode() {
	new daum.Postcode({
		oncomplete: function(data) {

			var fullAddr = ''; // 최종 주소 변수
			var extraAddr = ''; // 조합형 주소 변수

			if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
				fullAddr = data.roadAddress;

			} else { // 사용자가 지번 주소를 선택했을 경우(J)
				fullAddr = data.jibunAddress;
			}

			// 사용자가 선택한 주소가 도로명 타입일때 조합한다.
			if(data.userSelectedType === 'R'){
				//법정동명이 있을 경우 추가한다.
				if(data.bname !== ''){
					extraAddr += data.bname;
				}
				// 건물명이 있을 경우 추가한다.
				if(data.buildingName !== ''){
					extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
				}
				// 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
				fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
			}

			// 우편번호와 주소 정보를 해당 필드에 넣는다.
			document.getElementById("member_zip").value = data.zonecode; //5자리 새우편번호 사용
			document.getElementById("member_address1").value = fullAddr;

			// 커서를 상세주소 필드로 이동한다.
			document.getElementById("member_address2").focus();
		}
	}).open();
};
function go_top() {
	$("html, body").stop().animate({
		scrollTop: $("body").offset().top
	});
};